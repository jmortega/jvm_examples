package com.danibuiza.javacodegeeks.optional;

public class Car
{

    private String price;

    public Car( String price )
    {
        setPrice( price );
    }

    public void setPrice( String price )
    {
        this.price = price;
    }

    public String getPrice()
    {
        return this.price;
    }

    @Override
    public String toString()
    {
        return "this car costs " + getPrice();
    }

}
