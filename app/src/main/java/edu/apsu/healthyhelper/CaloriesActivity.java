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
import android.widget.TextView;

import java.util.List;

public class CaloriesActivity extends AppCompatActivity {
    private DBDataSource dataSource;
    private DBDataSourceExcercise dataSourceExcercise;
    private ListView foodlv = findViewById(R.id.food_listView);
    private ListView excerciselv = findViewById(R.id.excercise_listView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);

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

                                ArrayAdapter<Food> adapterFood = (ArrayAdapter<Food>) foodlv.getAdapter();
                                adapterFood.add(food);
                                adapterFood.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", null);

                builder.create().show();

            }
        });

        Button excercisButton = findViewById(R.id.add_excercise);
        excercisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CaloriesActivity.this);
                builder.setView(dialogView)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TextView tv = findViewById(R.id.textView4);
                                tv.setText("Insert Excercise");
                                EditText etexcercise = dialogView.findViewById(R.id.editText);
                                String exerciseStr = etexcercise.getText().toString().trim();
                                if(exerciseStr.length() == 0){
                                    return;
                                }
                                tv = findViewById(R.id.textView4);
                                tv.setText("Insert Calories");
                                EditText etCalories = dialogView.findViewById(R.id.editText2);
                                int calories  = Integer.parseInt(etCalories.getText().toString().trim());

                                Excercise excercise = dataSourceExcercise.createExcercise(
                                        exerciseStr, calories);

                                ArrayAdapter<Excercise> adapterExcercise = (ArrayAdapter<Excercise>) excerciselv.getAdapter();
                                adapterExcercise.add(excercise);
                                adapterExcercise.notifyDataSetChanged();
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

        List<Food> foods = dataSource.getAllFood();

        ArrayAdapter<Food> adapterFood = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foods);

        foodlv = findViewById(R.id.food_listView);
        foodlv.setAdapter(adapterFood);

        List<Excercise> excercises = dataSourceExcercise.getAllExcercise();

        ArrayAdapter<Excercise> adapterExcercise = new ArrayAdapter<>(this, android.R.layout.simple_list_item_2, excercises);

        excerciselv = findViewById(R.id.excercise_listView);
        excerciselv.setAdapter(adapterExcercise);


    }

    @Override
    protected void onStop() {
        super.onStop();

        dataSource.close();
    }

}