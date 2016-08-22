package com.bjsasc.plm.review2.expert.model;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;
import com.cascc.platform.aa.api.data.AAUserData;

/**
 * 域内专家
 * @author YHJ
 *
 */
public class DomainReviewExpert extends ATReviewExpert implements ReviewExpert,Manageable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8104695462815669108L;
	
	public static String DOMAIN_EXPERT_FLAG = "domainUser";
	
	private ObjectReference userRef;
	
	private ObjectReference siteRef;
	
	
	
	
	public ObjectReference getSiteRef() {
		return siteRef;
	}

	public void setSiteRef(ObjectReference siteRef) {
		this.siteRef = siteRef;
	}
	
	public Site getSite() {
		if (siteRef != null) {
			return (Site) siteRef.getObject();
		}
		return null;
		
	}
	public void setSite(Site site) {
		setSiteRef(site != null ? ObjectReference.newObjectReference(site) : null);
	}
	/**
	 * 组合属性
	 */
	private ManageInfo manageInfo;
	
	public DomainReviewExpert(){
		setClassId(DomainReviewExpert.class.getSimpleName());
	}
	
	/**
	 * 设置组合属性
	 */
	public void setManageInfo(ManageInfo manageInfo) {
		this.manageInfo = manageInfo;

	}
	
	/**
	 * 获取组合属性
	 */
	public ManageInfo getManageInfo() {
		return this.manageInfo;
	}
	
	public ObjectReference getUserRef() {
		return userRef;
	}

	public void setUserRef(ObjectReference userRef) {
		this.userRef = userRef;
	}
	
	/**
	 * 获取AAUserData对象
	 */
	public AAUserData getUser() {
		if (userRef != null) {
			return (AAUserData) userRef.getObject();
		}
		return null;
	}

	/**
	 * 设置AAUserData对象 
	 */
	public void setUser(AAUserData user) {
		setUserRef(user != null ? ObjectReference.newObjectReference(user) : null);
	}
}
