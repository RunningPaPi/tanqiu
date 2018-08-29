package com.artqiyi.tanqiu.game.impl;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/24
 * Modify On: 2018/07/24 20:15 by wufuchang
 */

import com.artqiyi.tanqiu.game.IGameRedpacketPrizeConfigService;
import com.artqiyi.tanqiu.game.domain.GameRedpacketPrizeConfig;
import com.artqiyi.tanqiu.game.domain.GameRedpacketPrizeConfigExample;
import com.artqiyi.tanqiu.game.mapper.GameRedpacketPrizeConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class GameRedpacketPrizeConfigServiceImpl implements IGameRedpacketPrizeConfigService {
    @Autowired
    private GameRedpacketPrizeConfigMapper mapper;

    @Override

    public List<GameRedpacketPrizeConfig> getPirzeListSortedByRank() {
        GameRedpacketPrizeConfigExample example = new GameRedpacketPrizeConfigExample();
        example.setOrderByClause("rank ASC");
        example.or().andStatusEqualTo((short) 1);
        return mapper.selectByExample(example);
    }

}
