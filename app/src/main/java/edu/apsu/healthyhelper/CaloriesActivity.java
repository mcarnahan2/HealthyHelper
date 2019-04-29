package edu.apsu.healthyhelper;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class CaloriesActivity extends MenuActivity {
    private DBDataSource dataSource;
    private DBDataSourceExcercise dataSourceExcercise;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Float calTotal = prefs.getFloat("calories", 0);
        String totalCalStr = calTotal.toString();

        TextView tv = findViewById(R.id.total_calories_textView);
        tv.setText(totalCalStr);

        tv = findViewById(R.id.total_calc_calories_textView);
        tv.setText(totalCalStr);

        dataSourceExcercise = new DBDataSourceExcercise(this);
        dataSource = new DBDataSource(this);

        final ListView food_listView = findViewById(R.id.food_listView);
        final ListView excercise_listView = findViewById(R.id.exercise_listView);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.food_edit_text, null);
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
                                if(foodStr.length() == 0){
                                    return;
                                }
                                EditText etCalories = dialogView.findViewById(R.id.editText2);
                                int calories  = Integer.parseInt(etCalories.getText().toString().trim());
                                String cal = String.valueOf(calories);

                                Food food = dataSource.createfood(foodStr, calories);

                                ArrayAdapter<Food> adapter = (ArrayAdapter<Food>) food_listView.getAdapter();
                                adapter.add(food);
                                adapter.notifyDataSetChanged();
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
                builder.setView(dialogView)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText etexcercise = dialogView.findViewById(R.id.editText);
                                String excerciseStr = etexcercise.getText().toString().trim();
                                if(excerciseStr.length() == 0){
                                    return;
                                }
                                EditText etCalories = dialogView.findViewById(R.id.editText2);
                                int calories  = Integer.parseInt(etCalories.getText().toString().trim());

                                Excercise excercise = dataSourceExcercise.createexcercise(excerciseStr, calories);

                                ArrayAdapter<Excercise> adapter = (ArrayAdapter<Excercise>) excercise_listView.getAdapter();
                                adapter.add(excercise);
                                adapter.notifyDataSetChanged();
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

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        dataSource.open();
        dataSourceExcercise.open();

        FoodList();
        ExcerciseList();

    }

    @Override
    protected void onStop() {
        super.onStop();

        dataSource.close();
        dataSourceExcercise.close();
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

}