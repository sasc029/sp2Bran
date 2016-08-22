package com.bjsasc.plm.review2.expert.service;

import java.util.List;

import com.bjsasc.plm.review2.expert.model.DomainReviewExpert;
import com.bjsasc.plm.review2.expert.model.OuterReviewExpert;

public interface ReviewExpertService {

	/**
	 * 查询域外专家对象
	 * @param
	 *     iid 域外专家对象id
	 */
	public OuterReviewExpert findOuterReviewExpertById(String innerId);
	
	/**
	 * 添加域外专家对象
	 * @param
	 *     outerReviewExpert 域外专家对象
	 */
	public void saveOuterReviewExpert(OuterReviewExpert outerReviewExpert);
	
	
	/**
	 * 删除域外专家对象
	 * @param
	 *     outerReviewExpert 域外专家对象
	 */
	public void delOuterReviewExpert(String[] ids);
	
	
	/**
	 * 修改域外专家对象
	 * @param
	 *     outerReviewExpert 域外专家对象
	 */
	public void updateOuterReviewExpert(OuterReviewExpert outerReviewExpert);
	
	/**
	 * 添加域内专家对象
	 * @param
	 *     domainReviewExpert 域内专家对象
	 */
	public void saveDomainReviewExpert(String[] userID,String siteInnerId,String nodeInnerId);
	
	/**
	 * 删除域内专家对象
	 * @param
	 *     outerReviewExpert 域外专家对象
	 */
	public void delDomainReviewExpert(String[] ids);
	
	/**
	 * 查询域外所有数据
	 */
	public List<DomainReviewExpert> findAllDomainReviewExpert();
	
	/**
	 * 查询域内所有数据
	 */
	public List<OuterReviewExpert> findAllOuterReviewExpert();
}
