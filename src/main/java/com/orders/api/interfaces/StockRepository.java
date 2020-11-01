package com.orders.api.interfaces;

import com.orders.api.model.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, Integer> {
}
