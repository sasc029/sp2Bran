package com.bjsasc.plm.review2.expert.action;

import java.io.IOException;
import java.util.List;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.avidm.core.site.SiteHelper;
import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.persist.PersistHelper;
import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.review2.common.BaseAction;
import com.bjsasc.plm.review2.expert.model.DomainReviewExpert;
import com.bjsasc.plm.review2.expert.model.ExpertNodeMember;
import com.bjsasc.plm.review2.expert.model.OuterReviewExpert;
import com.bjsasc.plm.review2.expert.service.ExpertNodeMemberHelper;
import com.bjsasc.plm.review2.expert.service.ReviewExpertHelper;
import com.bjsasc.plm.review2.expert.service.ReviewExpertService;
import com.bjsasc.plm.review2.util.ExpertUtil;

/**
 * ר�ҹ���action
 * @author YHJ
 *
 */
public class ReviewExpertAction  extends BaseAction{

	//ϵͳ���в������� service
	private ReviewExpertService service = ReviewExpertHelper.getService();
	
	private DomainReviewExpert domainReviewExpert;

	private OuterReviewExpert outerReviewExpert;
	
	private List<Site> dtSiteList;//���ݴ���վ�㼯��

	public List<Site> getDtSiteList() {
		return dtSiteList;
	}

	public void setDtSiteList(List<Site> dtSiteList) {
		this.dtSiteList = dtSiteList;
	}

	public DomainReviewExpert getDomainReviewExpert() {
		return domainReviewExpert;
	}

	public void setDomainReviewExpert(DomainReviewExpert domainReviewExpert) {
		this.domainReviewExpert = domainReviewExpert;
	}

	public OuterReviewExpert getOuterReviewExpert() {
		return outerReviewExpert;
	}

	public void setOuterReviewExpert(OuterReviewExpert outerReviewExpert) {
		this.outerReviewExpert = outerReviewExpert;
	}
	
	/**
	 * �������ר�Ҷ���
	 */
	public void addOuterReviewExpert() throws IOException{
		String  sourceSiteInnerId = request.getParameter("sourceSiteInnerId");
		String  nodeInnerId = request.getParameter("nodeInnerId");
		if(isExistsOuter(outerReviewExpert.getName(),sourceSiteInnerId)){/*�Ѿ�����*/
			response.getWriter().write("isExists");
			return;
		}
		
		Site site = SiteHelper.getSiteService().findSiteById(sourceSiteInnerId);
		outerReviewExpert.setSite(site);
		service.saveOuterReviewExpert(outerReviewExpert);
		ExpertUtil.getManager().addExpertNodeMember(nodeInnerId, outerReviewExpert);
		
		response.getWriter().print(SUCCESS);
	}
	
	/**
	 * ɾ������ר�Ҷ���
	 */
	public void delOuterReviewExpert() throws IOException{
		String id = request.getParameter("ids");
		String ids[] = id.split(",");
		for(int i=0;i<ids.length;i++){
		  List<ExpertNodeMember> expertNodeMembers = ExpertNodeMemberHelper.getService().getAllExpertNodeMemberListByExpertRef(ids[i]);
		  PersistHelper.getService().delete(expertNodeMembers);
		}
		service.delOuterReviewExpert(ids);
		response.getWriter().print(SUCCESS);
	}
	
	/**
	 * �༭����ר�Ҷ���
	 */
	public String editOuterReviewExpert() throws IOException{
		String innerId = request.getParameter("id");
		outerReviewExpert = service.findOuterReviewExpertById(innerId);
		request.setAttribute("sex", outerReviewExpert.getSex());
		return "editOuterReviewExpert";
	}
	
	/**
	 * �༭����ר�Ҷ���
	 */
	public void editOuterReviewExpertSave() throws IOException{
		service.updateOuterReviewExpert(outerReviewExpert);
		response.getWriter().print(SUCCESS);
	}
	
	/**
	 * ��ʼ������ת��ӽ�ɫ�û�ҳ�� 
	 */
	public String addDomainReviewExpert(){
		dtSiteList = SiteHelper.getSiteService().findAllSite();
		request.setAttribute("nodeInnerId", request.getParameter("innerId"));
		return "addDomainReviewExpert";
	}
	
	/**
	 * �������ר�Ҷ���
	 */
	public void addDomainReviewExpertSave() throws IOException{
		String userIDs = request.getParameter("userId");
		String siteInnerId = request.getParameter("siteInnerId");
		String nodeInnerId = request.getParameter("nodeInnerId");
		if(userIDs != null && !"".equals(userIDs)){
			String userID[] = userIDs.split(",");
			for(String s : userID){
				if(isExistsDomain(s)){/*�Ѿ�����*/
					response.getWriter().write("isExists");
					return;
				}
			}
			service.saveDomainReviewExpert(userID,siteInnerId,nodeInnerId);
			response.getWriter().print(SUCCESS);
		}
	}
	
	/**
	 * ɾ������ר�Ҷ���
	 */
	public void delDomainReviewExpert() throws IOException{
		String id = request.getParameter("ids");
		String ids[] = id.split(",");
		for(int i=0;i<ids.length;i++){
			List<ExpertNodeMember> expertNodeMembers = ExpertNodeMemberHelper.getService().getAllExpertNodeMemberListByExpertRef(ids[i]);
			PersistHelper.getService().delete(expertNodeMembers);
		}
		service.delDomainReviewExpert(ids);
		response.getWriter().print(SUCCESS);
	}
	
	/**
	 * �ж�վ���������û��Ƿ��Ѿ�����
	 * @param userIId
	 * @return
	 */
	public boolean isExistsDomain(String userIId){
		boolean result=false;
		User user = Helper.getSessionService().getUser();
		List<DomainReviewExpert> domainReviewExpertList=service.findAllDomainReviewExpert();
		if(!domainReviewExpertList.isEmpty()){
			for(DomainReviewExpert domainReviewExpert:domainReviewExpertList){
				if(userIId.equals(domainReviewExpert.getUser().getInnerId()) && domainReviewExpert.getManageInfo().getCreateBy().getInnerId().equals(user.getInnerId())){
					result=true;
				}
			}
		}
		return result;
	}
	
	/**
	 * �ж������������Ƿ��Ѿ�����
	 * @param userName
	 * @return
	 */
	public boolean isExistsOuter(String userName,String siteInnerId){
		boolean result=false;
		List<OuterReviewExpert> outerReviewExpertList=service.findAllOuterReviewExpert();
		if(!outerReviewExpertList.isEmpty()){
			for(OuterReviewExpert outerReviewExpert:outerReviewExpertList){
				if(userName.equals(outerReviewExpert.getName())&&outerReviewExpert.getSite().getInnerId().equals(siteInnerId)){
					result=true;
				}
			}
		}
		return result;
	}
}
