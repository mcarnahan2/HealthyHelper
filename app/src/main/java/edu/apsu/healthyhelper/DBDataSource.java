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

public class DBDataSource {
    private SQLiteDatabase database;
    private MySqlListHelper databaseHelper;

    public DBDataSource(Context context) {
        databaseHelper = new MySqlListHelper(context);
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Food createfood(String foodStr, int calories) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySqlListHelper.FoodColumns.food.toString(), foodStr);

        contentValues.put(MySqlListHelper.FoodColumns.calories.toString(), calories);
        //INSERT
        long id = database.insert(MySqlListHelper.FOOD_TABLE, null, contentValues);

        //SELECT
        Cursor cursor = database.query(
                MySqlListHelper.FOOD_TABLE,
                MySqlListHelper.FoodColumns.names(),
                MySqlListHelper.FoodColumns.food_id + " = " + id,
                null, null, null, null
        );

        cursor.moveToFirst();
        Food food = cursorToComment(cursor);
        cursor.close();

        return food;
    }

    public List<Food> getAllFood() {
        List<Food> foods = new ArrayList<>();

        String[] columns = MySqlListHelper.FoodColumns.names();

        Cursor cursor = database.query(MySqlListHelper.FOOD_TABLE, columns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Food food = cursorToComment(cursor);
            foods.add(food);
            cursor.moveToNext();
        }

        cursor.close();

        return foods;
    }

    private Food cursorToComment(Cursor cursor) {
        Food foods = new Food();

        int foodid = cursor.getInt(MySqlListHelper.FoodColumns.food_id.ordinal());
        foods.setFood_id(foodid);

        String foodStr = cursor.getString(MySqlListHelper.FoodColumns.food.ordinal());
        foods.setFood(foodStr);

        int calories = cursor.getInt(MySqlListHelper.FoodColumns.calories.ordinal());
        foods.setCalories(calories);

        return foods;
    }

}
