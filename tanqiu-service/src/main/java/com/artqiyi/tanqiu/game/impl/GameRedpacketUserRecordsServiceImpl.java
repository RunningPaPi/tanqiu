package com.artqiyi.tanqiu.game.impl;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/24
 * Modify On: 2018/07/24 16:36 by wufuchang
 */

import com.artqiyi.tanqiu.game.IGameRedpacketUserRecordsService;
import com.artqiyi.tanqiu.game.domain.GameRedpacketUserRecords;
import com.artqiyi.tanqiu.game.domain.GameRedpacketUserRecordsExample;
import com.artqiyi.tanqiu.game.mapper.GameRedpacketUserRecordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class GameRedpacketUserRecordsServiceImpl implements IGameRedpacketUserRecordsService {
    @Autowired
    private GameRedpacketUserRecordsMapper mapper;

    @Override
    public void saveOrUpdate(GameRedpacketUserRecords userRecords) {
        if(null==userRecords.getId() || userRecords.getId()==0){
            mapper.insertSelective(userRecords);
        }else{
            mapper.updateByPrimaryKeySelective(userRecords);
        }
    }

    @Override
    public Integer getContestNum(String gameNo) {
        GameRedpacketUserRecordsExample example = new GameRedpacketUserRecordsExample();
        example.or().andIsRobotEqualTo(false).andGameNoEqualTo(gameNo);
        return Integer.valueOf(mapper.countByExample(example)+"");
    }

    @Override
    public Integer getCheatedNum(String gameNo) {
        GameRedpacketUserRecordsExample example = new GameRedpacketUserRecordsExample();
        example.or().andIsRobotEqualTo(true).andGameNoEqualTo(gameNo);
        return Integer.valueOf(mapper.countByExample(example)+"");
    }

    @Override
    public List<GameRedpacketUserRecords> getCheatedRobot(String gameNo) {
        GameRedpacketUserRecordsExample example = new GameRedpacketUserRecordsExample();
        example.or().andGameNoEqualTo(gameNo).andIsRobotEqualTo(true);
        return mapper.selectByExample(example);
    }
}
