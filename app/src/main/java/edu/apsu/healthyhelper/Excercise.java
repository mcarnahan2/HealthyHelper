package edu.apsu.healthyhelper;

public class Excercise {
    private int calories;
    private String excercise_type;
    private int excercise_id;

    public Excercise() {

    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getExcercise_type() {
        return excercise_type;
    }

    public void setExcercise_type(String excercise_type) {
        this.excercise_type = excercise_type;
    }

    public int getExcercise_id() {
        return excercise_id;
    }

    public void setExcercise_id(int excercise_id) {
        this.excercise_id = excercise_id;
    }

    @Override
    public String toString() {
        String tmp = excercise_type;
        if(tmp.length() > 20){
            tmp = tmp.substring(0, 20) + " ...";
        }
        return tmp;
    }
}

