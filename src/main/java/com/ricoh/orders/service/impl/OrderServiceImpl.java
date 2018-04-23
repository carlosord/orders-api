package com.ricoh.orders.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricoh.orders.model.Associations;
import com.ricoh.orders.model.Order;
import com.ricoh.orders.repository.OrderRepository;
import com.ricoh.orders.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;

	@Override
	@Transactional()
	public Order create(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public Order read(Long id) {
		return orderRepository.findOne(id);
	}

	@Override
	@Transactional()
	public Order update(Long id, Order order) {
		Order toUpdate = orderRepository.findOne(id);
		toUpdate.getArticles().forEach(article -> Associations.Has.unlink(toUpdate, article)); // Unlink actual articles
		order.getArticles().forEach(article -> Associations.Has.link(toUpdate, article)); // Link new articles
		return orderRepository.save(toUpdate);
	}

	@Override
	@Transactional()
	public Order delete(Long id) {
		Order order = orderRepository.findOne(id);
		if (order != null) orderRepository.delete(id);
		return order;
	}

}
