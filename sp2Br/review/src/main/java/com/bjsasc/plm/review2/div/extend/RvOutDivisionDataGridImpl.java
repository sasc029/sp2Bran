package com.bjsasc.plm.review2.div.extend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.avidm.core.site.SiteHelper;
import com.bjsasc.plm.Helper;
import com.bjsasc.plm.core.query.condition.Condition;
import com.bjsasc.plm.grid.GridHelper;
import com.bjsasc.plm.grid.data.GridDataService;
import com.bjsasc.plm.review2.div.model.ReviewOutDivision;
import com.bjsasc.plm.type.type.Type;

public class RvOutDivisionDataGridImpl implements GridDataService{

	public List<Map<String, Object>> getRows(String spot, String spotInstance, 
			Map<Type, Condition> typeCondition, Map<String, Object> map) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<String> keys = GridHelper.getService().getMyLatestGridViewColumnIds(spot, spotInstance);
		String hql = "from ReviewOutDivision";
		List<ReviewOutDivision> result = Helper.getPersistService().find(hql);
		for (ReviewOutDivision rvDistribute : result) {
			Map<String, Object> rvOutDivMap = new HashMap<String, Object>();
			rvOutDivMap = Helper.getTypeManager().format(rvDistribute, keys);
			rvOutDivMap.put("RV_OUTDIV_SITENAME", getSiteName(rvDistribute.getSiteInnerId()));
			list.add(rvOutDivMap);
		}
		return list;
	}
	public static String getSiteName(String siteInnerId){
		Site site = SiteHelper.getSiteService().findSiteById(siteInnerId);
		if(site!=null){
			return site.getSiteData().getSiteName();
		}else{
			return "";
		}
	}
}
