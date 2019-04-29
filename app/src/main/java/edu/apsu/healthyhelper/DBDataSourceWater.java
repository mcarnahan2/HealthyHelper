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

public class DBDataSourceWater {
    private SQLiteDatabase database;
    private MySqlListHelperWater databaseHelper;

    public DBDataSourceWater (Context context) {
        databaseHelper = new MySqlListHelperWater(context);
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Water createwater(int bottle_count) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySqlListHelperWater.WaterColumns.bottle_count.toString(), bottle_count);

        //INSERT
        long id = database.insert(MySqlListHelperWater.WATER_TABLE, null, contentValues);

        //SELECT
        Cursor cursor = database.query(
                MySqlListHelperWater.WATER_TABLE,
                MySqlListHelperWater.WaterColumns.waters(),
                MySqlListHelperWater.WaterColumns.water_id + " = " + id,
                null, null, null, null
        );


        cursor.moveToFirst();
        Water water = cursorToComment(cursor);
        cursor.close();

        return water;
    }

    public List<Water> getAllWater() {
        List<Water> waters = new ArrayList<>();

        String[] columns = MySqlListHelperWater.WaterColumns.waters();

        Cursor cursor = database.query(MySqlListHelperWater.WATER_TABLE, columns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Water water = cursorToComment(cursor);
            waters.add(water);
            cursor.moveToNext();
        }

        cursor.close();

        return waters;
    }

    private Water cursorToComment(Cursor cursor) {
        Water waters = new Water();

        int waterid = cursor.getInt(MySqlListHelperWater.WaterColumns.water_id.ordinal());
        waters.setWater_id(waterid);

        int bottleCount = cursor.getInt(MySqlListHelperWater.WaterColumns.bottle_count.ordinal());
        waters.setBottle_count(bottleCount);

        return waters;
    }

}

