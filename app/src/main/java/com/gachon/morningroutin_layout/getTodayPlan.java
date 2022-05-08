package com.gachon.morningroutin_layout;

public class getTodayPlan {
    public String wakeTime, sleepTime;
    public String type;
    public String input;

    public getTodayPlan(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public getTodayPlan(String TYPE, String USER_INPUT_DATA, String wakeTime, String sleepTime) {
        this.type = TYPE;
        this.input = USER_INPUT_DATA;
        this.wakeTime = wakeTime;
        this.sleepTime = sleepTime;
    }


    public String getUserInputData() {
        return this.input;
    }
    public String getType() {
        return this.type;
    }
    public String getWakeTime() {
        return this.wakeTime;
    }
    public String getSleepTime() {
        return this.sleepTime;
    }
}
