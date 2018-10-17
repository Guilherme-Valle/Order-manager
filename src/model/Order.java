/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Maria
 */
public class Order implements Serializable {
    
    private int id;
    private String name;
    private int quantityOfSells;
    private double price;
    private static final long serialVersionUID = 456L;

    public Order(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityOfSells = 0;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void sell (){
        this.quantityOfSells += 1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantityOfSells() {
        return quantityOfSells;
    }

    public void setQuantityOfSells(int quantityOfSells) {
        this.quantityOfSells = quantityOfSells;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
    
    
    
}
