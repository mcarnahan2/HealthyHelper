package edu.apsu.healthyhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;

public class MySqlListHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "food.sqlite";
    private static final int DB_VERSION = 2;

    public static final String FOOD_TABLE = "Food";
    public enum FoodColumns {
        food_id, food, calories;

        public static String[] names(){
            FoodColumns[] v = values();
            String [] names = new String[v.length];
            for(int i =0; i <v.length; i++){
                names[i] = v[i].toString();
            }
            return names;
        }
    }

    public MySqlListHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + FOOD_TABLE + " ( " +
                FoodColumns.food_id + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                FoodColumns.food + " TEXT NOT NULL, " +
                FoodColumns.calories + " INTEGER NOT NULL " +
                ")";

        sqLiteDatabase.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
