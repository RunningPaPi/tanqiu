package com.artqiyi.tanqiu.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class GameNoGeneratorUtil {

    /**
     * 根据gameKey生成一天一次的游戏场次编号。如BREAK_2018_07_14_10_43
     *
     * @param gameKey
     * @return
     */
    public static String dailyGameNo(String gameKey) {
        String dateFormat = "YYYY_MM_dd_HH_mm";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return gameKey + "_" + sdf.format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(dailyGameNo("BREAK"));
    }
}
