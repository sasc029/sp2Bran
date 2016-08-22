package com.bjsasc.plm.review2.object.service;

import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.review2.object.model.ReviewObject;

public class ReviewObjectServiceImpl implements ReviewObjectService {

	@Override
	public ReviewObject findReviewObjectById(String innerId) {
		String hql = "from ReviewObject where innerId = ?";
		List list = Helper.getPersistService().find(hql.toString(), innerId);
		if(!list.isEmpty()){
			return (ReviewObject)list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void saveReviewObject(ReviewObject reviewObject) {
		Helper.getPersistService().save(reviewObject);
	}

	@Override
	public void delReviewObject(String[] ids) {
		for(int i=0; i<ids.length; i++){
			ReviewObject reviewObject = (ReviewObject)Helper.getPersistService().getObject(ReviewObject.class.getSimpleName(),ids[i]);
			deleteReviewObject(reviewObject);
		}
	}
	
	/**
	 * 删除评审对象
	 * @param
	 *     ReviewObject 评审对象
	 */
	public void deleteReviewObject(ReviewObject reviewObject){
		Helper.getPersistService().delete(reviewObject);
	}

	@Override
	public void updateReviewObject(ReviewObject reviewObject) {
		Helper.getPersistService().update(reviewObject);
	}

}
