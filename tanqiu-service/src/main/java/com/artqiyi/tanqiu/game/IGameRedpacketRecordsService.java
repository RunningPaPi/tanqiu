package com.artqiyi.tanqiu.game;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/23
 * Modify On: 2018/07/23 18:12 by wufuchang
 */

import com.artqiyi.tanqiu.base.service.IBaseService;
import com.artqiyi.tanqiu.game.domain.*;
import com.artqiyi.tanqiu.game.vo.LayerVo;
import com.artqiyi.tanqiu.game.vo.RedpacketGameBeginVo;
import com.artqiyi.tanqiu.user.domain.User;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface IGameRedpacketRecordsService extends IBaseService<GameRedpacketRecords,GameRedpacketRecordsExample>{
    GameRedpacketRecords addRedpacketRecords(Long gameModelId, String gameModelKey);

    void addCheatedPlayersByTask(GameRedpacketRecords gameRedpacketRecords);

    void addCheatedPlayersByTaskReboot(GameRedpacketRecords gameRedpacketRecords);

    void addCheatedPlayersWhenEnded(GameRedpacketRecords gameRedpacketRecords);

    void addCheatedPlayersByTask(GameRedpacketRecords gameRedpacketRecords, Integer cheatNum, List<GameRedpacketUserRecords> robotsExisted);

    void addCheatedPlays(Map param);

    List<List<LayerVo>> generateData(Integer totalRound, String gameModelKey);

    List<List<LayerVo>> generateDataForFight(int totalRound, String gameModelKey);
}
