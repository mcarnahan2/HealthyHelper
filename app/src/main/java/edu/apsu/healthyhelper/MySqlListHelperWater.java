package edu.apsu.healthyhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlListHelperWater extends SQLiteOpenHelper {
    private static final String DB_NAME = "water.sqlite";
    private static final int DB_VERSION = 2;

    public static final String WATER_TABLE ="Water" ;

    public enum WaterColumns {
        water_id, bottle_count;

        public static String[] waters() {
            WaterColumns[] v = values();
            String[] waters = new String[v.length];
            for (int i = 0; i < v.length; i++) {
                waters[i] = v[i].toString();
            }
            return waters;
        }
    }

    public MySqlListHelperWater(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE " + WATER_TABLE + " ( " +
                WaterColumns.water_id + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                WaterColumns.bottle_count + " TEXT NOT NULL " +
                ")";

        sqLiteDatabase.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}


