package com.muzimin.bean;

/**
 * @author: 李煌民
 * @date: 2022-06-14 23:21
 **/
public class Order {
    private String orderName;
    private int price;
    private String address;

    public Order(String orderName, int price) {
        this.orderName = orderName;
        this.price = price;
    }

    public Order(String orderName, int price, String address) {
        this.orderName = orderName;
        this.price = price;
        this.address = address;
    }

    public Order() {
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderName='" + orderName + '\'' +
                ", price=" + price +
                ", address='" + address + '\'' +
                '}';
    }
}
