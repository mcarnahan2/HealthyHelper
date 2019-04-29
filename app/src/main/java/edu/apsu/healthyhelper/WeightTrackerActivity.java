package edu.apsu.healthyhelper;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;

public class WeightTrackerActivity extends MenuActivity {
    private String weightStr;
    private DataPoint[] dp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_tracker);

        final ArrayList<Integer> arrayList = new ArrayList<>();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String name = prefs.getString("name", null);
        int height = prefs.getInt("height", 0);
        int weight = prefs.getInt("weight", 0);

        arrayList.add(weight);

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
                                weightStr= et.getText().toString().trim();
                                if(weightStr.length() == 0){
                                    return;
                                }

                                dp = new DataPoint[arrayList.size()];
                                for(int j=0; j<arrayList.size(); j++){
                                    dp[j] = new DataPoint(j, arrayList.get(j));
                                }

                                PointsGraphSeries<DataPoint> weightSeries = new PointsGraphSeries<>(dp);
                            }
                        })
                        .setNegativeButton("Cancel", null);

                builder.create().show();
            }
        });

        if(weightStr != null){
            int enteredWeight = Integer.parseInt(weightStr);
        }


        int lowWeight = (int) ((19 * Math.pow((height * 0.025), 2)) / 0.45);
        int highWeight = (int) ((24 * Math.pow((height * 0.025), 2)) / 0.45);

        final GraphView graph = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> lowSeries = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, lowWeight),
                new DataPoint(365, lowWeight)
        });

        lowSeries.setColor(Color.GREEN);
        lowSeries.setThickness(5);
        graph.addSeries(lowSeries);

        LineGraphSeries<DataPoint> highSeries = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, highWeight),
                new DataPoint(365, highWeight)
        });

        highSeries.setColor(Color.RED);
        highSeries.setThickness(5);
        graph.addSeries(highSeries);

        dp = new DataPoint[arrayList.size()];
        for(int i=0; i<arrayList.size(); i++){
            dp[i] = new DataPoint(i, arrayList.get(i));
        }

        PointsGraphSeries<DataPoint> weightSeries = new PointsGraphSeries<>(dp);

        weightSeries.setColor(Color.BLACK);
        graph.addSeries(weightSeries);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0.0);
        graph.getViewport().setMaxX(7.0);

        graph.setVisibility(View.VISIBLE);

    }
}
