package com.artqiyi.tanqiu.game;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/7/4
 * Modify On: 2018/7/4 by chencunjun
 */

import com.artqiyi.tanqiu.game.vo.GameFightUserRecordsVo;

import java.util.Map;

/**
 * 好友对战模式
 */
public interface IGameFightService {
    /**
     * 用户创建房间
     * @param userId 用户ID
     */
    String newRoom(long userId);

    /**
     * 进入房间匹配对战
     * @param userId 用户id
     * @param roomNo 房间编码
     */
    void enterRoom(long userId,String roomNo);

    /**
     * 逃跑
     * @param userId 用户id
     * @param roomNo 房间编码
     */
    void giveUp(long userId,String roomNo);

    /**
     * 表情
     * @param userId
     * @param roomNo
     */
    void emoticon(long userId,String roomNo,String data);

    /**
     * 比赛数据
     * @param userId
     * @param roomNo
     */
    void gameData(long userId,String roomNo,String data,int score);

    /**
     * 玩家死亡，提前结束比赛
     * @param userId
     * @param roomNo
     */
    void death(long userId,String roomNo);

    /**
     * 玩家完成比赛
     * @param userId
     * @param roomNo
     */
    void complete(long userId,String roomNo, String data, int score);

    /**
     * 再来一局
     * @param userId
     * @param roomNo
     */
    void again(long userId,long againstId,String roomNo,String data);

    /**
     * 结束PK后离开房间
     * @param userId
     * @param roomNo
     */
    void leave(long userId,long againstId,String roomNo);

    /**
     * 退出等待
     * @param userId
     * @param roomNo
     */
    void quit(long userId,String roomNo);

    /**
     * 赢得比赛PK
     * @param winPlayer 赢家记录
     * @param failPlayer 输家记录
     */
    void win(GameFightUserRecordsVo winPlayer,GameFightUserRecordsVo failPlayer,String msgCode);

    /**
     * 结算对战PK任务
     * @param map
     */
    void endFightPkTask(Map map);

    /**
     * 超时监测任务
     * @param map
     */
    void overtimeTask(Map map);

    /**
     * 心跳 非游戏中，用于保活链接
     * @param userId
     * @param roomNo
     */
    void heartBeat(Long userId, String roomNo);

    /**
     * 心跳 游戏中，用于重连与作弊检测
     * @param userId
     * @param roomNo
     */
    void heartBeatDuringCompetetion(Long userId, String roomNo);


}
