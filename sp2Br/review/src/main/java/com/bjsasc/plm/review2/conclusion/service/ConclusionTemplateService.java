package com.bjsasc.plm.review2.conclusion.service;

import java.util.List;

import com.bjsasc.plm.review2.conclusion.model.ConclusionTemplateHS;


public interface ConclusionTemplateService {

	/**
	 * ��ѯ���е�����
	 * @return
	 */
	public List<ConclusionTemplateHS> findAllConclusionTemplate();
	/**
	 * ��������
	 * @param conclusionTemplate
	 */
	public void saveConclusionTemplate(ConclusionTemplateHS conclusionTemplate);
	/**
	 * �޸�����
	 * @param conclusionTemplate
	 */
	public void updateConclusionTemplate(ConclusionTemplateHS conclusionTemplate);
	/**
	 * ����ConclusionTemplate��innerId��ѯ��������
	 * @param innerId
	 * @return
	 */
	public ConclusionTemplateHS findConclusionTemplateByInnerId(String innerId);
	/**
	 * ɾ����¼
	 */
	public void delConclusionTemplate(String[] ids);
}
