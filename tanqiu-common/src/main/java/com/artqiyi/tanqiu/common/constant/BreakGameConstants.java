package com.artqiyi.tanqiu.common.constant;

/**
 * 游戏相关常量
 */
public class BreakGameConstants {
    // 游戏模式
    public final static String SINGLE = "SINGLE";//单人模式
    public final static String BREAK = "BREAK";//闯关模式

    //SINGLE---type_key
    public final static String SINGLE_SYSTEM = "SINGLE_SYSTEM";//游戏系统相关配置
    public final static String SINGLE_GAME_RULE = "SINGLE_GAME_RULE";//游戏规则

    //BREAK---type_key
    public final static String BREAK_SYSTEM = "BREAK_SYSTEM";//游戏系统相关配置
    public final static String BREAK_GAME_RULE = "BREAK_GAME_RULE";//游戏规则
    public final static String BREAK_RULE = "BREAK_RULE";//闯关规则

    //通用 code
    public final static String TIME = "TIME";//游戏奖励发放时间
    public final static String MONEY = "MONEY";//奖池
    public final static String CONTEST = "CONTEST";//参与人数基数
    public final static String CHEAT = "CHEAT";//虚拟通关人数
    public final static String POSTER_URL = "POSTER_URL";//海报地址

    //SINGLE---code

    //BREAK--code
    public final static String BONUS_MIN = "BONUS_MIN";//最小红包
    public final static String BONUS_MAX = "BONUS_MAX";//最大红包
    public final static String LEVEL = "LEVEL";//关卡数
    public final static String DURATION = "DURATION";//游戏时长


    //游戏状态
    public final static Short GAME_STATUS_INVALID = 0;//无效
    public final static Short GAME_STATUS_VALID = 1;//正常

    //比赛状态：0.未开赛 1.比赛中 2.比赛结束
    public final static short GAME_STATUS_0 = 0;
    public final static short GAME_STATUS_1 = 1;
    public final static short GAME_STATUS_2 = 2;


    //定时任务阈值
    public final static int THRESHOLD = 5;//分钟

    //游戏数据过期时间25h
    public final static int GAME_DATA_EXP = 90000;

}
