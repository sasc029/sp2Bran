package com.bjsasc.plm.review2.committee.extend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.plm.core.query.condition.Condition;
import com.bjsasc.plm.foundation.Helper;
import com.bjsasc.plm.grid.GridHelper;
import com.bjsasc.plm.grid.data.GridDataService;
import com.bjsasc.plm.review2.committee.model.ReviewCommitteeMemberHS;
import com.bjsasc.plm.review2.committee.service.ReviewCommitteeMemberHelper;
import com.bjsasc.plm.review2.expert.model.DomainReviewExpert;
import com.bjsasc.plm.review2.expert.model.OuterReviewExpert;
import com.bjsasc.plm.review2.expert.model.ReviewExpert;
import com.bjsasc.plm.type.type.Type;

public class ReviewCommitteeMemberGridImpl  implements GridDataService{

	@Override
	public List<Map<String, Object>> getRows(String spot, String spotInstance,
			Map<Type, Condition> typeCondition, Map<String, Object> params) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		List<String> keys = GridHelper.getService().getMyLatestGridViewColumnIds(spot, spotInstance);
		String reviewOrderOID = (String)params.get("reviewOrderOID");
		List<ReviewCommitteeMemberHS> members = ReviewCommitteeMemberHelper.getMemberService().getMembersByReviewOrderId(Helper.getInnerId(reviewOrderOID));
		if(members!=null&&members.size()>0){
			for(ReviewCommitteeMemberHS member:members){
				Map<String, Object> resMap = new HashMap<String, Object>();
				resMap = Helper.getTypeManager().format(member,keys);
				String userLevel = member.getUserLevel();
				if(userLevel.equals(ReviewCommitteeMemberHS.USERLEVEL_COMMON)){
					resMap.put("RV_COMMITTEEMEMBER_USERLEVEL", "普通");
				}else if(userLevel.equals(ReviewCommitteeMemberHS.USERLEVEL_OFFICER)){
					resMap.put("RV_COMMITTEEMEMBER_USERLEVEL", "组长");
				}
				ReviewExpert expert = member.getExpert();
				if(expert instanceof OuterReviewExpert){
					resMap.put("RV_COMMITTEEMEMBER_USERNAME", ((OuterReviewExpert)member.getExpert()).getName());
					resMap.put("RV_COMMITTEEMEMBER_USERDEP", ((OuterReviewExpert)member.getExpert()).getWorkUnit());
					resMap.put("RV_COMMITTEEMEMBER_USERTYPE", "域外");
					resMap.put("RV_COMMITTEEMEMBER_TYPE", "outerUser");
				}else if(expert instanceof DomainReviewExpert){
					resMap.put("RV_COMMITTEEMEMBER_USERNAME", ((DomainReviewExpert)member.getExpert()).getUser().getName());
					resMap.put("RV_COMMITTEEMEMBER_USERDEP", ((DomainReviewExpert)member.getExpert()).getUser().getUserOrgname());
					resMap.put("RV_COMMITTEEMEMBER_USERTYPE", "域内");
					resMap.put("RV_COMMITTEEMEMBER_TYPE", "domainUser");
				}
				result.add(resMap);
			}
		}
		return result;
	}

}
