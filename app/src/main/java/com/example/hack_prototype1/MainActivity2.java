
package com.example.hack_prototype1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zichun Yuan
 * @author Yuqi You
 */
public class MainActivity2 extends AppCompatActivity {

    private String vehicle;
    private int year, price;
    TextView datVehicle;
    TextView datYear;
    TextView datPrice;
    TextView[] datTable;
    double lost = 0;
    DataPoint[] data;

    static double percentage = 0.15;
    final static int thisYear = 2020;
    private HashMap<String, Double> dataBase= new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        readCsv();// read the data from CSV and save the result to the dataBase

        Intent i = getIntent();

        vehicle = i.getStringExtra("vehicle");
        year = i.getIntExtra("year",0);
        price = i.getIntExtra("price",0);

        if(dataBase.containsKey(vehicle)){

            percentage = dataBase.get(vehicle) / 300;
        }




        datVehicle = (TextView) findViewById(R.id.vehicle);
        datYear = (TextView) findViewById(R.id.year);
        datPrice = (TextView) findViewById(R.id.price);

        datVehicle.setText("Model: "+vehicle);
        datYear.setText("Year: "+year);
        datPrice.setText("Price: $"+price);

        year = thisYear - year;
        datTable = new TextView[20];
        outputTable();
        outputGraph();
    }


    private void readCsv(){

        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        // get the single text at once

        String line = "";

            try {
                //step over headers

                reader.readLine();
                while ((line = reader.readLine()) != null) {

                    //split by','
                    String[] tokens = line.split(",");

                    //read name
                   // dataSample sample = new dataSample();
                   // sample.setModel(tokens[0]);
                   // sample.setDepRate(Double.parseDouble(tokens[1]));
                    dataBase.put(tokens[0],Double.parseDouble(tokens[1]));

                    Log.d("MyAcitivity","Just created key: " + dataBase.entrySet());
                }


            } catch (IOException e) {
                Log.wtf("Myactivity","Error:reading data file on Line" + line, e);
                e.printStackTrace();
            }



    }

    public void outputTable(){
        data= new DataPoint[6];
        data[0] = new DataPoint(0,price);
        for(int i = 0; i<datTable.length; i++){
            String buttonID = "t"+i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            datTable[i] = (TextView) findViewById(resID);
        }
        double value = price;
        for(int i = 0; i<5; i++){
            value *= (1-percentage); //vehicle depreciation
            data[i+1] = new DataPoint(year+i+1,value);
            datTable[4*i].setText(year+i+1+"");
            if(year+i+1>4) percentage/=1.2; //depreciate rate starts to go down
            datTable[4*i+1].setText(String.format("%.2f%%",percentage*100));
            lost+=price*percentage; //cumulative lost of the vehicle
            datTable[4*i+2].setText(String.format("$%.2f",lost));
            datTable[4*i+3].setText(String.format("$%.2f",value));
        }
    }

    public void outputGraph(){
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);
        graph.addSeries(series);
        graph.setTitle("Vehicle Value vs. Vehicle Age");
    }
}