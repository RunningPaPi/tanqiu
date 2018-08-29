package com.artqiyi.tanqiu.game.vo;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/23
 * Modify On: 2018/07/23 19:50 by wufuchang
 */

/**
 * 红包赛砖块
 */
public class BrickVo {
    /**
     *   DSC: 砖块所随机的位置,主要用于打乱生成的砖块
     *   VAL: (1，2，3，4)的随机值
     */
    private Integer pos;
    /**
     *  DSC: 砖块类型
     *  DSC-SUB: 主要有四种：Circle，FiveStar，Square，Triangle
     *  DSC-VAL: 1  ——   Circle
     *  DSC-VAL: 2  ——   FiveStar
     *  DSC-VAL: 3  ——   Square
     *  DSC-VAL: 4  ——   Triangle
     *  VAl: (1，2，3，4)的随机值
     */
    private Integer brickType;
    /**
     * DSC； 砖块的坐标信息，用于和pos一起描述具体位置
     * VAL:  x: (0 —— 49)取值范围
     *       x: (0 —— 19)取值范围
     */
    private PositionVo posXY;
    /**
     * DSC: 生命值，一个生命值代表一分
     * value = Math.random()*(layerNum*num*2)+1;
     *  arr.push(Math.ceil(value))
     */
    private Integer lifeVal;
    /**
     * DSC； 旋转角度
     * VAL; (1 —— 360)  的随机值
     */
    private Integer rotation;

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public Integer getBrickType() {
        return brickType;
    }

    public void setBrickType(Integer brickType) {
        this.brickType = brickType;
    }

    public PositionVo getPosXY() {
        return posXY;
    }

    public void setPosXY(PositionVo posXY) {
        this.posXY = posXY;
    }

    public Integer getLifeVal() {
        return lifeVal;
    }

    public void setLifeVal(Integer lifeVal) {
        this.lifeVal = lifeVal;
    }

    public Integer getRotation() {
        return rotation;
    }

    public void setRotation(Integer rotation) {
        this.rotation = rotation;
    }
}
