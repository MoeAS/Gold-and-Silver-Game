package project;

public class Player {
    private final String name;
    private int totalScore;

    Player(String name){
    	name = LogInScreen.getuname();
        this.name = name;
    }

    String getName(){
        return name;
    }

    int getTotalScore(){
        return totalScore;
    }

    void setTotalScore(int correctIn){
        totalScore += correctIn;
    }

}