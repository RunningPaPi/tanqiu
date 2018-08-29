package com.artqiyi.tanqiu.game.vo;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/23
 * Modify On: 2018/07/23 19:46 by wufuchang
 */

import java.util.List;

/**
 * 红包赛行数据
 */
public class LayerVo {
    /**
     *  DSC: 该行所拥有的砖块数量
     *  Val(取值范围)：(1，2，3，4)随机值
     */
    private Integer creatNum;
    /**
     * DATATYPE: 数组
     * DSC:   创建了几个砖块，数组就包含几个值
     */
    private List<BrickVo> data;

    public Integer getCreatNum() {
        return creatNum;
    }

    public void setCreatNum(Integer creatNum) {
        this.creatNum = creatNum;
    }

    public List<BrickVo> getData() {
        return data;
    }

    public void setData(List<BrickVo> data) {
        this.data = data;
    }
}
