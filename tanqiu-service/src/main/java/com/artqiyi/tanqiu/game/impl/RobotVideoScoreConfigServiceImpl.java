package com.artqiyi.tanqiu.game.impl;

import com.artqiyi.tanqiu.common.util.RandomUtil;
import com.artqiyi.tanqiu.game.IRobotVideoScoreConfigService;
import com.artqiyi.tanqiu.game.domain.RobotVideoConfig;
import com.artqiyi.tanqiu.game.domain.RobotVideoConfigExample;
import com.artqiyi.tanqiu.game.domain.RobotVideoScoreConfig;
import com.artqiyi.tanqiu.game.domain.RobotVideoScoreConfigExample;
import com.artqiyi.tanqiu.game.mapper.RobotVideoConfigMapper;
import com.artqiyi.tanqiu.game.mapper.RobotVideoScoreConfigMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/5/8
 * Modify On: 2018/5/8 by chencunjun
 */

/**
 * 机器人视频得分配置
 */
@Service
public class RobotVideoScoreConfigServiceImpl implements IRobotVideoScoreConfigService {
    @Autowired
    private RobotVideoScoreConfigMapper robotVideoScoreConfigMapper;
    @Autowired
    private RobotVideoConfigMapper robotVideoConfigMapper;

    @Override
    public List<RobotVideoScoreConfig> selectByExample(RobotVideoScoreConfigExample robotVideoScoreConfigExample) {
        return robotVideoScoreConfigMapper.selectByExample(robotVideoScoreConfigExample);
    }

    @Override
    public RobotVideoScoreConfig selectById(long id) {
        return robotVideoScoreConfigMapper.selectByPrimaryKey((int)id);
    }

    @Override
    public long countByExample(RobotVideoScoreConfigExample robotVideoScoreConfigExample) {
        return robotVideoScoreConfigMapper.countByExample(robotVideoScoreConfigExample);
    }

    /**
     * 匹配机器人列表 给每一个机器人都独自匹配视频
     * @param gameModelId 游戏赛场ID
     * @param round        赛场轮次
     * @param matchNum     匹配人数
     * @return
     */
    @Override
    public List<RobotVideoScoreConfig> matchRobot(long gameModelId, int round, int matchNum) {
        RobotVideoConfigExample robotVideoConfigExample=new RobotVideoConfigExample();
        robotVideoConfigExample.or().andGameModelIdEqualTo(gameModelId).andTurnNumEqualTo(round);
        List<RobotVideoConfig> robotVideoConfigs=robotVideoConfigMapper.selectByExample(robotVideoConfigExample);
        if(null!=robotVideoConfigs && robotVideoConfigs.size()>0){
            RobotVideoConfig robotVideoConfig=robotVideoConfigs.get(0);
            int num=robotVideoConfig.getEndVideoId()-robotVideoConfig.getStartVideoId()+1;//视频总数
            HashSet<Integer> randomSet=new HashSet<>(); //随机数结果
            RandomUtil.randomSet(0,num,matchNum,randomSet);//给每一个机器人都独自匹配视频
            List<Integer> videoIds=new ArrayList<>();  //随机抽取的视频ID
            int startId=robotVideoConfig.getStartVideoId(); //视频起始地址
            for(int index:randomSet){
                videoIds.add(startId+index);
            }
            //查询随机匹配视频列表
            RobotVideoScoreConfigExample robotVideoScoreConfigExample=new RobotVideoScoreConfigExample();
            robotVideoScoreConfigExample.or().andIdIn(videoIds);
            List<RobotVideoScoreConfig> robotVideoScoreConfigs=robotVideoScoreConfigMapper.selectByExample(robotVideoScoreConfigExample);
            return robotVideoScoreConfigs;
        }
        return null;
    }

    @Override
    public RobotVideoScoreConfig matchRobot4One(long gameModelId, int round) {
        List<RobotVideoScoreConfig> robotVideoScoreConfigs = matchRobot(gameModelId, round, 1);
        if (!CollectionUtils.isEmpty(robotVideoScoreConfigs)) {
            return robotVideoScoreConfigs.get(0);
        }
        return null;
    }
}
