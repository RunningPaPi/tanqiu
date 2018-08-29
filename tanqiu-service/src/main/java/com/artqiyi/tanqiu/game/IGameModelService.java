package com.artqiyi.tanqiu.game;

import com.artqiyi.tanqiu.base.service.IBaseService;
import com.artqiyi.tanqiu.game.domain.GameModel;
import com.artqiyi.tanqiu.game.domain.GameModelExample;

import java.util.List;

public interface IGameModelService extends IBaseService<GameModel,GameModelExample> {
    List<GameModel> getAllGame();
    GameModel getByGameModelKey(String gameKey);
}
