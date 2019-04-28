package edu.apsu.healthyhelper;

public class Food {
    private int food_id;
    private String food;
    private int calories;
    private int excercise_id;
    private String excercise_type;

    public Food (){

    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getExcercise_id() {
        return excercise_id;
    }

    public void setExcercise_id(int excercise_id) {
        this.excercise_id = excercise_id;
    }

    public String getExcercise_type() {
        return excercise_type;
    }

    public void setExcercise_type(String excercise_type) {
        this.excercise_type = excercise_type;
    }

    @Override
    public String toString() {
        String tmp = food, excercise_type;
        if(tmp.length() > 20){
            tmp = tmp.substring(0, 20) + " ...";
        }
        return tmp;
    }



}


