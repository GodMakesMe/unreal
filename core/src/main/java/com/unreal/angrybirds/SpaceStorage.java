package com.unreal.angrybirds;
import java.util.ArrayList;

public class SpaceStorage {
    ArrayList<SpaceIntroductionArguements> spaceIntroductions;
    ArrayList<SpaceLevelArguements> spaceLevels;

    SpaceStorage(){
        spaceIntroductions = new ArrayList<SpaceIntroductionArguements>();
//        spaceIntroductions.add();
    }
    void addSpaceIntroduction(SpaceIntroductionArguements spaceIntroduction){
        if (!spaceIntroductions.contains(spaceIntroduction)){
            for (SpaceIntroductionArguements i : spaceIntroductions){
                if (i.LevelName.equals(spaceIntroduction.LevelName)){
                    removeSpaceIntroduction(spaceIntroduction);
                    break;
                }
            }
        }
    }
    void removeSpaceIntroduction(SpaceIntroductionArguements spaceIntroduction){
        spaceIntroductions.remove(spaceIntroduction);
    }
    void removeSpaceIntroduction(String LevelName){
        for (SpaceIntroductionArguements spaceIntroduction : spaceIntroductions){
            if (spaceIntroduction.LevelName.equals(LevelName)){
                removeSpaceIntroduction(spaceIntroduction);
                break;
            }
        }
    }
}

