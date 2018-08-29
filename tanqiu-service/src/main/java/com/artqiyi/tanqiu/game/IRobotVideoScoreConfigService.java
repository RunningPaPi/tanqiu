package com.artqiyi.tanqiu.game;

import com.artqiyi.tanqiu.game.domain.RobotVideoScoreConfig;
import com.artqiyi.tanqiu.game.domain.RobotVideoScoreConfigExample;

import java.util.List;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/5/8
 * Modify On: 2018/5/8 by chencunjun
 */
public interface IRobotVideoScoreConfigService {
    List<RobotVideoScoreConfig>  selectByExample(RobotVideoScoreConfigExample robotVideoScoreConfigExample);

    RobotVideoScoreConfig selectById(long id);

    long countByExample(RobotVideoScoreConfigExample robotVideoScoreConfigExample);

    /**
     * 匹配机器人列表
     * @param gameModelId 游戏模式id
     * @param round        赛场轮次
     * @param matchNum     匹配人数
     * @return
     */
    List<RobotVideoScoreConfig> matchRobot(long gameModelId, int round, int matchNum);

    /**
     * 匹配单个机器人
     * @param gameModelId 游戏模式id
     * @param round        赛场轮次
     * @return
     */
    RobotVideoScoreConfig matchRobot4One(long gameModelId, int round);

}
