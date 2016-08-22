package com.bjsasc.plm.review2.result.service;

import java.util.List;

import com.bjsasc.plm.review2.expert.model.OuterReviewExpert;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.plm.review2.result.model.ReviewResult;

public interface ReviewResultService {

	/**
	 * ��ѯ���е�����
	 * @return
	 */
	public List<ReviewResult> getResultsByReview(String rvOrderId);
	
	public List<ReviewResult> getResultsByRvIdAndExecutor(String rvOrderId,String executorId);
	
	/**
	 * ����InnerId��ѯ������
	 * @return
	 */
	public ReviewResult getReviewResultById(String innerId);
	
	/**
	 * ��ʼ��
	 * @return
	 */
	public ReviewResult newResult();
	/**
	 * ����һ��������
	 * 
	 * @param op
	 * @return
	 */
	public ReviewResult createReviewResult(ReviewResult op);
	
	/**
	 * ����һ��������
	 * 
	 * @param op
	 * @return
	 */
	public ReviewResult createReviewResult(String resultType,String executorId,ReviewOrder rvOrder,ReviewObject rvObj,OuterReviewExpert outer);
	
}