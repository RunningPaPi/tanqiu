package com.artqiyi.tanqiu.system.service;
import com.artqiyi.tanqiu.system.domain.SystemConfig;
import com.artqiyi.tanqiu.system.domain.SystemConfigExample;
import com.artqiyi.tanqiu.system.mapper.SystemConfigMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("systemConfigService")
public class SystemConfigService {
    @Autowired
    private SystemConfigMapper systemConfigMapper;

 
    public int deleteById(long id) {
        return systemConfigMapper.deleteByPrimaryKey(id);
    }

    public SystemConfig selectById(long id) {
        return systemConfigMapper.selectByPrimaryKey(id);
    }

    public SystemConfig getByParamKey(String paramkey) {
        SystemConfigExample systemConfigExample = new SystemConfigExample();
        systemConfigExample.or().andParamKeyEqualTo(paramkey);
        List<SystemConfig> systemConfigList = systemConfigMapper.selectByExample(systemConfigExample);
        if(systemConfigList==null|| systemConfigList.isEmpty()){
            return null;
        }
        return systemConfigList.get(0);
    }
}
