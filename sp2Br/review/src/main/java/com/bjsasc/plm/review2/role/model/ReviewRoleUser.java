package com.bjsasc.plm.review2.role.model;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;
import com.bjsasc.plm.core.type.ATObject;
import com.cascc.platform.aa.api.data.AAUserData;

/**
 * �����ɫ��Ա����
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
	 * �������
	 */
	private ManageInfo manageInfo;
	
	public ReviewRoleUser(){
		setClassId(CLASSID);
	}
	/**
	 * ��ȡ���ö���(ReviewRole)��ɫ
	 */
	public ObjectReference getRoleRef() {
		return roleRef;
	}

	/**
	 * ���ö���(ReviewRole)��ɫ
	 */
	public void setRoleRef(ObjectReference roleRef) {
		this.roleRef = roleRef;
	}

	/**
	 * ��ȡReviewRole����
	 */
	public ReviewRole getRole() {
		if (roleRef != null) {
			return (ReviewRole) roleRef.getObject();
		}
		return null;
	}

	/**
	 * ����ReviewRole���� 
	 */
	public void setRole(ReviewRole role) {
		setRoleRef(role != null ? ObjectReference.newObjectReference(role) : null);
	}

	/**
	 * �����������
	 */
	public void setManageInfo(ManageInfo manageInfo) {
		this.manageInfo = manageInfo;

	}
	
	/**
	 * ��ȡ�������
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
	 * ��ȡAAUserData����
	 */
	public AAUserData getUser() {
		if (userRef != null) {
			return (AAUserData) userRef.getObject();
		}
		return null;
	}

	/**
	 * ����AAUserData���� 
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
