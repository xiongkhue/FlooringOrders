/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.flooringorders.dao;

import com.kx.flooringorders.dto.Order;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author khuxi
 */
public interface FlooringOrdersDao {
        
    /**
     * Adds the given Order to the list and associates it with the given 
     * Order id. If there is already a Order associated with the given 
     * Order id it will return that Order object, otherwise it will 
     * return null.
     * 
     * @param orderNumber id with which Order is to be associated
     * @param Order Order to be added to the list
     * @return the Order object previously associated with the given  
     * Order id if it exists, null otherwise
     */
    Order addOrder(BigDecimal orderNumber, Order order)
            throws FlooringOrdersDaoException;
    
    /**
     * Returns a String array containing the Order ids of all 
     * Orders in the list.
     * 
     * @return String array containing the ids of all the Orders 
     * in the list
     */
    HashMap<String,List> getAllOrders()
            throws FlooringOrdersDaoException;
    
    /**
     * Returns the Order object associated with the given Order id.
     * Returns null if no such Order exists
     * 
     * @param orderNumber ID of the Order to retrieve
     * @return the Order object associated with the given Order id,  
     * null if no such Order exists
     */
    Order getOrder(BigDecimal orderNumber)
            throws FlooringOrdersDaoException;
    
    /**
     * Edits from the Order list the Order associated with the given orderNumber. 
     * Returns the Order object that is being edited or null if 
     * there is no Order associated with the given orderNumber
     * 
     * @param orderNumber id of Order to be edited
     * @return Order object that was removed or null if no Order 
     * was associated with the given orderNumber
     */
    Order editOrder(BigDecimal orderNumber, Order order)
            throws FlooringOrdersDaoException;
    
    /**
     * Removes from the list the Order associated with the given id. 
     * Returns the Order object that is being removed or null if 
     * there is no Order associated with the given id
     * 
     * @param orderNumber id of Order to be removed
     * @return Order object that was removed or null if no Order 
     * was associated with the given Order id
     */
    Order removeOrder(BigDecimal orderNumber)
            throws FlooringOrdersDaoException;
    
    boolean loadOrder(String date)throws FlooringOrdersDaoException;
    String loadState(Order order) throws FlooringOrdersDaoException;
    String[] loadCost(Order order) throws FlooringOrdersDaoException;
    void writeData(String date) throws FlooringOrdersDaoException;
    void clearOrders();
    String[] loadConfig() throws FlooringOrdersDaoException;
    void loadAll() throws FlooringOrdersDaoException;
    void writeConfig(String[] config) throws FlooringOrdersDaoException;
    void updateConfig(String n) throws FlooringOrdersDaoException;

    HashMap returnOrders();
}
