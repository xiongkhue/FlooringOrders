/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.flooringorders.dao;

import com.kx.flooringorders.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author khuxi
 */
public class FlooringOrdersDaoImpl implements FlooringOrdersDao{
    
    HashMap<BigDecimal, Order> orders;
    HashMap<String, HashMap> allOrders;
    List<String> filenames;
    private String ORDER_FILE;
    private final String TAXES_FILE = "Taxes.txt";
    private final String PRODUCTS_FILE = "Products.txt";
    private final String CONFIG_FILE = "Configuration.txt";
    private final String SAVE_DELIMITER = ",";
    private final String LOAD_DELIMITER = ",";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public FlooringOrdersDaoImpl() {
        orders = new HashMap<>();
        allOrders = new HashMap<>();
    }

    @Override
    public Order addOrder(BigDecimal number, Order order) throws FlooringOrdersDaoException {
        orders.put(number, order);
        return order;
    }

    @Override
    public HashMap<String, List> getAllOrders() throws FlooringOrdersDaoException {
        loadAll();
        String key = "";
        HashMap<String, List> hList = new HashMap<>();
        for (Map.Entry<String, HashMap> e : allOrders.entrySet()) {
            key = e.getKey();
            orders = e.getValue();
            hList.put(key, new ArrayList<Order>(orders.values()));
        }
        return hList;
    }

    @Override
    public Order getOrder(BigDecimal number) throws FlooringOrdersDaoException {
        if (orders.get(number) == null) {
            throw new FlooringOrdersDaoException("That order was not found");
        }
        return orders.get(number);
    }

    @Override
    public Order editOrder(BigDecimal number, Order order) throws FlooringOrdersDaoException {
        if (orders.replace(number, order) == null) {
            throw new FlooringOrdersDaoException("That order was not found and was unable to be edited");
        }
        orders.put(number, order);
        return orders.get(number);
    }

    @Override
    public Order removeOrder(BigDecimal number) throws FlooringOrdersDaoException {
        if (orders.remove(number) == null) {
            //throw new NoOrderException("That herd was not found and was unable to be removed");
        }
        return orders.get(number);
    }

    public String marshallOrder(Order order) {
        String orderAsText = order.getOrderNumber() + SAVE_DELIMITER
                + order.getCustomer() + SAVE_DELIMITER
                + order.getState() + SAVE_DELIMITER
                + order.getTaxRate() + SAVE_DELIMITER
                + order.getType() + SAVE_DELIMITER
                + order.getArea() + SAVE_DELIMITER
                + order.getCostPSQR() + SAVE_DELIMITER
                + order.getLabCostPSQR() + SAVE_DELIMITER
                + order.getMatCost() + SAVE_DELIMITER
                + order.getLaborCost() + SAVE_DELIMITER
                + order.getTax() + SAVE_DELIMITER
                + order.getTotal();
        // herdAsText = herdAsText.substring(0, herdAsText.length() - 3);
        return orderAsText;
    }

    public void loadAll() throws FlooringOrdersDaoException {
        Scanner sc;

        try {
            filenames = Files.list(Paths.get("."))
                    .filter(Files::isRegularFile)
                    .map(p -> p.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new FlooringOrdersDaoException("Unable to collect filenames");
        }

        for (String fileName : filenames) {
            if (fileName.contains("Orders_")) {
                ORDER_FILE = fileName;

                try {
                    sc = new Scanner(new BufferedReader(new FileReader(ORDER_FILE)));
                } catch (Exception e) {
                    throw new FlooringOrdersDaoException("Unable to load order file");
                }

                String currentLine;
                Order order;

                while (sc.hasNextLine()) {
                    currentLine = sc.nextLine();
                    if (currentLine.trim().isEmpty()) {
                        allOrders.put(fileName, orders);
                        orders = new HashMap<>();
                        break;
                    } else {
                        order = unmarshallOrder(currentLine);
                        orders.put(order.getOrderNumber(), order);
                    }
                }
                sc.close();
            }
        }
    }

    public boolean loadOrder(String date) throws FlooringOrdersDaoException {
        Scanner sc;
        boolean isThere = true;

        try {
            filenames = Files.list(Paths.get("."))
                    .filter(Files::isRegularFile)
                    .map(p -> p.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new FlooringOrdersDaoException("Unable to collect filenames");
        }
        StringBuilder fileNameBuilder = new StringBuilder();
        //fileNameBuilder.delete(0, fileNameBuilder.length());
        fileNameBuilder.append("Orders_");
        fileNameBuilder.append(date);
        fileNameBuilder.append(".txt");
        File file = new File(fileNameBuilder.toString());
        if (!file.exists()) {
            isThere = false;
        } else {
            try {
                ORDER_FILE = fileNameBuilder.toString();
                sc = new Scanner(new BufferedReader(new FileReader(ORDER_FILE)));
            } catch (Exception e) {
                throw new FlooringOrdersDaoException("Unable to load order file");
            }

            String currentLine;
            Order order;

            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
                if (currentLine.trim().isEmpty()) {
                    break;
                } else {
                    order = unmarshallOrder(currentLine);
                    orders.put(order.getOrderNumber(), order);
                }
            }
            sc.close();
        }
        return isThere;
    }

    public String loadState(Order order) throws FlooringOrdersDaoException {
        Scanner sc;

        try {
            sc = new Scanner(new BufferedReader(new FileReader(TAXES_FILE)));
        } catch (Exception e) {
            throw new FlooringOrdersDaoException("Unable to load Taxes file");
        }

        String currentLine;
        String taxRate = null;

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            if (currentLine.trim().isEmpty()) {
                break;
            } else {
                if (order.getState().equalsIgnoreCase(unmarshallState(currentLine)[0])) {
                    taxRate = unmarshallState(currentLine)[1];
                }
            }
        }
        sc.close();
        return taxRate;
    }

    public String[] loadCost(Order order) throws FlooringOrdersDaoException {
        Scanner sc;

        try {
            sc = new Scanner(new BufferedReader(new FileReader(PRODUCTS_FILE)));
        } catch (Exception e) {
            throw new FlooringOrdersDaoException("Unable to load Products file");
        }

        String currentLine;
        String[] costs = new String[2];

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            if (currentLine.trim().isEmpty()) {
                break;
            } else {
                if (order.getType().equalsIgnoreCase(unmarshallCost(currentLine)[0])) {
                    costs[0] = unmarshallCost(currentLine)[1];
                    costs[1] = unmarshallCost(currentLine)[2];
                }
            }
        }
        sc.close();
        return costs;
    }

    public String[] loadConfig() throws FlooringOrdersDaoException {
        Scanner sc;
        String[] config = new String[2];
        String currentLine;

        try {
            sc = new Scanner(new BufferedReader(new FileReader(CONFIG_FILE)));
        } catch (Exception e) {
            throw new FlooringOrdersDaoException("Unable to load Configuration file");
        }

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            if (currentLine.trim().isEmpty()) {
                break;
            } else {
                config[0] = currentLine;
                config[1] = currentLine;
            }
        }
        sc.close();
        return config;
    }

    private Order unmarshallOrder(String orderAsText) throws FlooringOrdersDaoException {
        try{
        String[] orderTokens = orderAsText.split(LOAD_DELIMITER);

        Order newOrder = new Order(new BigDecimal(orderTokens[0]));
        newOrder.setCustomer(orderTokens[1]);
        newOrder.setState(orderTokens[2]);
        newOrder.setTaxRate(new BigDecimal(orderTokens[3]));
        newOrder.setType(orderTokens[4]);
        newOrder.setArea(new BigDecimal(orderTokens[5]));
        newOrder.setCostPSQR(new BigDecimal(orderTokens[6]));
        newOrder.setLabCostPSQR(new BigDecimal(orderTokens[7]));
        newOrder.setMatCost(new BigDecimal(orderTokens[8]));
        newOrder.setLaborCost(new BigDecimal(orderTokens[9]));
        newOrder.setTax(new BigDecimal(orderTokens[10]));
        newOrder.setTotal(new BigDecimal(orderTokens[11]));
        return newOrder;
        } catch (Exception e){
            throw new FlooringOrdersDaoException("Unable to unmarshall order.");
        }
    }

    private String[] unmarshallState(String orderAsText) {
        String[] orderTokens = orderAsText.split(LOAD_DELIMITER);
        return orderTokens;
    }

    private String[] unmarshallCost(String orderAsText) {
        String[] orderTokens = orderAsText.split(LOAD_DELIMITER);
        return orderTokens;
    }

    public void writeData(String date) throws FlooringOrdersDaoException {
        PrintWriter out;
        String orderAsText;

        try {
            filenames = Files.list(Paths.get("."))
                    .filter(Files::isRegularFile)
                    .map(p -> p.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new FlooringOrdersDaoException("Unable to collect filenames");
        }
        StringBuilder fileNameBuilder = new StringBuilder();
        //fileNameBuilder.delete(0, fileNameBuilder.length());
        fileNameBuilder.append("Orders_");
        fileNameBuilder.append(date);
        fileNameBuilder.append(".txt");
        File file = new File(fileNameBuilder.toString());
        if (!file.exists()) {
            try {
                file.createNewFile();

                ORDER_FILE = fileNameBuilder.toString();
                try {
                    out = new PrintWriter(new FileWriter(ORDER_FILE));
                } catch (Exception e) {
                    throw new FlooringOrdersDaoException("Unable to save order data");
                }
                for (Order o : orders.values()) {
                    orderAsText = marshallOrder(o);
                    out.println(orderAsText);
                    out.flush();
                }

            } catch (IOException e) {
                throw new FlooringOrdersDaoException("File creation error for " + date);
            }
        } else {
            ORDER_FILE = fileNameBuilder.toString();
            try {
                out = new PrintWriter(new FileWriter(ORDER_FILE));
            } catch (Exception e) {
                throw new FlooringOrdersDaoException("Unable to save order data");
            }

            for (Order o : orders.values()) {
                orderAsText = marshallOrder(o);
                out.println(orderAsText);
                out.flush();
            }
        }
        out.println();
        out.flush();
        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCost\n"
                + "PerSquareFoot,MaterialCost,LaborCost,Tax,Total");
        out.flush();
        out.close();
//            writeFileForData(file, date);
    }

    public void clearOrders() {
        orders.clear();
    }

    public void writeConfig(String[] config) throws FlooringOrdersDaoException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(CONFIG_FILE));
        } catch (Exception e) {
            throw new FlooringOrdersDaoException("Unable to save config data");
        }
        for (String s : config) {
            out.println(s);
            out.flush();
        }
        out.close();
    }
    
    public void updateConfig(String n) throws FlooringOrdersDaoException{
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(CONFIG_FILE));
        } catch (Exception e) {
            throw new FlooringOrdersDaoException("Unable to save config data");
        }
            out.println(n);
            out.flush();
            out.close();
    }
    
    public HashMap<BigDecimal, Order> returnOrders(){
        return orders;
    }
}