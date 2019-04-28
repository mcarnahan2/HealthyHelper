package edu.apsu.healthyhelper;

import android.os.Bundle;
import android.view.View;

import com.jjoe64.graphview.GraphView;

public class WeightTrackerActivity extends MenuActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_tracker);

        final GraphView graph = (GraphView) findViewById(R.id.graph);

        graph.setVisibility(View.VISIBLE);

    }
}
