package com.bjsasc.plm.review2.order.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.avidm.core.site.SiteHelper;
import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.attachment.AttachHelper;
import com.bjsasc.plm.core.context.rule.RuleHelper;
import com.bjsasc.plm.core.context.template.FileTemplate;
import com.bjsasc.plm.core.session.SessionHelper;
import com.bjsasc.plm.core.util.DateTimeUtil;
import com.bjsasc.plm.review2.committee.service.ReviewCommitteeMemberHelper;
import com.bjsasc.plm.review2.common.BaseAction;
import com.bjsasc.plm.review2.constant.ReviewOrderConstant;
import com.bjsasc.plm.review2.link.model.ReviewOrderMemberLink;
import com.bjsasc.plm.review2.link.service.ReviewMemberHelper;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.object.service.ReviewObjectHelper;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.plm.review2.order.service.ReviewOrderHelper;
import com.bjsasc.plm.review2.order.service.ReviewOrderService;
import com.bjsasc.ui.json.DataUtil;

public class ReviewOrderAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ReviewOrderService service = ReviewOrderHelper.getService();
	private ReviewOrder managedReview;
	private String templateId;

	public void addManagedReview() throws IOException {
		boolean isCheck = SessionHelper.getService().isCheckPermission();
		//关闭权限检查
		SessionHelper.getService().setCheckPermission(false) ;
		try{
			String reviewObjectId = request.getParameter("reviewObjectId");
			Map result=new HashMap();
			ReviewObject reviewObject = ReviewObjectHelper.getService().findReviewObjectById(reviewObjectId);
			Site targetSite = SiteHelper.getSiteService().findSiteById(reviewObject.getSourceSiteInnerId());
			managedReview.setContextInfo(reviewObject.getContextInfo());
			RuleHelper.getService().init(managedReview,managedReview.getContextInfo().getContext());
			managedReview.setDomainInfo(reviewObject.getDomainInfo());
			managedReview.setFolderInfo(reviewObject.getFolderInfo());
			FileTemplate fileTemplate = (FileTemplate) Helper.getPersistService().getObject(FileTemplate.CLASSID, templateId);
			managedReview.setState(ReviewOrderConstant.TASK_MR_NEW);
			long time=DateTimeUtil.getLongTime(new Date(), "yyyy-MM-dd");
			if (managedReview.getReviewTime()>=time){
				if(ReviewOrderConstant.REVIEW_TYPE_ELECTRON.equals(managedReview.getReviewType())){
					if(fileTemplate!= null){
						AttachHelper.getAttachService().copyFile(fileTemplate,managedReview);
						managedReview.setTemplate(fileTemplate);
						
					}else{
						result.put("flag", "fileno");	//评审文件模板校验失败
				    	response.getWriter().print(DataUtil.mapToSimpleJson(result));
				    	return;
					}
				}
				service.saveManagedReview(managedReview);
				ReviewMemberHelper.getService().saveReviewMemberLink(new ReviewOrderMemberLink(managedReview,reviewObject));
				
		    	result.put("flag", "success");	
		    	result.put("oid", Helper.getOid(managedReview));
		    	result.put("orderId", managedReview.getInnerId());
		    	result.put("reviewType",managedReview.getReviewType());
		    	response.getWriter().print(DataUtil.mapToSimpleJson(result));
			}else{
				result.put("flag", "timeno");	//时间校验失败
		    	response.getWriter().print(DataUtil.mapToSimpleJson(result));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			SessionHelper.getService().setCheckPermission(isCheck);
		}
	}

	public void delManagedReview() throws IOException {
		String id = request.getParameter("ids");
		String[] ids = id.split(",");
		boolean b=true;
		for(String s : ids){
			if(checkoutDel(s))
				b=false;
		}
		if(b){
			service.delManagedReview(ids);
			response.getWriter().print("success");
		}else{
			response.getWriter().print("false");
		}
	}

	public String editManagedReview() throws IOException {
		String innerId = request.getParameter("id");
		managedReview = service.findManagedReviewByInnerId(innerId);
		request.setAttribute("reviewScope",managedReview.getReviewScope());
		request.setAttribute("reviewType",managedReview.getReviewType());
		request.setAttribute("reviewTime",Long.valueOf(managedReview.getReviewTime()));
		return "edit";
	}

	public void editManagedReviewSave() throws IOException {
		String id = request.getParameter("id");
		ReviewOrder rO = service.findManagedReviewByInnerId(id);
		rO.setNumber(managedReview.getNumber());
		rO.setName(managedReview.getName());
		rO.setReviewTime(managedReview.getReviewTime());
		rO.setReviewPlace(managedReview.getReviewPlace());
		rO.setNotes(managedReview.getNotes());
		
		long time=DateTimeUtil.getLongTime(new Date(), "yyyy-MM-dd");
		if(rO.getReviewTime()>=time){
			if(ReviewOrderConstant.REVIEW_TYPE_ELECTRON.equals(rO.getReviewType())){
				FileTemplate fileTemplate = (FileTemplate) Helper.getPersistService().getObject(FileTemplate.CLASSID, templateId);
				rO.setTemplate(fileTemplate);
				if(fileTemplate!= null){
					service.updateManagedReview(rO);
					response.getWriter().print("success");
				}else{
					response.getWriter().print("fileno");
				}
			}else{
				service.updateManagedReview(rO);
				response.getWriter().print("success");
			}
			
		}else{
			response.getWriter().print("timeno");
		}
	}
	
	public String queryReviewOrder() throws IOException {
		String innerId = request.getParameter("orderIID");
		managedReview = service.findManagedReviewByInnerId(innerId);
		request.setAttribute("managedReview",managedReview);
		return "viewReviewOrder";
	}
	
	public boolean checkoutDel(String id) {
		List list =ReviewCommitteeMemberHelper.getMemberService().getMembersByReviewOrderId(id);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}

	public String getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public ReviewOrder getManagedReview() {
		return this.managedReview;
	}

	public void setManagedReview(ReviewOrder managedReview) {
		this.managedReview = managedReview;
	}
}