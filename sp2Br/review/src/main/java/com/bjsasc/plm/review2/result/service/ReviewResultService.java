package com.bjsasc.plm.review2.result.service;

import java.util.List;

import com.bjsasc.plm.review2.expert.model.OuterReviewExpert;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.plm.review2.result.model.ReviewResult;

public interface ReviewResultService {

	/**
	 * 查询所有的数据
	 * @return
	 */
	public List<ReviewResult> getResultsByReview(String rvOrderId);
	
	public List<ReviewResult> getResultsByRvIdAndExecutor(String rvOrderId,String executorId);
	
	/**
	 * 根据InnerId查询评审结果
	 * @return
	 */
	public ReviewResult getReviewResultById(String innerId);
	
	/**
	 * 初始化
	 * @return
	 */
	public ReviewResult newResult();
	/**
	 * 创建一个意见结果
	 * 
	 * @param op
	 * @return
	 */
	public ReviewResult createReviewResult(ReviewResult op);
	
	/**
	 * 创建一个意见结果
	 * 
	 * @param op
	 * @return
	 */
	public ReviewResult createReviewResult(String resultType,String executorId,ReviewOrder rvOrder,ReviewObject rvObj,OuterReviewExpert outer);
	
}