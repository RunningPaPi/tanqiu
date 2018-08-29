package com.artqiyi.tanqiu.game;

import com.artqiyi.tanqiu.base.service.IBaseService;
import com.artqiyi.tanqiu.game.domain.GameBreakUserRecords;
import com.artqiyi.tanqiu.game.domain.GameBreakUserRecordsExample;
import com.artqiyi.tanqiu.game.domain.GameRedpacketRewardRecord;
import com.artqiyi.tanqiu.game.domain.GameRedpacketRewardRecordExample;

import java.util.Map;

public interface IGameRedpacketRewardRecordService extends IBaseService<GameRedpacketRewardRecord,GameRedpacketRewardRecordExample> {
    Map repacketRewardNotice(Long userId);

    void repacketRewardNoticeRead(Long recordId);

}
