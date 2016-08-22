package com.bjsasc.plm.review2.soap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bjsasc.plm.collaboration.a4x.model.DataMap;
import com.bjsasc.plm.collaboration.util.ObjectToMapUtil;
import com.bjsasc.plm.review2.constant.ReviewObjectConstant;
import com.bjsasc.plm.review2.distribute.model.ReviewDistribute;
import com.bjsasc.plm.review2.div.model.ReviewOutDivision;
import com.bjsasc.plm.review2.object.model.ReviewObject;


public class ReviewDataConvertUtil {
	
	private static Logger logger = Logger.getLogger(ReviewDataConvertUtil.class);
	
	
	public static final String REVIEW_REQUEST_MESSAGE = "com.bjsasc.avidm.review4a5.ReviewRequestMessage";
	
	public static ReviewObject parseDataMapToA5ReviewObject(DataMap dataMap) {
		ReviewObject reviewObj = new ReviewObject();
		reviewObj.setDocId(dataMap.get("docId"));
		reviewObj.setPin(dataMap.get("pin"));
		reviewObj.setDataUrl(dataMap.get("dataUrl"));
		reviewObj.setDocName(dataMap.get("docName"));
		reviewObj.setVersion(dataMap.get("versionNo"));
		reviewObj.setState(dataMap.get("state"));
		reviewObj.setScurityLevel(dataMap.get("scurityLevel"));
		reviewObj.setPhaseName(dataMap.get("phaseName"));
		reviewObj.setPhaseId(dataMap.get("phaseId"));
		reviewObj.setProfessionID(dataMap.get("professionID"));
		reviewObj.setProfessionName(dataMap.get("professionName"));
		reviewObj.setCreateTime(Long.parseLong(dataMap.get("createTime")));
		reviewObj.setCreateUserIId(dataMap.get("createUserIId"));
		reviewObj.setCreateUserName(dataMap.get("createUserName"));
		reviewObj.setProductIID(dataMap.get("productIID"));
		reviewObj.setProductId(dataMap.get("productId"));
		reviewObj.setProductName(dataMap.get("productName"));
		reviewObj.setSourceSiteInnerId(dataMap.get("sourceSiteInnerId"));
		reviewObj.setReviewState(ReviewObjectConstant.RVOBJ_STATE_NEW);
		reviewObj.setReviewFlag(ReviewObjectConstant.RVOBJ_FLAG_ELECTRON);
		//上下文信息待补充
		return reviewObj;
	}
	
	
	public static Map parseReviewRequestDataListToMap(List dataList){
		 Map map = new HashMap();
		 ReviewObject reviewObj = null;
		 for(int i =0;i<dataList.size();i++){
			 DataMap dm =(DataMap)dataList.get(i);
			 if(dm.getClassName().equals(REVIEW_REQUEST_MESSAGE)){
				 reviewObj = parseDataMapToA5ReviewObject(dm);
			 }
		 }
		 map.put("reviewObj", reviewObj);
		 return map;
	 }
	
	public static Map convertToDataMap(Map map, String transferType){
		Map reviewDataMap = null;
		try {
			reviewDataMap = ObjectToMapUtil.mapToObject(map);
		} catch (Exception e) {
			logger.error("ReviewDataConvertUtil中调用convertToDataMap转换评审对象失败，类型为："+transferType, e);
			throw new RuntimeException("ReviewDataConvertUtil中调用convertToDataMap转换评审对象失败，类型为："+transferType, e);
		}
		return reviewDataMap;
	}
	
	
	public static List replyReviewToDataMapList(String docIID, String productIID) {
		List dataMapList = new ArrayList();
		// 定义一个DataMap 对象
		DataMap dm = new DataMap();
		dm.setClassName(Map.class.getName());
	    dm.put("docIID", docIID);
	    dm.put("productIID", productIID);
		dataMapList.add(dm);
		return dataMapList;
	}
	
	public static List reviewRoleToDataMapList(Map dataMap) {
		List dataMapList = new ArrayList();
		// 定义一个DataMap 对象
		DataMap dm = new DataMap();
		dm.setClassName(Map.class.getName());
	    dm.put("userIID", (String)dataMap.get("userIID"));
	    dm.put("roleType", (String)dataMap.get("roleType"));
	    dm.put("operate", (String)dataMap.get("operate"));
		dataMapList.add(dm);
		return dataMapList;
	}
	
	
	public static ReviewOutDivision parseDataMapToA5OutDiv(DataMap dataMap, String siteInnerId) {
		ReviewOutDivision div = new ReviewOutDivision();
		div.setInnerId(dataMap.get("IID"));
		div.setOutDivInnerId(dataMap.get("IID"));
		div.setOutDivId(dataMap.get("ID"));
		div.setOutDivNam(dataMap.get("NAME"));
		div.setParentDiv(dataMap.get("PARENTDIV"));
		div.setSiteInnerId(siteInnerId);
		return div;
	}
	
	
	public static List reviewDistributeToDataMapList(Map dataMap) {
		List dataMapList = new ArrayList();
		// 定义一个DataMap 对象
		DataMap dm = new DataMap();
		dm.setClassName(Map.class.getName());
	    dm.put("docIID", (String)dataMap.get("docIID"));
	    dm.put("productIID", (String)dataMap.get("productIID"));
	    dm.put("checkdate", (String)dataMap.get("checkdate"));
	    dm.put("checkplace", (String)dataMap.get("checkplace"));
	    
		dataMapList.add(dm);
		List<ReviewDistribute> distributeList = (List)dataMap.get("ReviewDistribute");
		for(int i=0;i<distributeList.size();i++){
			 dataMapList.add(parseDistributeDataToDataMap(distributeList.get(i)));
		}
		return dataMapList;
	}
	
	
	private static DataMap parseDistributeDataToDataMap(ReviewDistribute rd) {
		DataMap dm = new DataMap();
		dm.setClassName(rd.getClass().getName());
		dm.put("userInnerId", rd.getUserInnerId());
		dm.put("userName", rd.getUserName());
		dm.put("unitInnerId", rd.getUnitInnerId());
		dm.put("unitName", rd.getUnitName());
		dm.put("type", rd.getType());
		dm.put("countNum", String.valueOf(rd.getCountNum()));
		return dm;
	}

}
