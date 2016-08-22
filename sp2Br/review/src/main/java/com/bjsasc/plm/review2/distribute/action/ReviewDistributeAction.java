package com.bjsasc.plm.review2.distribute.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.plm.KeyS;
import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.review2.common.BaseAction;
import com.bjsasc.plm.review2.constant.ReviewObjectConstant;
import com.bjsasc.plm.review2.constant.ReviewOrderConstant;
import com.bjsasc.plm.review2.distribute.model.ReviewDistribute;
import com.bjsasc.plm.review2.distribute.service.ReviewDistributeHelper;
import com.bjsasc.plm.review2.distribute.service.ReviewDistributeService;
import com.bjsasc.plm.review2.link.service.ReviewMemberHelper;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.object.service.ReviewObjectHelper;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.plm.review2.order.service.ReviewOrderHelper;
import com.bjsasc.plm.review2.order.service.ReviewOrderService;
import com.bjsasc.plm.util.Ajax;
import com.bjsasc.plm.util.JsonUtil;
import com.bjsasc.ui.json.DataUtil;

/**
 * 会议评审分发信息action
 * @author 
 *
 */
public class ReviewDistributeAction  extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3780034094192760501L;
	private ReviewDistributeService service = ReviewDistributeHelper.getReviewDistributeService();
	private ReviewOrderService orderService = ReviewOrderHelper.getService();
	private ReviewDistribute rvDistribute;
	
	public void startMeetingReview() throws IOException{
		String orderOid = request.getParameter("oid");
		ReviewOrder reviewOrder = (ReviewOrder)Helper.getPersistService().getObject(orderOid);
		if(checkNum(reviewOrder.getInnerId())){
			//发送分发信息
			orderService.sendReviewDistribute(reviewOrder);
			//修改评审对象状态
			ReviewObject rvObj = (ReviewObject)ReviewMemberHelper.getService().getReviewedListByOrder(reviewOrder).get(0);
			rvObj.setReviewState(ReviewObjectConstant.RVOBJ_STATE_DEALING);
			ReviewObjectHelper.getService().updateReviewObject(rvObj);
			//修改评审单状态
			reviewOrder.setState(ReviewOrderConstant.TASK_MR_START);
			reviewOrder.setStartTime(new Date().getTime());
			orderService.updateManagedReview(reviewOrder);
			response.getWriter().write("success");
		}else{
			response.getWriter().write("fail");
		}
	}
	
	public void closeMeetingReview() throws IOException{
		String orderOid = request.getParameter("oid");
		ReviewOrder reviewOrder = (ReviewOrder)Helper.getPersistService().getObject(orderOid);
		//发送会议评审证明书
		orderService.sendMeetingReview(reviewOrder);
		//修改评审对象状态
		ReviewObject rvObj = (ReviewObject)ReviewMemberHelper.getService().getReviewedListByOrder(reviewOrder).get(0);
		rvObj.setReviewState(ReviewObjectConstant.RVOBJ_STATE_FINISH);
		ReviewObjectHelper.getService().updateReviewObject(rvObj);
		//修改评审单状态
		reviewOrder.setState(ReviewOrderConstant.TASK_MR_REVIEW_OVER);
		reviewOrder.setFinishTime(new Date().getTime());
		orderService.updateManagedReview(reviewOrder);
		response.getWriter().write("success");
	}
	public boolean checkNum(String orderId){
		List<ReviewDistribute> distributes = service.getRvDistributeListByOrderId(orderId);
		int count=0;
		if(distributes.isEmpty()){
			return false;
		}else{
			for(ReviewDistribute distribute:distributes){
				count+=distribute.getCountNum();
			}
			if(count>6){
				return true;
			}else{
				return false;
			}
		}
	}
	/**
	 * 批量删除
	 */
	public void deleteData(){
		try{
			String ids = request.getParameter("IIds");
			service.delReviewDistribute(ids);
			response.getWriter().print("success");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void checkUnit()throws Exception{
		String orderOid = request.getParameter("orderOID");
		String expertJson = request.getParameter("expertJson");
		Map<String, Object> result = new HashMap<String, Object>();
		
		ReviewOrder review = (ReviewOrder)Helper.getPersistService().getObject(orderOid);
		List<Map<String, String>> mapList = DataUtil.JsonToList(expertJson);
		boolean flag = true;
		if(mapList!=null&&mapList.size()>0){
			for(Map<String, String> map:mapList){
				String targetInnerId = map.get("innerId");
				if(review!=null){
					List<ReviewDistribute> divs = service.getRvDistributeListByOrderId(review.getInnerId());
					if(divs!=null&&divs.size()>0){
						for(ReviewDistribute div:divs){
							if(targetInnerId.equals(div.getUserInnerId())||targetInnerId.equals(div.getUnitInnerId())){
								flag = false;
								break;
							}
						}
					}
				}
			}
		}
		result.put(Ajax.SUCCESS, "true");
		if(!flag){
			result.put("msg", "false");
		}else{
			result.put("msg", "true");
		}
		response.getWriter().print(DataUtil.mapToSimpleJson(result));
	}
	
	public void check()throws Exception{
		String orderOid = request.getParameter("orderOID");
		String expertJson = request.getParameter(KeyS.DATA);
		
		ReviewOrder review = (ReviewOrder)Helper.getPersistService().getObject(orderOid);
		List<Map<String, Object>> mapList = JsonUtil.toList(expertJson);
		boolean flag = true;
		if(mapList!=null&&mapList.size()>0){
			for(Map<String, Object> map:mapList){
				String targetInnerId = map.get("EXPERT_INNERID").toString();
				if(review!=null){
					List<ReviewDistribute> divs = service.getRvDistributeListByOrderId(review.getInnerId());
					if(divs!=null&&divs.size()>0){
						for(ReviewDistribute div:divs){
							if(targetInnerId.equals(div.getUserInnerId())||targetInnerId.equals(div.getUnitInnerId())){
								flag = false;
								break;
							}
						}
					}
				}
			}
		}
		if(!flag){
			response.getWriter().print("{failure:true}");
    	}else{ 
    		response.getWriter().print("{success:true}");  
    	}
	}
	
	public void saveUser() throws Exception{
		String expertJson = request.getParameter(KeyS.DATA);
		String orderOid = request.getParameter("orderOID");
		
		ReviewOrder review = (ReviewOrder)Helper.getPersistService().getObject(orderOid);
		List<Map<String, Object>> mapList = JsonUtil.toList(expertJson);
		
		if(mapList!=null&&mapList.size()>0){
			for(Map<String, Object> map:mapList){
				String userInnerId = map.get("EXPERT_INNERID").toString();
				String userName = map.get("NAME").toString();
				rvDistribute =new ReviewDistribute();
				rvDistribute.setUserInnerId(userInnerId);
				rvDistribute.setUserName(userName);
				rvDistribute.setCountNum(1);
				rvDistribute.setType("domainUser");
				rvDistribute.setReviewOrder(review);
				
				Helper.getPersistService().save(rvDistribute);
			}
		}
	}
	
	public void saveUnit() throws Exception{
		String expertJson = request.getParameter("expertJson");
		String orderOid = request.getParameter("orderOID");
        Map<String, Object> result = new HashMap<String, Object>();
		
		ReviewOrder review = (ReviewOrder)Helper.getPersistService().getObject(orderOid);
		List<Map<String, String>> mapList = DataUtil.JsonToList(expertJson);
		
		if(mapList!=null&&mapList.size()>0){
			for(Map<String, String> map:mapList){
				String unitInnerId = map.get("innerId");
				String unitName = map.get("unit_name");
				String type = map.get("unit_type");
				rvDistribute =new ReviewDistribute();
				rvDistribute.setUnitInnerId(unitInnerId);
				rvDistribute.setUnitName(unitName);
				rvDistribute.setCountNum(1);
				rvDistribute.setType(type);
				rvDistribute.setReviewOrder(review);
				
				Helper.getPersistService().save(rvDistribute);
			}
		}
		result.put(Ajax.SUCCESS, "true");
		response.getWriter().print(DataUtil.mapToSimpleJson(result));
	}
	
	public void editData() throws IOException{
		String countNum = request.getParameter("value");
		String innerId = request.getParameter("columnInnerId");
		ReviewDistribute distribute = service.getRvDistributeByInnerId(innerId);
		distribute.setCountNum(Integer.parseInt(countNum));
		Helper.getPersistService().update(distribute);
		response.getWriter().write("success");
	}
}
