package edu.apsu.healthyhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBDataSourceExcercise {
    private SQLiteDatabase database;
    private MySqlListHelper databaseHelper;

    public DBDataSourceExcercise (Context context) {
        databaseHelper = new MySqlListHelper(context);
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Excercise createExcercise(String exerciseStr, int calories) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySqlListHelper.FoodColumns.ExcerciseColumns.excercise_type.toString(), exerciseStr);

        contentValues.put(MySqlListHelper.FoodColumns.ExcerciseColumns.calories.toString(), calories);
        //INSERT
        long id = database.insert(MySqlListHelper.EXCERCISE_TABLE, null, contentValues);

        //SELECT
        Cursor cursor = database.query(
                MySqlListHelper.EXCERCISE_TABLE,
                MySqlListHelper.FoodColumns.ExcerciseColumns.names(),
                MySqlListHelper.FoodColumns.ExcerciseColumns.excercise_id + " = " + id,
                null, null, null, null
        );

        cursor.moveToFirst();
        Excercise excercise = cursorToComment(cursor);
        cursor.close();

        return excercise;
    }

    public List<Excercise> getAllExcercise() {
        List<Excercise> excercises = new ArrayList<>();

        String[] columns = MySqlListHelper.FoodColumns.ExcerciseColumns.names();

        Cursor cursor = database.query(MySqlListHelper.EXCERCISE_TABLE, columns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Excercise excercise  = cursorToComment(cursor);
            excercises.add(excercise);
            cursor.moveToNext();
        }

        cursor.close();

        return excercises;
    }

    private Excercise cursorToComment(Cursor cursor) {
        Excercise excercises = new Excercise();

        int excerciseid = cursor.getInt(MySqlListHelper.FoodColumns.ExcerciseColumns.excercise_id.ordinal());
        excercises.setExcercise_id(excerciseid);

        String excerciseStr = cursor.getString(MySqlListHelper.FoodColumns.ExcerciseColumns.excercise_type.ordinal());
        excercises.setExcercise_type(excerciseStr);

        int calories = cursor.getInt(MySqlListHelper.FoodColumns.ExcerciseColumns.calories.ordinal());
        excercises.setCalories(calories);

        return excercises;
    }


}
