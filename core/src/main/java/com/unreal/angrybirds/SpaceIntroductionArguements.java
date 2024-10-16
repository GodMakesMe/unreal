package com.unreal.angrybirds;

public class SpaceIntroductionArguements{
    protected String LevelName;
    protected String BackgroundImage;
    protected String PlayButton;
    protected String PlayButtonHover;
    protected String BackButton;
    protected String BackButtonHover;
//    protected LevelData data;
    SpaceIntroductionArguements(String name, String backGroundDir, String playDir, String playHover, String backButtonDir, String backButtonHover){
        this.LevelName = name;
        this.BackgroundImage = backGroundDir;
        this.PlayButton = playDir;
        this.PlayButtonHover = playHover;
        this.BackButton = backButtonDir;
        this.BackButtonHover = backButtonHover;
    }
}
