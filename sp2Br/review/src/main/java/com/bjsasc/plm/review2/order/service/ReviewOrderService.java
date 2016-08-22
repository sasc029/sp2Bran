package com.bjsasc.plm.review2.order.service;

import java.util.List;

import com.bjsasc.plm.review2.order.model.ReviewOrder;


public interface ReviewOrderService {
	
	/**
	 * 根据评审对象编号生成单据编号
	 * 
	 */
	public String getOrderId(String docId);

	/**
	 * 查询所有的数据
	 * @return
	 */
	public List<ReviewOrder> findAllManagedReview();
	/**
	 * 新增数据
	 * @param ManagedReview
	 */
	public void saveManagedReview(ReviewOrder managedReview);
	/**
	 * 修改数据
	 * @param managedReview
	 */
	public void updateManagedReview(ReviewOrder managedReview);
	/**
	 * 根据ManagedReview的innerId查询单条数据
	 * @param innerId
	 * @return
	 */
	public ReviewOrder findManagedReviewByInnerId(String innerId);
	/**
	 * 删除记录
	 */
	public void delManagedReview(String[] ids);
	
	/**
	 * 发送函审证明书
	 * @param managedReview
	 */
	public void sendReviewCertificate(ReviewOrder managedReview);
	
	/**
	 * 发送会议函审证明书
	 * @param managedReview
	 */
	public void sendMeetingReview(ReviewOrder managedReview);
	
	/**
	 * 发送会议评审分发信息到A3系统
	 * @param reviewOrder
	 */
	public void sendReviewDistribute(ReviewOrder reviewOrder);
}
