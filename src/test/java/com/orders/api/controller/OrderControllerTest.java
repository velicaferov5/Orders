package com.orders.api.controller;

import com.orders.api.interfaces.OrderRepository;
import com.orders.api.interfaces.StockRepository;
import com.orders.api.model.Order;
import com.orders.api.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private StockRepository stockRepository;

    private static double PRODUCT_PRICE = 10.00;

    @BeforeEach
    void setup () {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    void getOrder() throws Exception {
        Optional<Order> order = Optional.of(newOrder(Order.Status.RUNNING));
        when(orderRepository.findById(anyInt())).thenReturn(order);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/order/get/0").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void cancelOrder() throws Exception {
        Optional<Order> order = Optional.of(newOrder(Order.Status.CANCELLED));
        when(orderRepository.findById(any())).thenReturn(order);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/api/order/cancel/0").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    Product newProduct() {
        Product product = new Product();
        product.setType("book");
        product.setName("Reality");
        product.setDescription("Book about Reality");
        product.setPrice(PRODUCT_PRICE);
        return product;
    }

    private Map<Product, Integer> newProducts(int count) {
        Map<Product, Integer> products = new HashMap<>();
        for (int index=0; index<count; index++) {
            products.put(newProduct(), 1);
        }
        return products;
    }

    private Order newOrder(Order.Status status) {
        Order order = new Order();
        int orderCount = 5;
        order.setProducts(newProducts(orderCount));
        order.setState(status);
        order.setTotalPrice(orderCount * PRODUCT_PRICE);
        return order;
    }
}