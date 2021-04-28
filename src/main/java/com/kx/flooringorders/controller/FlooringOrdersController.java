/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.flooringorders.controller;

import com.kx.flooringorders.dao.FlooringOrdersDaoException;
import com.kx.flooringorders.dto.Order;
import com.kx.flooringorders.service.FlooringOrdersService;
import com.kx.flooringorders.ui.FlooringOrdersView;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author khuxi
 */
public class FlooringOrdersController {
    FlooringOrdersView view;
    FlooringOrdersService service;
    
    public FlooringOrdersController(FlooringOrdersService service, FlooringOrdersView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        String[] config = new String[2];
        String numOrders = "0";

        try {
            config = service.loadConfig();
            config[0] = Boolean.toString(view.isProduction());
            numOrders = config[1];
            service.writeConfig(config);
        } catch (FlooringOrdersDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }

        if (config[0].equals("true")) {
            boolean keepGoing = true;
            int menuSelection = 0;

            try {
                while (keepGoing) {

                    menuSelection = getMenuSelection();

                    switch (menuSelection) {
                        case 1:
                            listOrders();
                            break;
                        case 2:
                            numOrders = createOrder(numOrders);
                            service.updateConfig(numOrders);
                            break;
                        case 3:
                            changeOrder();
                            break;
                        case 4:
                            viewOrder();
                            break;
                        case 5:
                            removeOrder();
                            break;
                        case 6:
                            keepGoing = false;
                            break;
                        default:
                            unknownCommad();
                    }

                }
                //exitMessage();
            } catch (FlooringOrdersDaoException e) {
                view.displayErrorMessage(e.getMessage());
            }
       }
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private String createOrder(String n) throws FlooringOrdersDaoException {
        BigDecimal taxRate = BigDecimal.ZERO;
        String[] costs = new String[2];
        String date = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.now();
        String[] setupDate = dtf.format(localDate).split("/");
        for (String s : setupDate) {
            date += s;
        }
        Order newOrder = view.getNewOrderInfo(n);
        do {
            try {
                taxRate = new BigDecimal(service.loadState(newOrder));
            } catch (Exception e) {
                view.displayErrorMessage(e.getMessage());
                view.getState(newOrder);
            }
        } while (taxRate.equals(BigDecimal.ZERO));
        costs = service.loadCost(newOrder);
        while (costs[0] == null) {
            view.getProduct(newOrder);
            costs = service.loadCost(newOrder);
        }

        newOrder = view.setFees(taxRate, costs, newOrder);
        view.calculateCost(newOrder);
        view.displayOrder(newOrder);
        if (view.confirmAdd()) {
            service.loadOrder(date);
            service.addOrder(newOrder.getOrderNumber(), newOrder);
            service.writeData(date);
            int x = Integer.parseInt(n) + 1;
            n = Integer.toString(x);
            service.clearOrders();
        }
        return n;
    }

    private void changeOrder() throws FlooringOrdersDaoException {
        BigDecimal taxRate = BigDecimal.ZERO;
        String[] costs = new String[2];
        String date = "";
        String[] setupDate = view.getDate().split("/");
        for (String s : setupDate) {
            date += s;
        }
        if (service.loadOrder(date)) {
            List<Order> orderList = new ArrayList<Order>(service.returnOrders().values());
            view.displayOrderList(orderList);
            try {
                String n = view.getNumberInput();
                BigDecimal number = new BigDecimal(n);
                Order currentOrder = service.getOrder(number);
                Order editOrder = view.getEditOrderInfo(currentOrder);
                do {
                    try {
                        taxRate = new BigDecimal(service.loadState(editOrder));
                    } catch (Exception e) {
                        view.displayErrorMessage(e.getMessage());
                        view.getState(editOrder);
                    }
                } while (taxRate.equals(BigDecimal.ZERO));
                costs = service.loadCost(editOrder);
                while (costs[0] == null) {
                    view.getProduct(editOrder);
                    costs = service.loadCost(editOrder);
                }
                editOrder = view.setFees(taxRate, costs, editOrder);
                view.calculateCost(editOrder);
                view.displayOrder(editOrder);
                if (view.confirmAdd()) {
                    service.editOrder(number, editOrder);
                    service.writeData(date);
                    service.clearOrders();
                }
            } catch (Exception e) {
                view.noOrder();
            }
        } else {
            view.noOrder();
        }
    }

    private void listOrders() throws FlooringOrdersDaoException {
        view.displayDisplayAllBanner();
        HashMap<String, List> orderList = service.getAllOrders();
        view.displayAllOrderList(orderList);
        service.clearOrders();
    }

    private void viewOrder() throws FlooringOrdersDaoException {
        String date = "";
        String[] setupDate = view.getDate().split("/");
        for (String s : setupDate) {
            date += s;
        }

        if (service.loadOrder(date)) {
            List<Order> orderList = new ArrayList<Order>(service.returnOrders().values());
            view.displayOrderList(orderList);
            try {
                String n = view.getNumberInput();
                BigDecimal number = new BigDecimal(n);
                Order currentOrder = service.getOrder(number);
                view.displayOrder(currentOrder);
                service.clearOrders();
            } catch (Exception e) {
                view.noOrder();
            }
        } else {
            view.noOrder();
        }
    }

    private void removeOrder() throws FlooringOrdersDaoException {
//        view.displayRemoveDVDBanner();
        String date = "";
        String[] setupDate = view.getDate().split("/");
        for (String s : setupDate) {
            date += s;
        }
        if (service.loadOrder(date)) {
            List<Order> orderList = new ArrayList<Order>(service.returnOrders().values());
            view.displayOrderList(orderList);
            try {
                String n = view.getNumberInput();
                BigDecimal number = new BigDecimal(n);
                Order currentOrder = service.getOrder(number);
                if (view.confirmRemove()) {
                    service.removeOrder(number);
                    service.writeData(date);
                    service.clearOrders();
                }
            } catch (Exception e) {
                view.noOrder();
            }
        } else {
            view.noOrder();
        }
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private void unknownCommad() {
        view.displayUnknownCommandBanner();
    }
}
