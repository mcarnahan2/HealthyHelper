package edu.apsu.healthyhelper;

public class Water {
    private int water_id;
    private String bottle_count;

    public Water () {
    }

    public int getWater_id() {
        return water_id;
    }

    public void setWater_id(int water_id) {
        this.water_id = water_id;
    }

    public String getBottle_count() {
        return bottle_count;
    }

    public void setBottle_count(String bottle_count) {
        this.bottle_count = bottle_count;
    }

    @Override
    public String toString() {
        String tmp = bottle_count;
        if(tmp.length() > 20){
            tmp = tmp.substring(0, 20) + " ...";
        }
        return tmp;
    }
}
