package com.bjsasc.plm.review2.dataconfig.service;

import java.util.List;

import com.bjsasc.plm.review2.dataconfig.model.ReviewDataConfig;


public interface ReviewDataConfigService {

	/**
	 * 查询所有的数据
	 * @return
	 */
	public List<ReviewDataConfig> findAllReviewDataConfig();
	/**
	 * 初始化数据
	 * @param reviewDataConfig
	 */
	public List<ReviewDataConfig> getInitDataList();
	/**
	 * 创建数据
	 * @param reviewDataConfig
	 */
	public ReviewDataConfig createDataConfList(String dataType,String dataName,String dataSource);
	/**
	 * 新增数据
	 * @param reviewDataConfig
	 */
	public void saveReviewDataConfig(ReviewDataConfig reviewDataConfig);
	/**
	 * 修改数据
	 * @param reviewDataConfig
	 */
	public void updateReviewDataConfig(ReviewDataConfig reviewDataConfig);
	/**
	 * 删除记录
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
