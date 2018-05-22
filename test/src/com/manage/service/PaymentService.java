package com.manage.service;

import java.util.List;

import com.manage.model.Payment;

public interface PaymentService {

	Boolean savePaymentReality(List<Payment> paymentRealityList);

}
