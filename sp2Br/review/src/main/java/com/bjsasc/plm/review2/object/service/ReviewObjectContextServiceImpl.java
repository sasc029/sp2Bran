package com.bjsasc.plm.review2.object.service;

import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.review2.object.model.ReviewObjectContext;

public class ReviewObjectContextServiceImpl implements ReviewObjectContextService {
	/**
	 * 查询所有数据
	 * @param reviewObjectContext
	 */
	@Override
	public List<ReviewObjectContext> findAllReviewObjectContext() {
		String hql = "from ReviewObjectContext";
		return (List<ReviewObjectContext>)Helper.getPersistService().find(hql);
	}
	
	/**
	 * 查询单条数据
	 * @param innerId
	 * @return
	 */
	@Override
	public ReviewObjectContext findReviewObjectContextByInnerId(String innerId) {
		List<ReviewObjectContext> reviewObjectContext=Helper.getPersistService().find(" from ReviewObjectContext WHERE innerId=?", innerId);
		if(!reviewObjectContext.isEmpty()){
			return reviewObjectContext.get(0);
		}
		return null;
	}

	/**
	 * 删除记录
	 * @param reviewObjectContext
	 */
	@Override
	public void deleteReviewObjectContext(ReviewObjectContext reviewObjectContext) {
		Helper.getPersistService().delete(reviewObjectContext);
	}

	/**
	 * 新增数据
	 * @param reviewObjectContext
	 */
	@Override
	public void saveReviewObjectContext(ReviewObjectContext reviewObjectContext) {
		Helper.getPersistService().save(reviewObjectContext);
	}

	/**
	 * 修改数据
	 * @param reviewObjectContext
	 */
	@Override
	public void updateReviewObjectContext(ReviewObjectContext reviewObjectContext) {
		Helper.getPersistService().update(reviewObjectContext);
	}

	/**
	 * 根据站点innerId查询ReviewObjectContext
	 * @param siteInnerId
	 * @return
	 */
	@Override
	public ReviewObjectContext findReviewObjectContextBySiteInnerId(String siteInnerId){
		List<ReviewObjectContext> reviewObjectContext=Helper.getPersistService().find(" from ReviewObjectContext WHERE sourceSiteRef.innerId=?", siteInnerId);
		if(!reviewObjectContext.isEmpty()){
			return reviewObjectContext.get(0);
		}
		return null;
	}
}
