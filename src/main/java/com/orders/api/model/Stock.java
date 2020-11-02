package com.orders.api.model;

import javax.persistence.*;

@Entity
@Table(name="stock")
public class Stock {

    @Id
    @Column
    private Integer id;
    @Column
    private int amount;
    @OneToOne
    @MapsId
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", amount=" + amount +
                ", product=" + product +
                '}';
    }
}
