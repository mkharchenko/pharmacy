package ua.nure.pharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Date;
import java.util.Random;

import ua.nure.pharmacy.controllers.SalesController;
import ua.nure.pharmacy.dto.MedicineLeftover;
import ua.nure.pharmacy.file_read.JsonManipulator;

public class VisActivity extends AppCompatActivity {
    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vis);

        JsonManipulator json_reader = new JsonManipulator(this);
        JSONArray sales = json_reader.ReadSalesJsonArray("sales.json");
        Map<Long, Integer> date_counts = new HashMap<Long, Integer>();
        String pattern = "dd/MM";
        DateFormat df = new SimpleDateFormat(pattern);

        for(int i = 0; i<sales.length();i++){
            JSONObject sale = sales.optJSONObject(i);
            try {
                Date sale_date = new Date(sale.getLong("date"));

                String todayAsString = df.format(sale_date);
                sale_date = df.parse(todayAsString);
                if (date_counts.get(sale_date.getTime()/1000) == null) {
                    date_counts.put(sale_date.getTime()/1000, 0);
                }
                Integer new_count = sale.getInt("amount") + date_counts.get(sale_date.getTime()/1000);
                date_counts.put(sale_date.getTime()/1000, new_count);
            }
            catch (Exception e){}

        }
        //test
        /*for (int i=0;i<100;i++){
            date_counts.put((long)1640561437+i*100000,new Random().nextInt(10));
        }*/
        TreeMap<Long,Integer> sorted_data = new TreeMap<Long,Integer>(date_counts);

        GraphView graphview = (GraphView) findViewById(R.id.view);
        graphview.getViewport().setScrollable(true);
        graphview.getViewport().setMinY(0);
        graphview.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return df.format(new Date((long)value*1000));
                }
                return super.formatLabel(value, isValueX);
            }
        });

        series = new LineGraphSeries<DataPoint>();
        for(Map.Entry<Long,Integer> entry: sorted_data.entrySet()) {
            Long x = entry.getKey();
            Integer y = entry.getValue();
            series.appendData(new DataPoint(x, y), false, 100);
        }
        graphview.addSeries(series);
    }
}
