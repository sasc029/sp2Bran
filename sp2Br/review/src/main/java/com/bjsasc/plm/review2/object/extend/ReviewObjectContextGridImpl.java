package com.bjsasc.plm.review2.object.extend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.plm.Helper;
import com.bjsasc.plm.core.query.condition.Condition;
import com.bjsasc.plm.grid.GridHelper;
import com.bjsasc.plm.grid.data.GridDataService;
import com.bjsasc.plm.review2.object.model.ReviewObjectContext;
import com.bjsasc.plm.type.type.Type;

public class ReviewObjectContextGridImpl implements GridDataService{

	public List<Map<String, Object>> getRows(String spot, String spotInstance, 
			Map<Type, Condition> typeCondition, Map<String, Object> map) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<String> keys = GridHelper.getService().getMyLatestGridViewColumnIds(spot, spotInstance);
		String hql = "from ReviewObjectContext";
		List<ReviewObjectContext> result = Helper.getPersistService().find(hql);
		for (ReviewObjectContext rvBusTempdata : result) {
			Map<String, Object> busTempdataMap = new HashMap<String, Object>();
			busTempdataMap = Helper.getTypeManager().format(rvBusTempdata, keys);
			String busType = (String)busTempdataMap.get("BUSTYPE");
			busTempdataMap.put("BUSTYPE", convertBusType(busType));
			list.add(busTempdataMap);
		}
		return list;
	}
	
 private String convertBusType(String str){
	 if("OUTSIGN".equalsIgnoreCase(str)){
		 return "跨域会签";
	 }else if("DISTRIBUTE".equalsIgnoreCase(str)){
		 return "跨域分发";
	 }else if("GATHER".equalsIgnoreCase(str)){
		 return "数据汇总";
	 }
	 return "";
 }
}
