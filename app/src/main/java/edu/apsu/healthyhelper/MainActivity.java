package edu.apsu.healthyhelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int age;
    private int height;
    private int weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = findViewById(R.id.error_textView);
        tv.setVisibility(View.INVISIBLE);

        Button button = findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = findViewById(R.id.name_editText);
                String nameET = et.getText().toString().trim();

                et = findViewById(R.id.age_editText);
                String ageET = et.getText().toString().trim();
                if(!ageET.equals("")) {
                    age = Integer.parseInt(ageET);
                }

                et = findViewById(R.id.height_editText);
                String heightET = et.getText().toString().trim();
                if(!heightET.equals("")) {
                    height = Integer.parseInt(heightET);
                }

                et = findViewById(R.id.weight_editText);
                String weightET = et.getText().toString().trim();
                if(!weightET.equals("")) {
                    weight = Integer.parseInt(weightET);
                }




                if(nameET.equals("") || ageET.equals("") || heightET.equals("") || weightET.equals("")) {
                    tv.setText("Please make sure all fields are filled in.");
                    tv.setVisibility(View.VISIBLE);

                } else{
                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    startActivity(intent);

                    float bmi = (float) (weight * 0.45) / (float) (Math.pow((height * 0.025), 2)) ;

                    //Female
                    //float calories = (float) ((10 * (float) weight + 6.25 * (float) height - 5 * (float) age -161));

                    //Male
                    float calories = (float) ((10 * (float) weight + 6.25 * (float) height - 5 * (float) age + 5));

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("name", nameET);
                    editor.putFloat("bmi", bmi);
                    editor.putFloat("calories", calories);
                    editor.putInt("height", height);
                    editor.putInt("weight", weight);
                    editor.apply();
                }
            }
        });

    }
}
