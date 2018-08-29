package com.artqiyi.tanqiu.game.impl;

import com.artqiyi.tanqiu.game.IRobotVideoConfigService;
import com.artqiyi.tanqiu.game.domain.RobotVideoConfig;
import com.artqiyi.tanqiu.game.domain.RobotVideoConfigExample;
import com.artqiyi.tanqiu.game.mapper.RobotVideoConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/5/8
 * Modify On: 2018/5/8 by chencunjun
 */
@Service
public class RobotVideoConfigServiceImpl implements IRobotVideoConfigService {
    @Autowired
    private RobotVideoConfigMapper robotVideoConfigMapper;

    @Override
    public List<RobotVideoConfig> selectByExample(RobotVideoConfigExample robotVideoConfigExample) {
        return robotVideoConfigMapper.selectByExample(robotVideoConfigExample);
    }

    @Override
    public RobotVideoConfig selectById(long id) {

        return robotVideoConfigMapper.selectByPrimaryKey((int)id);
    }
    @Override
    public long countByExample(RobotVideoConfigExample robotVideoConfigExample) {
        return robotVideoConfigMapper.countByExample(robotVideoConfigExample);
    }
}
