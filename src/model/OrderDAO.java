/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Maria
 */
public class OrderDAO {
    public static String db = "db.txt";
    
    public boolean createDb(ArrayList<Order> orders){
    File file = new File(db);
    
    if (file.exists()){
        return true;
    } else {
        Kombi kombi = new Kombi(orders);
        try {
            file.createNewFile();
            FileOutputStream fileOutput = new FileOutputStream(db);
            ObjectOutputStream objGravar = new ObjectOutputStream(fileOutput);
            
            objGravar.writeObject(kombi);
            objGravar.flush();
            objGravar.close();
            
            fileOutput.flush();
            fileOutput.close();
            
            
        } catch(Exception e){
            System.out.println("Erro de db");
        }
        return false;
    }
  
        
    }
    
    public void registraVenda (ArrayList<String> nomePedidos){
        try {
            FileInputStream fileInput = new FileInputStream(db);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            Kombi kombi = (Kombi)objectInput.readObject();
            objectInput.close();
            kombi.registraVenda(nomePedidos);
            
            FileOutputStream fileOutput = new FileOutputStream(db);
            ObjectOutputStream objGravar = new ObjectOutputStream(fileOutput);
            
            objGravar.writeObject(kombi);
            objGravar.flush();
            objGravar.close();
            
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public ArrayList<Order> retornaProdutos (){
        try {
            FileInputStream fileInput = new FileInputStream(db);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            Kombi kombi = (Kombi)objectInput.readObject();
            objectInput.close();
            
            return kombi.getOrders();
            
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
