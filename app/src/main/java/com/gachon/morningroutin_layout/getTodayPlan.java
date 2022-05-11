package com.gachon.morningroutin_layout;

public class getTodayPlan {
    public String wakeTime, sleepTime;
    public String type, specificType;
    public String input;

    public getTodayPlan(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public getTodayPlan(String TYPE, String specificType, String INPUT, String wakeTime, String sleepTime) {
        this.type = TYPE;
        this.input = INPUT;
        this.wakeTime = wakeTime;
        this.sleepTime = sleepTime;
        this.specificType = specificType;
    }


    public String getSpecificType() { return this.specificType; }
    public String getInput() {
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
