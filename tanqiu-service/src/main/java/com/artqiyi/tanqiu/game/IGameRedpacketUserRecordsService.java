package com.artqiyi.tanqiu.game;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/23
 * Modify On: 2018/07/23 18:15 by wufuchang
 */

import com.artqiyi.tanqiu.game.domain.GameRedpacketUserRecords;

import java.util.List;

/**
 *
 */
public interface IGameRedpacketUserRecordsService {

    void saveOrUpdate(GameRedpacketUserRecords userRecords);

    Integer getContestNum(String gameNo);

    Integer getCheatedNum(String gameNo);

    List<GameRedpacketUserRecords> getCheatedRobot(String gameNo);

}
