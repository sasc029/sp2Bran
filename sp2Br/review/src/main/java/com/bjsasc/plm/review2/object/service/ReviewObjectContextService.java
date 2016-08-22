package com.bjsasc.plm.review2.object.service;

import java.util.List;

import com.bjsasc.plm.review2.object.model.ReviewObjectContext;

public interface ReviewObjectContextService {

	/**
	 * ��ѯ���е�����
	 * @return
	 */
	public List<ReviewObjectContext> findAllReviewObjectContext();
	/**
	 * ��������
	 * @param reviewObjectContext
	 */
	public void saveReviewObjectContext(ReviewObjectContext reviewObjectContext);
	/**
	 * �޸�����
	 * @param reviewObjectContext
	 */
	public void updateReviewObjectContext(ReviewObjectContext reviewObjectContext);
	/**
	 * ����ReviewObjectContext��innerId��ѯ��������
	 * @param innerId
	 * @return
	 */
	public ReviewObjectContext findReviewObjectContextByInnerId(String innerId);
	/**
	 * ɾ����¼
	 * @param reviewObjectContext
	 */
	public void deleteReviewObjectContext(ReviewObjectContext reviewObjectContext);
	/**
	 * ����վ��innerId��ѯReviewObjectContext
	 * @param siteInnerId
	 * @return
	 */
	public ReviewObjectContext findReviewObjectContextBySiteInnerId(String siteInnerId);
}
