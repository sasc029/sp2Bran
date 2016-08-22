package com.bjsasc.plm.review2.distribute.extend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.plm.Helper;
import com.bjsasc.plm.core.query.condition.Condition;
import com.bjsasc.plm.grid.GridHelper;
import com.bjsasc.plm.grid.data.GridDataService;
import com.bjsasc.plm.review2.distribute.model.ReviewDistribute;
import com.bjsasc.plm.review2.distribute.service.ReviewDistributeHelper;
import com.bjsasc.plm.type.type.Type;

public class RvDistributeDataGridImpl implements GridDataService{

	public List<Map<String, Object>> getRows(String spot, String spotInstance, 
			Map<Type, Condition> typeCondition, Map<String, Object> map) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<String> keys = GridHelper.getService().getMyLatestGridViewColumnIds(spot, spotInstance);
		String orderId = (String)map.get("orderId");
		List<ReviewDistribute> result = ReviewDistributeHelper.getReviewDistributeService().getRvDistributeListByOrderId(orderId);
		for (ReviewDistribute rvDistribute : result) {
			Map<String, Object> rvDistributeMap = new HashMap<String, Object>();
			rvDistributeMap = Helper.getTypeManager().format(rvDistribute, keys);
			rvDistributeMap.put("RV_DIS_TYPE", converType(rvDistribute.getType()));
			list.add(rvDistributeMap);
		}
		return list;
	}
	
	public String converType(String type){
		if("domainUser".equals(type)){
			return "内部人员";
		}else if("outUnit".equals(type)){
			return "外部单位";
		}else{
			return "内部单位";
		}
	}
}
