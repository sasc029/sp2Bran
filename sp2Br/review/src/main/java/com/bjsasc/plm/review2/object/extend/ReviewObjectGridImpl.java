package com.bjsasc.plm.review2.object.extend;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.plm.Helper;
import com.bjsasc.plm.core.query.condition.Condition;
import com.bjsasc.plm.core.session.SessionHelper;
import com.bjsasc.plm.grid.GridHelper;
import com.bjsasc.plm.grid.data.GridDataService;
import com.bjsasc.plm.review2.constant.ReviewObjectConstant;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.util.ExpertUtil;
import com.bjsasc.plm.type.type.Type;

public class ReviewObjectGridImpl implements GridDataService{

	public List<Map<String, Object>> getRows(String spot, String spotInstance, 
			Map<Type, Condition> typeCondition, Map<String, Object> map) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<String> keys = GridHelper.getService().getMyLatestGridViewColumnIds(spot, spotInstance);
		String userId=SessionHelper.getService().getUser().getAaUserInnerId();
		boolean isPrincipal = ExpertUtil.isUserForPSFZR(userId); //是否是评审负责人
		
		//参数
		String reviewObjState = (String)map.get("reviewObjState");
		String sourceSiteInnerId = (String)map.get("sourceSiteInnerId");
		
		String hql = "from ReviewObject obj where 1=1 and sourceSiteInnerId=? ";
		
		if(reviewObjState.equals("newtask")){
			hql+=" and obj.reviewState ='newtask' ";
		}else if(reviewObjState.equals("dealingtask")){
			hql+=" and obj.reviewState ='dealingtask' ";
		}else if(reviewObjState.equals("finishtask")){
			hql+=" and obj.reviewState ='finishtask' ";
		}
		hql+=" order by createTime desc";
		List<ReviewObject> result = Helper.getPersistService().find(hql,sourceSiteInnerId);
		for (ReviewObject rvObjTempdata : result) {
			Map<String, Object> ObjTempdataMap = new HashMap<String, Object>();
			String dataUrl ="";
			try {
				 dataUrl = URLEncoder.encode(rvObjTempdata.getDataUrl(), "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
			ObjTempdataMap = Helper.getTypeManager().format(rvObjTempdata, keys);
			ObjTempdataMap.put("PIN_RVOBJ", "<a href='javascript:void(0);' title='"+rvObjTempdata.getPin()+"' onclick='showReviewObjInfo(\""+dataUrl+"\")'>"+rvObjTempdata.getPin()+"</a>");
			if(isPrincipal){
				ObjTempdataMap.put("ACCESS", true);
			}else{
				ObjTempdataMap.put("ACCESS", false);
			}
			ObjTempdataMap.put("REVIEWSTATE_RVOBJ", ReviewObjectConstant.RVOBJ_STATE_NEW.equals(rvObjTempdata.getReviewState())?"新任务":(ReviewObjectConstant.RVOBJ_STATE_DEALING.equals(rvObjTempdata.getReviewState())?"进行中":"已完成"));
			ObjTempdataMap.put("REVIEWFLAG_RVOBJ", rvObjTempdata.getReviewFlag());
			
			list.add(ObjTempdataMap);
		}
		return list;
	}
	
}
