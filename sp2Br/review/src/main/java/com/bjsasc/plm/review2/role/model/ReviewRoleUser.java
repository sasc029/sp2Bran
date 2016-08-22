package com.bjsasc.plm.review2.role.model;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;
import com.bjsasc.plm.core.type.ATObject;
import com.cascc.platform.aa.api.data.AAUserData;

/**
 * 评审角色人员管理
 * @author YHJ
 *
 */
public class ReviewRoleUser extends ATObject implements Manageable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8536748988761815664L;
	
	private ObjectReference roleRef;
	
	private ObjectReference userRef;
	
	private ObjectReference siteRef;
	
	public static final String CLASSID = ReviewRoleUser.class.getSimpleName();
	/**
	 * 组合属性
	 */
	private ManageInfo manageInfo;
	
	public ReviewRoleUser(){
		setClassId(CLASSID);
	}
	/**
	 * 获取引用对象(ReviewRole)角色
	 */
	public ObjectReference getRoleRef() {
		return roleRef;
	}

	/**
	 * 设置对象(ReviewRole)角色
	 */
	public void setRoleRef(ObjectReference roleRef) {
		this.roleRef = roleRef;
	}

	/**
	 * 获取ReviewRole对象
	 */
	public ReviewRole getRole() {
		if (roleRef != null) {
			return (ReviewRole) roleRef.getObject();
		}
		return null;
	}

	/**
	 * 设置ReviewRole对象 
	 */
	public void setRole(ReviewRole role) {
		setRoleRef(role != null ? ObjectReference.newObjectReference(role) : null);
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

	public void setSite(Site dtSite) {
		setSiteRef(dtSite != null ? ObjectReference.newObjectReference(dtSite) : null);
	}
	
}
