package com.artqiyi.tanqiu.common.constant;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/4/23
 * Modify On: 2018/4/23 by chencunjun
 */

/**
 * redis缓存命名空间常量
 */
public class RedisFiledConstant {
    public static String FILED_USER="tanqiu_user_info";      //用户信息
    public static String DIC_AREA="dic_area";      //地区码表
    public static String DIC_INFO="dic_info";      //字典码表

    public static String GAME_MESSAGE_TASK="tanqiu_game_message_task";//用户消息推送

    /**
     * add by chencunjun 2018/05/28
     */
    public static String BREAK_GAME_RECORD="tq_break_game_record" ;//闯关比赛当前赛场记录
    public static String BREAK_GAME_NOT_MATCH_PLAYER="tq_break_game_no_match_player" ;//闯关比赛当前赛场未匹配的玩家
    public static String BREAK_GAME_WAIT_PLAYER="tq_break_game_wait_player" ;//闯关比赛赢得关卡后等到开始的玩家
    public static String BREAK_GAME_PASS_RECORD="tq_break_game_pass_record" ;//闯关比赛赢得关卡后等到开始的玩家

    /**
     * 好友对战
     */
    public static String FIGHT_GAME_ROOM_RECORD="fight_game_room_record" ;//对战房间记录


    /**
     * 登录相关
     */
    public static String LOGIN_PASSWORD_FAIL="login_password_fail" ;//用户登录错误次数
    public static String LOGIN_ACCOUNT_LOCK="login_account_lock" ;//用户登录锁定
    public static final String USER_SESSION_KEY = "user_session_key:";

    /**
     * 邀请有礼
     */
    public final static String INVATE_PRIZE="invate_prize" ;//邀请有礼

//================================================================弹球新增=====================================
    public static final String TANQIU_GAME_MODEL_SINGLE_BALLNUM_AWARD = "tanqiu_game_model_single_ballNum_award";//用户单人模式单轮奖励的弹球总数
    public static final Object TANQIU_GAME_MODEL_SINGLE_RANK_SCORE_WEEK = "tanqiu_game_model_single_rank_score_week";//周排行榜
    public static final String TANQIU_GAME_MODEL_REDPACKET_RANK_SCORE = "tanqiu_game_model_redpacket_rank_score";//疯狂模式排行榜

    public static final Object TANQIU_GAME_MODEL_REDPACKET_FREE_PALY_TIMES = "tanqiu_game_model_redpacket_free_paly_times";//红包赛免费试玩次数


    public static final String REDPACKET_GAME_RECORD = "redpacket_game_record";
    public static final String REDPACKET_GAME_USER_RECORDS = "redpacket_game_user_records";
    public static final String FIGHT_ENTER_ROON_CONCURRENT_CONTROLL = "fight_enter_roon_concurrent_controll";
    public static final String FIGHT_MODEL_UNSENT_MESSAGE = "fight_model_unsent_message";

    /**
     * redis锁 key
     */
    public static final String REDIS_LOCK_TASK_WITHDRAW_ORDER_CHECK = "REDIS_LOCK_TASK_ORDER_CHECK";//订单查询任务
    public static final String CREATE_BREAK_GAME_LOCK = "create_break_lock";
    public static final String CLOSE_BREAK_GAME_LOCK = "close_break_lock";
    public static final String FIGHT_GAME_COMPLETE_LOCK = "fight_game_complete_lock";

    public static final String CREATE_REDPACKET_GAME_LOCK = "create_redpacket_game_lock";
    public static final String CLOSE_REDPACKET_GAME_LOCK = "close_redpacket_game_lock";


}
