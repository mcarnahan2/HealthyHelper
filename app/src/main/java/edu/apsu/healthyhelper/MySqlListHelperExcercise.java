package edu.apsu.healthyhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlListHelperExcercise extends SQLiteOpenHelper {
    private static final String DB_NAME = "excercise.sqlite";
    private static final int DB_VERSION = 2;

    public static final String EXCERCISE_TABLE ="Excercise" ;

    public enum ExcerciseColumns {
        excercise_id, excercise_type, calories;

        public static String[] excercises() {
            ExcerciseColumns[] v = values();
            String[] excercises = new String[v.length];
            for (int i = 0; i < v.length; i++) {
                excercises[i] = v[i].toString();
            }
            return excercises;
        }
    }

    public MySqlListHelperExcercise(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE " + EXCERCISE_TABLE + " ( " +
                ExcerciseColumns.excercise_id + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                ExcerciseColumns.excercise_type + " TEXT NOT NULL, " +
                ExcerciseColumns.calories + " INTEGER NOT NULL " +
                ")";

        sqLiteDatabase.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

