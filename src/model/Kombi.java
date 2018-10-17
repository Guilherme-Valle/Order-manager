/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Maria
 */
public class Kombi implements Serializable {
    private ArrayList<Order> orders;
    private static final long serialVersionUID = 1L;

    public Kombi(ArrayList<Order> orders) {
        this.orders = orders;
    }
    
    public ArrayList<Order> getOrders() {
        return orders;
    }
    
    public Order getItemById (int id){
        for (Order i: this.orders){
            if (i.getId() == id){
                return i;
            }
        }
        return null;
    }
    
    public void registraVenda (ArrayList<String> nomeProdutos){
        for (String nome : nomeProdutos){
            for (Order produto : this.orders){
                if (produto.getName().equals(nome)){
                    produto.sell();
                }
            }
        }
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
    
    
    
    
    
}
