/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.flooringorders.dto;

import java.math.BigDecimal;

/**
 *
 * @author khuxi
 */
public class Order {
    
    /**
    * Order Object contains :
    *  Big decimal Id -> orderNumber
    *  String customer name -> customer
    *  String State location -> state
    *  BigDecimal tax rate -> taxRate
    *  String product type -> type
    *  BigDeciaml floor area -> area
    *  BigDeciaml product cost per square ft -> costPSQR
    *  BigDeciaml labor cost per square ft -> labCostPSQR
    *  BigDeciaml product total cost -> matCost
    *  BigDeciaml labor total cost -> laborCost
    *  BigDeciaml tax calculated -> tax
    *  BigDeciaml total cost -> total
    *  
    * */
    
    private BigDecimal orderorderNumber;
    private String customer;
    private String state;
    private BigDecimal taxRate;
    private String type;
    private BigDecimal area;
    private BigDecimal costPSQR;
    private BigDecimal labCostPSQR;
    private BigDecimal matCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;
    
    public Order(BigDecimal orderorderNumber){
        this.orderorderNumber = orderorderNumber;
    }

    public BigDecimal getOrderNumber() {
        return orderorderNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getCostPSQR() {
        return costPSQR;
    }

    public void setCostPSQR(BigDecimal costPSQR) {
        this.costPSQR = costPSQR;
    }

    public BigDecimal getLabCostPSQR() {
        return labCostPSQR;
    }

    public void setLabCostPSQR(BigDecimal labCostPSQR) {
        this.labCostPSQR = labCostPSQR;
    }

    public BigDecimal getMatCost() {
        return matCost;
    }

    public void setMatCost(BigDecimal matCost) {
        this.matCost = matCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj==this) return true;
        if (obj==null || obj.getClass()!=this.getClass()) return false;
        if (obj instanceof Order) {
        Order order = (Order) obj;
        return (this.getOrderNumber().equals(order.getOrderNumber()) && 
        order.getCustomer().equals(this.getCustomer()) && 
        (order.getState().equals(this.getState())));
    }
    return false;
    }

    @Override
    public int hashCode() {
        return this.getOrderNumber().intValue();
    }
}
