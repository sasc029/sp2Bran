package com.bjsasc.plm.review2.conclusion.service;

import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.review2.conclusion.model.ConclusionTemplateHS;

public class ConclusionTemplateServiceImpl implements ConclusionTemplateService {
	/**
	 * ��ѯ��������
	 * @param conclusionTemplate
	 */
	@Override
	public List<ConclusionTemplateHS> findAllConclusionTemplate() {
		String hql = "from ConclusionTemplateHS";
		return (List<ConclusionTemplateHS>)Helper.getPersistService().find(hql);
	}
	
	/**
	 * ��ѯ��������
	 * @param innerId
	 * @return
	 */
	@Override
	public ConclusionTemplateHS findConclusionTemplateByInnerId(String innerId) {
		List<ConclusionTemplateHS> conclusionTemplate=Helper.getPersistService().find(" from ConclusionTemplateHS WHERE innerId=?", innerId);
		if(!conclusionTemplate.isEmpty()){
			return conclusionTemplate.get(0);
		}
		return null;
	}

	@Override
	public void delConclusionTemplate(String[] ids) {
		for(int i=0; i<ids.length; i++){
			ConclusionTemplateHS conclusionTemplate = findConclusionTemplateByInnerId(ids[i]);
			deleteConclusionTemplate(conclusionTemplate);
		}
	}
	
	/**
	 * ɾ����¼
	 * @param conclusionTemplate
	 */
	public void deleteConclusionTemplate(ConclusionTemplateHS conclusionTemplate) {
		Helper.getPersistService().delete(conclusionTemplate);
	}

	/**
	 * ��������
	 * @param conclusionTemplate
	 */
	@Override
	public void saveConclusionTemplate(ConclusionTemplateHS conclusionTemplate) {
		Helper.getPersistService().save(conclusionTemplate);
	}

	/**
	 * �޸�����
	 * @param reviewObjectContext
	 */
	@Override
	public void updateConclusionTemplate(ConclusionTemplateHS conclusionTemplate) {
		Helper.getPersistService().update(conclusionTemplate);
	}
}
