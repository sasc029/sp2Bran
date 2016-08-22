package com.bjsasc.plm.review2.expert.service;

import java.util.List;

import com.bjsasc.plm.review2.expert.model.DomainReviewExpert;
import com.bjsasc.plm.review2.expert.model.OuterReviewExpert;

public interface ReviewExpertService {

	/**
	 * ��ѯ����ר�Ҷ���
	 * @param
	 *     iid ����ר�Ҷ���id
	 */
	public OuterReviewExpert findOuterReviewExpertById(String innerId);
	
	/**
	 * �������ר�Ҷ���
	 * @param
	 *     outerReviewExpert ����ר�Ҷ���
	 */
	public void saveOuterReviewExpert(OuterReviewExpert outerReviewExpert);
	
	
	/**
	 * ɾ������ר�Ҷ���
	 * @param
	 *     outerReviewExpert ����ר�Ҷ���
	 */
	public void delOuterReviewExpert(String[] ids);
	
	
	/**
	 * �޸�����ר�Ҷ���
	 * @param
	 *     outerReviewExpert ����ר�Ҷ���
	 */
	public void updateOuterReviewExpert(OuterReviewExpert outerReviewExpert);
	
	/**
	 * �������ר�Ҷ���
	 * @param
	 *     domainReviewExpert ����ר�Ҷ���
	 */
	public void saveDomainReviewExpert(String[] userID,String siteInnerId,String nodeInnerId);
	
	/**
	 * ɾ������ר�Ҷ���
	 * @param
	 *     outerReviewExpert ����ר�Ҷ���
	 */
	public void delDomainReviewExpert(String[] ids);
	
	/**
	 * ��ѯ������������
	 */
	public List<DomainReviewExpert> findAllDomainReviewExpert();
	
	/**
	 * ��ѯ������������
	 */
	public List<OuterReviewExpert> findAllOuterReviewExpert();
}
