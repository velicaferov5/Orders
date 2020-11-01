package com.orders.api.service;

import com.orders.api.interfaces.OrderRepository;
import com.orders.api.interfaces.StockRepository;
import com.orders.api.model.Order;
import com.orders.api.model.Product;
import com.orders.api.model.Stock;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getOrderById() {
        Order order = newOrder(Order.Status.RUNNING);
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));
        assertTrue(orderRepository.findById(0).isPresent());
        assertOrder(order, orderRepository.findById(0).get());
    }

    @Test
    void makeOrder() {
        Order order = newOrder(Order.Status.RUNNING);
        order.setProducts(newProducts(5));
        when(stockRepository.findById(any())).thenReturn(Optional.of(newStock()));
        assertTrue(orderService.makeOrder(newProducts(5)).isPresent());
        Order actual = orderService.makeOrder(newProducts(5)).get();
        assertEquals(order.getProducts().size(), actual.getProducts().size());
    }

    @Test
    void cancelOrder() {
        Order order = newOrder(Order.Status.CANCELLED);
        when(orderRepository.findById(any())).thenReturn(Optional.of(order));
        assertTrue(orderService.cancelOrder(0).isPresent());
        assertOrder(order, orderService.cancelOrder(0).get());
    }

    private Map<Product, Integer> newProducts(int count) {
        Map<Product, Integer> products = new HashMap<>();
        for (int index=0; index<count; index++) {
            products.put(newProduct(), 1);
        }
        return products;
    }

    Product newProduct() {
        Product product = new Product();
        product.setType("book");
        product.setName("Reality");
        product.setDescription("Book about Reality");
        product.setPrice(15.0);
        return product;
    }

    private Stock newStock() {
        Stock stock = new Stock();
        stock.setAmount(10);
        return stock;
    }

    private Order newOrder(Order.Status status) {
        Order order = new Order();
        order.setProducts(newProducts(5));
        order.setState(status);
        order.setTotalPrice(500.00);
        return order;
    }

    private void assertOrder(Order expected, Order actual) {
        assertTrue((expected == null && actual == null) || (expected != null && actual != null) );
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getState(), actual.getState());
        assertEquals(expected.getTotalPrice(), actual.getTotalPrice());
        assertProducts(expected.getProducts(), actual.getProducts());
    }

    private void assertProducts(Map<Product, Integer> expected, Map<Product, Integer> actual) {
        assertEquals(expected.size(), actual.size());
        for (Map.Entry<Product, Integer> expEntry: expected.entrySet()) {
            assertEquals(expEntry.getValue(), actual.get(expEntry.getKey()));
        }
    }
}