package com.bjsasc.plm.review2.role.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.avidm.core.site.SiteHelper;
import com.bjsasc.avidm.core.transfer.TransferObject;
import com.bjsasc.avidm.core.transfer.TransferObjectHelper;
import com.bjsasc.avidm.core.transfer.util.TransferConstant;
import com.bjsasc.plm.review2.common.BaseAction;
import com.bjsasc.plm.review2.role.model.ReviewRole;
import com.bjsasc.plm.review2.role.model.ReviewRoleUser;
import com.bjsasc.plm.review2.role.service.ReviewRoleHelper;
import com.bjsasc.plm.review2.role.service.ReviewRoleUserHelper;
import com.bjsasc.ui.json.DataUtil;
import com.cascc.platform.uuidservice.UUID;

/**
 * ��ɫ���ɫ��Ա����action
 * @author YHJ
 *
 */
public class ReviewRoleAction  extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8070682600802745205L;
	private ReviewRole reviewRole;
	private List<Site> dtSiteList;//���ݴ���վ�㼯��	
	private String innerId;
	public ReviewRole getReviewRole() {
		return reviewRole;
	}

	public void setReviewRole(ReviewRole reviewRole) {
		this.reviewRole = reviewRole;
	}

	/**
	 * ��ʼ�������ɫ
	 */
	public void initReviewRoles(){
		try{
			List<ReviewRole> roles = ReviewRoleHelper.getReviewRoleService().getInitRoleList();
			if(roles!=null&&roles.size()>0){
				ReviewRoleHelper.getReviewRoleService().saveReviewRoleList(roles);
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("SUCCESS", "true");
			String json = DataUtil.mapToSimpleJson(map);
			response.getWriter().print(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * ��Ӻ����ɫ
	 */
	public void addReviewRole(){
		try{
			ReviewRoleHelper.getReviewRoleService().saveReviewRole(reviewRole);
			Map<String, String> map = new HashMap<String, String>();
			map.put("SUCCESS", "true");
			String json = DataUtil.mapToSimpleJson(map);
			response.getWriter().print(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * ��֤����Ƿ��Ѵ��� 
	 */
	public void checkRolID() throws IOException{
		String roleID = request.getParameter("roleID");
		//true �Ѵ���  false ������   
		boolean boo = ReviewRoleHelper.getReviewRoleService().checkReviewRoleId(roleID);
		response.getWriter().print(boo);
	}
	/**
	 * ����ɾ�������ɫ
	 */
	public void deleteReviewRoles(){
		try{
			String id = request.getParameter("ids");
			String[] ids = id.split(",");
			ReviewRoleHelper.getReviewRoleService().deleteReviewRoles(ids);
			Map<String, String> map = new HashMap<String, String>();
			map.put("SUCCESS", "true");
			String json = DataUtil.mapToSimpleJson(map);
			response.getWriter().print(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * ��ת �޸ĺ����ɫ ҳ��
	 */
	public String gotoPageOfEditReviewRole(){
		String id = request.getParameter("id");
		reviewRole = ReviewRoleHelper.getReviewRoleService().getReviewRoleByInnerId(id);
		return "editReviewRole";
	}
	
	/**
	 * �޸ĺ����ɫ
	 */
	public void updateReviewRole(){
		try{
			ReviewRoleHelper.getReviewRoleService().updateReviewRole(reviewRole);
			Map<String, String> map = new HashMap<String, String>();
			map.put("SUCCESS", "true");
			String json = DataUtil.mapToSimpleJson(map);
			response.getWriter().print(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * ����ӽ�ɫ�û�ҳ��
	 * @return
	 */
	public String gotoPageOfAddReviewRoleUser(){
		innerId = request.getParameter("innerId");
		reviewRole = ReviewRoleHelper.getReviewRoleService().getReviewRoleByInnerId(innerId);
		setDtSiteList(SiteHelper.getSiteService().findAllSite());
		return "addReviewRoleUser";
	}
	/**
	 * �����ɫ�û�
	 */
	public void saveReviewRoleUser(){
		try{
			//��ȡ��ɫID
			String roleId = request.getParameter("roleId");
			//վ��ID
			String siteId = request.getParameter("siteId");
			String userIds = request.getParameter("userIds");
			//���û����ת���û�����
			String[] arrayUserIds = userIds.split(",");
			//���û���ת��Ϊ�û�����
			String[] arrayUserNames = request.getParameter("userNames").split(",");
			//��ȡ��ƷID
			ReviewRoleUserHelper.getReviewRoleService().addReviewRoleUsers(roleId,siteId, arrayUserIds, arrayUserNames);
			ReviewRole rw = ReviewRoleHelper.getReviewRoleService().getReviewRoleByInnerId(roleId);
			sendReviewRoleMessage(userIds, siteId, rw.getRoleID(), "add");
			response.getWriter().print(SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void sendReviewRoleMessage(String userIds, String siteId, String roleType, String operate){
		TransferObject transferObject = new TransferObject();
		Site selfSite = SiteHelper.getSiteService().findLocalSiteInfo();
		transferObject.setObjIID(UUID.getUID());
		transferObject.setObjName("�����ɫ����֪ͨ��Ϣ");
		transferObject.setTransferType("reviewRoleRequest");
		transferObject.setObjType("review");
		transferObject.setSourceSite(selfSite);
		Site targetSite = SiteHelper.getSiteService().findSiteById(siteId);
		transferObject.setTargetSite(targetSite);
		transferObject.setCreateTime(System.currentTimeMillis());
		transferObject.setTransferState(TransferConstant.TRANSOBJECT_STATE_NEW);
		Map<String, String> map = new HashMap<String, String>();
		map.put("userIID", userIds);
		map.put("roleType", roleType);
		map.put("operate", operate);
		transferObject.setReqParamMap(map);
		transferObject.setRequestType(TransferConstant.REQTYPE_ASYN);
		transferObject.setFilePath("");
		TransferObjectHelper.getTransferService().sendRequest(transferObject);
	}
	
	/**
	 * ɾ����ɫ�û�
	 */
	public void deleteReviewRoleUser(){
		try{
			String id = request.getParameter("ids");
			String[] ids = id.split(",");
			String operate = "delete";
			for(int i=0; i<ids.length; i++){
				ReviewRoleUser tmpUser = ReviewRoleUserHelper.getReviewRoleService().getReviewRoleUserByInnerId(ids[i]);
				String userIds = tmpUser.getUser().getInnerId();
				String roleType = tmpUser.getRole().getRoleID();
				String siteId = tmpUser.getSite().getInnerId();
				sendReviewRoleMessage(userIds, siteId, roleType, operate);
				ReviewRoleUserHelper.getReviewRoleService().deleteReviewRoleUser(tmpUser);
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("SUCCESS", "true");
			String json = DataUtil.mapToSimpleJson(map);
			response.getWriter().print(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public List<Site> getDtSiteList() {
		return dtSiteList;
	}

	public void setDtSiteList(List<Site> dtSiteList) {
		this.dtSiteList = dtSiteList;
	}

	public String getInnerId() {
		return innerId;
	}

	public void setInnerId(String innerId) {
		this.innerId = innerId;
	}
	
	/**
	 * �жϽ�ɫ�û��Ƿ����
	 */
	public void checkUseExist(){
		try{
			String userId = request.getParameter("userId");
			String roleId = request.getParameter("roleId");
			boolean f = ReviewRoleUserHelper.getReviewRoleService().isExistReviewRoleUser(userId, roleId);
			response.getWriter().print(f);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
