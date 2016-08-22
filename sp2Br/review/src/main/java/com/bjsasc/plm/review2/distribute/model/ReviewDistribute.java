package com.bjsasc.plm.review2.distribute.model;

import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;
import com.bjsasc.plm.core.type.ATObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;

public class ReviewDistribute extends ATObject implements Manageable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8262880470331398134L;
	
    public static final String CLASSID = ReviewDistribute.class.getSimpleName();
	
	public ReviewDistribute(){
		setClassId(CLASSID);
	}
	
	private String userInnerId;
	
	private String userName;
	
	private String unitInnerId;
	
	private String unitName;
	
	private String type;
	
	private int countNum;
	
	private ObjectReference reviewOrderRef;
	
	private ManageInfo manageInfo;
	

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Override
	public void setManageInfo(ManageInfo manageInfo) {
		this.manageInfo = manageInfo;
		
	}

	@Override
	public ManageInfo getManageInfo() {
		return this.manageInfo;
	}

	public String getUserInnerId() {
		return userInnerId;
	}

	public void setUserInnerId(String userInnerId) {
		this.userInnerId = userInnerId;
	}

	public String getUnitInnerId() {
		return unitInnerId;
	}

	public void setUnitInnerId(String unitInnerId) {
		this.unitInnerId = unitInnerId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCountNum() {
		return countNum;
	}

	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}
	
	public ObjectReference getReviewOrderRef() {
		return reviewOrderRef;
	}

	public void setReviewOrderRef(ObjectReference reviewOrderRef) {
		this.reviewOrderRef = reviewOrderRef;
	}
	
	public ReviewOrder getReviewOrder() {
		if (reviewOrderRef != null) {
			return (ReviewOrder) reviewOrderRef.getObject();
		}
		return null;
	}

	public void setReviewOrder(ReviewOrder reviewOrder) {
		setReviewOrderRef(reviewOrder != null ? ObjectReference.newObjectReference(reviewOrder) : null);
	}
}
