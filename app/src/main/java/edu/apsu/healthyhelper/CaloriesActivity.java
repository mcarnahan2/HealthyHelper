package edu.apsu.healthyhelper;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import static edu.apsu.healthyhelper.MySqlListHelperWater.WaterColumns.bottle_count;

public class CaloriesActivity extends MenuActivity {
    private DBDataSource dataSource;
    private DBDataSourceExcercise dataSourceExcercise;
    private DBDataSourceWater dataSourceWater;
    private ListView listView;
    private float foodAndDrinkCal = 0;
    private TextView fADtextView;
    private String fADStr;
    private String calLeftStr;
    private float calLeft =0;
    private TextView leftTextView;
    private String calExStr;
    private float calEx =0;
    private TextView exTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final Float calTotal = prefs.getFloat("calories", 0);
        String totalCalStr = calTotal.toString();

        TextView tv = findViewById(R.id.total_calories_textView);
        tv.setText(totalCalStr);

        tv = findViewById(R.id.total_calc_calories_textView);
        tv.setText(totalCalStr);

        fADtextView = findViewById(R.id.food_textView);
        leftTextView = findViewById(R.id.used_calories_textView);
        exTextView = findViewById(R.id.exercise_textView);

        if(prefs.contains("foodCal")){
            foodAndDrinkCal = prefs.getFloat("foodCal", 0);
            fADStr = Float.toString(foodAndDrinkCal);
            fADtextView.setText(fADStr);
            Log.i("Cal", "Food is " + fADStr);
        }
        if(prefs.contains("calLeft")){
            calLeft = prefs.getFloat("calLeft", 0);
            calLeftStr = Float.toString(calLeft);
            leftTextView.setText(calLeftStr);
            Log.i("Cal", "Cal left is " + calLeftStr);
        }
        if(prefs.contains("calEx")){
            calEx = prefs.getFloat("calEx", 0);
            calExStr = Float.toString(calEx);
            exTextView.setText(calExStr);
            Log.i("Cal", "Exercise is " + calExStr);
        }


        dataSourceWater = new DBDataSourceWater(this);
        dataSourceExcercise = new DBDataSourceExcercise(this);
        dataSource = new DBDataSource(this);

        final ListView food_listView = findViewById(R.id.food_listView);
        final ListView excercise_listView = findViewById(R.id.exercise_listView);
        final ListView water_listView = findViewById(R.id.water_listView);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.food_edit_text, null, false);
        final View dialogView1 = inflater.inflate(R.layout.excercise_edit_text, null, false);
        final View dialogView2 = inflater.inflate(R.layout.water_edit_text, null, false);

        Button foodButton = findViewById(R.id.add_food);
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CaloriesActivity.this);
                builder.setView(dialogView)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText etfood = dialogView.findViewById(R.id.editText);
                                String foodStr = etfood.getText().toString().trim();
                                if (foodStr.length() == 0) {
                                    return;
                                }
                                EditText etCalories = dialogView.findViewById(R.id.editText2);
                                int calories = Integer.parseInt(etCalories.getText().toString().trim());
                                String cal = String.valueOf(calories);

                                foodAndDrinkCal+=calories;
                                fADStr = String.valueOf(foodAndDrinkCal);
                                fADtextView.setText(fADStr);

                                calLeft=calTotal-foodAndDrinkCal;
                                calLeftStr = String.valueOf(calLeft);
                                leftTextView.setText(calLeftStr);

                                Food food = dataSource.createfood(foodStr, calories);

                                ArrayAdapter<Food> adapter = (ArrayAdapter<Food>) food_listView.getAdapter();
                                adapter.add(food);
                                adapter.notifyDataSetChanged();

                                sharedPrefs();

                            }

                        })
                        .setNegativeButton("Cancel", null);

                builder.create().show();


            }

        });

        Button excerciseButton = findViewById(R.id.add_excercise);
        excerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CaloriesActivity.this);
                builder.setView(dialogView1)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText etexcercise = dialogView1.findViewById(R.id.editText);
                                String excerciseStr = etexcercise.getText().toString().trim();
                                if (excerciseStr.length() == 0) {
                                    return;
                                }
                                EditText etCalories = dialogView1.findViewById(R.id.editText2);
                                int calories = Integer.parseInt(etCalories.getText().toString().trim());

                                calEx+=calories;
                                calExStr = String.valueOf(calEx);
                                exTextView.setText(calExStr);

                                calLeft=calTotal+calEx;
                                calLeftStr = String.valueOf(calLeft);
                                leftTextView.setText(calLeftStr);

                                Excercise excercise = dataSourceExcercise.createexcercise(excerciseStr, calories);

                                ArrayAdapter<Excercise> adapter = (ArrayAdapter<Excercise>) excercise_listView.getAdapter();
                                adapter.add(excercise);
                                adapter.notifyDataSetChanged();

                                sharedPrefs();
                            }
                        })
                        .setNegativeButton("Cancel", null);

                builder.create().show();

            }
        });

        Button waterButton = findViewById(R.id.add_water);
        waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CaloriesActivity.this);
                builder.setView(dialogView2)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText etwater = dialogView2.findViewById(R.id.editText2);
                                String waterStr = etwater.getText().toString().trim();
                                if (waterStr.length() == 0) {
                                    return;
                                }

                                Water water = dataSourceWater.createwater(waterStr);

                                ArrayAdapter<Water> adapter = (ArrayAdapter<Water>) water_listView.getAdapter();
                                adapter.add(water);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", null);

                builder.create().show();

            }
        });
    }

    private void sharedPrefs() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("foodCal", foodAndDrinkCal);
        editor.putFloat("calLeft", calLeft);
        editor.putFloat("calEx", calEx);
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();

        dataSource.open();
        dataSourceExcercise.open();
        dataSourceWater.open();

        FoodList();
        ExcerciseList();
        WaterList();

    }

    @Override
    protected void onStop() {
        super.onStop();

        foodAndDrinkCal = 0;
        calLeft = 0;
        calEx = 0;

        sharedPrefs();

        dataSource.close();
        dataSourceExcercise.close();
        dataSourceWater.close();
    }


    public void FoodList () {
        List<Food> foods = dataSource.getAllFood();

        ArrayAdapter<Food> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foods);

        ListView listView = findViewById(R.id.food_listView);
        listView.setAdapter(adapter);
    }

    public void ExcerciseList () {
        List<Excercise> excercises = dataSourceExcercise.getAllExcercise();

        ArrayAdapter<Excercise> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, excercises);

        ListView listView1 = findViewById(R.id.exercise_listView);
        listView1.setAdapter(adapter);
    }

    public void WaterList() {
        List<Water> waters = dataSourceWater.getAllWater();

        ArrayAdapter<Water> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, waters);

        ListView listView2 = findViewById(R.id.water_listView);
        listView2.setAdapter(adapter);
    }


}