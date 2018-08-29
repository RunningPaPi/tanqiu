package com.artqiyi.tanqiu.common.constant;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/05/05
 * Modify On: 2018/05/05 16:05 by wufuchang
 */

/**
 * 游戏常量类
 */
public class GameConstants {

    //闯关比赛复活花费类型
    public static final int BREAK_GAME_RECOVER_COST_TYPE_COIN=1;//趣币
    public static final int BREAK_GAME_RECOVER_COST_TYPE_POINT=2;//积分
    public static final int BREAK_GAME_RECOVER_COST_TYPE_BALANCE=3;//红包

//=============================================================================弹球新增======================================================

    //model key
    public static final String TANQIU_GAME_MODEL_KEY_SINGLE = "SINGLE";//游戏模式-单机模式
    public static final String TANQIU_GAME_MODEL_KEY_REDPACKET = "REDPACKET";//红包赛gamekey
    public static final String TANQIU_GAME_MODEL_KEY_FIGHT = "FIGHT";
    public static final String TANQIU_GAME_MODEL_KEY_BREAK = "BREAK";

    //config type key
    public static final String TANQIU_GAME_CONFIG_TYPE_KEY_SINGLE_RULE = "SINGLE_RULE";//单人模式规则key
    public static final String TANQIU_GAME_CONFIG_TYPE_KEY_REDPACKET_RULE = "REDPACKET_RULE";//红包赛模式规则key
    public static final String TANQIU_GAME_CONFIG_TYPE_KEY_SINGLE_SYSTEM = "SINGLE_SYSTEM";//单人模式系统配置key
    public static final String TANQIU_GAME_CONFIG_TYPE_KEY_REDPACKET_SYSTEM = "REDPACKET_SYSTEM";//红包赛模式系统配置key


    //config code
    public static final String TANQIU_GAME_CONFIG_CODE_BALLNUM_DEFAULT = "BALLNUM_DEFAULT";//默认弹球数code
    public static final String TANQIU_GAME_CONFIG_CODE_BALLNUM_AWARD_FROM_FRIEND = "BALLNUM_AWARD_FROM_FRIEND";//好友助力奖励弹球数code
    public static final String TANQIU_GAME_CONFIG_CODE_BALLNUM_AWARD_LIMIT = "BALLNUM_AWARD_LIMIT";//弹球个数上限
    public static final String TANQIU_GAME_CONFIG_CODE_SHARE_LIMIT_WHTHIN_GAME = "SHARE_LIMIT_WHTHIN_GAME";//游戏内分享限制次数
    public static final String TANQIU_GAME_CONFIG_CODE_RECOVER_TIMES_LIMIT = "RECOVER_TIMES_LIMIT";//复活次数限制
    public static final String TANQIU_GAME_CONFIG_CODE_RECOVER_TYPE= "RECOVER_TYPE";//复活模式 单人模式复活方式 1：分享复活 2：看广告复活 3：其他待定
    public static final String TANQIU_GAME_CONFIG_CODE_GAME_DURING_TIME = "GAME_DURING_TIME";//好友对战模式
    public static final String TANQIU_GAME_CONFIG_CODE_GAME_DATA_OVER_TIME = "GAME_DATA_OVER_TIME";
    public static final String TANQIU_GAME_CONFIG_CODE_END_PK_TASK_EXTRA_TIME = "GAME_END_PK_TASK_EXTRA_TIME";

    public static final String TANQIU_GAME_CONFIG_CODE_CHEAT_PLAYER_NUM = "CHEAT_PLAYER_NUM";//作弊人数数量
    public static final String TANQIU_GAME_CONFIG_CODE_CHEAT_SCORE_FLOOR_RANGE = "CHEAT_SCORE_FLOOR_RANGE";//作弊分数下线
    public static final String TANQIU_GAME_CONFIG_CODE_FREE_PLAY_TIMES = "FREE_PLAY_TIMES";//免费试玩次数
    public static final String TANQIU_GAME_CONFIG_CODE_PLAY_COST= "PLAY_COST";//参赛费用
    public static final String TANQIU_GAME_CONFIG_LEVEL= "LEVEL";//关卡数
    public static final String TANQIU_GAME_CONFIG_TIME= "TIME";//关卡数

    public static final int TANQIU_SINGLE_MODEL_RANKBOARD_TOP_LIMIT = 50;//排行榜显示上限 即Top50
    public static final int TANQIU_REDPACKET_MODEL_RANKBOARD_TOP_LIMIT = 100;//红包赛排行榜显示上限 即Top50

}
