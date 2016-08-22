package com.bjsasc.plm.review2.role.extend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.plm.Helper;
import com.bjsasc.plm.core.query.condition.Condition;
import com.bjsasc.plm.grid.GridHelper;
import com.bjsasc.plm.grid.data.GridDataService;
import com.bjsasc.plm.review2.role.model.ReviewRole;
import com.bjsasc.plm.review2.role.service.ReviewRoleHelper;
import com.bjsasc.plm.type.type.Type;

public class ReviewRoleDataGridImpl implements GridDataService{

	@Override
	public List<Map<String, Object>> getRows(String spot, String spotInstance,
			Map<Type, Condition> typeCondition, Map<String, Object> params) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		List<String> keys = GridHelper.getService().getMyLatestGridViewColumnIds(spot, spotInstance);
		List<ReviewRole> roles = ReviewRoleHelper.getReviewRoleService().getAllRoleList();
		if(roles!=null&&roles.size()>0){
			for(ReviewRole role:roles){
				Map<String, Object> resMap = new HashMap<String, Object>();
				resMap = Helper.getTypeManager().format(role,keys);
				result.add(resMap);
			}
		}
		return result;
	}

}
