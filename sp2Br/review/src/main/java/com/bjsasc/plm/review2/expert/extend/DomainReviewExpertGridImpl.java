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
import com.bjsasc.plm.review2.expert.model.DomainReviewExpert;
import com.bjsasc.plm.type.type.Type;

public class DomainReviewExpertGridImpl implements GridDataService{

	public List<Map<String, Object>> getRows(String spot, String spotInstance, 
			Map<Type, Condition> typeCondition, Map<String, Object> map) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<String> keys = GridHelper.getService().getMyLatestGridViewColumnIds(spot, spotInstance);
		String sourceSiteInnerId = (String)map.get("sourceSiteInnerId");
		User user = Helper.getSessionService().getUser();
		String hql = "from DomainReviewExpert where siteRef.innerId=? and manageInfo.createByRef.innerId =?";
		List<DomainReviewExpert> result = Helper.getPersistService().find(hql,sourceSiteInnerId,user.getInnerId());
		for (DomainReviewExpert domainReviewExpert : result) {
			Map<String, Object> domainReviewExpertMap = new HashMap<String, Object>();
			domainReviewExpertMap = Helper.getTypeManager().format(domainReviewExpert, keys);
			domainReviewExpertMap.put("INNERID", domainReviewExpert.getInnerId());
			list.add(domainReviewExpertMap);
		}
		return list;
	}
}
