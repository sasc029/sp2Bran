package com.bjsasc.plm.review2.expert.service;

import java.util.List;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.avidm.core.site.SiteHelper;
import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.review2.expert.model.DomainReviewExpert;
import com.bjsasc.plm.review2.expert.model.OuterReviewExpert;
import com.bjsasc.plm.review2.util.ExpertUtil;
import com.cascc.platform.aa.AAProvider;
import com.cascc.platform.aa.api.UserService;
import com.cascc.platform.aa.api.data.AAUserData;


public class ReviewExpertServiceImpl implements ReviewExpertService {

	@Override
	public void saveOuterReviewExpert(OuterReviewExpert outerReviewExpert) {
		Helper.getPersistService().save(outerReviewExpert);
	}

	@Override
	public void delOuterReviewExpert(String[] ids) {
		for(int i=0; i<ids.length; i++){
			OuterReviewExpert outerReviewExpert = (OuterReviewExpert)Helper.getPersistService().getObject(OuterReviewExpert.class.getSimpleName(),ids[i]);
			deleteOuterReviewExpert(outerReviewExpert);
		}
	}

	/**
	 * 删除域外专家
	 * @param
	 *     outerReviewExpert 域外专家对象
	 */
	public void deleteOuterReviewExpert(OuterReviewExpert outerReviewExpert){
		Helper.getPersistService().delete(outerReviewExpert);
	}

	@Override
	public void updateOuterReviewExpert(OuterReviewExpert outerReviewExpert) {
		Helper.getPersistService().update(outerReviewExpert);
	}

	@Override
	public OuterReviewExpert findOuterReviewExpertById(String innerId) {
		String hql = "from OuterReviewExpert where innerId = ?";
		List list = Helper.getPersistService().find(hql.toString(), innerId);
		if(!list.isEmpty()){
			return (OuterReviewExpert)list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void saveDomainReviewExpert(String[] userID,String siteInnerId,String nodeInnerId) {
		// 获取目标站点人员对象
		UserService userService = AAProvider.getUserService();
		Site site = SiteHelper.getSiteService().findSiteById(siteInnerId);
		for(String s : userID){
			DomainReviewExpert domainReviewExpert = new DomainReviewExpert();
			AAUserData user = userService.getUser(null, s);
			domainReviewExpert.setSite(site);
			domainReviewExpert.setUser(user);
			addDomainReviewExpert(domainReviewExpert);
			ExpertUtil.getManager().addExpertNodeMember(nodeInnerId, domainReviewExpert);
		}
		
	}
	
	/**
	 * 添加域内专家
	 * @param
	 *     domainReviewExpert 域内专家对象
	 */
	public void addDomainReviewExpert(DomainReviewExpert domainReviewExpert){
		Helper.getPersistService().save(domainReviewExpert);
	}
	
	@Override
	public void delDomainReviewExpert(String[] ids) {
		for(int i=0; i<ids.length; i++){
			DomainReviewExpert domainReviewExpert = (DomainReviewExpert)Helper.getPersistService().getObject(DomainReviewExpert.class.getSimpleName(),ids[i]);
			deleteDomainReviewExpert(domainReviewExpert);
		}
	}

	/**
	 * 删除域内专家
	 * @param
	 *     outerReviewExpert 域内专家对象
	 */
	public void deleteDomainReviewExpert(DomainReviewExpert domainReviewExpert){
		Helper.getPersistService().delete(domainReviewExpert);
	}

	@Override
	public List<DomainReviewExpert> findAllDomainReviewExpert() {
		String hql = "from DomainReviewExpert";
		List list = Helper.getPersistService().find(hql.toString());
		return list;
	}

	@Override
	public List<OuterReviewExpert> findAllOuterReviewExpert() {
		String hql = "from OuterReviewExpert";
		List list = Helper.getPersistService().find(hql.toString());
		return list;
	}
}
