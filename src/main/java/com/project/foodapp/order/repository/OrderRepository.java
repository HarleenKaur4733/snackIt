package com.project.foodapp.order.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.foodapp.auth_users.entity.User;
import com.project.foodapp.enums.OrderStatus;
import com.project.foodapp.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByOrderStatus(OrderStatus orderStatus, Pageable pageable);

    List<Order> findByUserOrderByOrderDateDesc(User users);

    @Query("SELECT COUNT(DISTINCT o.user.id) FROM Order o")
    long countDistinctUser();
}
