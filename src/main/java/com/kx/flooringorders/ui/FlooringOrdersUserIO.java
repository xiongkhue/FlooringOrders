/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.flooringorders.ui;

import java.math.BigDecimal;

/**
 *
 * @author khuxi
 */
public interface FlooringOrdersUserIO {
    
    //UserIO/View print functionality
    void print(String msg);
    void print(String message, BigDecimal c);

    //Read in user input when expecting int
    int readInt(String prompt);
    int readInt(String prompt, int min, int max);
    
    //Read in string
    String readString(String prompt);

    //Read in user input when expecting Double
    double readDouble(String prompt);
    double readDouble(String prompt, double min, double max);

    //Read in user input when expecting Float
    float readFloat(String prompt);
    float readFloat(String prompt, float min, float max);

    //Read in user input when expecting Long
    long readLong(String prompt);
    long readLong(String prompt, long min, long max);
    
    String readLocalDate(String promptY, String promptM, String promptD);
    
    String readStringBDM(String prompt);
    
}
