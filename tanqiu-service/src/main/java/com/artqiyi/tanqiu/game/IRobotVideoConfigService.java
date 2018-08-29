package com.artqiyi.tanqiu.game;


import com.artqiyi.tanqiu.base.service.IBaseService;
import com.artqiyi.tanqiu.game.domain.RobotVideoConfig;
import com.artqiyi.tanqiu.game.domain.RobotVideoConfigExample;

import java.util.List;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/5/8
 * Modify On: 2018/5/8 by chencunjun
 */
public interface IRobotVideoConfigService{
    List<RobotVideoConfig>  selectByExample(RobotVideoConfigExample robotVideoConfigExample);

    RobotVideoConfig selectById(long id);

    long countByExample(RobotVideoConfigExample robotVideoConfigExample);

}
