package com.bjsasc.plm.review2.committee.model;

import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.core.type.ATObject;
import com.bjsasc.plm.review2.expert.model.ReviewExpert;
import com.bjsasc.plm.review2.order.model.ReviewOrder;

/**
 * 评委实体类
 * @author YHJ
 *
 */
public class ReviewCommitteeMemberHS extends ATObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1681151739933644089L;
	
	public static final String CLASSID = ReviewCommitteeMemberHS.class.getSimpleName();
	
	public static final String USERLEVEL_COMMON = "COMMON";//普通评委
	public static final String USERLEVEL_OFFICER = "OFFICER";//评审组长
	
	private ObjectReference expertRef;
	private ObjectReference reviewOrderRef;
	private String userLevel;//职位
	private int userorder;//评委顺序
	
	public ReviewCommitteeMemberHS(){
		setClassId(CLASSID);
	}

	public ObjectReference getExpertRef() {
		return expertRef;
	}

	public void setExpertRef(ObjectReference expertRef) {
		this.expertRef = expertRef;
	}
	
	public ReviewExpert getExpert() {
		if (expertRef != null) {
			return (ReviewExpert) expertRef.getObject();
		}
		return null;
	}

	public void setExpert(ReviewExpert expert) {
		setExpertRef(expert != null ? ObjectReference.newObjectReference(expert) : null);
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

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public int getUserorder() {
		return userorder;
	}

	public void setUserorder(int userorder) {
		this.userorder = userorder;
	}
	
	
	
}
