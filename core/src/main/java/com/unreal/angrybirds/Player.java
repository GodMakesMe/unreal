package com.unreal.angrybirds;

import java.io.Serializable;

public class Player implements Serializable {
    int Score;
    public Player() {
        Score = 0;
    }
    public void setScore(int score) {
        Score = score;
    }
    public int getScore() {
        return Score;
    }
}
