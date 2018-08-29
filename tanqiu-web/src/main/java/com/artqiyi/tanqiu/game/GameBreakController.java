package com.artqiyi.tanqiu.game;

import com.artqiyi.tanqiu.aspect.AuthPassport;
import com.artqiyi.tanqiu.common.constant.BreakGameConstants;
import com.artqiyi.tanqiu.common.constant.RedisFiledConstant;
import com.artqiyi.tanqiu.common.constant.ResponseCodeConstant;
import com.artqiyi.tanqiu.common.socket.SocketConstant;
import com.artqiyi.tanqiu.common.util.DateUtil;
import com.artqiyi.tanqiu.game.domain.*;
import com.artqiyi.tanqiu.game.vo.GameBreakIntroductionVo;
import com.artqiyi.tanqiu.game.vo.GameBreakUserRecordsVo;
import com.artqiyi.tanqiu.game.vo.PassAwardRecord;
import com.artqiyi.tanqiu.redis.RedisClient;
import com.artqiyi.tanqiu.response.UserResponse;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/game")
public class GameBreakController {
    private static final Logger log = LoggerFactory.getLogger(GameBreakController.class);
    @Autowired
    private IGameModelService breakGameService;
    @Autowired
    private IGameBreakService bigBonusGameSevice;
    @Autowired
    private IGameConfigService breakGameConfigService;
    @Autowired
    private IGameBreakUserQualifyService BreakGameUserQualifyService;
    @Autowired
    private IGameBreakRecoverCostService breakGameRecoverCostService;
    @Autowired
    private RedisClient redisClient;


    /**
     * 通关消息已读
     * @param userId
     * @return
     */
    @AuthPassport(checkLogin = true,checkSign = false)
    @DeleteMapping("/noticed/{userId}")
    public UserResponse noticed(@PathVariable("userId") Long userId){
        log.info("【通关消息已读】 userId={}", userId);
        UserResponse rsp = new UserResponse();
        redisClient.hDel(RedisFiledConstant.BREAK_GAME_PASS_RECORD,userId.toString());
        rsp.setCode(ResponseCodeConstant.SUCCESS);
        return rsp;
    }

    /**
     * 游戏规则
     *
     * @param gameKey
     * @return
     */
    @AuthPassport(checkLogin = false,checkSign = true)
    @GetMapping("/rules/{gameKey}")
    @ResponseBody
    public UserResponse getBigBonusRules(@PathVariable("gameKey") String gameKey) {
        UserResponse response = new UserResponse();
        //扩展时用switch(gameKey)查询不同游戏对应的规则
        List<GameConfig> breakGameConfigs = null;
        switch (gameKey) {
            case BreakGameConstants.SINGLE:
                breakGameConfigs = breakGameConfigService.getByType(gameKey, BreakGameConstants.SINGLE_GAME_RULE);
                break;
            case BreakGameConstants.BREAK:
                breakGameConfigs = breakGameConfigService.getByType(gameKey, BreakGameConstants.BREAK_GAME_RULE);
                break;
            default:
                break;
        }


        if (breakGameConfigs == null) {
            response.setMsg("没有查到游戏规则");
            response.setResult(null);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            return response;
        }

        List<String> rules = breakGameConfigs.stream()
                .map(e -> e.getTypeValue()).collect(Collectors.toList());
        response.setResult(rules);
        response.setCode(ResponseCodeConstant.SUCCESS);
        return response;
    }

    /**
     * 闯关规则
     *
     * @param gameKey
     * @return
     */
    @AuthPassport(checkLogin = false,checkSign = true)
    @GetMapping("/rules/break/{gameKey}")
    @ResponseBody
    public UserResponse getBreakRules(@PathVariable("gameKey") String gameKey) {
        UserResponse response = new UserResponse();
        List<GameConfig> breakGameConfigs = breakGameConfigService.getByType(gameKey, BreakGameConstants.BREAK_RULE);
        if (breakGameConfigs == null) {
            response.setMsg("没有查到闯关规则");
            response.setResult(null);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            return response;
        }
        List<String> rules = breakGameConfigs.stream()
                .map(e -> e.getTypeValue()).collect(Collectors.toList());
        response.setResult(rules);
        response.setCode(ResponseCodeConstant.SUCCESS);
        return response;
    }

    /**
     * 游戏统计
     *
     * @param gameKey
     * @return
     */

    @CrossOrigin(allowCredentials = "true")
    @GetMapping("/statistics/{gameKey}")
    @ResponseBody
    public UserResponse getTotalPlayers(@PathVariable("gameKey") String gameKey) {
        UserResponse response = new UserResponse();
        GameModel breakGame = breakGameService.getByGameModelKey(gameKey);
        if (breakGame == null) {
            log.error("【游戏统计】 break_game未配置game_key={}的相关参数", gameKey);
            response.setMsg("数据出错,没有gameKey=" + gameKey + "相关数据");
            response.setResult(null);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            return response;
        }
        //获取当前赛场信息
        GameBreakRecords breakGameRecords = redisClient.hGet(RedisFiledConstant.BREAK_GAME_RECORD, String.valueOf(breakGame.getId()), GameBreakRecords.class);
        if (breakGameRecords == null) {
            log.error("【游戏统计】 从Redis上获取当前场次游戏信息为空key:{},hKey:{}", RedisFiledConstant.BREAK_GAME_RECORD, breakGame.getId());
            response.setMsg("数据出错");
            response.setResult(null);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            return response;
        }
        Map<String, Integer> map = new HashMap();
        map.put("contestNum", breakGameRecords.getContestNum());
        map.put("passThroughNum", breakGameRecords.getPassThroughNum());
        response.setResult(map);
        response.setCode(ResponseCodeConstant.SUCCESS);
        return response;
    }

    /**
     * 每关消耗
     *
     * @param gameKey
     * @return
     */
    @AuthPassport(checkLogin = false,checkSign = true)
    @GetMapping("/levelCost/{gameKey}")
    @ResponseBody
    public UserResponse levelCost(@PathVariable("gameKey") String gameKey) {
        UserResponse response = new UserResponse();
        List<GameBreakRecoverCost> list = breakGameRecoverCostService.getByGameModelKey(gameKey);
        if (list == null || list.isEmpty()) {
            log.error("【每关消耗】 game_break_recover_cost未配置game_key={}的相关参数", gameKey);
            response.setCode(ResponseCodeConstant.SUCCESS);
            response.setResult(new ArrayList<>());
            return response;
        }
        response.setResult(list);
        response.setCode(ResponseCodeConstant.SUCCESS);
        return response;
    }

    /**
     * 获取动态参赛/通关人数
     *
     * @param configs
     * @return
     */
    private Integer getContestNum(Map<String, String> configs, int type) {
        Integer contestNum = 0;
        if (configs.get(BreakGameConstants.CONTEST) != null && configs.get(BreakGameConstants.TIME) != null) {
            Date endTime = DateUtil.getEndTime(configs.get(BreakGameConstants.TIME));
            long period = endTime.getTime() - System.currentTimeMillis();
            int totalNum = 0;
            if (type == 0) {
                totalNum = Integer.valueOf(configs.get(BreakGameConstants.CONTEST));
            } else if (type == 1) {
                totalNum = Integer.valueOf(configs.get(BreakGameConstants.CHEAT));
            }
            int times = 60 * 24 / 10;//24小时分钟数
            int minutes = (int) period / 1000 / 60 / 10;//距离结束总分钟数
            if (minutes <= BreakGameConstants.THRESHOLD) {
                contestNum = totalNum;
            } else {
                contestNum = totalNum - (int) (minutes / (times * 1.0) * totalNum);
            }
        }
        return contestNum;
    }

    /**
     * 游戏介绍
     *
     * @param userId
     * @param gameKey
     * @return
     */
    @AuthPassport(checkLogin = false,checkSign = true)
    @GetMapping("/introduction/{userId}/{gameKey}")
    @ResponseBody
    public UserResponse getIntroduction(@PathVariable("userId") Long userId,
                                        @PathVariable("gameKey") String gameKey) {
        UserResponse response = new UserResponse();
        //获取游戏信息
        GameModel game = breakGameService.getByGameModelKey(gameKey);
        Long gameId = game.getId();
        //获取当前场次游戏信息
        GameBreakRecords breakRecords = redisClient.hGet(RedisFiledConstant.BREAK_GAME_RECORD, String.valueOf(gameId), GameBreakRecords.class);//获取当前赛场信息
        GameBreakIntroductionVo introductionVo = new GameBreakIntroductionVo();
        //获取游戏参数信息
        Map<String, String> configs = breakGameConfigService.getByGameModelKey(game.getGameModelKey());


        if (configs == null) {
            log.error("【游戏介绍】 游戏配置表break_game_config未未配置game_key={}的相关参数", gameKey);
            response.setMsg("数据出错");
            response.setCode(ResponseCodeConstant.FAIL);
            return response;
        }

        if (breakRecords == null) {
            //如果游戏的状态为0,表示游戏暂不开放
            if (BreakGameConstants.GAME_STATUS_INVALID.equals(game.getStatus())) {
                response.setMsg("游戏暂不开放");
                response.setCode(ResponseCodeConstant.SERVICE_FAIL);
                return response;
            } else {
                //否则创建游戏场次相关信息
                breakRecords = bigBonusGameSevice.createNewGame(game.getId());
            }
        }
        GameBreakUserQualify userPrize = BreakGameUserQualifyService.getLatestUserPrize(userId, gameId, breakRecords.getGameNo());

        introductionVo.setUserId(userId);
        //通关人数
        Integer cheat = 0;
        if (configs.get(BreakGameConstants.CHEAT) != null) {
            cheat = Integer.valueOf(getContestNum(configs,1));
        }
        introductionVo.setPassThroughNum(breakRecords.getPassThroughNum() + cheat);
        //参赛人数
        Integer contestNum = 0;
        if (configs.get(BreakGameConstants.CONTEST) != null) {
            contestNum = Integer.valueOf(getContestNum(configs,0));
        }
        introductionVo.setGameCloseTime(DateUtil.getEndTime(configs.get(BreakGameConstants.TIME)));
        introductionVo.setContestNum(breakRecords.getContestNum() + contestNum);
        //结算时间
        introductionVo.setAwardTime(configs.get(BreakGameConstants.TIME));
        //奖池
        introductionVo.setMoney(NumberUtils.createInteger(configs.get(BreakGameConstants.CHEAT)));
        //奖池图片
        introductionVo.setPosterUrl(configs.get(BreakGameConstants.POSTER_URL));
        //游戏总关次
        introductionVo.setTotalLevels(NumberUtils.createInteger(configs.get(BreakGameConstants.LEVEL)));
        //赛场编号
        introductionVo.setGameNo(breakRecords.getGameNo());
        //游戏gameKey
        introductionVo.setGameKey(game.getGameModelKey());
        //游戏名称
        introductionVo.setGameName(game.getGameModelName());
        //游戏id
        introductionVo.setGameId(gameId);
        //通关次数
        introductionVo.setPassTimes(0);
        //游戏规则
        introductionVo.setGameRules(breakGameConfigService.getGameRules(gameKey));
        //闯关规则
        introductionVo.setBreakRules(breakGameConfigService.getBreakRules(gameKey));

        if (userPrize != null) {
            introductionVo.setPassTimes(userPrize.getPassTimes());
        }
        introductionVo.setSystemTime(new Date());


        //先到游戏数据区获取数据
        GameBreakUserRecordsVo userRecordsVo = redisClient.hGet(breakRecords.getGameNo(), String.valueOf(userId), GameBreakUserRecordsVo.class);
        //如果数据热区存在该条记录，则该玩家是逃跑玩家，先结算该场游戏
//        if (userRecordsVo != null) {
//            GameBreakUserRecordsVo against = redisClient.hGet(breakRecords.getGameNo(), String.valueOf(userRecordsVo.getAgainstId()), GameBreakUserRecordsVo.class);
//            bigBonusGameSevice.win(against, userRecordsVo, SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_GIVE_UP);
//        }
        introductionVo.setLevelCost(0);
        //获取关卡相关数据
        userRecordsVo = redisClient.hGet(RedisFiledConstant.BREAK_GAME_WAIT_PLAYER, String.valueOf(userId), GameBreakUserRecordsVo.class);
        //为空，表示没玩过
        if (userRecordsVo == null) {
            introductionVo.setCurrentLevel(0);
            introductionVo.setIsLive(false);
        } else {
            //获取闯关游戏每关的消耗配置参数
            GameBreakRecoverCost roundCost = breakGameRecoverCostService.getRoundCost(gameId, userRecordsVo.getPassMaxLevel() + 1);
            if (roundCost == null) {
                log.error("【游戏介绍】 未配置game_break_recover_cost的gameId={}对应的game_round={} 的参数", gameId, userRecordsVo.getPassMaxLevel() + 1);
                response.setCode(ResponseCodeConstant.SERVICE_FAIL);
                response.setMsg("数据出错");
                return response;
            }
            //复活消耗
            introductionVo.setLevelCost(roundCost.getCostNum());
            //消耗账户类型
            introductionVo.setCostType(roundCost.getCostType());
            introductionVo.setCurrentLevel(userRecordsVo.getPassMaxLevel() + 1);
            introductionVo.setIsLive(userRecordsVo.isIslive());
        }
        introductionVo.setPassAwardRecord(redisClient.hGet(RedisFiledConstant.BREAK_GAME_PASS_RECORD, userId.toString(),PassAwardRecord.class));
        response.setResult(introductionVo);
        response.setCode(ResponseCodeConstant.SUCCESS);
        return response;
    }
}
