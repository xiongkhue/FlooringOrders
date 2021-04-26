/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.flooringorders.dao;

/**
 *
 * @author khuxi
 */
public class FlooringOrdersDaoException extends Exception{
    
    public FlooringOrdersDaoException(String message) {
        super(message);
    }
    
    public FlooringOrdersDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
