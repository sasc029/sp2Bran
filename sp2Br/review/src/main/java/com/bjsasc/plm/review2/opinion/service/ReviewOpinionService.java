package com.bjsasc.plm.review2.opinion.service;

import java.util.List;

import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.review2.opinion.model.ReviewOpinion;
import com.bjsasc.plm.review2.order.model.ReviewOrder;

public interface ReviewOpinionService {

	/**
	 * ��ѯ���е�����
	 * @return
	 */
	public List<ReviewOpinion> getOpinionsByReview(String rvOrderId);
	
	/**
	 * ����InnerId��ѯ�������
	 * @return
	 */
	public ReviewOpinion getOpinionsById(String innerId);
	
	/**
	 * ɾ���������
	 * 
	 * */
	public void delReviewOpinions(String[] ids);
	/**
	 * ��ʼ��
	 * @return
	 */
	public ReviewOpinion newOpinion();
	/**
	 * ����һ���������
	 * 
	 * @param op
	 * @return
	 */
	public ReviewOpinion createOpinion(ReviewOpinion op);
	/**
	 * �ж��Ƿ����鳤
	 * @return
	 */
	public boolean canConfirmOpinion(ReviewOrder rv,User user);
	
	/**
	 * �޸��������
	 * 
	 * */
	public void updateReviewOpinions(ReviewOpinion op);
	/**
	 * �������������ȡ����б�
	 * 
	 * */
	public List<ReviewOpinion> getOpinions(String taskId);
	/**
	 * �������󵥣������û�,����״̬��ȡ����б�
	 * 
	 * */
	public List<ReviewOpinion> getOpinions(String rvOrderId,String opnState,String UserInnerId);
	
	/**
	 * �������󵥣������û�,����״̬,���״̬��ȡ����б�
	 * 
	 * */
	public List<ReviewOpinion> getOpinions(String rvOrderId,String opnState,String type,String UserInnerId);
	/**
	 * �������󵥣������û���ȡ����б�
	 * 
	 * */
	public List<ReviewOpinion> getOpinions(String rvOrderId,String UserInnerId);
	
	public List<ReviewOpinion> getOpinionsByOutUserId(String rvOrderId,String outerUserId);
}