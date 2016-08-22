package com.bjsasc.plm.review2.conclusion.service;

import java.util.List;

import com.bjsasc.plm.review2.conclusion.model.ConclusionTemplateHS;


public interface ConclusionTemplateService {

	/**
	 * 查询所有的数据
	 * @return
	 */
	public List<ConclusionTemplateHS> findAllConclusionTemplate();
	/**
	 * 新增数据
	 * @param conclusionTemplate
	 */
	public void saveConclusionTemplate(ConclusionTemplateHS conclusionTemplate);
	/**
	 * 修改数据
	 * @param conclusionTemplate
	 */
	public void updateConclusionTemplate(ConclusionTemplateHS conclusionTemplate);
	/**
	 * 根据ConclusionTemplate的innerId查询单条数据
	 * @param innerId
	 * @return
	 */
	public ConclusionTemplateHS findConclusionTemplateByInnerId(String innerId);
	/**
	 * 删除记录
	 */
	public void delConclusionTemplate(String[] ids);
}
