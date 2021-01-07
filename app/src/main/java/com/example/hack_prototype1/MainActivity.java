
package com.example.hack_prototype1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String vehicle;
    private int year, price;
    EditText vehicleInput, yearInput,mileageInput;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vehicleInput = (EditText) findViewById(R.id.vehicle);
        yearInput = (EditText) findViewById(R.id.year);
        mileageInput = (EditText) findViewById(R.id.price);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicle = vehicleInput.getText().toString().trim();
                year = Integer.parseInt(yearInput.getText().toString().trim());
                price = Integer.parseInt(mileageInput.getText().toString().trim());
                showIt(vehicle);
                showIt(String.valueOf(year));
                showIt(String.valueOf(price));
                openResult();
            }
        });
    }

    public void openResult(){
        Intent intent = new Intent(this,MainActivity2.class);
        intent.putExtra("vehicle",vehicle);
        intent.putExtra("year",year);
        intent.putExtra("price",price);

        startActivity(intent);
    }



    private void showIt(String word){
        Toast.makeText(MainActivity.this, word, Toast.LENGTH_SHORT).show();
    }
}