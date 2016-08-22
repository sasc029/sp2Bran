package com.bjsasc.plm.review2.conclusion.service;

import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.review2.conclusion.model.ReviewConclusionHS;

public class ReviewConclusionServiceImpl implements ReviewConclusionService {
	/**
	 * 查询所有数据
	 * @param reviewConclusion
	 */
	@Override
	public List<ReviewConclusionHS> findAllReviewConclusion() {
		String hql = "from ReviewConclusionHS";
		return (List<ReviewConclusionHS>)Helper.getPersistService().find(hql);
	}
	
	/**
	 * 查询单条数据
	 * @param innerId
	 * @return
	 */
	@Override
	public ReviewConclusionHS findReviewConclusionByInnerId(String innerId) {
		List<ReviewConclusionHS> reviewConclusion=Helper.getPersistService().find(" from ReviewConclusionHS WHERE innerId=?", innerId);
		if(!reviewConclusion.isEmpty()){
			return reviewConclusion.get(0);
		}
		return null;
	}

	@Override
	public void delReviewConclusion(String[] ids) {
		for(int i=0; i<ids.length; i++){
			ReviewConclusionHS reviewConclusion = findReviewConclusionByInnerId(ids[i]);
			deleteReviewConclusion(reviewConclusion);
		}
	}
	
	/**
	 * 删除记录
	 * @param reviewConclusion
	 */
	public void deleteReviewConclusion(ReviewConclusionHS reviewConclusion) {
		Helper.getPersistService().delete(reviewConclusion);
	}

	/**
	 * 新增数据
	 * @param reviewConclusion
	 */
	@Override
	public void saveReviewConclusion(ReviewConclusionHS reviewConclusion) {
		Helper.getPersistService().save(reviewConclusion);
	}

	/**
	 * 修改数据
	 * @param reviewConclusion
	 */
	@Override
	public void updateReviewConclusion(ReviewConclusionHS reviewConclusion) {
		Helper.getPersistService().update(reviewConclusion);
	}

	@Override
	public List<ReviewConclusionHS> findReviewConclusionByReviewOrder(String innerId) {
		String hql = "from ReviewConclusionHS as r where r.reviewOrderRef.innerId = ?";
		List<ReviewConclusionHS> list= (List<ReviewConclusionHS>)Helper.getPersistService().find(hql,innerId);
		return list;
	}
}
