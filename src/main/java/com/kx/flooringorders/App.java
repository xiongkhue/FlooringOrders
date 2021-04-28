/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.flooringorders;

import com.kx.flooringorders.controller.FlooringOrdersController;
import com.kx.flooringorders.dao.FlooringOrdersPersistenceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author khuxi
 */
public class App {
   public static void main(String[] args) throws FlooringOrdersPersistenceException {
        
        ApplicationContext ctx = 
           new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringOrdersController controller = 
           ctx.getBean("controller", FlooringOrdersController.class);
        controller.run();
    } 
}
