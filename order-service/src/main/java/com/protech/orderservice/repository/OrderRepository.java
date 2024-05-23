package com.protech.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.protech.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
