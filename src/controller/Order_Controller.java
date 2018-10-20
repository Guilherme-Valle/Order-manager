/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocPrintJob;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import model.Kombi;
import model.Order;
import model.OrderDAO;
import view.Orders_Screen;


/**
 *
 * @author Maria
 */
public class Order_Controller {
    
    private Orders_Screen theView;
    public ArrayList<Order> listOfProducts;
    public Kombi kombi;
    public int numPedido;
    DefaultListModel<Order> modelSell;
    /**
     * Construtor do controlador
     * @param view 
     */
    public Order_Controller (Orders_Screen view){
        /** Declara um arrayList com a lista dos produtos */
        listOfProducts = new ArrayList<Order>();
        listOfProducts.add(new Order(0, "Hamburguer barbecue", 16));
        listOfProducts.add(new Order(1, "Hamburguer mostarda e mel", 16));
        listOfProducts.add(new Order(2, "Hamburguer gorgonzola", 16));
        listOfProducts.add(new Order(3, "Batata promo", 4));
        listOfProducts.add(new Order(4, "Batata frita", 7));
        listOfProducts.add(new Order(5, "Churros", 4));
        listOfProducts.add(new Order(6, "Churros gelato chocolate", 8));
        listOfProducts.add(new Order(6, "Churros gelato morango", 8));
        listOfProducts.add(new Order(6, "Churros gelato creme", 8));
        listOfProducts.add(new Order(7, "Suco copo", 5));
        listOfProducts.add(new Order(8, "Suco garrafa", 12));
        listOfProducts.add(new Order(9, "Sorvete chocolate", 5));
        listOfProducts.add(new Order(10, "Sorvete creme", 5));
        listOfProducts.add(new Order(11, "Sorvete morango", 5));
        listOfProducts.add(new Order(12, "Milkshake chocolate", 8));
        listOfProducts.add(new Order(13, "Milkshake ovomaltine", 8));
        listOfProducts.add(new Order(14, "Milkshake morango", 8));
        listOfProducts.add(new Order(14, "Milkshake creme", 8));

        kombi = new Kombi(listOfProducts);
        numPedido = 0;
        DefaultListModel<Order> model = new DefaultListModel<Order>();
        for (Order i: listOfProducts){
            model.addElement(i);
        }
        
        modelSell = new DefaultListModel<Order>();
        
        OrderDAO db = new OrderDAO();
        
        if (db.createDb(listOfProducts)){
            JOptionPane.showMessageDialog(theView, "Bem vindo ao sistema da barraca Kombi."
                    + "O sistema já foi inicializado! Viva Cristo Rei!");
        } else {
            JOptionPane.showMessageDialog(theView, "Banco de dados inicializado! Bem-vindo ao sistema"
                    + " da barraca Kombi! Salve Maria!");
        }
        
        view.setListOfProducts(model);
        view.setListOfSells(modelSell);
        view.setListenerBtnAddProduct(new listenerBtnAddProduct());
        view.setListenerBtnSell(new listenerBtnSell());
        view.setListenerBtnClearList(new listenerBtnClearList());
        view.setListenerBtnRelatory(new listenerBtnRelatory());
        this.theView = view;
        
    }
    
    /**
     * O botao de adicionar produto seleciona os elementos selecionados na lista,
     * e adiciona na lista da comanda.
     */
    class listenerBtnAddProduct implements ActionListener {
        public void actionPerformed(ActionEvent e){
           int[] selectedIds = theView.getIndicesOfListOfProducts();
           for (int i=0; i < selectedIds.length; i++){
               Order obj = kombi.getItemById(selectedIds[i]);
               modelSell.addElement(obj);
           }
           theView.setListOfSells(modelSell);
        }
        
    }
    
    /**
     * Ao clicar em gerar comanda, os itens da comanda em questão são registrados em arquivo.
     * Após isso, é gerado um PDF com o nome dos itens
     */
    class listenerBtnSell implements ActionListener {
        public void actionPerformed(ActionEvent e){
            ArrayList<String> pedidos = new ArrayList<String>();
            for (int i=0; i < modelSell.getSize(); i++){
                pedidos.add(modelSell.getElementAt(i).getName());
            }
            OrderDAO db = new OrderDAO();
            db.registraVenda(pedidos);
            Document document = new Document();
            Random gerador = new Random();
            numPedido++;
            int numRandomComanda = gerador.nextInt();
            try {    
                PdfWriter.getInstance(document, new FileOutputStream("comanda"+numRandomComanda+".pdf"));
                document.open();
                double totalPedido = 0;
                Font font = new Font();
                font.setSize(40);
                
                document.add(new Paragraph("Número do pedido: " +numPedido, font));
                font.setSize(30);
                document.add(new Paragraph("                                 "));

                for (int z=0; z < modelSell.getSize(); z++){
                    document.add(new Paragraph(modelSell.getElementAt(z).getName(), font));
                    totalPedido += modelSell.getElementAt(z).getPrice();
                }
                
                document.add(new Paragraph("Total do pedido: R$"+totalPedido, font));
                
            } catch(DocumentException de){
                System.err.println(de.getMessage());
            } catch(IOException ioe){
                System.err.println(ioe.getMessage());
            }
            document.close();
            try {
                Desktop.getDesktop().open(new File("comanda"+numRandomComanda+".pdf"));
            } catch (IOException ex) {
                Logger.getLogger(Order_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class listenerBtnClearList implements ActionListener {
        public void actionPerformed(ActionEvent e){
            modelSell = new DefaultListModel<Order>();
            theView.setListOfSells(modelSell);
        }
    }
    
    class listenerBtnRelatory implements ActionListener {
        public void actionPerformed(ActionEvent e){
            OrderDAO db = new OrderDAO();
            ArrayList<Order> produtos = db.retornaProdutos();
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream("relatorio.pdf"));
                document.open();
               int vendas = 0;
               double lucro = 0;
                for (Order i: produtos){
                    document.add(new Paragraph(i.getName()+" - Vendas: " + i.getQuantityOfSells() + " - Rendeu: R$"+ i.getPrice() * i.getQuantityOfSells()));
                    vendas += i.getQuantityOfSells();
                    lucro += i.getPrice() * i.getQuantityOfSells();
                }
                document.add(new Paragraph("Faturamento: R$" +lucro));
                document.add(new Paragraph("Total de vendas: " +vendas));
               
                
            } catch(DocumentException de){
                System.err.println(de.getMessage());
            } catch(IOException ioe){
                System.err.println(ioe.getMessage());
            }
            document.close();
            
            try {
                Desktop.getDesktop().open(new File("relatorio.pdf"));
            } catch (IOException ex) {
                Logger.getLogger(Order_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    }
    
    
   
}
