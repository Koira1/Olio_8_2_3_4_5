package com.example.olio_8_2_3_4_5;

public class Bottle {

    public String name;

    public String manufacturer;

    public double total_energy;

    public double size;

    public double price;


    public Bottle() {
        name = "Pepsi Max";
        manufacturer = "Pepsi";
        total_energy = 0.3;
        size = 0.5;
        price = 1.80;
    }

    public Bottle(String nameX, String manuf, double totE, double sizeX, double priceX){
        name = nameX;
        manufacturer = manuf;
        total_energy = totE;
        size = sizeX;
        price = priceX;
    }

    public String getName(){
        return name;
    }

    public String getManufacturer(){
        return manufacturer;
    }

    public double getEnergy(){
        return total_energy;
    }

    public double getPrice() {
        return price;
    }
    public double getSize() {
        return size;
    }
}