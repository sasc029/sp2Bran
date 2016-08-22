package com.bjsasc.plm.review2.conclusion.action;

import java.util.Date;
import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.review2.Reviewed;
import com.bjsasc.plm.core.session.SessionHelper;
import com.bjsasc.plm.core.util.DateTimeUtil;
import com.bjsasc.plm.review2.common.BaseAction;
import com.bjsasc.plm.review2.conclusion.model.ReviewConclusionHS;
import com.bjsasc.plm.review2.conclusion.service.ReviewConclusionHelper;
import com.bjsasc.plm.review2.conclusion.service.ReviewConclusionService;
import com.bjsasc.plm.review2.constant.ReviewConclusionConstant;
import com.bjsasc.plm.review2.constant.ReviewObjectConstant;
import com.bjsasc.plm.review2.item.model.ReviewItem;
import com.bjsasc.plm.review2.item.service.ReviewItemHelper;
import com.bjsasc.plm.review2.link.service.ReviewMemberHelper;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.cascc.platform.aa.AAProvider;
import com.cascc.platform.aa.api.UserService;
import com.cascc.platform.uuidservice.UUID;

/**
 * 评审结论action
 *
 */
@SuppressWarnings("serial")
public class ReviewConclusionAction  extends BaseAction{

	//系统运行参数配置 service
	private ReviewConclusionService service = ReviewConclusionHelper.getService();
	
	private UserService userService = AAProvider.getUserService();
	
	private ReviewConclusionHS reviewConclusion;

	public ReviewConclusionHS getReviewConclusion() {
		return reviewConclusion;
	}

	public void setReviewConclusion(ReviewConclusionHS reviewConclusion) {
		this.reviewConclusion = reviewConclusion;
	}
	
	public String showReviewConclusion() throws Exception{
		String oid = request.getParameter("OID");
	    String reviewOid = oid;
			
		ReviewOrder reviewOrder = (ReviewOrder)Helper.getPersistService().getObject(reviewOid);
		List<ReviewConclusionHS> rcList = service.findReviewConclusionByReviewOrder(reviewOrder.getInnerId());
		if(rcList.size()>0){
			request.setAttribute("operate","update");
			reviewConclusion = rcList.get(0);
			request.setAttribute("reviewResult", reviewConclusion.getReviewResult());
			request.setAttribute("state", reviewConclusion.getState());
		}else{
			request.setAttribute("operate","insert");
		}
		request.setAttribute("OID", reviewOid);
		return "showReviewConclusion";
	}
	
	public void addReviewConclusion() throws Exception{
		boolean flag =false;
		String reviewOid = request.getParameter("OID");
		String type= request.getParameter("type");//保存还是提交
		String operate= request.getParameter("operate");
		String isPrincipal = request.getParameter("isPrincipal");//负责人为true
		ReviewOrder reviewOrder = (ReviewOrder)Helper.getPersistService().getObject(reviewOid);
		
		List<Reviewed> rvObjs=ReviewMemberHelper.getService().getReviewedListByOrder(reviewOrder);
        ReviewObject obj = (ReviewObject)rvObjs.get(0);
		if(type.equals("save")){
			reviewConclusion.setState(ReviewConclusionConstant.RC_STATE_SAVE);
		}else if(type.equals("submit")){
			if(isPrincipal.equals("true")){
				reviewConclusion.setState(ReviewConclusionConstant.RC_STATE_PRINCIPAL_SUBMIT);
			}else{
				reviewConclusion.setState(ReviewConclusionConstant.RC_STATE_LEADER_SUBMIT);
			}
		}
		reviewConclusion.setReviewOrder(reviewOrder);
		if(operate.equals("update")){
			service.updateReviewConclusion(reviewConclusion);
			if("submit".equals(type)){
				if("true".equals(isPrincipal)){
					if("2".equals(reviewConclusion.getReviewResult())){//会议评审
						this.createMeetingRvObj(reviewOrder);
						flag = true;
					}else if("1".equals(reviewConclusion.getReviewResult())){//评审通过出正式文件
						ReviewItem item = ReviewItemHelper.getService().newReviewItem();
						Date deadLine = new Date();
						deadLine.setDate(deadLine.getDate()+7);
						item.setItemResult("1");
						item.setFormatter("file");
						item.setItemName("评审通过");
						item.setProductIID(obj.getProductIID());
						item.setProductName(obj.getProductName());
						item.setPhaseId(obj.getPhaseId());
						item.setPhaseName(obj.getPhaseName());
						item.setItemSource("review");
						item.setItemType("department");
						item.setProfessionID(obj.getProfessionID());
						item.setProfessionName(obj.getProfessionName());
						item.setUser(userService.getUser(null,obj.getCreateUserIId()));
						item.setStartTime(DateTimeUtil.getLongTime(new Date(), "yyyy-MM-dd"));
						item.setDeadLine(DateTimeUtil.getLongTime(deadLine, "yyyy-MM-dd"));
						item.setSourceSiteInnerId(obj.getSourceSiteInnerId());
						item.setReviewOrder(reviewOrder);
						item.setItemContents("通过评审，修改后直接出正式文件");
						Helper.getPersistService().save(item);
					}else if("3".equals(reviewConclusion.getReviewResult())){//评审不通过
						ReviewItem item = ReviewItemHelper.getService().newReviewItem();
						Date deadLine = new Date();
						deadLine.setDate(deadLine.getDate()+7);
						item.setItemResult("3");
						item.setFormatter("file");
						item.setItemName("未通过评审");
						item.setReviewOrder(reviewOrder);
						item.setProductIID(obj.getProductIID());
						item.setProductName(obj.getProductName());
						item.setPhaseId(obj.getPhaseId());
						item.setPhaseName(obj.getPhaseName());
						item.setProfessionID(obj.getProfessionID());
						item.setProfessionName(obj.getProfessionName());
						item.setItemSource("review");
						item.setItemType("department");
						item.setUser(userService.getUser(null,obj.getCreateUserIId()));
						item.setSourceSiteInnerId(obj.getSourceSiteInnerId());
						item.setStartTime(DateTimeUtil.getLongTime(new Date(), "yyyy-MM-dd"));
						item.setDeadLine(DateTimeUtil.getLongTime(deadLine, "yyyy-MM-dd"));
						item.setItemContents("未通过评审，需重新修改文件");
						Helper.getPersistService().save(item);
					}
				}
			}
		}else{
			service.saveReviewConclusion(reviewConclusion);
		}
		if(flag){
			request.setAttribute("OID", reviewOid);
			response.getWriter().print("meeting");
		}else{
			request.setAttribute("OID", reviewOid);
			response.getWriter().print("success");
		}
	}
	public void createMeetingRvObj(ReviewOrder rvOrder){
		ReviewObject rvObj = (ReviewObject)ReviewMemberHelper.getService().getReviewedListByOrder(rvOrder).get(0);
		ReviewObject meetingObj = new ReviewObject();
		meetingObj.setReviewFlag(ReviewObjectConstant.RVOBJ_FLAG_METTING);
		meetingObj.setReviewState(ReviewObjectConstant.RVOBJ_STATE_NEW);
		meetingObj.setDocId(rvObj.getDocId());
		meetingObj.setDocName(rvObj.getDocName());
		meetingObj.setPin(rvObj.getPin()+"(会议评审)");
		meetingObj.setDataUrl(rvObj.getDataUrl());
		meetingObj.setVersion(rvObj.getVersion());
		meetingObj.setState(rvObj.getState());
		meetingObj.setScurityLevel(rvObj.getScurityLevel());
		meetingObj.setPhaseName(rvObj.getPhaseName());
		meetingObj.setCreateTime(new Date().getTime());
		meetingObj.setCreateUserIId(rvObj.getCreateUserIId());
		meetingObj.setCreateUserName(rvObj.getCreateUserName());
		meetingObj.setProductIID(rvObj.getProductIID());
		meetingObj.setProductId(rvObj.getProductId());
		meetingObj.setProductName(rvObj.getProductName());
		meetingObj.setSourceSiteInnerId(rvObj.getSourceSiteInnerId());
		meetingObj.setPhaseId(rvObj.getPhaseId());
		meetingObj.setPhaseName(rvObj.getPhaseName());
		meetingObj.setProfessionID(rvObj.getProfessionID());
		meetingObj.setProfessionName(rvObj.getProfessionName());
		meetingObj.setContextInfo(rvObj.getContextInfo());
		meetingObj.setFolderInfo(rvObj.getFolderInfo());
		meetingObj.setDomainInfo(rvObj.getDomainInfo());
		meetingObj.setInnerId(UUID.getUID());
		
		boolean isCheck = SessionHelper.getService().isCheckPermission();
		//关闭权限检查
		SessionHelper.getService().setCheckPermission(false) ;
		Helper.getPersistService().save(meetingObj);
		SessionHelper.getService().setCheckPermission(isCheck);
	}
}