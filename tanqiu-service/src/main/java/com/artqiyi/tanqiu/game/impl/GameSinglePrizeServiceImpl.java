package com.artqiyi.tanqiu.game.impl;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/05
 * Modify On: 2018/07/05 18:58 by wufuchang
 */

import com.artqiyi.tanqiu.game.IGameSinglePrizeService;
import com.artqiyi.tanqiu.game.domain.GameSinglePrize;
import com.artqiyi.tanqiu.game.domain.GameSinglePrizeExample;
import com.artqiyi.tanqiu.game.mapper.GameSinglePrizeMapper;
import com.artqiyi.tanqiu.game.vo.ScorePrizeVo;
import com.github.pagehelper.PageHelper;
import org.codehaus.jackson.util.MinimalPrettyPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class GameSinglePrizeServiceImpl implements IGameSinglePrizeService {
    public static final Logger logger = LoggerFactory.getLogger(GameSinglePrizeServiceImpl.class);

    @Autowired
    GameSinglePrizeMapper gameSinglePrizeMapper;


    /**
     * 根据分数获取本级奖励的信息
     * @param score
     * @return
     */
    @Override
    public Integer getPrizeCoinByScore(Integer score) {
        Integer prizeCoin = null;
        GameSinglePrizeExample example = new GameSinglePrizeExample();
        example.or().andDeletedEqualTo((short) 0);
        List<GameSinglePrize> gameSinglePrizes = gameSinglePrizeMapper.selectByExample(example);
        if (gameSinglePrizes.size()==0) {
            logger.warn("单人模式没有配置奖励");
            return 0;
        }
        gameSinglePrizes = gameSinglePrizes.stream().sorted(Comparator.comparing(GameSinglePrize::getMinPoint)).collect(Collectors.toList());
        List<GameSinglePrize> targetPrizeList = gameSinglePrizes.stream().filter(gameSinglePrize -> score >= gameSinglePrize.getMinPoint() && score <= gameSinglePrize.getMaxPoint()).collect(Collectors.toList());
        if (targetPrizeList.size()==0) {
            GameSinglePrize minPrize = gameSinglePrizes.get(0);
            GameSinglePrize maxPrize = gameSinglePrizes.get(gameSinglePrizes.size()-1);
            if (score < minPrize.getMinPoint()) {
                //可能低于最低分段，没有配置，奖励为0
                prizeCoin = 0;
            }else if(score > maxPrize.getMaxPoint()){
                //可能超出最高分段，没有配置，但是为最高奖励
                prizeCoin = maxPrize.getPrizeCoin();
            }
        }else if(targetPrizeList.size()>1){
            logger.warn("单人模式奖励配置表区间有交集，返回低等级配置的奖励");
            prizeCoin =  targetPrizeList.get(0).getPrizeCoin();
        }else{
            prizeCoin = targetPrizeList.get(0).getPrizeCoin();
        }
        return prizeCoin;
    }

    /**
     * 根据分数获取奖励即下级奖励等信息
     * @param score
     * @return
     */
    @Override
    public ScorePrizeVo getSingleModelPrizeByScore(Integer score) {
        ScorePrizeVo prizeVo = new ScorePrizeVo();
        prizeVo.setScore(score);
        GameSinglePrizeExample example = new GameSinglePrizeExample();
        example.or().andDeletedNotEqualTo((short) 1);
        List<GameSinglePrize> gameSinglePrizes = gameSinglePrizeMapper.selectByExample(example);
        if (gameSinglePrizes.size()==0) {
            logger.warn("单人模式没有配置奖励");
            prizeVo.setNextPrizeNeededScore(0);
            prizeVo.setPrizeCoin(0);
            prizeVo.setNextPrizeCoin(0);
            return prizeVo;
        }
        //排序
        gameSinglePrizes = gameSinglePrizes.stream().sorted(Comparator.comparing(GameSinglePrize::getMinPoint)).collect(Collectors.toList());
        GameSinglePrize minPrize = gameSinglePrizes.get(0);
        GameSinglePrize maxPrize = gameSinglePrizes.get(gameSinglePrizes.size() - 1);
        Integer minPoint = minPrize.getMinPoint();
        Integer maxPoint = maxPrize.getMaxPoint();
        Integer prizeCoinForMin = minPrize.getPrizeCoin();
        Integer prizeCoinForMax = maxPrize.getPrizeCoin();
        if (score < minPoint) {
            //未达到最低奖励的配置分数
            prizeVo.setNextPrizeNeededScore(minPoint-score);
            prizeVo.setPrizeCoin(0);
            prizeVo.setNextPrizeCoin(prizeCoinForMin);
        } else if (score > maxPoint) {
            //等于或已经超过设定的最高分
            prizeVo.setNextPrizeNeededScore(0);
            prizeVo.setPrizeCoin(prizeCoinForMax);
            prizeVo.setNextPrizeCoin(prizeCoinForMax);
        }else{
            //分数落在配置分数之间
            //过滤找出右边界大于等于该分数的奖励对象
            List<GameSinglePrize> targetPrizeList = gameSinglePrizes.stream().filter(gameSinglePrize -> gameSinglePrize.getMaxPoint() >= score).collect(Collectors.toList());
            if (targetPrizeList.size()==1) {
                //说明是最高分段 或者只配置了一个分数
                prizeVo.setNextPrizeNeededScore(0);
                prizeVo.setPrizeCoin(prizeCoinForMax);
                prizeVo.setNextPrizeCoin(prizeCoinForMax);
            }else{
                //说明不是最高分段
                //既有本分数段的奖励，也有下一分数段的奖励
                GameSinglePrize currentPrize = targetPrizeList.get(0);
                GameSinglePrize nextPrize = targetPrizeList.get(1);
                prizeVo.setNextPrizeNeededScore(nextPrize.getMinPoint()-score);
                prizeVo.setPrizeCoin(currentPrize.getPrizeCoin());
                prizeVo.setNextPrizeCoin(nextPrize.getPrizeCoin());
            }
        }
        return prizeVo;
    }
}
