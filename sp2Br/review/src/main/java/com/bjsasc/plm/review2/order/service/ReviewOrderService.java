package com.bjsasc.plm.review2.order.service;

import java.util.List;

import com.bjsasc.plm.review2.order.model.ReviewOrder;


public interface ReviewOrderService {
	
	/**
	 * ����������������ɵ��ݱ��
	 * 
	 */
	public String getOrderId(String docId);

	/**
	 * ��ѯ���е�����
	 * @return
	 */
	public List<ReviewOrder> findAllManagedReview();
	/**
	 * ��������
	 * @param ManagedReview
	 */
	public void saveManagedReview(ReviewOrder managedReview);
	/**
	 * �޸�����
	 * @param managedReview
	 */
	public void updateManagedReview(ReviewOrder managedReview);
	/**
	 * ����ManagedReview��innerId��ѯ��������
	 * @param innerId
	 * @return
	 */
	public ReviewOrder findManagedReviewByInnerId(String innerId);
	/**
	 * ɾ����¼
	 */
	public void delManagedReview(String[] ids);
	
	/**
	 * ���ͺ���֤����
	 * @param managedReview
	 */
	public void sendReviewCertificate(ReviewOrder managedReview);
	
	/**
	 * ���ͻ��麯��֤����
	 * @param managedReview
	 */
	public void sendMeetingReview(ReviewOrder managedReview);
	
	/**
	 * ���ͻ�������ַ���Ϣ��A3ϵͳ
	 * @param reviewOrder
	 */
	public void sendReviewDistribute(ReviewOrder reviewOrder);
}
