package com.artqiyi.tanqiu.game.vo;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/23
 * Modify On: 2018/07/23 19:44 by wufuchang
 */

import java.util.List;

/**
 *  红包赛开始接口返回数据
 */
public class RedpacketGameBeginVo {
    private List<LayerVo> gameDataVoLevelOne;
    private List<LayerVo> gameDataVoLevelTwo;
    private List<LayerVo> gameDataVoLevelThree;

    public List<LayerVo> getGameDataVoLevelOne() {
        return gameDataVoLevelOne;
    }

    public void setGameDataVoLevelOne(List<LayerVo> gameDataVoLevelOne) {
        this.gameDataVoLevelOne = gameDataVoLevelOne;
    }

    public List<LayerVo> getGameDataVoLevelTwo() {
        return gameDataVoLevelTwo;
    }

    public void setGameDataVoLevelTwo(List<LayerVo> gameDataVoLevelTwo) {
        this.gameDataVoLevelTwo = gameDataVoLevelTwo;
    }

    public List<LayerVo> getGameDataVoLevelThree() {
        return gameDataVoLevelThree;
    }

    public void setGameDataVoLevelThree(List<LayerVo> gameDataVoLevelThree) {
        this.gameDataVoLevelThree = gameDataVoLevelThree;
    }
}
