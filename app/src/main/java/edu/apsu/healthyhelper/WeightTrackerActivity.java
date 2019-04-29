package edu.apsu.healthyhelper;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class WeightTrackerActivity extends MenuActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_tracker);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String name = prefs.getString("name", null);
        Float bmi = prefs.getFloat("bmi", 0);
        int bmiInt = Math.round(bmi);

        String bmiStr = String.valueOf(bmiInt);
        String bmiRisk = "";

        if(bmiInt < 19){
            bmiRisk = "Underweight BMI";
        } else if (bmiInt >= 19 && bmiInt <=24){
            bmiRisk = "Healthy BMI";
        } else if (bmiInt >= 25 && bmiInt <= 29) {
            bmiRisk = "Overweight BMI";
        } else if (bmiInt>=30 && bmiInt <= 39) {
            bmiRisk = "Obesity BMI";
        } else {
            bmiRisk = "Extreme Obesity BMI";
        }

        TextView tv = findViewById(R.id.bmi_textView);
        tv.setText("Hello, " + name + "! Your BMI is "+ bmiStr + ": " + bmiRisk);

        findViewById(R.id.weight_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.weight_entry, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(WeightTrackerActivity.this);
                builder.setTitle("Add Daily Weight")
                        .setView(dialogView)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText et = dialogView.findViewById(R.id.weight_edit_text);
                                String weighttStr = et.getText().toString().trim();
                                if(weighttStr.length() == 0){
                                    return;
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null);

                builder.create().show();
            }
        });

        final GraphView graph = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> lowSeries = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 150),
                new DataPoint(2, 150),
                new DataPoint(3, 150),
                new DataPoint(4, 150),
                new DataPoint(5, 150),
                new DataPoint(6, 150),
                new DataPoint(7, 150)
        });
        graph.addSeries(lowSeries);

        graph.setVisibility(View.VISIBLE);

    }
}
