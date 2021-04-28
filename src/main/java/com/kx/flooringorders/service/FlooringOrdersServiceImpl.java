/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.flooringorders.service;

import com.kx.flooringorders.dao.FlooringOrdersAuditDao;
import com.kx.flooringorders.dao.FlooringOrdersDao;
import com.kx.flooringorders.dao.FlooringOrdersDaoException;
import com.kx.flooringorders.dto.Order;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author khuxi
 */
public class FlooringOrdersServiceImpl implements FlooringOrdersService{

    FlooringOrdersDao dao;
    private FlooringOrdersAuditDao auditDao;
    //auditDao.writeAuditEntry(buying.getTag() + ": "+buying.getQuantity()+" remaining in stock");
    
    public FlooringOrdersServiceImpl(FlooringOrdersDao dao, FlooringOrdersAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }
    
    @Override
    public Order addOrder(BigDecimal number, Order order) throws FlooringOrdersDaoException {
        auditDao.writeAuditEntry("*New Order ADDED!* : " + number);
        return dao.addOrder(number, order);
    }

    @Override
    public HashMap<String, List> getAllOrders() throws FlooringOrdersDaoException {
        return dao.getAllOrders();
    }

    @Override
    public Order getOrder(BigDecimal number) throws FlooringOrdersDaoException {
        return dao.getOrder(number);
    }

    @Override
    public Order editOrder(BigDecimal number, Order order) throws FlooringOrdersDaoException {
        auditDao.writeAuditEntry("*Order EDITED!* : " + number);
        return dao.editOrder(number, order);
    }

    @Override
    public Order removeOrder(BigDecimal number) throws FlooringOrdersDaoException {
        auditDao.writeAuditEntry("*Order REMOVED!* : " + number);
        return dao.removeOrder(number);
    }

    @Override
    public boolean loadOrder(String date) throws FlooringOrdersDaoException {
        return dao.loadOrder(date);
    }

    @Override
    public void writeData(String date) throws FlooringOrdersDaoException {
        auditDao.writeAuditEntry("|| "+date+" ||");
        dao.writeData(date);
    }

    @Override
    public void clearOrders() {
        dao.clearOrders();
    }

    @Override
    public String[] loadConfig() throws FlooringOrdersDaoException {
        return dao.loadConfig();
    }

    @Override
    public void loadAll() throws FlooringOrdersDaoException {
        dao.loadAll();
    }

    @Override
    public void writeConfig(String[] config) throws FlooringOrdersDaoException {
        dao.writeConfig(config);
    }

    @Override
    public void updateConfig(String n) throws FlooringOrdersDaoException {
        dao.updateConfig(n);
    }

    @Override
    public String loadState(Order order) throws FlooringOrdersDaoException {
        return dao.loadState(order);
    }

    @Override
    public String[] loadCost(Order order) throws FlooringOrdersDaoException {
        return dao.loadCost(order);
    }

    @Override
    public HashMap<BigDecimal, Order> returnOrders() {
        return dao.returnOrders();
    }
    
}
