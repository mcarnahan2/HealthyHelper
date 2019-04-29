package edu.apsu.healthyhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.w3c.dom.Comment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBDataSourceExcercise {
    private SQLiteDatabase database;
    private MySqlListHelperExcercise databaseHelper;

    public DBDataSourceExcercise (Context context) {
        databaseHelper = new MySqlListHelperExcercise(context);
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Excercise createexcercise(String excerciseStr, int calories) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySqlListHelperExcercise.ExcerciseColumns.excercise_type.toString(), excerciseStr);

        contentValues.put(MySqlListHelperExcercise.ExcerciseColumns.calories.toString(), calories);
        //INSERT
        long id = database.insert(MySqlListHelperExcercise.EXCERCISE_TABLE, null, contentValues);

        //SELECT
        Cursor cursor = database.query(
                MySqlListHelperExcercise.EXCERCISE_TABLE,
                MySqlListHelperExcercise.ExcerciseColumns.excercises(),
                MySqlListHelperExcercise.ExcerciseColumns.excercise_id + " = " + id,
                null, null, null, null
        );


        cursor.moveToFirst();
        Excercise excercise = cursorToComment(cursor);
        cursor.close();

        return excercise;
    }

    public List<Excercise> getAllExcercise() {
        List<Excercise> excercises = new ArrayList<>();

        String[] columns = MySqlListHelperExcercise.ExcerciseColumns.excercises();

        Cursor cursor = database.query(MySqlListHelperExcercise.EXCERCISE_TABLE, columns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Excercise excercise = cursorToComment(cursor);
            excercises.add(excercise);
            cursor.moveToNext();
        }

        cursor.close();

        return excercises;
    }

    private Excercise cursorToComment(Cursor cursor) {
        Excercise excercises = new Excercise();

        int excerciseid = cursor.getInt(MySqlListHelperExcercise.ExcerciseColumns.excercise_id.ordinal());
        excercises.setExcercise_id(excerciseid);

        String excerciseStr = cursor.getString(MySqlListHelperExcercise.ExcerciseColumns.excercise_type.ordinal());
        excercises.setExcercise_type(excerciseStr);

        int calories = cursor.getInt(MySqlListHelperExcercise.ExcerciseColumns.calories.ordinal());
        excercises.setCalories(calories);

        return excercises;
    }

}

