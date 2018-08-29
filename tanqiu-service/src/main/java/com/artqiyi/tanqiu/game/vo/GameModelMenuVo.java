package com.artqiyi.tanqiu.game.vo;

public class GameModelMenuVo {
    private Long gameId;
    private String gameName;
    private String gameKey;
    private String bgImg;
    private String bgImgX;
    private String iconUrlX;
    private String iconUrl;
    private boolean isOpen;
    private int currentPlayers;
    private String gameNo;

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameKey(String gameKey) {
        this.gameKey = gameKey;
    }

    public String getGameKey() {
        return gameKey;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }

    public String getBgImg() {
        return bgImg;
    }

    public void setBgImgX(String bgImgX) {
        this.bgImgX = bgImgX;
    }

    public String getBgImgX() {
        return bgImgX;
    }

    public void setIconUrlX(String iconUrlX) {
        this.iconUrlX = iconUrlX;
    }

    public String getIconUrlX() {
        return iconUrlX;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean getIsOpen() {
        return isOpen;
    }

    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public void setGameNo(String gameNo) {
        this.gameNo = gameNo;
    }

    public String getGameNo() {
        return gameNo;
    }
}
