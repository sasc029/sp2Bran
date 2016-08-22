package com.bjsasc.plm.review2.object.service;

import com.bjsasc.plm.review2.object.model.ReviewObject;

public interface ReviewObjectService {

	/**
	 * 查询评审对象
	 * @param
	 *     iid 评审对象id
	 */
	public ReviewObject findReviewObjectById(String innerId);
	
	/**
	 * 添加评审对象
	 * @param
	 *     reviewObject 评审对象
	 */
	public void saveReviewObject(ReviewObject reviewObject);
	
	/**
	 * 删除评审对象
	 * @param
	 *     reviewObject 评审对象
	 */
	public void delReviewObject(String[] ids);
	
	/**
	 * 修改评审对象
	 * @param
	 *     reviewObject 评审对象
	 */
	public void updateReviewObject(ReviewObject reviewObject);
	
}
