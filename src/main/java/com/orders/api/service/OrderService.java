package com.orders.api.service;

import com.orders.api.interfaces.OrderRepository;
import com.orders.api.interfaces.StockRepository;
import com.orders.api.model.Order;
import com.orders.api.model.Product;
import com.orders.api.model.Stock;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


/**
 * Services to manage Orders
 */
@Service
public class OrderService {

    private OrderRepository orderRepository;
    private StockRepository stockRepository;

    public OrderService(OrderRepository orderRepository, StockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.stockRepository = stockRepository;
    }

    /**
     * Gets Order by {@param id}.
     * @param id
     * @return Order
     */
    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    /**
     * Makes order
     * @param products
     * @return optional Order
     */
    public Optional<Order> makeOrder(Map<Product, Integer> products) {
        try {
            double totalPrice = 0;
            for (Map.Entry<Product, Integer> product: products.entrySet()) {
                Optional<Stock> inStock = stockRepository.findById(product.getKey().getId());
                if (inStock.isEmpty() || inStock.get().getAmount() <= product.getValue()) {
                    return Optional.empty();
                } else {
                    totalPrice += product.getKey().getPrice() * product.getValue();
                }
            }
            Order order = new Order();
            order.setState(Order.Status.RUNNING);
            order.setProducts(products);
            order.setTotalPrice(totalPrice);
            orderRepository.save(order);
            return Optional.of(order);
        } catch(Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Allows to cancel Order with {@param id}.
     * @param id
     * @return result of removing
     */
    public Optional<Order> cancelOrder(int id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order updatedOrder = order.get();
            updatedOrder.setState(Order.Status.CANCELLED);
            orderRepository.save(updatedOrder);
            return Optional.of(updatedOrder);
        } else {
            return Optional.empty();
        }
    }
}
