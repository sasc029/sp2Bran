package com.bjsasc.plm.review2.task.service;

import java.util.List;

import com.bjsasc.plm.review2.task.model.ReviewTask;

public interface ReviewTaskService {

	/**
	 * 查询所有的数据
	 * @return
	 */
	public List<ReviewTask> getRvTasksByReview(String rvOrderId);
	
	/**
	 * 根据任务Id查询数据
	 * @return
	 */
	public ReviewTask getRvTasksById(String taskId);
	
	/**
	 * 根据任务状态和执行人查询函审任务
	 * @return
	 */
	public List<ReviewTask> getRvTasksByState(String taskState,String userId);
	
	/**
	 * 根据任务执行人和评审单查询函审任务
	 * @return
	 */
	public List<ReviewTask> getRvTasksByExecutorAndOrder(String userId,String orderId);
	
	/**
	 * 结束任务
	 * @return
	 */
	public void closeRvTaskById(String taskId);
	
	/**
	 * 创建一个函审任务
	 * 
	 * @param op
	 * @return
	 */
	public ReviewTask createReviewTask(String name,String taskRole,String rvOrderId,String aaUserId,String objectId);
}