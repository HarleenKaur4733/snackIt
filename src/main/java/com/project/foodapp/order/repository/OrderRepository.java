package com.project.foodapp.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.foodapp.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
