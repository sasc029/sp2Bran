package com.bjsasc.plm.review2.expert.extend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.plm.Helper;
import com.bjsasc.plm.core.query.condition.Condition;
import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.grid.GridHelper;
import com.bjsasc.plm.grid.data.GridDataService;
import com.bjsasc.plm.review2.expert.model.OuterReviewExpert;
import com.bjsasc.plm.type.type.Type;

public class OuterReviewExpertGridImpl implements GridDataService{

	public List<Map<String, Object>> getRows(String spot, String spotInstance, 
			Map<Type, Condition> typeCondition, Map<String, Object> map) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<String> keys = GridHelper.getService().getMyLatestGridViewColumnIds(spot, spotInstance);
		User user = Helper.getSessionService().getUser();
		String hql = "from OuterReviewExpert where manageInfo.createByRef.innerId =?";
		List<OuterReviewExpert> result = Helper.getPersistService().find(hql,user.getInnerId());
		for (OuterReviewExpert outerReviewExpert : result) {
			Map<String, Object> outerReviewExpertMap = new HashMap<String, Object>();
			outerReviewExpertMap = Helper.getTypeManager().format(outerReviewExpert, keys);
			if(outerReviewExpert.getSex().equals("1")){
				outerReviewExpertMap.put("SEX_OUTUSER", "ÄÐ");
			}else{
				outerReviewExpertMap.put("SEX_OUTUSER", "Å®");
			}
			list.add(outerReviewExpertMap);
		}
		return list;
	}
}
