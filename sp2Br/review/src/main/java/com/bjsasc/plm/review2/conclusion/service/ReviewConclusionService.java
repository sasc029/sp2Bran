package com.bjsasc.plm.review2.conclusion.service;

import java.util.List;

import com.bjsasc.plm.review2.conclusion.model.ReviewConclusionHS;


public interface ReviewConclusionService {

	/**
	 * ��ѯ���е�����
	 * @return
	 */
	public List<ReviewConclusionHS> findAllReviewConclusion();
	/**
	 * ��������
	 * @param reviewConclusion
	 */
	public void saveReviewConclusion(ReviewConclusionHS reviewConclusion);
	/**
	 * �޸�����
	 * @param reviewConclusion
	 */
	public void updateReviewConclusion(ReviewConclusionHS reviewConclusion);
	/**
	 * ����ReviewConclusion��innerId��ѯ��������
	 * @param innerId
	 * @return
	 */
	public ReviewConclusionHS findReviewConclusionByInnerId(String innerId);
	/**
	 * ɾ����¼
	 */
	public void delReviewConclusion(String[] ids);
	
	/**
	 * ��������id��ѯ��������
	 * @param managedReviewId ���󵥶���Id
	 * @return
	 */
	public List<ReviewConclusionHS> findReviewConclusionByReviewOrder(String innerId);
	
}
