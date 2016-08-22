package com.bjsasc.plm.review2.opinion.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.platform.webframework.util.DateUtil;
import com.bjsasc.plm.Helper;
import com.bjsasc.plm.core.persist.PersistHelper;
import com.bjsasc.plm.core.session.SessionHelper;
import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.review2.committee.model.ReviewCommitteeMemberHS;
import com.bjsasc.plm.review2.committee.service.ReviewCommitteeMemberHelper;
import com.bjsasc.plm.review2.common.BaseAction;
import com.bjsasc.plm.review2.constant.ReviewTaskConstant;
import com.bjsasc.plm.review2.expert.model.DomainReviewExpert;
import com.bjsasc.plm.review2.expert.model.OuterReviewExpert;
import com.bjsasc.plm.review2.expert.model.ReviewExpert;
import com.bjsasc.plm.review2.expert.service.ReviewExpertHelper;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.object.service.ReviewObjectHelper;
import com.bjsasc.plm.review2.opinion.model.ReviewOpinion;
import com.bjsasc.plm.review2.opinion.service.ReviewOpinionHelper;
import com.bjsasc.plm.review2.opinion.service.ReviewOpinionService;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.plm.review2.result.model.ReviewResult;
import com.bjsasc.plm.review2.result.service.ReviewResultHelper;
import com.bjsasc.plm.review2.task.model.ReviewTask;
import com.bjsasc.plm.review2.task.service.ReviewTaskHelper;
import com.bjsasc.plm.review2.util.ReviewObjectUtil;
import com.bjsasc.plm.review2.util.ReviewTaskUtil;
import com.bjsasc.ui.json.DataUtil;
import com.cascc.platform.aa.AAProvider;
import com.cascc.platform.aa.api.UserService;
import com.cascc.platform.aa.api.data.AAUserData;

public class ReviewOpinionAction extends BaseAction {

	private static final long serialVersionUID = -490798782789829633L;
	ReviewOpinionService service = ReviewOpinionHelper.getService();
	private User user = SessionHelper.getService().getUser();
	private ReviewOpinion reviewOpinion;
	private List<ReviewOpinion> reviewOpinionList;
	
	public String beforeEditAnswer(){
		String innerId = request.getParameter("innerId");
		ReviewOpinion rvOpn = service.getOpinionsById(innerId);
		request.setAttribute("rvOpn", rvOpn);
		return "editAnswer";
	}
	public void editAnswer(){
		String innerId = request.getParameter("innerId");
		String answerContent = request.getParameter("Txt");
		
		ReviewOpinion rvOpn = service.getOpinionsById(innerId);
		rvOpn.setAnswerContent(answerContent);
		PersistHelper.getService().update(rvOpn);
	}
	public String beforeEditIdea(){
		String innerId = request.getParameter("innerId");
		ReviewOpinion rvOpn = service.getOpinionsById(innerId);
		request.setAttribute("rvOpn", rvOpn);
		return "editIdea";
	}
	public void editIdea(){
		String innerId = request.getParameter("innerId");
		String opnContent = request.getParameter("Txt");
		
		ReviewOpinion rvOpn = service.getOpinionsById(innerId);
		rvOpn.setOpinionContent(opnContent);
		PersistHelper.getService().update(rvOpn);
	}
    public void isCanEditAnswer() throws Exception{
    	String orderId = request.getParameter("orderId");
    	String innerId = request.getParameter("innerId");
    	ReviewOpinion opn = service.getOpinionsById(innerId);
    	if(ReviewOpinion.OPNTYPE_DOMAIN.equals(opn.getOpinionType())){
    		ReviewResult rvResult = ReviewResultHelper.getService().getResultsByRvIdAndExecutor(orderId, opn.getExecutor().getInnerId()).get(0);
    		if(!"0".equals(rvResult.getIsExpertConfirm())){
    			response.getWriter().print("fail");
    		}else{
    			response.getWriter().print("success");
    		}
    	}else{
    		List<ReviewResult> rvResults = ReviewResultHelper.getService().getResultsByRvIdAndExecutor(orderId, opn.getExecutor().getInnerId());
    		for(ReviewResult rvResult:rvResults){
    			if(rvResult.getOuterExpert().getInnerId().equals(opn.getOuterExpert().getInnerId())){
    				if(!"0".equals(rvResult.getIsExpertConfirm())){
    	    			response.getWriter().print("fail");
    	    		}else{
    	    			response.getWriter().print("success");
    	    		}
    				break;
    			}
    		}
    	}
    }
	public void getOpinions() throws Exception{
		String reviewOrderId  = Helper.getPersistService().getInnerId(request.getParameter("orderOid"));
		List<ReviewOpinion> list = service.getOpinionsByReview(reviewOrderId);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for(int i=0;i<list.size();i++){
			ReviewOpinion reviewOpinion=(ReviewOpinion)list.get(i);
			Map<String, Object> listMap = new HashMap<String, Object>();
			String executorName = "";
			if(reviewOpinion.getOpinionType().endsWith(ReviewOpinion.OPNTYPE_DOMAIN)){
				executorName = reviewOpinion.getExecutor().getName();
			}else{
				executorName = reviewOpinion.getOuterExpert().getName();
			}
			listMap.put("OID", reviewOpinion.getOid());
		    listMap.put("INNERID", reviewOpinion.getInnerId());
		    listMap.put("CLASSID", reviewOpinion.getClassId());
		    listMap.put("CREATOR", executorName);
		    listMap.put("CREATORID", reviewOpinion.getManageInfo().getCreateBy().getInnerId());
		    listMap.put("CONTENTS", reviewOpinion.getOpinionContent());
		    listMap.put("ANSWERCONTENTS", reviewOpinion.getAnswerContent()==null?"":reviewOpinion.getAnswerContent());
		    listMap.put("OPNSTATE", reviewOpinion.getOpinionState());
		    listMap.put("CREATE_TIME", DateUtil.toDateTime(reviewOpinion.getManageInfo().getCreateTime()));
		    listMap.put("SHOWANNEX", "<img src='/avidm/plm/images/baselib/property.gif' alt='附件浏览'/>");
		    dataList.add(listMap);
			
		}
		String json= DataUtil.listToJson(dataList.size(), dataList);
		response.getWriter().print(json);
	}
	
	public String getOpnStateStr(int state){
		if(state==ReviewOpinion.OPNSTATE_NEW){
			return "已开启";
		}else if(state==ReviewOpinion.OPNSTATE_SUBMIT){
			return "审阅已完成";
		}else if(state==ReviewOpinion.OPNSTATE_ANSWER){
			return "设计师解答已完成";
		}else if(state==ReviewOpinion.OPNSTATE_CONFIRM){
			return "专家确认已完成";
		}else if(state==ReviewOpinion.OPNSTATE_LEADER_CONFIRM){
			return "组长确认已完成";
		}else{
			return "组长确认已完成 ";
		}
	}
	public void checkUser() throws Exception{
		String reviewoid = request.getParameter("reviewoid");
		ReviewOrder reviewOrder = (ReviewOrder)PersistHelper.getService().getObject(reviewoid);
        //判断是否设计师登录
        String createUserIId = ReviewObjectUtil.getDesignerAAUserInnerId(reviewOrder);
		if(createUserIId.equals(user.getAaUserInnerId())){
			response.getWriter().print("isTrue");
		}else{
			response.getWriter().print("isFalse");
		}
	}
	public void createOpinion() throws Exception{
		String reviewoid = request.getParameter("reviewoid");
		String objId = request.getParameter("objoid");
		String type = request.getParameter("type");
		String opinionType = request.getParameter("opinionType");
		String outerUserId = request.getParameter("outerUserId");
		//上传附件
		String classid = null;
		String innerid = null;
		String create = null;
		String expertInnerId = "";
		
		ReviewOrder review = (ReviewOrder)PersistHelper.getService().getObject(reviewoid);
		ReviewObject obj = ReviewObjectHelper.getService().findReviewObjectById(objId);
		//创建意见
		if (type != null) {
			reviewOpinion.setOpinionType(opinionType);
			reviewOpinion.setReviewOrder(review);
			reviewOpinion.setReviewObject(obj);
			reviewOpinion.setOpinionState(String.valueOf(ReviewOpinion.OPNSTATE_NEW));
			UserService userService = AAProvider.getUserService();
			AAUserData aaUser = userService.getUser(null, user.getAaUserInnerId());
			reviewOpinion.setExecutor(aaUser);
			
			if(ReviewOpinion.OPNTYPE_OUTER.equals(opinionType)){
				OuterReviewExpert out = ReviewExpertHelper.getService().findOuterReviewExpertById(outerUserId);
				reviewOpinion.setOuterExpert(out);
				expertInnerId = outerUserId;
			}else{
				List<ReviewCommitteeMemberHS> listRcm = ReviewCommitteeMemberHelper.getMemberService().getMembersByReviewOrderId(review.getInnerId());
				for(ReviewCommitteeMemberHS rcm : listRcm){
					ReviewExpert re=rcm.getExpert(); 
					if(re instanceof DomainReviewExpert){
						DomainReviewExpert domainExpert = (DomainReviewExpert)re;
						if(domainExpert.getUser().getInnerId().equals(user.getAaUserInnerId())){
							expertInnerId = domainExpert.getInnerId();
							break;
						}
					}
				}		
			}
			ReviewCommitteeMemberHS member = ReviewCommitteeMemberHelper.getMemberService().getCommitteeMember(review.getInnerId(), expertInnerId);
			reviewOpinion.setOrderFlag(String.valueOf(member.getUserorder()));
			
			ReviewOpinion o = ReviewOpinionHelper.getService().createOpinion(reviewOpinion);
			//标识
			create = "true";
			//附件上传需要的参数
			classid = ReviewOpinion.CLASSID;
			innerid = o.getInnerId();
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("SUCCESS", create);
		result.put("CLASSID", classid);
		result.put("INNERID", innerid);
		String json = DataUtil.mapToSimpleJson(result);
		response.getWriter().print(json);
	}
	
	public void delReviewOpinion() throws Exception{
		boolean flag=true;
		String id = request.getParameter("ids");
		String[] ids = id.split(",");
		User user=SessionHelper.getService().getUser();
		for(int i=0;i<ids.length;i++){
			ReviewOpinion ro=service.getOpinionsById(ids[i]);
			if(ro!=null&&(!user.getInnerId().equals(ro.getManageInfo().getCreateBy().getInnerId())||ro.getOpinionStateInt()>ReviewOpinion.OPNSTATE_NEW)){
				flag = false;
			}
		}
		if(flag){
			service.delReviewOpinions(ids);
			response.getWriter().print("success");
		}
	}
	public void updateContents() throws Exception{
		String innerId = request.getParameter("innerId");
		String contents = request.getParameter("contents");
		String flag = request.getParameter("flag");
		
		ReviewOpinion ro = service.getOpinionsById(innerId);
		if("contents".equals(flag)){
			if(user.getInnerId().equals(ro.getManageInfo().getCreateBy().getInnerId())){
				PersistHelper.getService().update(ro);
				response.getWriter().print("success");
			}else{
				response.getWriter().print("false");
			}
		}else{
			String designerId = request.getParameter("designerId");
			if(user.getAaUserInnerId().equals(designerId)){
				PersistHelper.getService().update(ro);
				response.getWriter().print("success");
			}else{
				response.getWriter().print("false");
			}
		}
		
	}
	public void submitReviewOpinion() throws Exception{
		String flag = request.getParameter("flag");
		String innerid = request.getParameter("innerid");
		String Txt=request.getParameter("Txt");
		reviewOpinion=service.getOpinionsById(innerid);
		if("save".equals(flag)){
			PersistHelper.getService().update(reviewOpinion);
		}else{
			//reviewOpinion.setOpinionState(String.valueOf(ReviewOpinion.OPNSTATE_SUBMIT));
			//PersistHelper.getService().update(reviewOpinion);
			PersistHelper.getService().update(reviewOpinion);
			request.setAttribute("opnOid",PersistHelper.getService().getOid(ReviewOpinion.class.getSimpleName(), innerid));
			//ReviewTaskUtil.submitCheckTask(reviewOpinion.getInnerId());
		}
	}
	
	public void answerReviewOpinion() throws Exception{
		String innerid = request.getParameter("innerid");
		String Txt=request.getParameter("Txt");
		String[] innerids = innerid.split(",");
		for(int i=0;i<innerids.length;i++){
			reviewOpinion=service.getOpinionsById(innerids[i]);
			reviewOpinion.setOpinionState(String.valueOf(ReviewOpinion.OPNSTATE_ANSWER));
			reviewOpinion.setAnswerTime(System.currentTimeMillis());
			UserService userService = AAProvider.getUserService();
			AAUserData aaUser = userService.getUser(null, user.getAaUserInnerId());
			reviewOpinion.setAnswerUser(aaUser);
			reviewOpinion.setAnswerContent(Txt);
			PersistHelper.getService().update(reviewOpinion);
		}
	}
	
	
	public void conFirmReviewOpinion() throws Exception{
		String innerid = request.getParameter("innerid");
		String Txt=request.getParameter("Txt");
		reviewOpinion=service.getOpinionsById(innerid);
		//reviewOpinion.setOpinionState(String.valueOf(ReviewOpinion.OPNSTATE_CONFIRM));
		PersistHelper.getService().update(reviewOpinion);
		//ReviewTaskUtil.conFirmTask(reviewOpinion.getInnerId());
	}
	
	public void closeReviewOpinion() throws Exception{
		reviewOpinionList = new ArrayList<ReviewOpinion>();
		String innerid = request.getParameter("innerid");
		String Txt=request.getParameter("Txt");
		String[] innerids = innerid.split(",");
		for(int i=0;i<innerids.length;i++){
			reviewOpinion=service.getOpinionsById(innerids[i]);
			reviewOpinion.setOpinionState(String.valueOf(ReviewOpinion.OPNSTATE_LEADER_CONFIRM));
			PersistHelper.getService().update(reviewOpinion);
			reviewOpinionList.add(reviewOpinion);
		}
		ReviewTaskUtil.leaderConfirmTask(reviewOpinionList);
	}
	public void checkReviewOpinion() throws Exception{
		boolean canAnswer=true;
		boolean canConfirm=true;
		String innerid = request.getParameter("innerid");
		String[] innerids = innerid.split(",");
		for(int i=0;i<innerids.length;i++){
			reviewOpinion=service.getOpinionsById(innerids[i]);
			if(reviewOpinion.getOpinionStateInt()!=ReviewOpinion.OPNSTATE_SUBMIT ){
				canAnswer=false;
			}
			if(reviewOpinion.getOpinionStateInt()!=ReviewOpinion.OPNSTATE_CONFIRM && reviewOpinion.getOpinionStateInt()!=ReviewOpinion.AGREEOVER){
				canConfirm=false;
			}
		}
		if(canAnswer){
			response.getWriter().print("true");
		}
		if(canConfirm){
			response.getWriter().print("success");
		}
	}
	//提交任务
	public void submitRvOpinionTask() throws Exception{
		String taskId = request.getParameter("taskId");
		String reviewOid = request.getParameter("reviewoid");
		String opnState = "";
		String userId = "";
		ReviewTask rt = ReviewTaskHelper.getService().getRvTasksById(taskId);
		ReviewOrder review = (ReviewOrder)PersistHelper.getService().getObject(reviewOid);
		if(ReviewTaskConstant.TASK_TYPE_CHECK.equals("")){
			opnState = String.valueOf(ReviewOpinion.OPNSTATE_NEW);
			userId = user.getInnerId();
		}else if(ReviewTaskConstant.TASK_TYPE_ANSWER.equals("")){
			opnState = String.valueOf(ReviewOpinion.OPNSTATE_ANSWER);
			userId = rt.getManageInfo().getCreateBy().getInnerId();
		}
		List<ReviewOpinion> opns = service.getOpinions(review.getInnerId(), opnState, userId);
		if(!opns.isEmpty()){
			if(ReviewTaskConstant.TASK_TYPE_CHECK.equals("")){
				ReviewTaskUtil.submitCheckTask(opns);
			}else if(ReviewTaskConstant.TASK_TYPE_ANSWER.equals("")){
			    List<ReviewOpinion> allOpn = service.getOpinions(taskId);
			    if(allOpn.size()>opns.size()){
			    	response.getWriter().print("noAnswer");
					return;
			    }else{
			    	ReviewTaskUtil.submitAnswerTask(opns);
			    }
			}
		}else{
			if(ReviewTaskConstant.TASK_TYPE_CHECK.equals("")){
				response.getWriter().print("noOpn");
				return;
			}else if(ReviewTaskConstant.TASK_TYPE_ANSWER.equals("")){
				response.getWriter().print("noAnswer");
				return;
			}
		}
	}
	//意见确认
	/*public void confirmOpn() throws Exception{
		String taskId = request.getParameter("taskId");
		String reviewOid = request.getParameter("reviewoid");
		String opnState = "";
		ReviewTask rt = ReviewTaskHelper.getService().getRvTasksById(taskId);
		ReviewOrder review = (ReviewOrder)PersistHelper.getService().getObject(reviewOid);
		if(ReviewTaskConstant.TASK_TYPE_CHECK.equals("")){
			opnState = String.valueOf(ReviewOpinion.OPNSTATE_NEW);
		}else if(ReviewTaskConstant.TASK_TYPE_EXPERT_CONFIRM.equals(rt.getTaskType())){
			ReviewTaskUtil.conFirmTask(rt);
			return;
		}
		List<ReviewOpinion> opns = service.getOpinions(review.getInnerId(), opnState, user.getInnerId());
		if(!opns.isEmpty()){
			if(ReviewTaskConstant.TASK_TYPE_CHECK.equals(rt.getTaskType())){
				ReviewTaskUtil.submitCheckTask(opns);
			}
		}else{
			response.getWriter().print("noOpn");
			return;
		}
	}*/
	//给组长发送确认任务
	public void confirmOpnTask() throws Exception{
		String taskId = request.getParameter("taskId");
		String reviewOid = request.getParameter("reviewoid");
		ReviewTaskUtil.noOpnConFirmTask(taskId, Helper.getInnerId(reviewOid));
	}
	//组长确认意见
	/*public void leaderConfirmOpn() throws Exception{
		String taskId = request.getParameter("taskId");
		String reviewOid = request.getParameter("reviewoid");
		String opnState = "";
		String userId = "";
		ReviewTask rt = ReviewTaskHelper.getService().getRvTasksById(taskId);
		ReviewOrder review = (ReviewOrder)PersistHelper.getService().getObject(reviewOid);
		if(ReviewTaskConstant.TASK_TYPE_LEADER_CONFIRM.equals(rt.getTaskType())){
			opnState = String.valueOf(ReviewOpinion.OPNSTATE_CONFIRM);
			userId = rt.getManageInfo().getCreateBy().getInnerId();
		}
		List<ReviewOpinion> opns = service.getOpinions(review.getInnerId(), opnState, userId);
		if(!opns.isEmpty()){
			ReviewTaskUtil.leaderConFirmTask(taskId, Helper.getInnerId(reviewOid));
		}else{
			ReviewTaskUtil.leaderConFirmTask(taskId, Helper.getInnerId(reviewOid));
		}
	}*/
	public List<ReviewOpinion> getReviewOpinionList() {
		return reviewOpinionList;
	}

	public void setReviewOpinionList(List<ReviewOpinion> reviewOpinionList) {
		this.reviewOpinionList = reviewOpinionList;
	}

	public ReviewOpinion getReviewOpinion() {
		return reviewOpinion;
	}

	public void setReviewOpinion(ReviewOpinion reviewOpinion) {
		this.reviewOpinion = reviewOpinion;
	}
}