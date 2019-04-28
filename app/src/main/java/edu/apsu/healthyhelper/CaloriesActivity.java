package edu.apsu.healthyhelper;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class CaloriesActivity extends AppCompatActivity {
    private DBDataSource dataSource;
    private DBDataSourceExcercise dataSourceExcercise;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);

        listView = findViewById(R.id.listView);

        dataSource = new DBDataSource(this);
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

                                Food food = dataSource.createfood(foodStr, calories);

                                ArrayAdapter<Food> adapter = (ArrayAdapter<Food>) listView.getAdapter();
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
                                EditText etfood = dialogView.findViewById(R.id.editText);
                                String exerciseStr = etfood.getText().toString().trim();
                                if(exerciseStr.length() == 0){
                                    return;
                                }
                                EditText etCalories = dialogView.findViewById(R.id.editText2);
                                int calories  = Integer.parseInt(etCalories.getText().toString().trim());

                                Excercise excercise = dataSourceExcercise.createExcercise(exerciseStr, calories);

                                ArrayAdapter<Excercise> adapter = (ArrayAdapter<Excercise>) listView.getAdapter();
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

    public void FoodList() {
        List<Food> foods = dataSource.getAllFood();

        ArrayAdapter<Food> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foods);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

    }

    public void ExcerciseList() {
        List<Excercise> excercises = dataSourceExcercise.getAllExcercise();

        ArrayAdapter<Excercise> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_2, excercises);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

    }

}