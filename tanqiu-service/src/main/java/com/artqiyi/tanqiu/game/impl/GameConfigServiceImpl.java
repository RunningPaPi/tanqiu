package com.artqiyi.tanqiu.game.impl;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/03
 * Modify On: 2018/07/03 17:06 by wufuchang
 */

import com.artqiyi.tanqiu.common.constant.BreakGameConstants;
import com.artqiyi.tanqiu.common.constant.GameConstants;
import com.artqiyi.tanqiu.common.util.StringUtils;
import com.artqiyi.tanqiu.game.IGameConfigService;
import com.artqiyi.tanqiu.game.domain.GameConfig;
import com.artqiyi.tanqiu.game.domain.GameConfigExample;
import com.artqiyi.tanqiu.game.mapper.GameConfigMapper;
import com.artqiyi.tanqiu.game.vo.GameSingleModelInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 弹球游戏业务实现类
 */
@Service
public class GameConfigServiceImpl implements IGameConfigService {
    @Autowired
    private GameConfigMapper configMapper;

    @Override
    public GameSingleModelInfoVo getGameModelPageInfo(String gameModelKey) {
        List<GameConfig> gameConfigs = getConfigsByModelKeyAndCode(gameModelKey,null);
        if (gameConfigs==null||gameConfigs.size()==0) {
            throw new RuntimeException("后台没有配置该模式的配置信息");
        }
        GameSingleModelInfoVo pageInfoVo = new GameSingleModelInfoVo();
        List<GameConfig> ruleList = new ArrayList<>();
        for (GameConfig gameConfig : gameConfigs) {
            String typeKey = gameConfig.getTypeKey();
            String code = gameConfig.getCode();
            switch (typeKey) {
                case GameConstants.TANQIU_GAME_CONFIG_TYPE_KEY_SINGLE_SYSTEM:
                    switch (code) {
                        case GameConstants.TANQIU_GAME_CONFIG_CODE_BALLNUM_DEFAULT:
                            pageInfoVo.setBallNumDefault(Integer.valueOf(gameConfig.getTypeValue()));
                            break;
                        case GameConstants.TANQIU_GAME_CONFIG_CODE_BALLNUM_AWARD_FROM_FRIEND:
                            pageInfoVo.setBallNumAwardFromFriend(Integer.valueOf(gameConfig.getTypeValue()));
                            break;
                        case GameConstants.TANQIU_GAME_CONFIG_CODE_BALLNUM_AWARD_LIMIT:
                            pageInfoVo.setBallNumAwardLimit(Integer.valueOf(gameConfig.getTypeValue()));
                            break;
                        case GameConstants.TANQIU_GAME_CONFIG_CODE_SHARE_LIMIT_WHTHIN_GAME:
                            pageInfoVo.setShareLimitWithinGame(Integer.valueOf(gameConfig.getTypeValue()));
                            break;
                        case GameConstants.TANQIU_GAME_CONFIG_CODE_RECOVER_TYPE:
                            pageInfoVo.setRecoverType(Short.valueOf(gameConfig.getTypeValue()));
                            break;
                        case GameConstants.TANQIU_GAME_CONFIG_CODE_RECOVER_TIMES_LIMIT:
                            pageInfoVo.setRecoverTimesLimit(Integer.valueOf(gameConfig.getTypeValue()));
                        default:break;
                    }
                    break;
                case GameConstants.TANQIU_GAME_CONFIG_TYPE_KEY_SINGLE_RULE:
                    ruleList.add(gameConfig);
                    break;
                default:break;
            }
        }
        List<String> rules = ruleList.stream().sorted(Comparator.comparing(GameConfig::getSort)).map(gameConfig -> gameConfig.getTypeValue()).collect(Collectors.toList());
        pageInfoVo.setSingleModelRules(rules);
        return pageInfoVo;
    }


    public List<GameConfig> getConfigsByModelKeyAndCode(String gameModelKey,String configCode) {
        GameConfigExample gameConfigExample = new GameConfigExample();
        gameConfigExample.setOrderByClause("sort ASC");
        GameConfigExample.Criteria criteria = gameConfigExample.createCriteria();
        if (!StringUtils.isEmpty(gameModelKey)) {
            criteria.andGameModelKeyEqualTo(gameModelKey);
        }
        if (!StringUtils.isEmpty(configCode)) {
            criteria.andCodeEqualTo(configCode);
        }
        return configMapper.selectByExample(gameConfigExample);
    }

    /**
     * 根据gameKey 获取相关的code(Map的键)和value(Map的值)
     * @param gameModelKey
     * @return
     */
    @Override
    public Map<String, String> getByGameModelKey(String gameModelKey) {
        GameConfigExample breakGameConfigExample = new GameConfigExample();
        breakGameConfigExample.or().andGameModelKeyEqualTo(gameModelKey)
                .andStatusEqualTo(BreakGameConstants.GAME_STATUS_1);
        List<GameConfig> breakGameConfigs = configMapper.selectByExample(breakGameConfigExample);
        if (breakGameConfigs == null || breakGameConfigs.isEmpty()) {
            return null;
        }

        Map<String, String> gameConfigMap = new HashMap<>();

        for (GameConfig breakGameConfig : breakGameConfigs) {
            gameConfigMap.put(breakGameConfig.getCode(), breakGameConfig.getTypeValue());
        }
        return gameConfigMap;
    }

    /**
     * 获取gameModelKey类型typeKey的参数
     * @param gameModelKey
     * @param typeKey
     * @return
     */
    @Override
    public List<GameConfig> getByType(String gameModelKey, String typeKey) {
        GameConfigExample example = new GameConfigExample();
            example.or().andGameModelKeyEqualTo(gameModelKey).andTypeKeyEqualTo(typeKey)
                .andStatusEqualTo(BreakGameConstants.GAME_STATUS_1);
        List<GameConfig> breakGameConfigs = configMapper.selectByExample(example);
        if (breakGameConfigs == null || breakGameConfigs.isEmpty()) {
            return null;
        }
        return breakGameConfigs;
    }

    /**
     * 获取游戏规则
     * @param gameKey
     * @return
     */
    @Override
    public List<String> getGameRules(String gameKey) {
        List<GameConfig> breakGameConfigs = null;
        switch (gameKey) {
            case BreakGameConstants.BREAK:
                breakGameConfigs = getByType(gameKey, BreakGameConstants.BREAK_GAME_RULE);
                break;
            default:
                break;
        }
        if (breakGameConfigs == null || breakGameConfigs.isEmpty()) {
            return null;
        }
        List<String> rules = breakGameConfigs.stream()
                .map(e -> e.getTypeValue()).collect(Collectors.toList());
        return rules;
    }

    /**
     * 获取闯关规则
     * @param gameKey
     * @return
     */
    @Override
    public List<String> getBreakRules(String gameKey) {
        List<GameConfig> breakGameConfigs = getByType(gameKey, BreakGameConstants.BREAK_RULE);
        if (breakGameConfigs == null || breakGameConfigs.isEmpty()) {
            return null;
        }
        List<String> rules = breakGameConfigs.stream()
                .map(e -> e.getTypeValue()).collect(Collectors.toList());
        return rules;
    }
}
