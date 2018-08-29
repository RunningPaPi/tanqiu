package com.artqiyi.tanqiu.game;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/23
 * Modify On: 2018/07/23 18:15 by wufuchang
 */

import com.artqiyi.tanqiu.game.domain.GameRedpacketRecords;
import com.artqiyi.tanqiu.game.vo.LayerVo;
import com.artqiyi.tanqiu.game.vo.RedpacketGameBeginVo;
import com.artqiyi.tanqiu.game.vo.RedpacketModelInfoVo;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface IGameRedpackService {
    RedpacketModelInfoVo getRedpacketModelInfo4User(Long userId);

    Map redpacketGameBegin(Long userId,String nickName,String headUrl,Long gameModelId,String gameModelKey) ;

    void redpacketGameEnd(Map param);

    Map redpacketUserEnd(Long userId, Integer score, String gameNo);

    GameRedpacketRecords createRedpacketRecord(Long gameModelId, String gameModelKey);
}
