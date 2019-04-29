package edu.apsu.healthyhelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CalendarView;
import android.widget.TextView;

public class WelcomeActivity extends MenuActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        TextView tv = findViewById(R.id.welcome_textView);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String name = prefs.getString("name", null);

        tv.setText("Hello " + name);

        CalendarView calendarView =  findViewById(R.id.calendarView);




    }
}
