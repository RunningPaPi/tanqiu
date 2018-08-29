package com.artqiyi.tanqiu.game;

import com.artqiyi.tanqiu.game.domain.GameBreakRecords;
import com.artqiyi.tanqiu.game.vo.GameBreakUserRecordsVo;

import java.util.Map;

/**
 * 闯关游戏service接口
 */
public interface IGameBreakService {
    /**
     * 开始游戏
     * @param userId 用户ID
     * @param gameId 游戏ID
     */
    void startGame(long userId,long gameId,String gameNo);

    /**
     * 继续闯关
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @param gameNo 游戏赛场编码
     */
    void continueGame(long userId,long gameId,String gameNo);

    /**
     * 复活
     * @param userId 用户ID
     * @param gameNo 游戏编码
     * @param round  当前关卡
     */
    Map<String, Object> recover(long userId, long gameId, String gameNo, int round);

    /**
     * 玩家退出认输
     * @param userId
     */
    void giveUp(long userId,long againstId,String gameNo);

    /**
     * 玩家死亡，提前结束比赛
     * @param userId
     */
    void death(long userId,long againstId,String gameNo);

    /**
     * gameOver
     * @param userId
     * @param againstId
     * @param gameNo
     */
    void gameOver(long userId, long againstId, String gameNo);

    /**
     * 表情
     * @param userId
     * @param againstId
     * @param gameNo
     */
    void emoticon(long userId, long againstId, String gameNo, String data);

    /**
     * 比赛过程中数据传输
     * @param userId
     * @param againstId
     * @param gameNo
     */
    void gameData(long userId, long againstId, String gameNo, int score,String data);

    /**
     * 赢得单轮pk
     * @param userRecordsVo  当家玩家记录
     * @param againstRecordsVo 对手玩家记录
     */
    void win(GameBreakUserRecordsVo userRecordsVo, GameBreakUserRecordsVo againstRecordsVo, String resultCode);

    /**
     * 匹配对手
     * @param breakGameUserRecordsVo 当前玩家游戏信息
     */
    void match(GameBreakUserRecordsVo breakGameUserRecordsVo);

    void robotDead(Map map);
    /**
     * 机器人匹配
     * @param userRecordsVo
     */
    void robotMatch(GameBreakUserRecordsVo userRecordsVo);
    /**
     * 退出比赛(长时间等待匹配对手)
     * @param userId
     * @param gameNo
     */
    void quit(long userId,long gameId,String gameNo,boolean isBegin);

    /**
     * 结算游戏
     */
    void closeGame(Long gameId);

    /**
     * 开启新游戏
     */
    GameBreakRecords createNewGame(Long gameId);
}
