package com.bjsasc.plm.review2.order.extend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.plm.Helper;
import com.bjsasc.plm.core.query.condition.Condition;
import com.bjsasc.plm.core.session.SessionHelper;
import com.bjsasc.plm.grid.GridHelper;
import com.bjsasc.plm.grid.data.GridDataService;
import com.bjsasc.plm.review2.committee.model.ReviewCommitteeMemberHS;
import com.bjsasc.plm.review2.committee.service.ReviewCommitteeMemberHelper;
import com.bjsasc.plm.review2.constant.ReviewOrderConstant;
import com.bjsasc.plm.review2.expert.model.DomainReviewExpert;
import com.bjsasc.plm.review2.link.service.ReviewMemberHelper;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.plm.review2.util.ExpertUtil;
import com.bjsasc.plm.type.type.Type;

public class ReviewOrderGridImpl implements GridDataService{

	public List<Map<String, Object>> getRows(String spot, String spotInstance, 
			Map<Type, Condition> typeCondition, Map<String, Object> map) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<String> keys = GridHelper.getService().getMyLatestGridViewColumnIds(spot, spotInstance);
		String userId=SessionHelper.getService().getUser().getAaUserInnerId();
		boolean isPrincipal = ExpertUtil.isUserForPSFZR(userId); //是否是评审负责人
		String sourceSiteInnerId = (String)map.get("sourceSiteInnerId");
		String hql = "from ReviewOrder order by manageInfo.createTime desc";
		List<ReviewOrder> rvOrders = Helper.getPersistService().find(hql);
		List<ReviewOrder> result = this.checkOrder(rvOrders, sourceSiteInnerId);
		for (ReviewOrder rvObjTempdata : result) {
			Map<String, Object> ObjTempdataMap = new HashMap<String, Object>();
			ObjTempdataMap = Helper.getTypeManager().format(rvObjTempdata, keys);
			if(isPrincipal){
				ObjTempdataMap.put("ACCESS", true);
			}else{
				ReviewCommitteeMemberHS rcm = ReviewCommitteeMemberHelper.getMemberService().getLeader(rvObjTempdata.getInnerId());
				if(rcm!=null){
					if(((DomainReviewExpert)rcm.getExpert()).getUser().getInnerId().equals(userId)){
						ObjTempdataMap.put("ACCESS", true);
					}else{
						ObjTempdataMap.put("ACCESS", false);
					}
				}
				
			}
			String mRState = (String)ObjTempdataMap.get("RVORDER_STATE");
			ObjTempdataMap.put("RVORDER_STATE", convertBusState(mRState));
			ObjTempdataMap.put("RVORDER_TYPE", convertRvType(rvObjTempdata.getReviewType()));
			ObjTempdataMap.put("RVORDER_NAME", getRvOrderLink(rvObjTempdata.getInnerId(), rvObjTempdata.getName(),rvObjTempdata.getReviewType()));
			list.add(ObjTempdataMap);
		}
		return list;
	}
	
	 private String convertBusState(String str){
		 if(ReviewOrderConstant.TASK_MR_NEW.equalsIgnoreCase(str)){
			 return "新建";
		 }else if(ReviewOrderConstant.TASK_MR_START.equalsIgnoreCase(str)){
			 return "已启动";
		 }else if(ReviewOrderConstant.TASK_MR_REVIEW_OVER.equalsIgnoreCase(str)){
			 return "已结束";
		 }else{
			 return "审阅中";
		 }
	 }
	 
	 private String convertRvType(String str){
		 if(ReviewOrderConstant.REVIEW_TYPE_ELECTRON.equalsIgnoreCase(str)){
			 return "电子评审";
		 }else if(ReviewOrderConstant.REVIEW_TYPE_MEETING.equalsIgnoreCase(str)){
			 return "会议评审";
		 }else{
			 return "";
		 }
	 }
	 
	 private String getRvOrderLink(String orderId, String orderName,String flag){
			StringBuffer str = new StringBuffer();
			str.append("<a href='javascript:void(0)' title='").append(orderName).append("' onclick='viewData(\"").append(orderId).append("\",\"").append(flag).append("\")'>").append(orderName).append("</a>");
			return str.toString();
		}
	private List<ReviewOrder> checkOrder(List<ReviewOrder> rvOrders,String sourceSiteInnerId){
		List<ReviewOrder>  list = new ArrayList<ReviewOrder>();
		for(ReviewOrder rvOrder:rvOrders){
			ReviewObject rvObj = (ReviewObject)ReviewMemberHelper.getService().getReviewedListByOrder(rvOrder).get(0);
			if(sourceSiteInnerId.equals(rvObj.getSourceSiteInnerId())){
				list.add(rvOrder);
			}
		}
		return list;
	}
}
