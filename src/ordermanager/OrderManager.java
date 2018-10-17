/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordermanager;

import controller.Order_Controller;
import view.Orders_Screen;

/**
 *
 * @author Maria
 */
public class OrderManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Orders_Screen theView = new Orders_Screen();
        Order_Controller theController = new Order_Controller(theView);
        
        theView.setExtendedState(theView.MAXIMIZED_BOTH); 
        theView.setVisible(true);
        
    }
    
}
