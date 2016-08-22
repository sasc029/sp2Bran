package com.bjsasc.plm.review2.dataconfig.service;

import java.util.List;

import com.bjsasc.plm.review2.dataconfig.model.ReviewDataConfig;


public interface ReviewDataConfigService {

	/**
	 * ��ѯ���е�����
	 * @return
	 */
	public List<ReviewDataConfig> findAllReviewDataConfig();
	/**
	 * ��ʼ������
	 * @param reviewDataConfig
	 */
	public List<ReviewDataConfig> getInitDataList();
	/**
	 * ��������
	 * @param reviewDataConfig
	 */
	public ReviewDataConfig createDataConfList(String dataType,String dataName,String dataSource);
	/**
	 * ��������
	 * @param reviewDataConfig
	 */
	public void saveReviewDataConfig(ReviewDataConfig reviewDataConfig);
	/**
	 * �޸�����
	 * @param reviewDataConfig
	 */
	public void updateReviewDataConfig(ReviewDataConfig reviewDataConfig);
	/**
	 * ɾ����¼
	 */
	public void delReviewDataConfig(String[] ids);
	
	/**
	 * 
	 * @return
	 */
	public List<ReviewDataConfig> findRvDataCfgByDataSource(String dataSource);
	
	public ReviewDataConfig findRvDataCfgByDataType(String dataType);
	
	public ReviewDataConfig findReviewDataConfigByInnerId(String innerId);
}
