package com.artqiyi.tanqiu.game.impl;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/24
 * Modify On: 2018/07/24 14:26 by wufuchang
 */

import com.artqiyi.tanqiu.game.IGameDifficultyConfigService;
import com.artqiyi.tanqiu.game.domain.GameDifficultyConfig;
import com.artqiyi.tanqiu.game.domain.GameDifficultyConfigExample;
import com.artqiyi.tanqiu.game.mapper.GameDifficultyConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class GameDifficultyConfigServiceImpl implements IGameDifficultyConfigService {
    @Autowired
    private GameDifficultyConfigMapper gameDifficultyConfigMapper;

    @Override
    public List<GameDifficultyConfig> selectByExample(GameDifficultyConfigExample configExample) {
        return gameDifficultyConfigMapper.selectByExample(configExample);
    }

}
