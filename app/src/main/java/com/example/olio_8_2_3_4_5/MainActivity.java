package com.example.olio_8_2_3_4_5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static com.example.olio_8_2_3_4_5.BottleDispenser.getInstance;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    BottleDispenser INSTANCE;
    TextView console;
    TextView money;
    Button addmoney;
    ArrayList<TextView> prices = new ArrayList<TextView>();
    SeekBar seek_bar;
    TextView progressText;
    int progressValue;
    Spinner spinner1;
    Spinner spinner2;
    TextView price;
    Button osta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        INSTANCE = getInstance();
        Button ulos = findViewById(R.id.ulos);
        osta = findViewById(R.id.osta);
        addmoney = findViewById(R.id.add);

        spinner1 = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.tuotteet, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.koko, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        ulos.setOnClickListener(this);
        osta.setOnClickListener(this);

        console = (TextView) findViewById(R.id.console);
        price = findViewById(R.id.hinta);


        money = (TextView) findViewById(R.id.raha);

        seek_bar = findViewById(R.id.seekBar);
        progressText = findViewById(R.id.textView15);
        seekbar();


    }

    public void seekbar () {

        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressValue = progress;
                progressText.setText(String.valueOf(progress/10));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                progressText.setText(String.valueOf(progressValue/10));
            }
        });
    }
    void setPrice(int id) {
        INSTANCE.setPrice(price, id);
    }

    void buyBottle(int choice) throws IOException {
        INSTANCE.money = INSTANCE.buyBottle(INSTANCE.money, choice, console);
        money.setText(String.valueOf(INSTANCE.money));
        String receipt = "Thank you for your purchase!\n" +
                "You purchased " + spinner1.getSelectedItem().toString() + " " + spinner2.getSelectedItem().toString() + "!\n" +
                "Price of the purchase: " + price.getText() + "€";
        writeReceipt(receipt);
    }

    public void addMoney(View v){
        if(seek_bar.getProgress() == 0){
            INSTANCE.money = INSTANCE.addMoney(INSTANCE.money, console);
        }
        else {
            INSTANCE.money = INSTANCE.money + seek_bar.getProgress()/10;
        }

        money.setText(String.valueOf(INSTANCE.money));
    }

    public void withdrawMoney(){
        INSTANCE.money = INSTANCE.returnMoney(INSTANCE.money, console);
        money.setText(String.valueOf(INSTANCE.money));
    }

    public void makePurchase(View v) throws IOException {
        if (spinner1.getSelectedItem().toString().equals("Pepsi max") && spinner2.getSelectedItem().toString().equals("0.5l")){
            buyBottle(1);
        }
        else if(spinner1.getSelectedItem().toString().equals("Pepsi max") && spinner2.getSelectedItem().toString().equals("1.5l")){
            buyBottle(2);
        }
        else if(spinner1.getSelectedItem().toString().equals("Coca cola zero") && spinner2.getSelectedItem().equals("0.5l")){
            buyBottle(3);
        }
        else if(spinner1.getSelectedItem().toString().equals("Coca cola zero") && spinner2.getSelectedItem().equals("1.5l")){
            buyBottle(4);
        }
        else if(spinner1.getSelectedItem().toString().equals("Fanta") && spinner2.getSelectedItem().toString().equals("0.5l")){
            buyBottle(5);
        }
        else {
            if(INSTANCE.money > 0) {
                console.setText("Product does not exist!");
            }
        }
    }

    private void writeReceipt(String data) throws IOException {
        FileOutputStream stream = openFileOutput("receipt.txt", Context.MODE_PRIVATE);
        try {
            stream.write(data.getBytes());
        } finally {
            stream.close();
            Toast.makeText(this, "Receipt added!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ulos:
                withdrawMoney();
                break;
            case R.id.add:
                addMoney(v);
                break;
            case R.id.osta:
                try {
                    makePurchase(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinner1.getSelectedItem().toString().equals("Pepsi max") && spinner2.getSelectedItem().toString().equals("0.5l")){
            setPrice(0);
        }
        else if(spinner1.getSelectedItem().toString().equals("Pepsi max") && spinner2.getSelectedItem().toString().equals("1.5l")){
            setPrice(1);
        }
        else if(spinner1.getSelectedItem().toString().equals("Coca cola zero") && spinner2.getSelectedItem().equals("0.5l")){
            setPrice(2);
        }
        else if(spinner1.getSelectedItem().toString().equals("Coca cola zero") && spinner2.getSelectedItem().equals("1.5l")){
            setPrice(3);
        }
        else if(spinner1.getSelectedItem().toString().equals("Fanta") && spinner2.getSelectedItem().toString().equals("0.5l")){
            setPrice(4);
        }
        else {
            if(INSTANCE.money > 0) {
                console.setText("Product does not exist!");
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
