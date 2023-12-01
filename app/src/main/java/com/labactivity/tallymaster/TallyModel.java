package com.labactivity.tallymaster;

public class TallyModel {

    String b_title, c_goal, d_increment;


    TallyModel(){

    }

    public TallyModel(String b_title, String c_goal, String d_increment) {
        this.b_title = b_title;
        this.c_goal = c_goal;
        this.d_increment = d_increment;
    }

    public String getB_title() {
        return b_title;
    }

    public void setB_name(String b_title) {
        this.b_title = b_title;
    }

    public String getC_goal() {
        return c_goal;
    }

    public void setC_goal(String c_goal) {
        this.c_goal = c_goal;
    }

    public String getD_increment() {
        return d_increment;
    }

    public void setD_increment(String d_increment) {
        this.d_increment = d_increment;
    }

}
