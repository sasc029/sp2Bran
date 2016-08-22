package com.bjsasc.plm.review2.opinion.service;

import java.util.List;

import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.review2.opinion.model.ReviewOpinion;
import com.bjsasc.plm.review2.order.model.ReviewOrder;

public interface ReviewOpinionService {

	/**
	 * 查询所有的数据
	 * @return
	 */
	public List<ReviewOpinion> getOpinionsByReview(String rvOrderId);
	
	/**
	 * 根据InnerId查询评审意见
	 * @return
	 */
	public ReviewOpinion getOpinionsById(String innerId);
	
	/**
	 * 删除评审意见
	 * 
	 * */
	public void delReviewOpinions(String[] ids);
	/**
	 * 初始化
	 * @return
	 */
	public ReviewOpinion newOpinion();
	/**
	 * 创建一个意见对象
	 * 
	 * @param op
	 * @return
	 */
	public ReviewOpinion createOpinion(ReviewOpinion op);
	/**
	 * 判断是否是组长
	 * @return
	 */
	public boolean canConfirmOpinion(ReviewOrder rv,User user);
	
	/**
	 * 修改评审意见
	 * 
	 * */
	public void updateReviewOpinions(ReviewOpinion op);
	/**
	 * 根据评审任务获取意见列表
	 * 
	 * */
	public List<ReviewOpinion> getOpinions(String taskId);
	/**
	 * 根据评审单，创建用户,评审状态获取意见列表
	 * 
	 * */
	public List<ReviewOpinion> getOpinions(String rvOrderId,String opnState,String UserInnerId);
	
	/**
	 * 根据评审单，创建用户,评审状态,意见状态获取意见列表
	 * 
	 * */
	public List<ReviewOpinion> getOpinions(String rvOrderId,String opnState,String type,String UserInnerId);
	/**
	 * 根据评审单，创建用户获取意见列表
	 * 
	 * */
	public List<ReviewOpinion> getOpinions(String rvOrderId,String UserInnerId);
	
	public List<ReviewOpinion> getOpinionsByOutUserId(String rvOrderId,String outerUserId);
}