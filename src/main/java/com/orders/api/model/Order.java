package com.orders.api.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Table(name="Orders")
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column
    private Integer id;

    @ElementCollection
    @Column
    private Map<Product, Integer> products;

    @Column
    private Status status;

    @Column
    private Double totalPrice;

    @Column
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public Status getState() {
        return status;
    }

    public void setState(Status status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public enum Status {
        NOT_AVAILABLE,
        RUNNING,
        DELIVERED,
        CANCELLED
    }
}
