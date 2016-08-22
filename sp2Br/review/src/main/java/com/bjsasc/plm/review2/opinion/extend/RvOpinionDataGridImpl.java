package com.bjsasc.plm.review2.opinion.extend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.plm.Helper;
import com.bjsasc.plm.core.query.condition.Condition;
import com.bjsasc.plm.grid.GridHelper;
import com.bjsasc.plm.grid.data.GridDataService;
import com.bjsasc.plm.review2.opinion.model.ReviewOpinion;
import com.bjsasc.plm.review2.opinion.service.ReviewOpinionHelper;
import com.bjsasc.plm.type.type.Type;

public class RvOpinionDataGridImpl implements GridDataService{

	public List<Map<String, Object>> getRows(String spot, String spotInstance, 
			Map<Type, Condition> typeCondition, Map<String, Object> map) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<String> keys = GridHelper.getService().getMyLatestGridViewColumnIds(spot, spotInstance);
		String orderId = (String)map.get("orderId");
		List<ReviewOpinion> result = ReviewOpinionHelper.getService().getOpinionsByReview(orderId);
		for (ReviewOpinion rvOpn : result) {
			Map<String, Object> rvOpinionMap = new HashMap<String, Object>();
			rvOpinionMap = Helper.getTypeManager().format(rvOpn, keys);
			list.add(rvOpinionMap);
		}
		return list;
	}
	
}
