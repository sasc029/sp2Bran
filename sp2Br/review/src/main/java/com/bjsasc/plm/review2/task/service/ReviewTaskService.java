package com.bjsasc.plm.review2.task.service;

import java.util.List;

import com.bjsasc.plm.review2.task.model.ReviewTask;

public interface ReviewTaskService {

	/**
	 * ��ѯ���е�����
	 * @return
	 */
	public List<ReviewTask> getRvTasksByReview(String rvOrderId);
	
	/**
	 * ��������Id��ѯ����
	 * @return
	 */
	public ReviewTask getRvTasksById(String taskId);
	
	/**
	 * ��������״̬��ִ���˲�ѯ��������
	 * @return
	 */
	public List<ReviewTask> getRvTasksByState(String taskState,String userId);
	
	/**
	 * ��������ִ���˺����󵥲�ѯ��������
	 * @return
	 */
	public List<ReviewTask> getRvTasksByExecutorAndOrder(String userId,String orderId);
	
	/**
	 * ��������
	 * @return
	 */
	public void closeRvTaskById(String taskId);
	
	/**
	 * ����һ����������
	 * 
	 * @param op
	 * @return
	 */
	public ReviewTask createReviewTask(String name,String taskRole,String rvOrderId,String aaUserId,String objectId);
}