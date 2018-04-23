package com.ricoh.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ricoh.orders.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
