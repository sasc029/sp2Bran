package com.bjsasc.plm.review2.object.service;

import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.review2.object.model.ReviewObjectContext;

public class ReviewObjectContextServiceImpl implements ReviewObjectContextService {
	/**
	 * ��ѯ��������
	 * @param reviewObjectContext
	 */
	@Override
	public List<ReviewObjectContext> findAllReviewObjectContext() {
		String hql = "from ReviewObjectContext";
		return (List<ReviewObjectContext>)Helper.getPersistService().find(hql);
	}
	
	/**
	 * ��ѯ��������
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
	 * ɾ����¼
	 * @param reviewObjectContext
	 */
	@Override
	public void deleteReviewObjectContext(ReviewObjectContext reviewObjectContext) {
		Helper.getPersistService().delete(reviewObjectContext);
	}

	/**
	 * ��������
	 * @param reviewObjectContext
	 */
	@Override
	public void saveReviewObjectContext(ReviewObjectContext reviewObjectContext) {
		Helper.getPersistService().save(reviewObjectContext);
	}

	/**
	 * �޸�����
	 * @param reviewObjectContext
	 */
	@Override
	public void updateReviewObjectContext(ReviewObjectContext reviewObjectContext) {
		Helper.getPersistService().update(reviewObjectContext);
	}

	/**
	 * ����վ��innerId��ѯReviewObjectContext
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
