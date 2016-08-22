package com.bjsasc.plm.review2.conclusion.service;

import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.review2.conclusion.model.ReviewConclusionHS;

public class ReviewConclusionServiceImpl implements ReviewConclusionService {
	/**
	 * ��ѯ��������
	 * @param reviewConclusion
	 */
	@Override
	public List<ReviewConclusionHS> findAllReviewConclusion() {
		String hql = "from ReviewConclusionHS";
		return (List<ReviewConclusionHS>)Helper.getPersistService().find(hql);
	}
	
	/**
	 * ��ѯ��������
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
	 * ɾ����¼
	 * @param reviewConclusion
	 */
	public void deleteReviewConclusion(ReviewConclusionHS reviewConclusion) {
		Helper.getPersistService().delete(reviewConclusion);
	}

	/**
	 * ��������
	 * @param reviewConclusion
	 */
	@Override
	public void saveReviewConclusion(ReviewConclusionHS reviewConclusion) {
		Helper.getPersistService().save(reviewConclusion);
	}

	/**
	 * �޸�����
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
