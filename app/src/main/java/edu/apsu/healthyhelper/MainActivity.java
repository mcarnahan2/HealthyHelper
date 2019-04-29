package edu.apsu.healthyhelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = findViewById(R.id.name_editText);
                String nameET = et.getText().toString();

                et = findViewById(R.id.age_editText);
                String ageET = et.getText().toString();
                int age = Integer.parseInt(ageET);

                et = findViewById(R.id.height_editText);
                String heightET = et.getText().toString();
                int height = Integer.parseInt(heightET);

                et = findViewById(R.id.weight_editText);
                String weightET = et.getText().toString();
                int weight = Integer.parseInt(weightET);

                float bmi = (float) (weight * 0.45) / (float) (Math.pow((height * 0.025), 2)) ;

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("name", nameET);
                editor.putFloat("bmi", bmi);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(intent);
            }
        });

        button = findViewById(R.id.signup_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
