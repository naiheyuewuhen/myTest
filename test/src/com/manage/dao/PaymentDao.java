package com.manage.dao;

import java.util.List;
import java.util.Map;

import com.manage.model.Payment;
import com.manage.model.WebPage;

public interface PaymentDao {

	/**
	 * 批量插入约定付款表
	 * @param paymentTableName
	 * @param poaymentList
	 * @return
	 */
	Boolean saveAgreedBatch(List<Payment> paymentAgreedList);

	/**
	 * 批量插入实际付款表
	 * @param paymentTableName
	 * @param poaymentList
	 * @return
	 */
	Boolean saveRealityBatch(List<Payment> paymentRealityList);

	/**
	 * 更新状态为失效
	 * @param payment.updateUser
	 * @param payment.updateTime
	 * @param payment.contractId
	 * @return
	 */
	Integer updateAgreedUnuse(Payment payment);

	Boolean updateAgreedBatch(List<Payment> paymentList);

	WebPage<Payment> getRealityAll(Map<String,String> map,Boolean hasPage);

	WebPage<Payment> getAgreedAll(Map<String,String> map,Boolean hasPage);

}
