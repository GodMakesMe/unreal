package com.unreal.angrybirds;

import java.io.Serializable;

public class Player implements Serializable {
    int Score;
    int winScore;
    int maxScore;
    int birdsLeft;
    int totalBirds;
    public Player() {
        Score = 0;
    }
    public void setScore(int score) {
        Score = score;
    }
    public int getScore() {
        return Score;
    }
    public void setWinScore(int allSumPig){
        this.winScore = allSumPig;
    }
    public boolean hasWin(){
        return Score >= winScore;
    }
    public void setMaxScore(int allSum){
        this.maxScore = allSum;
    }
    public int getBirdsUsed(){
        return totalBirds - birdsLeft;
    }
    public int calculateStar(){
        if (!hasWin()) return 0;
        int birdValue = (maxScore - winScore)/3;
        if (getScore() + birdValue*birdsLeft > winScore + birdValue*2){
            return 3;
        }else if (getScore() + birdValue*birdsLeft > winScore + birdValue){
            return 2;
        }else if (getScore() + birdValue*birdsLeft > winScore){
            return 1;
        }else{
            return 0;
        }
    }
}
