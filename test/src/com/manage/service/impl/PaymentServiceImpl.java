package com.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manage.dao.ContractDao;
import com.manage.dao.PaymentDao;
import com.manage.model.Payment;
import com.manage.service.PaymentService;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

	@Resource
	private PaymentDao paymentDao;
	@Resource
	private ContractDao contractDao;
	@Override
	public Boolean savePaymentReality(List<Payment> paymentRealityList) {
		paymentDao.saveRealityBatch(paymentRealityList);
		contractDao.updateStatus(paymentRealityList.get(0).getContractId(),6);//更新合同状态为可拆解状态
		return true;
	}
}
