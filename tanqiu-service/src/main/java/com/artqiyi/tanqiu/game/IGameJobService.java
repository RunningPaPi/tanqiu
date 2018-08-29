package com.artqiyi.tanqiu.game;

import com.artqiyi.tanqiu.game.domain.GameRedpacketRecords;
import com.artqiyi.tanqiu.game.vo.GameBreakUserRecordsVo;
import com.artqiyi.tanqiu.game.vo.GameFightUserRecordsVo;
import com.artqiyi.tanqiu.user.domain.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 游戏相关任务
 */
public interface IGameJobService {
    void breakGameOvertimeTask(GameBreakUserRecordsVo gameBreakUserRecordsVo);
    void fightGameEndPkTask(GameFightUserRecordsVo gameFightUserRecordsVo);
    void fightGameOvertimeTask(GameFightUserRecordsVo gameFightUserRecordsVo);
    void removeGameJob(String groupName, String JobName);
    void removeGroupGameJob(String groupName);
    void addCloseBreakGameJob(Map<String, Object> params);
    void createRepacketEndJob(GameRedpacketRecords gameRedpacketRecords);
    void createCheatedPlayersJob(GameRedpacketRecords gameRedpacketRecords, int i, Date cronDate, List<User> users);

    void robotMacthTask(GameBreakUserRecordsVo userRecordsVo, int time);

    void robotDeadTask(GameBreakUserRecordsVo userRecordsVo, GameBreakUserRecordsVo ruserRecordsVo, int time);
}
