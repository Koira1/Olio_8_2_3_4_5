package com.example.olio_8_2_3_4_5;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.DecimalFormat;
import android.view.View;

import org.w3c.dom.Text;


public final class BottleDispenser {

    private int bottles;

    private static final BottleDispenser INSTANCE = new BottleDispenser();



    // The array for the Bottle-objects

    private ArrayList<Bottle> bottle_list;

    public double money;

    int x;

    public static BottleDispenser getInstance() {
        return INSTANCE;
    }


    private BottleDispenser() {

        bottles = 2;

        money = 0;

        x = 0;


        // Initialize the array

        bottle_list = new ArrayList();
        Bottle x = new Bottle("Pepsi Max", "Pepsi", 0.3, 0.5, 1.8);
        bottle_list.add(x);
        x = new Bottle("Pepsi Max", "Pepsi", 0.3, 1.5, 2.2);
        bottle_list.add(x);
        x = new Bottle("Coca-Cola Zero", "Coca-Cola", 0.3, 0.5, 2.0);
        bottle_list.add(x);
        x = new Bottle("Coca-Cola Zero", "Coca-Cola", 0.3, 1.5, 2.5);
        bottle_list.add(x);
        x = new Bottle("Fanta Zero", "Coca-Cola", 0.3, 0.5, 1.95);
        bottle_list.add(x);

    }



    public double addMoney(double money, TextView console) {

        money += 1;

        console.setText("Added money!");
        return money;

    }



    public double buyBottle(double money, int choice, TextView console) {

        if(money == 0) {
            console.setText("Add money first!");
        }

        else if(money < bottle_list.get(0).getPrice() && money > 0) {
            console.setText("Not enough money!");
        }

        else {
            console.setText("KACHUNK!");
            money -= bottle_list.get(choice - 1).getPrice();

        }
        return money;



    }

    public void setPrice(TextView price_text, int id) {
        double price = bottle_list.get(id).price;
        String _price = String.valueOf(price);
        price_text.setText(_price);

    }

    public double returnMoney(double money, TextView console) {
        DecimalFormat f = new DecimalFormat("#0.00");
        String moneyString = String.valueOf(money);
        moneyString = f.format(money);
        moneyString = moneyString.replace(".", ",");
        console.setText("Klink klink. Money came out! You got " + moneyString + "€ back");
        money = 0;
        return money;

    }

}
