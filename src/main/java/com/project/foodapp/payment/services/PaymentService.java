package com.project.foodapp.payment.services;

import java.util.List;

import com.project.foodapp.payment.dtos.PaymentDTO;
import com.project.foodapp.response.Response;

public interface PaymentService {

    Response<?> initializePayment(PaymentDTO paymentDTO);

    void updatePaymentForOrder(PaymentDTO paymentDTO);

    Response<List<PaymentDTO>> getAllPayments();

    Response<PaymentDTO> getPaymentById(Long paymentId);

}
