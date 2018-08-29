package com.artqiyi.tanqiu.game;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/03
 * Modify On: 2018/07/03 17:06 by wufuchang
 */

import com.artqiyi.tanqiu.aspect.AuthPassport;
import com.artqiyi.tanqiu.common.constant.GameConstants;
import com.artqiyi.tanqiu.common.constant.MsgConstant;
import com.artqiyi.tanqiu.common.constant.ResponseCodeConstant;
import com.artqiyi.tanqiu.common.util.StringUtils;
import com.artqiyi.tanqiu.game.vo.*;
import com.artqiyi.tanqiu.response.ApiResponse;
import com.artqiyi.tanqiu.response.DefaultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 弹球游戏rest接口
 */
@RestController
@RequestMapping("/api/game")
public class GameController {
    public static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private IGameConfigService gameConfigService;
    @Autowired
    private IGameSingleModelService gameSingleModelService;
    @Autowired
    private IGameRankRecordService gameRankRecordService;
    @Autowired
    private IGameSinglePrizeService gameSinglePrizeService;

    //点击相应模式 进入的界面 接口
    @GetMapping("/gameModelPageInfo")
    public ApiResponse getGameModelPageInfo(@RequestParam(value = "gameModelKey",required = true)String gameModelKey){
        ApiResponse response = new DefaultResponse();
        if (StringUtils.isEmpty(gameModelKey)) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:gameModelKey can not be null,or empty String");
            return response;
        }
        try {
            GameSingleModelInfoVo result = gameConfigService.getGameModelPageInfo(gameModelKey);
            response.setCode(ResponseCodeConstant.SUCCESS);
            response.setMsg(MsgConstant.SUCCESS_SEARCH);
            response.setResult(result);
        } catch (Exception e) {
            logger.error("异常"+e);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    //开始游戏 接口
    @GetMapping("/gameBegin")
    public ApiResponse gameBegin(@RequestParam(value = "userId",required = true)Long userId,@RequestParam(value = "gameModelKey",required = true)String gameModelKey){
        ApiResponse response = new DefaultResponse();
        if (userId==null&&userId<=0) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:userId can not be null,or,less than 0");
            return response;
        }
        if (StringUtils.isEmpty(gameModelKey)) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:gameModelKey can not be null,or empty String");
            return response;
        }
        try {
            GameBeginVo gameBeginVo = null;
            switch (gameModelKey) {
                case GameConstants.TANQIU_GAME_MODEL_KEY_SINGLE:
                     gameBeginVo = gameSingleModelService.gameBegin(userId);
                    break;
                default:break;
            }
            response.setCode(ResponseCodeConstant.SUCCESS);
            response.setMsg(MsgConstant.SUCCESS_TANQIU_BEGIN);
            response.setResult(gameBeginVo);
        } catch (Exception e) {
            logger.error("异常"+e);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    //好友助力 接口 取消了
    //@PostMapping("/assistanceByFriend")
    public ApiResponse assistanceByFriend(@RequestParam(value = "userId",required = true)Long userId,@RequestParam(value = "gameModelKey",required = true)String gameModelKey){
        ApiResponse response = new DefaultResponse();
        if (userId==null&&userId<=0) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:userId can not be null,or,less than 0");
            return response;
        }
        if (StringUtils.isEmpty(gameModelKey)) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:gameModelKey can not be null,or empty String");
            return response;
        }
        AssistanceResulltVo resulltVo = null;
        try {
            switch (gameModelKey) {
                case GameConstants.TANQIU_GAME_MODEL_KEY_SINGLE:
                   resulltVo =  gameSingleModelService.assistanceByFriend(userId);
                    break;
                default:break;
            }
            response.setCode(ResponseCodeConstant.SUCCESS);
            response.setMsg(MsgConstant.SUCCESS_FRIEND_ASSISTANCE);
            response.setResult(resulltVo);
        } catch (Exception e) {
            logger.error("异常"+e);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    //游戏结算 接口
    @AuthPassport(checkSign = true)
    @PostMapping("/gameEnd")
    public ApiResponse gameEnd(@RequestParam(value = "userId",required = true)Long userId,
                               @RequestParam(value = "userNickName",required = true)String userNickName ,
                               @RequestParam(value = "gameModelKey",required = true)String gameModelKey,
                               @RequestParam(value = "gameModelId",required = true)Long gameModelId,
                               @RequestParam(value = "score",required = true)Integer score){
        ApiResponse response = new DefaultResponse();
        if (userId==null&&userId<=0) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:userId can not be null,or,less than 0");
            return response;
        }
        if (gameModelId==null&&gameModelId<=0) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:gameModelId can not be null,or,less than 0");
            return response;
        }
        if (StringUtils.isEmpty(gameModelKey)) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:gameModelKey can not be null,or empty String");
            return response;
        }
        if (score==null) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:score can not be null");
            return response;
        }
        GameEndVo gameEndVo = null;
        try {
            switch (gameModelKey) {
                case GameConstants.TANQIU_GAME_MODEL_KEY_SINGLE:
                     gameEndVo = gameSingleModelService.gameEnd(userId,userNickName,gameModelKey,gameModelId,score);
                    break;
                default:throw new RuntimeException("gameKey NOT CORRECT!");
            }
            response.setCode(ResponseCodeConstant.SUCCESS);
            response.setMsg(MsgConstant.SUCCESS_TANQIU_END);
            response.setResult(gameEndVo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("异常"+e);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    //好友排行榜接口
    //@GetMapping("/friendRankBoard")
    public ApiResponse friendRankBoard(@RequestParam(value = "userId",required = true)Long userId,
                               @RequestParam(value = "page",required = false)Integer page,
                               @RequestParam(value = "pageSize",required = false)Integer pageSize){
        ApiResponse response = new DefaultResponse();
        if (userId==null&&userId<=0) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:userId can not be null,or,less than 0");
            return response;
        }
        RankBoardVo rankBoardVo = null;
        try {
            rankBoardVo = gameRankRecordService.friendRankBoard(userId,page,pageSize);
            response.setCode(ResponseCodeConstant.SUCCESS);
            response.setMsg(MsgConstant.SUCCESS_SEARCH);
            response.setResult(rankBoardVo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("异常"+e);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    //世界排行榜接口
    //@GetMapping("/worldRankBoard")
    public ApiResponse worldRankBoard(@RequestParam(value = "userId",required = true)Long userId,
                                      @RequestParam(value = "page",required = false)Integer page,
                                      @RequestParam(value = "pageSize",required = false)Integer pageSize){
        ApiResponse response = new DefaultResponse();
        if (userId==null&&userId<=0) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:userId can not be null,or,less than 0");
            return response;
        }

        RankBoardVo rankBoardVo = null;
        try {
            rankBoardVo = gameRankRecordService.worldRankBoard(userId,page,pageSize);
            response.setCode(ResponseCodeConstant.SUCCESS);
            response.setMsg(MsgConstant.SUCCESS_SEARCH);
            response.setResult(rankBoardVo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("异常"+e);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    //根据分数获取奖励 和下一级奖励的信息
    @AuthPassport(checkSign = true)
    @GetMapping("/getSingleModelPrizeByScore")
    public ApiResponse getSingleModelPrizeByScore(@RequestParam(value = "score",required = true)Integer score){
        ApiResponse response = new DefaultResponse();
        ScorePrizeVo scorePrizeVo = null;
        if (score==null) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:score can not be null");
            return response;
        }
        try {
            scorePrizeVo= gameSinglePrizeService.getSingleModelPrizeByScore(score);
            response.setCode(ResponseCodeConstant.SUCCESS);
            response.setMsg(MsgConstant.SUCCESS_SEARCH);
            response.setResult(scorePrizeVo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("异常"+e);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            response.setMsg(e.getMessage());
        }
        return response;
    }



}
