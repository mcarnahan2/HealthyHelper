package edu.apsu.healthyhelper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WelcomeActivity extends MenuActivity {

    CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMM- yyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        TextView tv = findViewById(R.id.welcome_textView);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String name = prefs.getString("name", null);

        tv.setText("Hello " + name);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        compactCalendarView = findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        Event event = new Event(Color.RED,1556460000000L, "Teacher's" +
                "Professional Day"  );
        compactCalendarView.addEvent(event);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();

                if (dateClicked.toString().compareTo("Sun Apr 28 09:00:00 AST 2019") == 0) {
                    String message = "<html>" +
                            "<h1> Menu For The Day </h1>" +
                            "<p><b>Breakfast:</b> Plain yogurt (with a splash of maple syrup) with granola or berries </p>" +
                            "<p><b>Lunch:</b> Healthy Egg Salad with Basil </p>" +
                            "<p><b>Dinner:</b> Veggie Burrito Bowl with Cauliflower Rice  </p>" +
                            "</html>";

                    AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
                    builder.setMessage(Html.fromHtml(message));
                    builder.setPositiveButton("OK", null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    String message = "<html>" +
                            "<h1> Menu For The Day </h1>" +
                            "<p><b>Breakfast:</b> Plain yogurt (with a splash of maple syrup) with granola or berries </p>" +
                            "<p><b>Lunch:</b> Healthy Egg Salad with Basil </p>" +
                            "<p><b>Dinner:</b> Veggie Burrito Bowl with Cauliflower Rice  </p>" +
                            "</html>";

                    AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
                    builder.setMessage(Html.fromHtml(message));
                    builder.setPositiveButton("OK", null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });


        findViewById(R.id.webmd_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.webmd.com/"));
                startActivity(intent);
            }
        });

        findViewById(R.id.vandy_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.vanderbilthealth.com/"));
                startActivity(intent);
            }
        });

        findViewById(R.id.nih_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.nih.gov/"));
                startActivity(intent);
            }
        });

        findViewById(R.id.mayo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.mayoclinic.org/"));
                startActivity(intent);
            }
        });



    }
}
