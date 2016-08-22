package com.bjsasc.plm.review2.object.service;

import java.util.List;

import com.bjsasc.plm.review2.object.model.ReviewObjectContext;

public interface ReviewObjectContextService {

	/**
	 * 查询所有的数据
	 * @return
	 */
	public List<ReviewObjectContext> findAllReviewObjectContext();
	/**
	 * 新增数据
	 * @param reviewObjectContext
	 */
	public void saveReviewObjectContext(ReviewObjectContext reviewObjectContext);
	/**
	 * 修改数据
	 * @param reviewObjectContext
	 */
	public void updateReviewObjectContext(ReviewObjectContext reviewObjectContext);
	/**
	 * 根据ReviewObjectContext的innerId查询单条数据
	 * @param innerId
	 * @return
	 */
	public ReviewObjectContext findReviewObjectContextByInnerId(String innerId);
	/**
	 * 删除记录
	 * @param reviewObjectContext
	 */
	public void deleteReviewObjectContext(ReviewObjectContext reviewObjectContext);
	/**
	 * 根据站点innerId查询ReviewObjectContext
	 * @param siteInnerId
	 * @return
	 */
	public ReviewObjectContext findReviewObjectContextBySiteInnerId(String siteInnerId);
}
