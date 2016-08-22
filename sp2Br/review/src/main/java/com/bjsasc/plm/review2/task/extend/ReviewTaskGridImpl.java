package com.bjsasc.plm.review2.task.extend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.plm.Helper;
import com.bjsasc.plm.core.query.condition.Condition;
import com.bjsasc.plm.core.session.SessionHelper;
import com.bjsasc.plm.grid.GridHelper;
import com.bjsasc.plm.grid.data.GridDataService;
import com.bjsasc.plm.review2.task.model.ReviewTask;
import com.bjsasc.plm.type.type.Type;

public class ReviewTaskGridImpl implements GridDataService{

	public List<Map<String, Object>> getRows(String spot, String spotInstance, 
			Map<Type, Condition> typeCondition, Map<String, Object> map) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<String> keys = GridHelper.getService().getMyLatestGridViewColumnIds(spot, spotInstance);
		String userId=SessionHelper.getService().getUser().getInnerId();
		
		//参数
		String reviewTaskState = (String)map.get("reviewTaskState");
		
		String hql = "from ReviewTask rt where rt.executorRef.innerId='"+userId+"'";
		
		if(reviewTaskState.equals(ReviewTask.RVTASK_STATE_DEAL)){
			hql+=" and rt.taskState='"+ReviewTask.RVTASK_STATE_DEAL+"' ";
		}else if(reviewTaskState.equals(ReviewTask.RVTASK_STATE_END)){
			hql+=" and rt.taskState='"+ReviewTask.RVTASK_STATE_END+"' ";
		}
		hql+=" order by rt.manageInfo.createTime desc";
		
		List<ReviewTask> result = Helper.getPersistService().find(hql);
		for (ReviewTask rvTaskTempdata : result) {
			Map<String, Object> taskTempdataMap = new HashMap<String, Object>();
			taskTempdataMap = Helper.getTypeManager().format(rvTaskTempdata, keys);
			taskTempdataMap.put("RVTASK_RVORDER", "<a href='javascript:void(0);' title='"+rvTaskTempdata.getReviewOrder().getName()+"' onclick='showReviewInfo(\""+rvTaskTempdata.getInnerId()+"\",\""+rvTaskTempdata.getReviewOrder().getInnerId()+"\")'>"+rvTaskTempdata.getReviewOrder().getName()+"</a>");
			taskTempdataMap.put("RVTASK_STATE", ReviewTask.RVTASK_STATE_DEAL.equals(rvTaskTempdata.getTaskState())?"待处理":"已完成");
			list.add(taskTempdataMap);
		}
		return list;
	}
	
}
