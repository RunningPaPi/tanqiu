package com.artqiyi.tanqiu.game;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/03
 * Modify On: 2018/07/03 17:05 by wufuchang
 */

import com.artqiyi.tanqiu.game.domain.GameConfig;
import com.artqiyi.tanqiu.game.vo.GameSingleModelInfoVo;

import java.util.List;
import java.util.Map;

/**
 * 弹球游戏业务接口
 */
public interface IGameConfigService {
    GameSingleModelInfoVo getGameModelPageInfo(String gameModelKey);

    List<GameConfig> getConfigsByModelKeyAndCode(String gameModelKey,String configCode) ;

    /**
     * 根据gameKey 获取相关的code(Map的键)和value(Map的值)
     * @param gameModelKey
     * @return
     */
    Map<String,String> getByGameModelKey(String gameModelKey);

    /**
     * 获取gameModelKey类型typeKey的参数
     * @param gameModelKey
     * @param typeKey
     * @return
     */
    List<GameConfig> getByType(String gameModelKey, String typeKey);
    /**
     * 获取游戏规则
     * @param gameKey
     * @return
     */
    List<String> getGameRules(String gameKey);

    /**
     * 获取闯关规则
     * @param gameKey
     * @return
     */
    List<String> getBreakRules(String gameKey);
}
