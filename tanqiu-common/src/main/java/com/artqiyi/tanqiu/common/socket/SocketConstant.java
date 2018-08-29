package com.artqiyi.tanqiu.common.socket;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/5/8
 * Modify On: 2018/5/8 by chencunjun
 */

/**
 * sockect常量
 */
public class SocketConstant {
    /**
     * 闯关比赛socket相关操作
     */
    public static  final String SOCKET_OPERATE_SEND_BREAK_GAME_END="tq_break_game_end"; //比赛结束
    public static  final String SOCKET_OPERATE_SEND_BREAK_GAME_BEGIN="tq_break_game_begin"; //比赛开始
    public static  final String SOCKET_OPERATE_SEND_BREAK_GAME_CONTINUE="tq_break_game_continue"; //继续闯关
    public static  final String SOCKET_OPERATE_SEND_BREAK_GAME_GIVE_UP="tq_break_game_give_up"; //放弃比赛
    public static  final String SOCKET_OPERATE_SEND_BREAK_GAME_RECOVER="tq_break_game_recover"; //比赛复活
    public static  final String SOCKET_OPERATE_SEND_BREAK_GAME_RESULT="tq_break_game_result"; //比赛结果
    public static  final String SOCKET_OPERATE_SEND_BREAK_GAME_EMO ="tq_break_game_emo"; //表情
    public static  final String SOCKET_OPERATE_SEND_BREAK_GAME_DATA="tq_break_game_data"; //比赛数据
    public static  final String SOCKET_OPERATE_SEND_BREAK_GAME_DEATH="tq_break_game_death"; //比赛一方提前死亡，比赛提前结束
    public static  final String SOCKET_OPERATE_SEND_BREAK_GAME_OVER="tq_break_game_over"; //比赛结束
    public static  final String SOCKET_OPERATE_SEND_BREAK_GAME_PING="ping" ;//比赛掉线
    public static  final String SOCKET_OPERATE_SEND_BREAK_GAME_QUIT_WAIT="tq_break_game_quit_wait"; //退出等待

    /**
     * 好友对战比赛socket相关操作
     */
    public static  final String SOCKET_OPERATE_SEND_FIGHT_GAME_CREATE_ROOM="tq_fight_game_create_room"; //创建房间
    public static  final String SOCKET_OPERATE_SEND_FIGHT_GAME_ENTER_ROOM="tq_fight_game_enter_room"; //加入房间
    public static  final String SOCKET_OPERATE_SEND_FIGHT_GAME_ENTER_ROOM_FAIL="tq_fight_game_enter_room_fail"; //加入房间失败
    public static  final String SOCKET_OPERATE_SEND_FIGHT_GAME_BEGIN="tq_fight_game_begin"; //比赛开始
    public static  final String SOCKET_OPERATE_SEND_FIGHT_GAME_EMOTICON="tq_fight_game_emoticon"; //表情
    public static  final String SOCKET_OPERATE_SEND_FIGHT_GAME_RESULT="tq_fight_game_result"; //比赛结果
    public static  final String SOCKET_OPERATE_SEND_FIGHT_GAME_GIVE_UP="tq_fight_game_give_up"; //放弃比赛
    public static  final String SOCKET_OPERATE_SEND_FIGHT_GAME_DATA="tq_fight_game_data"; //比赛数据
    public static  final String SOCKET_OPERATE_SEND_FIGHT_GAME_DEATH="tq_fight_game_death"; //一方玩家死亡，比赛提前结束
    public static  final String SOCKET_OPERATE_SEND_FIGHT_GAME_AGAIN="tq_fight_game_again"; //再来一局
    public static  final String SOCKET_OPERATE_SEND_FIGHT_GAME_LEAVE="tq_fight_game_leave"; //离开
    public static  final String SOCKET_OPERATE_SEND_FIGHT_GAME_OVERTIME="tq_fight_game_overtime" ;//比赛掉线
    public static  final String SOCKET_OPERATE_SEND_FIGHT_GAME_QUIT_WAIT="tq_fight_game_quit_wait"; //退出等待
    public static  final String SOCKET_OPERATE_SEND_FIGHT_GAME_WAIT_FOR_END = "tq_fight_game_wait_for_end";//推送等待好友完成游戏
    public static  final String SOCKET_OPERATE_SEND_FIGHT_GAME_COMPLETE = "tq_fight_game_complete";//接收玩家完成游戏
    public static  final String SOCKET_OPERATE_SEND_FIGHT_GAME_HEART_BEAT = "heartbeat";//等待或结算界面后发送心跳保持socket链接
    public static final String SOCKET_OPERATE_SEND_FIGHT_GAME_HEART_BEAT_COMPETE = "heartbeat_compete";//比赛期间的心跳，用于重连和检测作弊



}
