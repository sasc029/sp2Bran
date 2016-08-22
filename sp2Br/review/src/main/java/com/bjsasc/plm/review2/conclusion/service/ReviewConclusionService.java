package com.bjsasc.plm.review2.conclusion.service;

import java.util.List;

import com.bjsasc.plm.review2.conclusion.model.ReviewConclusionHS;


public interface ReviewConclusionService {

	/**
	 * 查询所有的数据
	 * @return
	 */
	public List<ReviewConclusionHS> findAllReviewConclusion();
	/**
	 * 新增数据
	 * @param reviewConclusion
	 */
	public void saveReviewConclusion(ReviewConclusionHS reviewConclusion);
	/**
	 * 修改数据
	 * @param reviewConclusion
	 */
	public void updateReviewConclusion(ReviewConclusionHS reviewConclusion);
	/**
	 * 根据ReviewConclusion的innerId查询单条数据
	 * @param innerId
	 * @return
	 */
	public ReviewConclusionHS findReviewConclusionByInnerId(String innerId);
	/**
	 * 删除记录
	 */
	public void delReviewConclusion(String[] ids);
	
	/**
	 * 根据评审单id查询单条数据
	 * @param managedReviewId 评审单对象Id
	 * @return
	 */
	public List<ReviewConclusionHS> findReviewConclusionByReviewOrder(String innerId);
	
}
