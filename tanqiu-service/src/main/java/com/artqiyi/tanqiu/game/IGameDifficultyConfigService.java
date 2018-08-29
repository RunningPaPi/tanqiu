package com.artqiyi.tanqiu.game;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/23
 * Modify On: 2018/07/23 18:12 by wufuchang
 */

import com.artqiyi.tanqiu.game.domain.GameDifficultyConfig;
import com.artqiyi.tanqiu.game.domain.GameDifficultyConfigExample;

import java.util.List;

/**
 *
 */
public interface IGameDifficultyConfigService {
    List<GameDifficultyConfig> selectByExample(GameDifficultyConfigExample configExample);
}
