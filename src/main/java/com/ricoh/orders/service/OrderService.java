package com.ricoh.orders.service;

import com.ricoh.orders.model.Order;

public interface OrderService {
	
	public Order create(Order order);
	public Order read(Long id);
	public Order update(Long id, Order order);
	public Order delete(Long id);

}
