package com.bjsasc.plm.review2.dataconfig.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.util.XmlFileUtil;
import com.bjsasc.plm.review2.dataconfig.model.ReviewDataConfig;

public class ReviewDataConfigServiceImpl implements ReviewDataConfigService {
	/**
	 * 查询所有数据
	 * @param reviewConclusion
	 */
	@Override
	public List<ReviewDataConfig> findAllReviewDataConfig() {
		String hql = "from ReviewDataConfig";
		return (List<ReviewDataConfig>)Helper.getPersistService().find(hql);
	}
	
	/**
	 * 查询单条数据
	 * @param innerId
	 * @return
	 */
	public ReviewDataConfig findReviewDataConfigByInnerId(String innerId) {
		List<ReviewDataConfig> reviewDataConfig=Helper.getPersistService().find(" from ReviewDataConfig WHERE innerId=?", innerId);
		if(!reviewDataConfig.isEmpty()){
			return reviewDataConfig.get(0);
		}
		return null;
	}

	@Override
	public void delReviewDataConfig(String[] ids) {
		for(int i=0; i<ids.length; i++){
			ReviewDataConfig reviewDataConfig = findReviewDataConfigByInnerId(ids[i]);
			deleteReviewConclusion(reviewDataConfig);
		}
	}
	
	/**
	 * 删除记录
	 * @param reviewConclusion
	 */
	public void deleteReviewConclusion(ReviewDataConfig reviewDataConfig) {
		Helper.getPersistService().delete(reviewDataConfig);
	}

	/**
	 * 新增数据
	 * @param reviewDataConfig
	 */
	@Override
	public void saveReviewDataConfig(ReviewDataConfig reviewDataConfig) {
		Helper.getPersistService().save(reviewDataConfig);
	}

	/**
	 * 修改数据
	 * @param reviewDataConfig
	 */
	@Override
	public void updateReviewDataConfig(ReviewDataConfig reviewDataConfig) {
		Helper.getPersistService().update(reviewDataConfig);
	}

	@Override
	public List<ReviewDataConfig> findRvDataCfgByDataSource(String dataSource) {
		List<ReviewDataConfig> reviewDataConfig=Helper.getPersistService().find(" from ReviewDataConfig WHERE dataSource=?", dataSource);
		return reviewDataConfig;
	}

	@Override
	public ReviewDataConfig findRvDataCfgByDataType(String dataType) {
		String sql = "from ReviewDataConfig WHERE dataType=?";
		List<ReviewDataConfig> reviewDataConfig=Helper.getPersistService().find(sql, dataType);
		if(!reviewDataConfig.isEmpty()){
			return reviewDataConfig.get(0);
		}
		return null;
	}

	@Override
	public List<ReviewDataConfig> getInitDataList() {
		Document doc = XmlFileUtil.loadByFilePath(System.getProperty("AVIDM_HOME") + File.separator+"plm"+File.separator+"review"+File.separator+"config"+File.separator+"reviewDataConfigInit.xml");
		Element rootElement = doc.getRootElement();
		List<Element> list = rootElement.elements();
		List<ReviewDataConfig> dataConfigLists = new ArrayList<ReviewDataConfig>();
		if(list!=null){
			for(Element e:list){
				String dataType="";
				String dataName = "";
				String dataSource = "";
				dataType=e.attributeValue("id");
				ReviewDataConfig dataTypeExsit = getReviewDataConfigById(dataType);
				if(dataTypeExsit==null){
					List<Element> childrenList = e.elements();
					for(Element ec:childrenList){
						if(ec.attributeValue("name").equals("dataName")){
							dataName = ec.attributeValue("value");
						}
						if(ec.attributeValue("name").equals("dataSource")){
							dataSource = ec.attributeValue("value");
						}
					}
					dataConfigLists.add(ReviewDataConfigHelper.getService().createDataConfList(dataType, dataName, dataSource));
				}
			}
		}
		return dataConfigLists;
	}
	
	//根据dataType判断是否有重复记录
	public ReviewDataConfig getReviewDataConfigById(String dataType) {
		String hql = "from ReviewDataConfig where dataType=?";
		List<ReviewDataConfig> lists = (List<ReviewDataConfig>)Helper.getPersistService().find(hql,dataType);
		if(lists!=null&&lists.size()>0){
			return lists.get(0);
		}else{
			return null;
		}
	}

	@Override
	//创建对象
	public ReviewDataConfig createDataConfList(String dataType,String dataName, String dataSource) {
		ReviewDataConfig reviewDataConfig = new ReviewDataConfig();
		reviewDataConfig.setDataType(dataType);
		reviewDataConfig.setDataName(dataName);
		reviewDataConfig.setDataSource(dataSource);
		return reviewDataConfig;
	}

}
