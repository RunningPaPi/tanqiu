package com.artqiyi.tanqiu.game.impl;

import com.alibaba.fastjson.JSON;
import com.artqiyi.tanqiu.common.constant.*;
import com.artqiyi.tanqiu.common.socket.SocketConstant;
import com.artqiyi.tanqiu.common.socket.SocketResponseMsg;
import com.artqiyi.tanqiu.common.util.*;
import com.artqiyi.tanqiu.game.*;
import com.artqiyi.tanqiu.game.domain.*;
import com.artqiyi.tanqiu.game.vo.*;
import com.artqiyi.tanqiu.payment.domain.CoinTranslog;
import com.artqiyi.tanqiu.payment.service.TransLogService;
import com.artqiyi.tanqiu.redis.RedisClient;
import com.artqiyi.tanqiu.redis.RedisLock;
import com.artqiyi.tanqiu.user.domain.User;
import com.artqiyi.tanqiu.user.domain.UserInfo;
import com.artqiyi.tanqiu.user.service.IUserInfoService;
import com.artqiyi.tanqiu.user.service.IUserService;
import com.artqiyi.tanqiu.websocket.service.WebSocketHandlerService;
import com.artqiyi.tanqiu.websocket.service.WebSocketSessionService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

/**
 * 弹球闯关游戏模式服务层
 */
@Service("gameBreakService")
public class GameBreakServiceImpl implements IGameBreakService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private WebSocketHandlerService webSocketHandlerService;
    @Autowired
    private IGameBreakRecoverCostService gameBreakRecoverCostService;
    @Autowired
    private IGameBreakRecoverRecordsService gameBreakRecoverRecordsService;
    @Autowired
    private TransLogService transLogService;
    @Autowired
    private IGameBreakUserQualifyService gameBreakUserQualifyService;
    @Autowired
    private IGameBreakAgainstRecordsService gameBreakAgainstRecordsService;
    @Autowired
    private IGameJobService gameJobService;
    @Autowired
    private IGameBreakRecordsService gameBreakRecordsService;
    @Autowired
    private RedisLock redisLock;
    @Autowired
    private IGameModelService gameModelService;
    @Autowired
    private IGameConfigService gameConfigService;
    @Autowired
    private IGameBreakUserRecordsService breakGameUserRecordsService;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private IGameRedpacketRecordsService gameRedpacketRecordsService;
    @Autowired
    private IRobotVideoScoreConfigService robotVideoScoreConfigService;

    /**
     * 开始游戏
     *
     * @param userId 用户ID
     * @param gameId 游戏ID
     */
    @Override
    public void startGame(long userId, long gameId, String gameNo) {
        redisClient.hDel(RedisFiledConstant.BREAK_GAME_WAIT_PLAYER, String.valueOf(userId));
        //用户比赛记录放进缓存
        GameBreakUserRecordsVo breakGameUserRecordsVo = new GameBreakUserRecordsVo();
        breakGameUserRecordsVo.setCreateTime(new Date());
        breakGameUserRecordsVo.setUpdateTime(new Date());
        breakGameUserRecordsVo.setGameId(gameId);
        breakGameUserRecordsVo.setPass(false);
        breakGameUserRecordsVo.setPassMaxLevel((short) 0);
        breakGameUserRecordsVo.setData(null);
        breakGameUserRecordsVo.setUserId(userId);
        breakGameUserRecordsVo.setIslive(true);
        breakGameUserRecordsVo.setRecoveryTimes(0);
        breakGameUserRecordsVo.setScore(0);
        User user = userService.selectById(userId);

        if (user != null) {
            UserInfo userInfo = userInfoService.selectByUserId(userId);
            breakGameUserRecordsVo.setHeadUrl(userInfo.getHeadPicUrl());
            breakGameUserRecordsVo.setNickName(user.getNickName());
            breakGameUserRecordsVo.setGender(userInfo.getGender());
        }
        //匹配对手
        match(breakGameUserRecordsVo);
    }

    /**
     * 继续闯关
     *
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @param gameNo 游戏赛场编码
     */
    @Override
    public void continueGame(long userId, long gameId, String gameNo) {
        GameBreakUserRecordsVo gameBreakUserRecordsVo = redisClient.hGet(RedisFiledConstant.BREAK_GAME_WAIT_PLAYER, String.valueOf(userId), GameBreakUserRecordsVo.class);//获取当前用户信息
        if (gameBreakUserRecordsVo == null) {
            logger.error("【游戏异常】该游戏当前赛场等待继续闯关用户游戏记录不存在！gameNo={},againstId={}", gameNo, userId);
            sendMessage(null, gameNo, userId, SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_END);//本赛场已结束
            return;
        }
        //更新当前用户信息
        logger.info("【继续游戏】 gameBreakUserRecordsVo={}", JSONUtil.toJson(gameBreakUserRecordsVo));
        if (gameBreakUserRecordsVo == null || !gameBreakUserRecordsVo.isIslive()) {
            logger.error("【游戏异常】该游戏当前赛场等待继续闯关用户游戏记录暂未复活！gameNo={},againstId={}", gameNo, userId);
            return;
        }
        //匹配对手
        match(gameBreakUserRecordsVo);

    }

    /**
     * 游戏复活
     *
     * @param userId 用户ID
     * @param gameNo 游戏编码
     * @param round  当前关卡
     */
    @Override
    public Map<String, Object> recover(long userId, long gameId, String gameNo, int round) {
        GameBreakUserRecordsVo breakGameUserRecordsVo = redisClient.hGet(RedisFiledConstant.BREAK_GAME_WAIT_PLAYER, String.valueOf(userId), GameBreakUserRecordsVo.class);//获取当前用户信息

        if (breakGameUserRecordsVo == null) {
            logger.error("【游戏异常】该游戏当前赛场等待继续闯关用户游戏记录不存在！gameNo={},againstId={}", gameNo, userId);
            return null;
        }
        round = breakGameUserRecordsVo.getPassMaxLevel() + 1;
        Map<String, Object> result = new HashMap<>();//封装返回结果
        boolean isRecoverSuccess = true;
        //获取user和user_info
        User user = userService.selectById(userId);
        UserInfo userInfo = userInfoService.selectByUserId(userId);
        //用户复活记录
        GameBreakRecoverRecords breakGameRecoverRecords = new GameBreakRecoverRecords();
        breakGameRecoverRecords.setUserId(userId);
        breakGameRecoverRecords.setGameModelId(gameId);
        breakGameRecoverRecords.setGameNo(gameNo);
        breakGameRecoverRecords.setCreateTime(new Date());
        breakGameRecoverRecords.setGameRound(round);
        breakGameRecoverRecords.setCostNum(0);
        //用户账户流水记录
        CoinTranslog translog = new CoinTranslog();
        translog.setUserId(userId);
        translog.setCreateTime(new Date());
        translog.setUpdateTime(new Date());
        translog.setTransTime(new Date());
        translog.setUserName(user.getNickName());
        translog.setTransFlag(SystemConstant.TRANS_DIRECT_OUTCOME);
        translog.setTransType(SystemConstant.TRANS_TYPE_BREAK_GAME_RECOVER);
        translog.setAccountType(SystemConstant.ACC_TYPE_GOLDCOIN);
        translog.setRemark("弹球闯关复活");
        //游戏复活费用配置
        GameBreakRecoverCostExample breakGameRecoverCostExample = new GameBreakRecoverCostExample();
        breakGameRecoverCostExample.or().andGameModelIdEqualTo(gameId).andGameRoundEqualTo(round);
        List<GameBreakRecoverCost> breakGameRecoverCostList = gameBreakRecoverCostService.selectByExample(breakGameRecoverCostExample);
        if (null != breakGameRecoverCostList && breakGameRecoverCostList.size() > 0) {
            GameBreakRecoverCost breakGameRecoverCost = breakGameRecoverCostList.get(0);
            int cost = breakGameRecoverCost.getCostNum();
            int costTypeCode = breakGameRecoverCost.getCostType();
            Integer coin = userInfo.getCoin();
            Integer point = userInfo.getPoint();
            Long balance = userInfo.getBalance();
            //复活记录费用信息设置
            breakGameRecoverRecords.setCostNum(cost);
            translog.setTransAmount(cost);
            breakGameRecoverRecords.setCostType((short) costTypeCode);
            result.put("recoverCost", cost);
            switch (costTypeCode) {
                case GameConstants.BREAK_GAME_RECOVER_COST_TYPE_COIN: //趣币
                    if (coin.compareTo(cost) >= 0) {
                        userInfo.setCoin(coin - cost);
                        translog.setAccountType(SystemConstant.ACC_TYPE_GOLDCOIN);
                        translog.setBalance(coin - cost);
                    } else {
                        isRecoverSuccess = false;
                        result.put("isRecoverSuccess", false);
                        result.put("failReason", 1);
                        result.put("message", MsgConstant.FAIL_SIGN_UP_COIN);
                    }
                    break;
                case GameConstants.BREAK_GAME_RECOVER_COST_TYPE_POINT: //积分
                    if (point.compareTo(cost) >= 0) {
                        userInfo.setPoint(point - cost);
                        translog.setAccountType(SystemConstant.ACC_TYPE_SCORE);
                        translog.setBalance(point - cost);
                    } else {
                        isRecoverSuccess = false;
                        result.put("isRecoverSuccess", false);
                        result.put("failReason", 2);
                        result.put("message", MsgConstant.FAIL_SIGN_UP_POINT);
                    }
                    break;
                case GameConstants.BREAK_GAME_RECOVER_COST_TYPE_BALANCE: //红包
                    if (balance.compareTo(Long.valueOf(cost)) >= 0) {
                        userInfo.setBalance(balance - cost);
                        translog.setAccountType(SystemConstant.ACC_TYPE_BALANCE);
                        translog.setBalance((int) (balance - cost));
                    } else {
                        isRecoverSuccess = false;
                        result.put("isRecoverSuccess", false);
                        result.put("failReason", 3);
                        result.put("message", MsgConstant.FAIL_SIGN_UP_BALANCE);
                    }
                    break;
                default:
                    break;
            }
        }
        //复活成功
        if (isRecoverSuccess) {
            result.put("isRecoverSuccess", true);
            result.put("message", MsgConstant.SUCCES_RECOVER);
            HashMap<String, Object> data = new HashMap<>();
            data.put("user", MapConventUtil.obj2Map(user));
            data.put("userInfo", MapConventUtil.obj2Map(userInfo));
            redisClient.hSet(RedisFiledConstant.FILED_USER, user.getToken(), data);//保存用户信息至redis
            userInfoService.saveOrUpdate(userInfo);//更新至数据库

            //设置已复活状态
            breakGameUserRecordsVo.setIslive(true);
            //移至redis等待继续闯关的玩家
            redisClient.hSet(RedisFiledConstant.BREAK_GAME_WAIT_PLAYER, String.valueOf(userId), breakGameUserRecordsVo);

            //保存复活记录
            gameBreakRecoverRecordsService.saveOrUpdate(breakGameRecoverRecords);
            //保存账户流水信息
            transLogService.addTranslog(translog);
        } else {
            result.put("isRecoverSuccess", false);
            if (!result.containsKey("failReason")) {
                result.put("failReason", 0);
            }
            result.put("message", MsgConstant.FAIL_RECOVER);
        }
        //推送消息给用户
        sendMessage(result, gameNo, userId, SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_RECOVER);
        return result;
    }

    /**
     * 用户认输放弃比赛
     *
     * @param userId
     * @param againstId
     * @param gameNo
     */
    @Override
    public void giveUp(long userId, long againstId, String gameNo) {
        GameBreakUserRecordsVo gameBreakUserRecordsVo = redisClient.hGet(gameNo, String.valueOf(userId), GameBreakUserRecordsVo.class);//获取当前用户信息
        if (gameBreakUserRecordsVo == null) {
            logger.info("【游戏异常】该游戏当前赛场用户游戏记录不存在！gameNo={},againstId={}", gameNo, userId);
            return;
        }
        GameBreakUserRecordsVo againstPlayerRecordsVo = redisClient.hGet(gameNo, String.valueOf(againstId), GameBreakUserRecordsVo.class);//获取当前用户信息
        if (againstPlayerRecordsVo == null) {
            logger.info("【游戏异常】该游戏当前赛场用户对手游戏记录不存在！gameNo={},againstId={}", gameNo, againstId);
            return;
        }
        win(againstPlayerRecordsVo, gameBreakUserRecordsVo, SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_GIVE_UP);
    }

    /**
     * 玩家死亡，提前结束比赛
     *
     * @param userId
     * @param againstId
     * @param gameNo
     */
    @Override
    public void death(long userId, long againstId, String gameNo) {
        GameBreakUserRecordsVo breakGameUserRecordsVo = redisClient.hGet(gameNo, String.valueOf(userId), GameBreakUserRecordsVo.class);//获取当前用户信息
        if (breakGameUserRecordsVo == null) {
            logger.info("【游戏异常】该游戏当前赛场用户游戏记录不存在！gameNo={},againstId={}", gameNo, userId);
            return;
        }
        GameBreakUserRecordsVo againstPlayerRecordsVo = redisClient.hGet(gameNo, String.valueOf(againstId), GameBreakUserRecordsVo.class);//获取当前用户信息
        if (againstPlayerRecordsVo == null) {
            logger.info("【游戏异常】该游戏当前赛场用户对手游戏记录不存在！gameNo={},againstId={}", gameNo, againstId);
            return;
        }
        win(againstPlayerRecordsVo, breakGameUserRecordsVo, SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_DEATH);
    }

    @Override
    public synchronized void gameOver(long userId, long againstId, String gameNo) {
        GameBreakUserRecordsVo breakGameUserRecordsVo = redisClient.hGet(gameNo, String.valueOf(userId), GameBreakUserRecordsVo.class);//获取当前用户信息
        if (breakGameUserRecordsVo == null) {
            logger.info("【游戏异常】该游戏当前赛场用户游戏记录不存在！gameNo={},againstId={}", gameNo, userId);
            return;
        }
        GameBreakUserRecordsVo againstPlayerRecordsVo = redisClient.hGet(gameNo, String.valueOf(againstId), GameBreakUserRecordsVo.class);//获取当前用户信息
        if (againstPlayerRecordsVo == null) {
            logger.info("【游戏异常】该游戏当前赛场用户对手游戏记录不存在！gameNo={},againstId={}", gameNo, againstId);
            return;
        }
        if (breakGameUserRecordsVo.getScore() > againstPlayerRecordsVo.getScore()) {
            win(breakGameUserRecordsVo, againstPlayerRecordsVo, SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_OVER);
        } else if (breakGameUserRecordsVo.getScore() == againstPlayerRecordsVo.getScore()) {
            if (breakGameUserRecordsVo.getUpdateTime().getTime() < againstPlayerRecordsVo.getUpdateTime().getTime()) {
                win(breakGameUserRecordsVo, againstPlayerRecordsVo, SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_OVER);
            } else {
                win(againstPlayerRecordsVo, breakGameUserRecordsVo, SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_OVER);
            }
        } else {
            win(againstPlayerRecordsVo, breakGameUserRecordsVo, SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_OVER);
        }
    }

    /**
     * 表情
     *
     * @param userId    用户id
     * @param againstId 对手id
     * @param gameNo    赛场编码
     * @param data      表情数据
     */
    @Override
    public void emoticon(long userId, long againstId, String gameNo, String data) {
        sendMessage(data, gameNo, againstId, SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_EMO);
    }

    /**
     * 比赛数据传输
     *
     * @param userId
     * @param againstId
     * @param gameNo
     * @param score
     * @param data
     */
    @Override
    public void gameData(long userId, long againstId, String gameNo, int score, String data) {
        GameBreakUserRecordsVo gameBreakUserRecordsVo = redisClient.hGet(gameNo, String.valueOf(userId), GameBreakUserRecordsVo.class);//获取当前用户信息

        if (gameBreakUserRecordsVo == null) {
            logger.info("【游戏异常】该游戏当前赛场用户游戏记录不存在！gameNo={},againstId={}", gameNo, userId);
            return;
        }
        GameBreakUserRecordsVo againstUserRecord = redisClient.hGet(gameNo, String.valueOf(againstId), GameBreakUserRecordsVo.class);//获取当前用户信息
        if (againstUserRecord == null) {
            logger.info("【游戏异常】该游戏当前赛场用户对手游戏记录不存在！gameNo={},againstId={}", gameNo, againstId);
            return;
        }
        if (againstUserRecord.isRobot() && data != null){
            Map datas = JSON.parseObject(data, HashMap.class);
            againstUserRecord.setScore((Integer) datas.get("rscore"));
            redisClient.hSet(gameNo, String.valueOf(againstId), againstUserRecord);
        }
        gameBreakUserRecordsVo.setScore(score);
        gameBreakUserRecordsVo.setUpdateTime(new Date());
        redisClient.hSet(gameNo.trim(), String.valueOf(userId).trim(), gameBreakUserRecordsVo);
        //推送消息给对手
        if (!againstUserRecord.isRobot()) {
            GameDataVo gameDataVo = new GameDataVo();
            gameDataVo.setAgainstScore(score);
            gameDataVo.setAgainstGameData(data);
            sendMessage(gameDataVo, gameNo, againstId, SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_DATA);
        }
    }

    /**
     * 赢得单轮pk
     *
     * @param userRecordsVo    赢家玩家记录
     * @param againstRecordsVo 对手玩家记录
     * @param resultCode       结果编码
     */
    @Override
    public void win(GameBreakUserRecordsVo userRecordsVo, GameBreakUserRecordsVo againstRecordsVo, String resultCode) {
        //移除任务
        deleteTask(userRecordsVo.getUserId(), userRecordsVo.getAgainstId(), userRecordsVo.getGameNo());
        //更新在线人数
        GameBreakRecords gameBreakRecords = redisClient.hGet(RedisFiledConstant.BREAK_GAME_RECORD, String.valueOf(userRecordsVo.getGameId()), GameBreakRecords.class);//获取当前赛场信息
        if (gameBreakRecords == null) {
            logger.error("【游戏异常】该游戏当前赛场记录不存在！gameId={}", userRecordsVo.getGameId());
            return;
        }

        if (againstRecordsVo == null) {
            logger.info("【机器人】 对战机器人,userId={}", userRecordsVo.getUserId());
            return;
        }
        boolean isDeath = false;//是否一方玩家死亡，比赛提前结束
        if (resultCode.equals(SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_DEATH)) {
            isDeath = true;
        }

        int round = userRecordsVo.getPassMaxLevel();//当前关卡数
        int lastRound = round + 1;
        int maxRound = gameBreakRecords.getTotalRound();//最终关卡数
        boolean isPass = false;
        //通关
        if (lastRound >= maxRound) {
            isPass = true;
        }

        userRecordsVo.setPassMaxLevel((short) lastRound);
        userRecordsVo.setUpdateTime(new Date());
        userRecordsVo.setWin(true);
        userRecordsVo.setIslive(true);

        if (!userRecordsVo.isRobot()) {
            //当前用户推送胜利消息
            PkResultVo userPkResultVo = new PkResultVo();
            userPkResultVo.setScore(userRecordsVo.getScore());
            userPkResultVo.setAgainstScore(againstRecordsVo.getScore());
            userPkResultVo.setCurrentRound(lastRound);
            userPkResultVo.setCalledGame(isDeath);
            if (isPass) {
                userPkResultVo.setPass(true);
            } else {
                userPkResultVo.setPass(false);
            }
            userPkResultVo.setWin(true);
            sendMessage(userPkResultVo, userRecordsVo.getGameNo(), userRecordsVo.getUserId(), resultCode);
        }


        if (!againstRecordsVo.isRobot()) {
            //对手用户推送淘汰消息
            PkResultVo againstPkResultVo = new PkResultVo();
            againstPkResultVo.setCurrentRound(againstRecordsVo.getPassMaxLevel() + (short) 1);
            againstPkResultVo.setPass(false);
            againstPkResultVo.setWin(false);
            againstPkResultVo.setScore(againstRecordsVo.getScore());
            againstPkResultVo.setAgainstScore(userRecordsVo.getScore());
            againstPkResultVo.setCalledGame(isDeath);
            sendMessage(againstPkResultVo, againstRecordsVo.getGameNo(), againstRecordsVo.getUserId(), resultCode);
        }

        //如果是通关则当前人数减2，通关人次加1；否则当前人数减1
        if (isPass) {
            gameBreakRecords.setContestNum(gameBreakRecords.getContestNum() - 2);
            gameBreakRecords.setPassThroughNum(gameBreakRecords.getPassThroughNum() + 1);
        } else {
            gameBreakRecords.setContestNum(gameBreakRecords.getContestNum() - 1);
        }
        gameBreakRecords.setUpdateTime(new Date());
        redisClient.hSet(RedisFiledConstant.BREAK_GAME_RECORD, String.valueOf(userRecordsVo.getGameId()), gameBreakRecords);

        //淘汰对手比赛信息保存至等待复活(如果当前是第1关则不需要)
        if (!againstRecordsVo.isRobot() && againstRecordsVo.getPassMaxLevel() > 0) {
            againstRecordsVo.setWin(false);
            againstRecordsVo.setIslive(false);
            againstRecordsVo.setData(null);
            againstRecordsVo.setAgaistData(null);
            redisClient.hSet(RedisFiledConstant.BREAK_GAME_WAIT_PLAYER, String.valueOf(againstRecordsVo.getUserId()), againstRecordsVo);
        } else {
            redisClient.hDel(RedisFiledConstant.BREAK_GAME_WAIT_PLAYER, String.valueOf(againstRecordsVo.getUserId()));
        }

        //如果通关则回写当前用户比赛记录至数据库,否则继续等待匹配比赛
        if (!userRecordsVo.isRobot()) {
            if (isPass) {
                passGame(userRecordsVo);
            } else {
                //闯关比赛赢得关卡后等待继续闯关的玩家
                userRecordsVo.setData(null);
                userRecordsVo.setAgaistData(null);
                redisClient.hSet(RedisFiledConstant.BREAK_GAME_WAIT_PLAYER, String.valueOf(userRecordsVo.getUserId()), userRecordsVo);
            }
        }
        //移除玩家正在比赛记录
        redisClient.hDel(userRecordsVo.getGameNo(), String.valueOf(userRecordsVo.getAgainstId()));
        redisClient.hDel(userRecordsVo.getGameNo(), String.valueOf(userRecordsVo.getUserId()));

        saveData(userRecordsVo, againstRecordsVo, isPass, isDeath);
    }

    /**
     * 匹配对手
     *
     * @param userRecordsVo 当前玩家游戏信息
     */
    @Override
    public synchronized void match(GameBreakUserRecordsVo userRecordsVo) {
        GameBreakRecords gameBreakRecords = redisClient.hGet(RedisFiledConstant.BREAK_GAME_RECORD, String.valueOf(userRecordsVo.getGameId()), GameBreakRecords.class);//获取当前赛场信息
        if (gameBreakRecords == null) {
            logger.info("【游戏异常】该游戏当前赛场不存在！gameId={}", userRecordsVo.getGameId());
            sendMessage(null, userRecordsVo.getGameNo(), userRecordsVo.getUserId(), SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_END);//本赛场已结束
            return;
        }
        long userId = userRecordsVo.getUserId();
        final String key = RedisFiledConstant.BREAK_GAME_NOT_MATCH_PLAYER;
        final String field = String.valueOf(userId);
        userRecordsVo.setGameNo(gameBreakRecords.getGameNo());

        Map<String, GameBreakUserRecordsVo> matchPlayers = redisClient.hGetAll(key, GameBreakUserRecordsVo.class);//获取当前赛场等待匹配玩家信息
        //如等待区无选手等待则玩家进入等待，否则匹配对手进入游戏
        if (null != matchPlayers && matchPlayers.size() > 0) {
            logger.info("【游戏正常】该游戏当前赛场有玩家等待，进行匹配！gameNO={}", gameBreakRecords.getGameNo());

            GameBreakUserRecordsVo againstUserRecord = matchPlayers.entrySet().iterator().next().getValue();
            //是否为同一玩家
            if (userRecordsVo.getUserId().equals(againstUserRecord.getUserId())) {
                logger.info("【游戏正常】该游戏当前赛场无其他玩家等待！userId={},gameNO={}", userRecordsVo.getUserId(), gameBreakRecords.getGameNo());
                //过期自动移除
                redisClient.hSetWithExpire(key, field, userRecordsVo, 30);
            } else {
                //删除机器人匹配任务
                gameJobService.removeGameJob(SystemConstant.TASK_GROUP_BREAK_GAME_ROBOT_MATCH,againstUserRecord.getGameNo() + againstUserRecord.getUserId());

                List<List<LayerVo>> gameData = gameRedpacketRecordsService.generateDataForFight(1, GameConstants.TANQIU_GAME_MODEL_KEY_BREAK);//初始弹球渲染数据
                //当前玩家推送消息
                userRecordsVo.setAgainstId(againstUserRecord.getUserId());
                userRecordsVo.setAgaistHeadUrl(againstUserRecord.getHeadUrl());
                userRecordsVo.setAgaistNickName(againstUserRecord.getNickName());
                userRecordsVo.setAgaistGender(againstUserRecord.getGender());
                userRecordsVo.setData(gameData);
                userRecordsVo.setScore(0);

                userRecordsVo.setAgaistData(gameData);
                userRecordsVo.setGameTime(gameBreakRecords.getGameTime());
                sendMessage(userRecordsVo, gameBreakRecords.getGameNo(), userId, SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_BEGIN);

                //当前玩家PK对手推送消息
                againstUserRecord.setAgainstId(userRecordsVo.getUserId());
                againstUserRecord.setAgaistHeadUrl(userRecordsVo.getHeadUrl());
                againstUserRecord.setAgaistNickName(userRecordsVo.getNickName());
                againstUserRecord.setAgaistGender(userRecordsVo.getGender());
                againstUserRecord.setData(gameData);
                againstUserRecord.setScore(0);
                againstUserRecord.setAgaistData(gameData);
                againstUserRecord.setGameTime(gameBreakRecords.getGameTime());
                sendMessage(againstUserRecord, gameBreakRecords.getGameNo(), againstUserRecord.getUserId(), SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_BEGIN);

                //记录移至比赛数据缓存热区
                redisClient.hDel(key, String.valueOf(againstUserRecord.getUserId()));
                redisClient.hSet(gameBreakRecords.getGameNo(), String.valueOf(userId), userRecordsVo);
                redisClient.hSet(gameBreakRecords.getGameNo(), String.valueOf(againstUserRecord.getUserId()), againstUserRecord);
            }
        } else {

            logger.info("【游戏正常】该游戏当前赛场无玩家等待！gameNO={}", gameBreakRecords.getGameNo());
            redisClient.hSetWithExpire(key, field, userRecordsVo, 30);
            //机器人匹配任务
            int randomTimes=0;
            int random=(int)(Math.random() * 100);
            if(random>=80){
                randomTimes=(int)(Math.random() * 25*1000)+50;//匹配时间50-30*1000毫秒间
                logger.info("机器人匹配时间："+randomTimes);
                gameJobService.robotMacthTask(userRecordsVo,randomTimes);
            }else if(random>=30 && random<80) {
                randomTimes=(int)(Math.random() * 3*1000);//匹配时间0-3*1000毫秒间
                logger.info("机器人匹配时间："+randomTimes);
                gameJobService.robotMacthTask(userRecordsVo,randomTimes);
            }else{
                robotMatch(userRecordsVo);
            }

        }

        //更新当前在线人数
        gameBreakRecords.setContestNum(gameBreakRecords.getContestNum() + 1);
        redisClient.hSet(RedisFiledConstant.BREAK_GAME_RECORD, String.valueOf(userRecordsVo.getGameId()), gameBreakRecords);

    }

    /**
     * 游戏数据保存
     */
    private void saveData(GameBreakUserRecordsVo userRecordsVo, GameBreakUserRecordsVo againstRecordsVo, boolean isPass, boolean isDeath){
        if (!userRecordsVo.isRobot()) {
            //当前用户该轮次比赛记录保存
            GameBreakAgainstRecords currentRoundRecords = new GameBreakAgainstRecords();
            currentRoundRecords.setUserId(userRecordsVo.getUserId());
            currentRoundRecords.setGameModelId(userRecordsVo.getGameId());
            currentRoundRecords.setGameNo(userRecordsVo.getGameNo());
            currentRoundRecords.setScore(userRecordsVo.getScore());
            currentRoundRecords.setAgainstScore(againstRecordsVo.getScore());
            currentRoundRecords.setIsGameEnd(isDeath);
            if (isPass) {
                currentRoundRecords.setGameRound(userRecordsVo.getPassMaxLevel());
            } else {
                currentRoundRecords.setGameRound((short) (userRecordsVo.getPassMaxLevel() + 1));
            }
            currentRoundRecords.setIsWin(true);
            currentRoundRecords.setCreateTime(new Date());
            gameBreakAgainstRecordsService.saveOrUpdate(currentRoundRecords);
        }
        if (!againstRecordsVo.isRobot()) {
            //对手该轮次比赛记录保存
            GameBreakAgainstRecords againstRoundRecords = new GameBreakAgainstRecords();
            againstRoundRecords.setUserId(againstRecordsVo.getUserId());
            againstRoundRecords.setGameModelId(againstRecordsVo.getGameId());
            againstRoundRecords.setGameNo(againstRecordsVo.getGameNo());
            againstRoundRecords.setGameRound((short) (againstRecordsVo.getPassMaxLevel() + 1));
            againstRoundRecords.setIsWin(false);
            againstRoundRecords.setCreateTime(new Date());
            againstRoundRecords.setScore(againstRecordsVo.getScore());
            againstRoundRecords.setAgainstScore(userRecordsVo.getScore());
            againstRoundRecords.setIsGameEnd(isDeath);
            gameBreakAgainstRecordsService.saveOrUpdate(againstRoundRecords);
        }
    }

    /**
     * 通关
     */
    private void passGame(GameBreakUserRecordsVo userRecordsVo){
        //用户领奖资格记录
        GameBreakUserQualify breakGameUserQualify = new GameBreakUserQualify();
        GameBreakUserQualifyExample breakGameUserQualifyExample = new GameBreakUserQualifyExample();
        breakGameUserQualifyExample.or().andGameModelIdEqualTo(userRecordsVo.getGameId()).andGameNoEqualTo(userRecordsVo.getGameNo()).andUserIdEqualTo(userRecordsVo.getUserId());
        List<GameBreakUserQualify> breakGameUserQualifyList = gameBreakUserQualifyService.selectByExample(breakGameUserQualifyExample);
        if (null != breakGameUserQualifyList && breakGameUserQualifyList.size() > 0) {
            breakGameUserQualify = breakGameUserQualifyList.get(0);
            breakGameUserQualify.setPassTimes(breakGameUserQualify.getPassTimes() + 1);
            breakGameUserQualify.setUpdateTime(new Date());
        } else {
            breakGameUserQualify.setCreateTime(new Date());
            breakGameUserQualify.setUpdateTime(new Date());
            breakGameUserQualify.setGameModelId(userRecordsVo.getGameId());
            breakGameUserQualify.setGameNo(userRecordsVo.getGameNo());
            breakGameUserQualify.setUserId(userRecordsVo.getUserId());
            breakGameUserQualify.setNickName(userRecordsVo.getNickName());
            breakGameUserQualify.setHeadUrl(userRecordsVo.getHeadUrl());
            breakGameUserQualify.setIsAward(false);
            breakGameUserQualify.setPassTimes(1);
        }
        gameBreakUserQualifyService.saveOrUpdate(breakGameUserQualify);
        redisClient.hDel(RedisFiledConstant.BREAK_GAME_WAIT_PLAYER, String.valueOf(userRecordsVo.getUserId()));
    }


    /**
     * 机器人死亡
     * @param map
     */
    public void robotDead(Map map){
        GameBreakUserRecordsVo userRecordsVo = (GameBreakUserRecordsVo)map.get("userRecordsVo");
        GameBreakUserRecordsVo robotRecordsVo = (GameBreakUserRecordsVo)map.get("robotRecordsVo");
        String gameNo = userRecordsVo.getGameNo();
        Long userId = userRecordsVo.getUserId();
        Long againstId = robotRecordsVo.getUserId();

        GameBreakUserRecordsVo breakGameUserRecordsVo = redisClient.hGet(gameNo, String.valueOf(userId), GameBreakUserRecordsVo.class);//获取当前用户信息
        if (breakGameUserRecordsVo == null) {
            logger.info("【游戏异常】该游戏当前赛场用户游戏记录不存在！gameNo={},againstId={}", gameNo, userId);
            return;
        }
        GameBreakUserRecordsVo againstPlayerRecordsVo = redisClient.hGet(gameNo, String.valueOf(againstId), GameBreakUserRecordsVo.class);//获取当前用户信息
        if (againstPlayerRecordsVo == null) {
            logger.info("【游戏异常】该游戏当前赛场用户对手游戏记录不存在！gameNo={},againstId={}", gameNo, againstId);
            return;
        }
        win(breakGameUserRecordsVo, againstPlayerRecordsVo, SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_DEATH);
    }

    public void robotMatch(GameBreakUserRecordsVo userRecordsVo){
        GameBreakRecords gameBreakRecords = redisClient.hGet(RedisFiledConstant.BREAK_GAME_RECORD, String.valueOf(userRecordsVo.getGameId()), GameBreakRecords.class);//获取当前赛场信息
        if (gameBreakRecords == null) {
            logger.info("【游戏异常】该游戏当前赛场不存在！gameId={}", userRecordsVo.getGameId());
            sendMessage(null, userRecordsVo.getGameNo(), userRecordsVo.getUserId(), SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_END);//本赛场已结束
            return;
        }
        GameBreakUserRecordsVo robotRecordsVo = new GameBreakUserRecordsVo();
        RobotVideoScoreConfig robotVideoScoreConfig = robotVideoScoreConfigService.matchRobot4One(userRecordsVo.getGameId(), userRecordsVo.getPassMaxLevel() + 1);
        robotRecordsVo.setData(robotVideoScoreConfig.getScoreConfig());
        String scoreConfig = robotVideoScoreConfig.getScoreConfig();
        TreeMap treeMap = JSON.parseObject(scoreConfig, TreeMap.class);
        Integer deadTime = (Integer)treeMap.descendingMap().firstKey();
        List<List<LayerVo>> gameData = gameRedpacketRecordsService.generateDataForFight(1, GameConstants.TANQIU_GAME_MODEL_KEY_BREAK);//初始弹球渲染数据


        Random random = new Random();
        Long randomId = Long.valueOf(random.nextInt(119)+1);
        logger.info("【机器人死亡时间】 time={},robotId={},robotVideoScoreConfig.getScoreConfig()={}", deadTime,randomId,robotVideoScoreConfig.getScoreConfig());
        User user = userService.selectById(randomId);
        UserInfo userInfo = userInfoService.selectByUserId(user.getId());
        robotRecordsVo.setUserId(user.getId());
        robotRecordsVo.setGameId(2L);
        robotRecordsVo.setNickName(user.getNickName());
        robotRecordsVo.setGender(userInfo.getGender());
        robotRecordsVo.setHeadUrl(userInfo.getHeadPicUrl());
        robotRecordsVo.setRecoveryTimes(0);
        robotRecordsVo.setPass(false);
        robotRecordsVo.setData(gameData);
        robotRecordsVo.setGameTime(gameBreakRecords.getGameTime());

        int totalScore = 0;
        for (Object o : treeMap.values()) {
            Integer scorei = (Integer) o;
            totalScore += scorei;
        }
        robotRecordsVo.setScore(totalScore);
        robotRecordsVo.setPassMaxLevel((short)1);
        robotRecordsVo.setAgaistGender(userRecordsVo.getGender());
        robotRecordsVo.setAgaistNickName(userRecordsVo.getNickName());
        robotRecordsVo.setAgaistHeadUrl(userRecordsVo.getHeadUrl());
        robotRecordsVo.setAgainstId(userRecordsVo.getUserId());
        GameBreakRecords breakGameRecords = redisClient.hGet(RedisFiledConstant.BREAK_GAME_RECORD, String.valueOf(userRecordsVo.getGameId()),GameBreakRecords.class); //获取当前赛场信息
        if (breakGameRecords == null) {
            logger.info("【游戏异常】该游戏当前赛场不存在！gameId={}", userRecordsVo.getGameId());
            sendMessage(null, userRecordsVo.getGameNo(), userRecordsVo.getUserId(), SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_END);//本赛场已结束
            return;
        }
        long userId = userRecordsVo.getUserId();
        final String key = RedisFiledConstant.BREAK_GAME_NOT_MATCH_PLAYER;
        userRecordsVo.setGameNo(breakGameRecords.getGameNo());
        robotRecordsVo.setGameNo(breakGameRecords.getGameNo());
        robotRecordsVo.setRobot(true);
        //真实玩家推送消息
        userRecordsVo.setScore(0);
        userRecordsVo.setAgainstId(robotRecordsVo.getUserId());
        userRecordsVo.setAgaistHeadUrl(robotRecordsVo.getHeadUrl());
        userRecordsVo.setAgaistNickName(robotRecordsVo.getNickName());
        userRecordsVo.setAgaistGender(robotRecordsVo.getGender());
        userRecordsVo.setAgaistData(robotVideoScoreConfig.getScoreConfig());
        userRecordsVo.setWin(false);
        userRecordsVo.setData(gameData);
        userRecordsVo.setGameTime(gameBreakRecords.getGameTime());
        sendMessage(userRecordsVo, breakGameRecords.getGameNo(), userId, SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_BEGIN);


        //记录移至比赛数据缓存热区
        redisClient.hDel(key, String.valueOf(userRecordsVo.getUserId()));
        redisClient.hSet(breakGameRecords.getGameNo(), String.valueOf(userId), userRecordsVo);
        redisClient.hSet(breakGameRecords.getGameNo(), String.valueOf(robotRecordsVo.getUserId()), robotRecordsVo);
        //更新当前在线人数
        breakGameRecords.setContestNum(breakGameRecords.getContestNum() + 1);
        redisClient.hSet(RedisFiledConstant.BREAK_GAME_RECORD, String.valueOf(userRecordsVo.getGameId()), breakGameRecords);

        gameJobService.robotDeadTask(userRecordsVo, robotRecordsVo, deadTime);
    }


    /**
     * 退出比赛(长时间等待匹配对手)
     *
     * @param userId  用户ID
     * @param gameNo  游戏编码
     * @param isBegin 是否是第一关
     */
    @Override
    public void quit(long userId, long gameId, String gameNo, boolean isBegin) {
        if (isBegin) {
            redisClient.hDel(RedisFiledConstant.BREAK_GAME_NOT_MATCH_PLAYER, String.valueOf(userId));
        } else {
            GameBreakUserRecordsVo matchPlayer = redisClient.hGet(RedisFiledConstant.BREAK_GAME_NOT_MATCH_PLAYER, String.valueOf(userId), GameBreakUserRecordsVo.class);//获取当前用户正在匹配信息
            if (matchPlayer == null) {
                logger.info("【游戏异常】该游戏当前赛场用户游戏记录不存在！gameNo={},againstId={}", gameNo, userId);
                return;
            }
            redisClient.hDel(RedisFiledConstant.BREAK_GAME_NOT_MATCH_PLAYER, String.valueOf(userId));
            redisClient.hSet(RedisFiledConstant.BREAK_GAME_WAIT_PLAYER, String.valueOf(userId), matchPlayer);
        }

        //更新在线人数
        GameBreakRecords gameBreakRecords = redisClient.hGet(RedisFiledConstant.BREAK_GAME_RECORD, String.valueOf(gameId), GameBreakRecords.class);//获取当前赛场信息
        if (gameBreakRecords == null) {
            logger.info("【游戏异常】该游戏当前赛场记录不存在！gameId={}", gameId);
            return;
        }
        gameBreakRecords.setContestNum(gameBreakRecords.getContestNum() - 1);
        redisClient.hSet(RedisFiledConstant.BREAK_GAME_RECORD, String.valueOf(gameId), gameBreakRecords);
        //删除机器人匹配任务
        gameJobService.removeGameJob(SystemConstant.TASK_GROUP_BREAK_GAME_ROBOT_MATCH,gameNo+ userId);
    }


    /**
     * 推送消息
     */
    public void sendMessage(Object data, String group, long userId, String msgCode) {
        SocketResponseMsg msg = new SocketResponseMsg();
        msg.setCode(msgCode);
        msg.setSuccess(true);
        msg.setResult(data);
        webSocketHandlerService.sendMessageToUser(group, "user" + userId, new TextMessage(JSONUtil.toJson(msg)));
    }

    /**
     * 移除相关定时任务
     *
     * @param userId
     * @param againstId
     * @param gameNo
     */
    public void deleteTask(long userId, long againstId, String gameNo) {
        //移除该轮比赛结算任务
        gameJobService.removeGameJob(SystemConstant.TASK_GROUP_BREAK_GAME_ROBOT_DEAD, gameNo + "_" + userId);
    }

    /**
     * 游戏结算
     *
     * @param gameId
     */
    @Override
    public void closeGame(Long gameId) {
        String requestId = UUID.randomUUID().toString();
        int expireTime = 1000 * 60 * 60;//过期时间毫秒数 1hour
        //redis分布式锁 加锁
        if (!redisLock.tryGetDistributedLock(RedisFiledConstant.CLOSE_BREAK_GAME_LOCK, requestId, expireTime)) {
            return;
        }
        logger.info("【弹球结算】 开始闯关结算");
        String gameNo = null;

        GameModel game = gameModelService.selectById(gameId);
        if (game == null) {
            return;
        }
        GameBreakRecords gameRecord = redisClient.hGet(RedisFiledConstant.BREAK_GAME_RECORD, String.valueOf(gameId), GameBreakRecords.class);


        Map<String, String> configs = gameConfigService.getByGameModelKey(game.getGameModelKey());

        //游戏结算
        if (gameRecord != null) {
            try {
                gameNo = gameRecord.getGameNo();
                closeGame(gameId, gameRecord, configs);
            } catch (Exception e) {
                logger.error("【弹球结算】 发放奖励结算失败。Exception:{}", e.toString());
            }
        }

        //保存等待区用户游戏记录
        if (gameNo != null) {
            try {
                Map<String, GameBreakUserRecordsVo> map = redisClient.hGetAll(RedisFiledConstant.BREAK_GAME_WAIT_PLAYER + "_" + gameNo, GameBreakUserRecordsVo.class);
                if (map != null || !map.isEmpty()) {
                    for (Map.Entry<String, GameBreakUserRecordsVo> entry : map.entrySet()) {
                        GameBreakUserRecords records = new GameBreakUserRecords();
                        BeanUtils.copyProperties(entry.getValue(), records);
                        breakGameUserRecordsService.saveOrUpdate(records);
                    }
                }
                redisClient.del(RedisFiledConstant.BREAK_GAME_WAIT_PLAYER + "_" + gameNo);
            } catch (Exception e) {
                logger.error("【弹球结算】 保存等待区用户失败。Exception:{}", e.toString());
            }
        }


        logger.info("【弹球结算】 弹球结算结束");
        //结算完毕，开启新一轮比赛
        try {
            gameJobService.removeGameJob(SystemConstant.TASK_GROUP_BREAK_GAME_CLOSE, gameNo);
            logger.info("【弹球结算】 创建弹球结算任务开始");
            redisClient.hDel(RedisFiledConstant.BREAK_GAME_RECORD, String.valueOf(gameId));
            createNewGame(gameId);
            logger.info("【弹球结算】 创建弹球结算任务结束");
        } catch (Exception e) {
            logger.error("【弹球结算】 创建弹球结算任务失败。Exception:{}", e.toString());
        }

    }

    /**
     * 定时结算，分离出来处理事务和异常
     *
     * @param gameId
     * @param gameRecord
     * @param configs
     */
    @Transactional
    public void closeGame(Long gameId, GameBreakRecords gameRecord, Map<String, String> configs) {
        //通关人数
        Integer passNum = gameRecord.getPassThroughNum();

        if (passNum != null && passNum > 0) {
            //生成随机红包
            int bonusMax = Integer.valueOf(configs.get(BreakGameConstants.BONUS_MAX));
            int bonusMin = Integer.valueOf(configs.get(BreakGameConstants.BONUS_MIN));
            Random random = new Random();
            int bonus = bonusMin + random.nextInt(bonusMax - bonusMin);
            gameRecord.setPerAward(bonus);
            gameRecord.setPassThroughNum(passNum);
            //给用户发放奖金
            GameBreakUserQualifyExample qualifyExample = new GameBreakUserQualifyExample();
            //根据gameNo和gameId查询有获奖资格的用户
            qualifyExample.or().andGameNoEqualTo(gameRecord.getGameNo())
                    .andGameModelIdEqualTo(gameId).andIsAwardEqualTo(false);
            List<GameBreakUserQualify> qualifies = gameBreakUserQualifyService.selectByExample(qualifyExample);
            //奖励资格表不为空才进行发放
            if (qualifies != null && !qualifies.isEmpty()) {
                for (GameBreakUserQualify qualify : qualifies) {
                    UserInfo userInfo = userInfoService.selectByUserId(qualify.getUserId());
                    Integer moneyGet = qualify.getPassTimes() * bonus;
                    //
                    userInfo.setBalance(userInfo.getBalance() + moneyGet);
                    userInfoService.saveOrUpdate(userInfo);
                    //将奖励资格表中的该用户领奖状态设置为已颁发
                    qualify.setIsAward(true);
                    gameBreakUserQualifyService.saveOrUpdate(qualify);
                    //交易流水
                    User user = userService.selectById(userInfo.getUserId());
                    CoinTranslog translog = new CoinTranslog();
                    translog.setUserId(userInfo.getUserId());
                    translog.setUserName(user.getNickName());
                    translog.setTransType(SystemConstant.TRANS_TYPE_PLAY_GAME);
                    translog.setAccountType(SystemConstant.ACC_TYPE_BALANCE);
                    translog.setTransFlag(SystemConstant.TRANS_DIRECT_INCOM);
                    translog.setTransTime(new Date());
                    translog.setUserId(userInfo.getUserId());
                    translog.setBalance(userInfo.getBalance().intValue());
                    translog.setTransAmount(Integer.valueOf(moneyGet));
                    translog.setRemark(MessageHelper.generateByModel(MsgModelConstant.BREAK_GAME_NOTICE_REMARK,qualify.getPassTimes()));
                    transLogService.addTranslog(translog);

                    PassAwardRecord passAwardRecord = new PassAwardRecord();
                    passAwardRecord.setAwardNum(moneyGet.longValue());
                    passAwardRecord.setPassTimes(qualify.getPassTimes());
                    passAwardRecord.setUserId(qualify.getUserId());
                    passAwardRecord.setLatestPassTime(qualify.getUpdateTime());
                    passAwardRecord.setReadStat(false);
                    redisClient.hSet(RedisFiledConstant.BREAK_GAME_PASS_RECORD, qualify.getUserId().toString(), passAwardRecord);
                }
            }
        } else {
            //无人通关结算
            gameRecord.setPerAward(0);
            gameRecord.setPassThroughNum(0);
        }
        //更改游戏状态为已结算
        gameRecord.setGameStatus(BreakGameConstants.GAME_STATUS_2);
        gameBreakRecordsService.saveOrUpdate(gameRecord);
    }


    /**
     * 创建一个新游戏并创建结算定时任务
     *
     * @param gameId
     */
    @Transactional
    public synchronized GameBreakRecords createNewGame(Long gameId) {
        //redis分布式锁 加锁
        String requestId = UUID.randomUUID().toString();
        int expireTime = 30000;//过期时间毫秒数 30秒
        while (!redisLock.tryGetDistributedLock(RedisFiledConstant.CREATE_BREAK_GAME_LOCK, requestId, expireTime)) {
            try {
                Thread.sleep(100);
                if (redisLock.tryGetDistributedLock(RedisFiledConstant.CREATE_BREAK_GAME_LOCK, requestId, expireTime)) {
                    GameBreakRecords gameRecord = redisClient.hGet(RedisFiledConstant.BREAK_GAME_RECORD, String.valueOf(gameId), GameBreakRecords.class);
                    redisLock.releaseDistributedLock(RedisFiledConstant.CREATE_BREAK_GAME_LOCK, requestId);
                    return gameRecord;
                }
            } catch (Exception e) {
                logger.error("【闯关游戏】 创建游戏获取锁异常Exception={}", e);
            }
        }

        GameModel game = gameModelService.selectById(gameId);
        GameBreakRecords redisGameRecord = redisClient.hGet(RedisFiledConstant.BREAK_GAME_RECORD, String.valueOf(gameId), GameBreakRecords.class);
        //设置游戏结算时间
        Map<String, String> configs = gameConfigService.getByGameModelKey(game.getGameModelKey());
        String awardTime = configs.get(BreakGameConstants.TIME);
        //已经存在记录说明游戏已经创建,防止重复创建游戏
        if (redisGameRecord != null) {
            if (redisGameRecord.getEndTime().equals(DateUtil.getEndTime(awardTime))) {
                logger.info("【弹球游戏】 重复创建游戏：{}", game.getGameModelName());
                redisLock.releaseDistributedLock(RedisFiledConstant.CREATE_BREAK_GAME_LOCK, requestId);
                return redisGameRecord;
            }
        }

        //开启下一场
        DateTime time = new DateTime();
        GameBreakRecords gameRecord = new GameBreakRecords();
        gameRecord.setContestNum(0);
        gameRecord.setGameFiledName(game.getGameModelName());
        gameRecord.setGameModelId(game.getId());
        //游戏场次编号
        gameRecord.setGameNo(GameNoGeneratorUtil.dailyGameNo("TQ_"+game.getGameModelKey()));
        WebSocketSessionService.INSTANCE.addGroup("BREAK",gameRecord.getGameNo());
        //游戏唯一编号
        gameRecord.setGameModelKey(game.getGameModelKey());
        gameRecord.setPassThroughNum(0);
        gameRecord.setGameStatus(BreakGameConstants.GAME_STATUS_1);
        //游戏时长
        gameRecord.setGameTime(Integer.parseInt(configs.get(BreakGameConstants.DURATION)));
        //关卡数
        gameRecord.setTotalRound(Integer.parseInt(configs.get(BreakGameConstants.LEVEL)));

        gameRecord.setStartTime(time.toDate());
        gameRecord.setEndTime(DateUtil.getEndTime(awardTime));

        long recordId = gameBreakRecordsService.saveOrUpdate(gameRecord);
        gameRecord.setId(recordId);
        //更新redis上的游戏信息
        redisClient.hSet(RedisFiledConstant.BREAK_GAME_RECORD, String.valueOf(gameId), gameRecord);
        //redis分布式锁 解锁
        redisLock.releaseDistributedLock(RedisFiledConstant.CREATE_BREAK_GAME_LOCK, requestId);
        //创建任务
        Map<String, Object> params = new HashMap<>();
        params.put("game", game);
        params.put("gameRecord", gameRecord);
        gameJobService.addCloseBreakGameJob(params);
        return gameRecord;
    }
}
