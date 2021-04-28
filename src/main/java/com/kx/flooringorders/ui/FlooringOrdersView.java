/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.flooringorders.ui;

import com.kx.flooringorders.dto.Order;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author khuxi
 */
public class FlooringOrdersView {
    
    private FlooringOrdersUserIO io;

    public FlooringOrdersView(FlooringOrdersUserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. View All Orders");
        io.print("2. Add Order");
        io.print("3. Edit Order");
        io.print("4. View Order");
        io.print("5. Remove Order");
        io.print("6. Exit");

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    public Order getNewOrderInfo(String n) {
        BigDecimal number = new BigDecimal(n);
        String customer = "";
        String state = "";
        String type = "";
        String area = ".00";
        while (customer.equals("")) {
            customer = io.readString("Please enter customer name");
        }
        while (state.equals("")) {
            state = io.readString("Please Enter State");
        }
        while (type.equals("")) {
            type = io.readString("Please enter Product Type");
        }
        while (area.equals(".00")) {
            area = io.readStringBDM("Please enter floor area");
        }

        Order currentOrder = new Order(number);
        currentOrder.setCustomer(customer);
        currentOrder.setState(state);
        currentOrder.setType(type);
        currentOrder.setArea(new BigDecimal(area));
        return currentOrder;
    }

    public Order getEditOrderInfo(Order order) {
        if (order != null) {
            String customer = io.readString("Please enter customer name");
            if (!customer.equals("")) {
                order.setCustomer(customer);
            }
            String state = io.readString("Please Enter State");
            if (!state.equals("")) {
                order.setState(state);
            }
            String type = io.readString("Please enter Product Type");
            if (!type.equals("")) {
                order.setType(type);
            }
            String area = io.readStringBDM("Please enter floor area");
            if (!area.equals(".00")) {
                order.setArea(new BigDecimal(area));
            }

        } else {
            io.print("No such Order.");
        }
        io.readString("Please hit enter to continue.");
        return order;
    }

    public void displayAllOrderList(HashMap<String, List> orderList) {
        String key;
        List<Order> oList;
        for (Map.Entry<String, List> e : orderList.entrySet()) {
            key = e.getKey();
            oList = e.getValue();
            io.print(key);
            for (Order currentOrder : oList) {
                io.print(currentOrder.getOrderNumber() + " "
                        + currentOrder.getCustomer() + " "
                        + currentOrder.getState() + " "
                        + currentOrder.getTaxRate() + " "
                        + currentOrder.getType() + " "
                        + currentOrder.getArea() + " "
                        + currentOrder.getCostPSQR() + " "
                        + currentOrder.getLabCostPSQR() + " "
                        + currentOrder.getMatCost() + " "
                        + currentOrder.getLaborCost() + " "
                        + currentOrder.getTax() + " "
                        + currentOrder.getTotal());
            }
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayDisplayAllBanner() {
        io.print("=== All Orders ===");
    }

    public void displayOrder(Order order) {
        if (order != null) {
            io.print(order.getOrderNumber().toString());
            io.print(order.getCustomer());
            io.print(order.getState());
            io.print(order.getTaxRate().toString());
            io.print(order.getType());
            io.print(order.getArea().toString());
            io.print(order.getCostPSQR().toString());
            io.print(order.getLabCostPSQR().toString());
            io.print(order.getMatCost().toString());
            io.print(order.getLaborCost().toString());
            io.print(order.getTax().toString());
            io.print(order.getTotal().toString());
            io.print("");
        } else {
            io.print("No such Order.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayOrderList(List<Order> orderList) {
            for (Order currentOrder : orderList) {
                io.print(currentOrder.getOrderNumber() + " "
                        + currentOrder.getCustomer() + " "
                        + currentOrder.getState() + " "
                        + currentOrder.getTaxRate() + " "
                        + currentOrder.getType() + " "
                        + currentOrder.getArea() + " "
                        + currentOrder.getCostPSQR() + " "
                        + currentOrder.getLabCostPSQR() + " "
                        + currentOrder.getMatCost() + " "
                        + currentOrder.getLaborCost() + " "
                        + currentOrder.getTax() + " "
                        + currentOrder.getTotal());
            }
        io.readString("Please hit enter to continue.");
    }

    public void displayItemBanner() {
        io.print("=== Selected Vending Item ===");
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public String getNumberInput() {
        return Integer.toString(io.readInt("Order Number"));
    }

    public Order setFees(BigDecimal taxRate, String[] costs, Order order) {
        order.setTaxRate(taxRate);
        order.setCostPSQR(new BigDecimal(costs[0]));
        order.setLabCostPSQR(new BigDecimal(costs[1]));
        return order;
    }

    public Order calculateCost(Order order) {
        BigDecimal matCost = (order.getArea().multiply(order.getCostPSQR()).setScale(2, RoundingMode.HALF_UP));
        order.setMatCost(matCost);
        BigDecimal labCost = (order.getArea().multiply(order.getLabCostPSQR()).setScale(2, RoundingMode.HALF_UP));
        order.setLaborCost(labCost);
        order.setTax((order.getMatCost().add(order.getLaborCost())).multiply(order.getTaxRate().divide(new BigDecimal("100"))).setScale(2, RoundingMode.HALF_UP));
        order.setTotal((order.getMatCost().add(order.getLaborCost().add(order.getTax()))).setScale(2, RoundingMode.HALF_UP));
        return order;
    }

    public boolean confirmAdd() {
        return (io.readString("Please Confirm your order! [y/n]").equalsIgnoreCase("y"));
    }

    public boolean confirmRemove() {
        return (io.readString("Please Confirm order removal [y/n]").equalsIgnoreCase("y"));
    }

    public String getDate() {
        String localDate = io.readLocalDate("Please enter year of release date", "Please enter Month number of release date", "Please enter day number of release date");
        return localDate;
    }

    public void noOrder() {
        io.print("No such Order");
    }

    public boolean isProduction() {
        boolean isProduction = false;
        boolean isAcceptable = false;
        String in = io.readString("Is this for Testing? [y/n]");
        while (!isAcceptable) {
            if (in.equalsIgnoreCase("n")) {
                isProduction = true;
                isAcceptable = true;
            } else if (in.equalsIgnoreCase("y")) {
                isProduction = false;
                isAcceptable = true;
            }
        }
        return isProduction;
    }

    public Order getState(Order order) {
        order.setState(io.readString("Please Enter a state amongst OH, MI, PA, IN"));
        return order;
    }

    public Order getProduct(Order order) {
        order.setType(io.readString("Please Enter a product amongst Carpet, Laminate, Tile, Wood"));
        return order;
    }
}
