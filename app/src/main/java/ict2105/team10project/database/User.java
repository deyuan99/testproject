package ict2105.team10project.database;

public class User {
    private String userId;
    private String name;
    private int Level;
    private int exp;
    private boolean isStepsChallengeCompleted;
    private boolean isPushupChallengeCompleted;
    private boolean isSprintChallengeCompleted;
    private int stepsChallengeAttempts;
    private int pushupChallengeAttempts;
    private int sprintChallengeAttempts;
    private int stepsTotal;
    private int pushupTotal;
    private int sprintTotal;
    private int athletePower;                   // Raw score for player's average ability
    private int rankingPoints;                  // Score that will be displayed in leaderboard (multiplied with average challenges attempts)
    private int incrementalPoints;              // Total points that the user has won OR lose

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public boolean isStepsChallengeCompleted() {
        return isStepsChallengeCompleted;
    }

    public void setStepsChallengeCompleted(boolean stepsChallengeCompleted) {
        isStepsChallengeCompleted = stepsChallengeCompleted;
    }

    public boolean isPushupChallengeCompleted() {
        return isPushupChallengeCompleted;
    }

    public void setPushupChallengeCompleted(boolean pushupChallengeCompleted) {
        isPushupChallengeCompleted = pushupChallengeCompleted;
    }

    public boolean isSprintChallengeCompleted() {
        return isSprintChallengeCompleted;
    }

    public void setSprintChallengeCompleted(boolean sprintChallengeCompleted) {
        isSprintChallengeCompleted = sprintChallengeCompleted;
    }

    public int getStepsChallengeAttempts() {
        return stepsChallengeAttempts;
    }

    public void setStepsChallengeAttempts(int stepsChallengeAttempts) {
        this.stepsChallengeAttempts = stepsChallengeAttempts;
    }

    public int getPushupChallengeAttempts() {
        return pushupChallengeAttempts;
    }

    public void setPushupChallengeAttempts(int pushupChallengeAttempts) {
        this.pushupChallengeAttempts = pushupChallengeAttempts;
    }

    public int getSprintChallengeAttempts() {
        return sprintChallengeAttempts;
    }

    public void setSprintChallengeAttempts(int sprintChallengeAttempts) {
        this.sprintChallengeAttempts = sprintChallengeAttempts;
    }

    public int getStepsTotal() {
        return stepsTotal;
    }

    public void setStepsTotal(int stepsTotal) {
        this.stepsTotal = stepsTotal;
    }

    public int getPushupTotal() {
        return pushupTotal;
    }

    public void setPushupTotal(int pushupTotal) {
        this.pushupTotal = pushupTotal;
    }

    public int getSprintTotal() {
        return sprintTotal;
    }

    public void setSprintTotal(int sprintTotal) {
        this.sprintTotal = sprintTotal;
    }

    public int getAthletePower() {
        return athletePower;
    }

    public void setAthletePower(int athletePower) {
        this.athletePower = athletePower;
    }

    public int getRankingPoints() {
        return rankingPoints;
    }

    public void setRankingPoints(int rankingPoints) {
        this.rankingPoints = rankingPoints;
    }

    public int getIncrementalPoints() {
        return incrementalPoints;
    }

    public void setIncrementalPoints(int incrementalPoints) {
        this.incrementalPoints = incrementalPoints;
    }
}
