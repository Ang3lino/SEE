package com.example.ang3l.see;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.ang3l.see.classes.VolleyHelper;
import com.example.ang3l.see.classes.VotingRoom;
import com.example.ang3l.see.items.PostulantElectionItem;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrendsActivity extends AppCompatActivity {
    private HorizontalBarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);

        barChart = findViewById(R.id.barchar_trends);
        buildBarChart();
    }

    private void buildBarChart() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                VolleyHelper.getHostUrl("count_votes.php"),
                response -> { // obtengo la respuesta del servidor
                    try {
                        JSONArray array = new JSONArray(response);
                        ArrayList<BarEntry> entries = new ArrayList<>();
                        ArrayList<String> labels = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject currentObject = array.getJSONObject(i);
                            float count = (float) currentObject.getInt("count");
                            String name = currentObject.getString("nombre");
                            entries.add(new BarEntry(i, count));
                            labels.add(name);
                        }
                        BarDataSet dataSet = new BarDataSet(entries, "Candidatos");
                        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        BarData data = new BarData(dataSet);
                        barChart.setData(data);
                        barChart.invalidate();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                },
                error -> { // si el servidor esta apagado nos vamos aqui
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
        ) {
            @Override // le pasamos los datos del celular medianto un HashMap
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("number", "" + VotingRoom.get().getNumber());
                return params;
            }
        };
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
