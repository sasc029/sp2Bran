package com.bjsasc.plm.review2.div.service;

import java.util.List;

import com.bjsasc.plm.review2.div.model.ReviewOutDivision;


public interface ReviewOutDivisionService {
	/**
	 * 获取所有函审外部单位列表
	 * @return
	 * @author 
	 */
	public List<ReviewOutDivision> getAllRvOutDivisionList();
	
	/**
	 * 获取函审外部单位列表通过siteInnerId
	 * @return
	 * @author 
	 */
	public List<ReviewOutDivision> getRvOutDivisionListBySiteInnerId(String siteInnerId);
	
	/**
	 * 根据InnerId查询对象
	 * @return
	 * @author 
	 */
	public ReviewOutDivision getRvOutDivisionByInnerId(String innerId);
	
	/**
	 * 根据outDivInnerId和siteInnerId查询对象
	 * @return
	 * @author 
	 */
	public ReviewOutDivision getRvOutDivByDivInnerIdAndSiteInnerId(String outDivInnerId,String siteInnerId);
	
}
