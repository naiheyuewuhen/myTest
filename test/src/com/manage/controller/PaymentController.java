package com.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.model.Payment;
import com.manage.service.PaymentService;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	@Resource
	private PaymentService paymentService;
	
	@ResponseBody()
	@RequestMapping(value="/save",produces="application/json; charset=utf-8")
	public String save(Payment payment,HttpSession session){
		Date date=new Date();
		payment.setStatus(1);
		payment.setCreateUser((String)session.getAttribute("userName"));
		payment.setCreateTime(date);
		payment.setUpdateUser((String)session.getAttribute("userName"));
		payment.setUpdateTime(date);
		List<Payment> payments = new ArrayList<Payment>();
		payments.add(payment);
		paymentService.savePaymentReality(payments);
		return "{\"success\":true,\"data\":\"ok\"}";
	}
}
