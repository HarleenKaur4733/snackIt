package com.project.foodapp.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.foodapp.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
