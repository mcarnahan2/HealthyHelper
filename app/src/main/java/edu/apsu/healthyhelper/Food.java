package edu.apsu.healthyhelper;

public class Food {
    private int food_id;
    private String food;
    private int calories;

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

    @Override
    public String toString() {
        String tmp = food;
        if(tmp.length() > 20){
            tmp = tmp.substring(0, 20) + " ...";
        }
        return tmp;
    }
}


