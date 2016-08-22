package com.bjsasc.plm.review2.expert.model;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;

/**
 * 域外专家
 * 
 * @author YHJ
 * 
 */
public class OuterReviewExpert extends ATReviewExpert implements ReviewExpert,
		Manageable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2547679492686180098L;
	
	public static String OUTER_EXPERT_FLAG = "outUser";

	private String name;

	private String sex;

	private String phone;

	private String workUnit;

	private String note;
	
	private ObjectReference siteRef;
	
	public OuterReviewExpert() {
		setClassId(OuterReviewExpert.class.getSimpleName());
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
	public void setSite(Site site) {
		setSiteRef(site != null ? ObjectReference.newObjectReference(site) : null);
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	/**
	 * 组合属性
	 */
	private ManageInfo manageInfo;

	/**
	 * 设置组合属性
	 */
	public void setManageInfo(ManageInfo manageInfo) {
		this.manageInfo = manageInfo;

	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 获取组合属性
	 */
	public ManageInfo getManageInfo() {
		return this.manageInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
