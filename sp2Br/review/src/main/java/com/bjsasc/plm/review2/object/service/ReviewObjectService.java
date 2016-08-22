package com.bjsasc.plm.review2.object.service;

import com.bjsasc.plm.review2.object.model.ReviewObject;

public interface ReviewObjectService {

	/**
	 * ��ѯ�������
	 * @param
	 *     iid �������id
	 */
	public ReviewObject findReviewObjectById(String innerId);
	
	/**
	 * ����������
	 * @param
	 *     reviewObject �������
	 */
	public void saveReviewObject(ReviewObject reviewObject);
	
	/**
	 * ɾ���������
	 * @param
	 *     reviewObject �������
	 */
	public void delReviewObject(String[] ids);
	
	/**
	 * �޸��������
	 * @param
	 *     reviewObject �������
	 */
	public void updateReviewObject(ReviewObject reviewObject);
	
}
