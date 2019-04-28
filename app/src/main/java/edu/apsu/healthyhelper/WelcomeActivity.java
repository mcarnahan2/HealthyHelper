package edu.apsu.healthyhelper;

import android.app.usage.UsageEvents;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class WelcomeActivity extends MenuActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        final TextView tv = findViewById(R.id.welcome_textView);

        CalendarView calendarView =  findViewById(R.id.calendarView);




    }
}
