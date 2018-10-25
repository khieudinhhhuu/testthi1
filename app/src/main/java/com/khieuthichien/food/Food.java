package com.khieuthichien.food;

public class Food {
    private int idfood;
    private String namefood;
    private long price;

    public Food(int idfood, String namefood, long price) {
        this.idfood = idfood;
        this.namefood = namefood;
        this.price = price;
    }

    public Food() {
    }

    public int getIdfood() {
        return idfood;
    }

    public void setIdfood(int idfood) {
        this.idfood = idfood;
    }

    public String getNamefood() {
        return namefood;
    }

    public void setNamefood(String namefood) {
        this.namefood = namefood;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
