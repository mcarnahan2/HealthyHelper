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

public class CaloriesActivity extends AppCompatActivity {
    private DBDataSource dataSource;
    ListView listView;

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

        dataSource = new DBDataSource(this);

        Button foodButton = findViewById(R.id.add_food);
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.food_edit_text, null);

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

        ArrayAdapter<Food> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foods);

        ListView listView = findViewById(R.id.food_listView);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onStop() {
        super.onStop();

        dataSource.close();
    }

}