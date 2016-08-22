package com.bjsasc.plm.review2.div.service;

import java.util.List;

import com.bjsasc.plm.review2.div.model.ReviewOutDivision;


public interface ReviewOutDivisionService {
	/**
	 * ��ȡ���к����ⲿ��λ�б�
	 * @return
	 * @author 
	 */
	public List<ReviewOutDivision> getAllRvOutDivisionList();
	
	/**
	 * ��ȡ�����ⲿ��λ�б�ͨ��siteInnerId
	 * @return
	 * @author 
	 */
	public List<ReviewOutDivision> getRvOutDivisionListBySiteInnerId(String siteInnerId);
	
	/**
	 * ����InnerId��ѯ����
	 * @return
	 * @author 
	 */
	public ReviewOutDivision getRvOutDivisionByInnerId(String innerId);
	
	/**
	 * ����outDivInnerId��siteInnerId��ѯ����
	 * @return
	 * @author 
	 */
	public ReviewOutDivision getRvOutDivByDivInnerIdAndSiteInnerId(String outDivInnerId,String siteInnerId);
	
}
