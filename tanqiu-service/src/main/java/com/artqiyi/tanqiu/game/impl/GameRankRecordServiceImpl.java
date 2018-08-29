package com.artqiyi.tanqiu.game.impl;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/03
 * Modify On: 2018/07/03 20:44 by wufuchang
 */

import com.artqiyi.tanqiu.common.constant.GameConstants;
import com.artqiyi.tanqiu.common.constant.RedisFiledConstant;
import com.artqiyi.tanqiu.game.IGameRankRecordService;
import com.artqiyi.tanqiu.game.domain.GameRankRecord;
import com.artqiyi.tanqiu.game.mapper.GameRankRecordMapper;
import com.artqiyi.tanqiu.game.vo.GameRankVo;
import com.artqiyi.tanqiu.game.vo.RankBoardVo;
import com.artqiyi.tanqiu.user.domain.User;
import com.artqiyi.tanqiu.user.domain.UserInfo;
import com.artqiyi.tanqiu.user.service.IUserInfoService;
import com.artqiyi.tanqiu.user.service.IUserInviteService;
import com.artqiyi.tanqiu.user.service.IUserService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class GameRankRecordServiceImpl implements IGameRankRecordService {
    @Autowired
    private GameRankRecordMapper gameRankRecordMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IUserInviteService userInviteService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserInfoService userInfoService;

    @Override
    public long saveOrUpdate(GameRankRecord gameRankRecord) {
        if(null==gameRankRecord.getId() || gameRankRecord.getId()==0){
            gameRankRecordMapper.insertSelective(gameRankRecord);
        }else{
            gameRankRecordMapper.updateByPrimaryKeySelective(gameRankRecord);
        }
        return gameRankRecord.getId();
    }

    /**
     * 增加记录
     * @param userId
     * @param userNickName
     * @param gameModelKey
     * @param gameModelId
     * @param score
     */
    @Override
    public void addGameRecord(Long userId, String userNickName, String gameModelKey, Long gameModelId, Integer score) {
        GameRankRecord record = new GameRankRecord();
        record.setGameModelId(gameModelId);
        record.setGameModelKey(gameModelKey);
        record.setUserId(userId);
        record.setUserNickname(userNickName);
        record.setScore(score);
        saveOrUpdate(record);
    }

    /**
     * 获取好友排行榜
     * @param userId
     * @return
     */
    @Override
    public ImmutablePair<List<GameRankVo>,Integer> getFriendRankList(Long userId,Integer page, Integer pageSize) {
        //封装数据
        List<GameRankVo> list = new ArrayList<>();
        //获取好友列表
        List<Long> friendIdList = userInviteService.getFriendIdList(userId);
        //计算好友列表世界排名
        for (Long friendId : friendIdList) {
            GameRankVo gameRankVo = generateWeekWorldRank(friendId);
            if (gameRankVo != null) {
                list.add(gameRankVo);
            }
        }
        //加入自己的世界排名
        GameRankVo gameRankVo = generateWeekWorldRank(userId);
        if (gameRankVo != null) {
            list.add(gameRankVo);
        }
        //再根据worldRank排序
        list = list.stream().sorted(Comparator.comparing(GameRankVo::getWorldRank)).collect(Collectors.toList());
        //计算好友排名
        Integer myFriendRank = null;
        for (int i = 0; i < list.size(); i++) {
            GameRankVo rankVo = list.get(i);
            rankVo.setFriendRank(i+1);
            if (rankVo.getUserId().equals(userId)) {
                myFriendRank = rankVo.getFriendRank();
            }
        }
        //过滤分页条件  在排行榜界面分页显示时需要
        if (page != null && pageSize != null) {
            int startIndex = (page - 1) * pageSize;
            int toIndex = startIndex + pageSize > list.size()?list.size():startIndex + pageSize;//开区间
            //最多显示前50名
            if (toIndex > GameConstants.TANQIU_SINGLE_MODEL_RANKBOARD_TOP_LIMIT) {
                toIndex = GameConstants.TANQIU_SINGLE_MODEL_RANKBOARD_TOP_LIMIT;
            }
            if (startIndex > toIndex) {
                list = Collections.EMPTY_LIST;
            } else {
                list = list.subList(startIndex, toIndex);
            }
        }
        return  new ImmutablePair(list,myFriendRank);
    }


    /**
     * 好友排行榜单
     *
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public RankBoardVo friendRankBoard(Long userId, Integer page, Integer pageSize) {
        //获取本周好友排行榜
        //获取本人及其所有好友，根据好友id去获取好友的总排名和分数
        //List<GameRankVo> friendGameRankVoList = getFriendRankList(userId,page,pageSize);
        ImmutablePair<List<GameRankVo>,Integer> pair= getFriendRankList(userId,page,pageSize);
        List<GameRankVo> friendGameRankVoList = pair.getLeft();
        //获取本人最高排行，根据本人id取获取本人的总排名和分数
        GameRankVo myGameRankVo = generateWeekWorldRank(userId);
        if (myGameRankVo != null) {
            myGameRankVo.setFriendRank(pair.getRight());
        }
        RankBoardVo rankBoardVo = new RankBoardVo();
        rankBoardVo.setGameRankVoList(friendGameRankVoList);
        rankBoardVo.setMyGameRankVo(myGameRankVo);
        return rankBoardVo;
    }

    /**
     * 世界排行榜单
     * @return
     */
    @Override
    public RankBoardVo worldRankBoard(Long userId, Integer page, Integer pageSize) {
        //封装数据
        RankBoardVo rankBoardVo = new RankBoardVo();
        List<GameRankVo> list = new ArrayList<>();
        //过滤分页条件
        Set<ZSetOperations.TypedTuple<Object>> tuples;
        int startIndex = 0;
        if (page != null && pageSize != null) {
            startIndex = (page - 1) * pageSize;
            int toIndex = startIndex + pageSize -1;//包含该索引位置
            //最多显示前50条
            if (toIndex >= GameConstants.TANQIU_SINGLE_MODEL_RANKBOARD_TOP_LIMIT) {
                toIndex = GameConstants.TANQIU_SINGLE_MODEL_RANKBOARD_TOP_LIMIT -1;
            }
            if (startIndex > toIndex) {
                tuples = Collections.EMPTY_SET;
            }else{
                tuples = redisTemplate.opsForZSet().reverseRangeWithScores(RedisFiledConstant.TANQIU_GAME_MODEL_SINGLE_RANK_SCORE_WEEK, startIndex, toIndex);
            }
        }else{
            tuples = redisTemplate.opsForZSet().reverseRangeWithScores(RedisFiledConstant.TANQIU_GAME_MODEL_SINGLE_RANK_SCORE_WEEK, 0, GameConstants.TANQIU_SINGLE_MODEL_RANKBOARD_TOP_LIMIT -1);
        }

        Iterator<ZSetOperations.TypedTuple<Object>> iterator = tuples.iterator();
        while (iterator.hasNext()) {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            Long userIdWorld = (Long) typedTuple.getValue();
            Double scoreWithTimeStamp = typedTuple.getScore();
            Integer score = Integer.valueOf(scoreWithTimeStamp.longValue() / 10000000 + "");
            System.out.println("value:" +userIdWorld + " score:" + score);

            GameRankVo gameRankVo = new GameRankVo();
            gameRankVo.setUserId(userIdWorld);
            User user = userService.selectById(userIdWorld);
            UserInfo userInfo = userInfoService.selectByUserId(userIdWorld);
            gameRankVo.setUserNickName(user.getNickName());
            gameRankVo.setUserHeadPicUrl(userInfo.getHeadPicUrl());
            gameRankVo.setScore(score);
            list.add(gameRankVo);
        }
        //设置世界排名
        for (int i = 0; i < list.size(); i++) {
            GameRankVo gameRankVo = list.get(i);
            gameRankVo.setWorldRank(startIndex+i+1);
        }
        //获取个人排行
        GameRankVo myGameRankVo = generateWeekWorldRank(userId);
        rankBoardVo.setGameRankVoList(list);
        rankBoardVo.setMyGameRankVo(myGameRankVo);
        return rankBoardVo;
    }



    /**
     * 获取指定好友的世界排名
     * @param list
     * @param friendId
     */
    private GameRankVo generateWeekWorldRank(Long friendId) {
        Long rank = redisTemplate.opsForZSet().reverseRank(RedisFiledConstant.TANQIU_GAME_MODEL_SINGLE_RANK_SCORE_WEEK, friendId);
        if (rank != null) {
            Double scoreWithTimeStamp = redisTemplate.opsForZSet().score(RedisFiledConstant.TANQIU_GAME_MODEL_SINGLE_RANK_SCORE_WEEK, friendId);
            Integer score = Integer.valueOf(scoreWithTimeStamp.longValue() / 10000000+ "") ;
            GameRankVo gameRankVo = new GameRankVo();
            gameRankVo.setUserId(friendId);
            User user = userService.selectById(friendId);
            UserInfo userInfo = userInfoService.selectByUserId(friendId);
            gameRankVo.setUserNickName(user.getNickName());
            gameRankVo.setUserHeadPicUrl(userInfo.getHeadPicUrl());
            gameRankVo.setWorldRank(Integer.valueOf(rank+1+""));
            gameRankVo.setScore(score);
            return gameRankVo;
        }
        return null;
    }

    /**
     * 获取本人本周最高分数
     * @param userId
     * @param gameModelId
     * @param score
     * @return
     */
    @Override
    public Integer getSelfWeekHighestScore(Long userId, Long gameModelId, Integer score) {
        Double scoreWithTimeStamp = redisTemplate.opsForZSet().score(RedisFiledConstant.TANQIU_GAME_MODEL_SINGLE_RANK_SCORE_WEEK, userId);
        //解析出真实分数
        if (scoreWithTimeStamp!=null) {
            Integer scoreHighest = Integer.valueOf(scoreWithTimeStamp.longValue()/ 10000000 + "") ;
            return scoreHighest;
        }
        return null;
    }


}
