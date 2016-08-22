package com.bjsasc.plm.review2.conclusion.model;

import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;
import com.bjsasc.plm.core.type.ATObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;

/**
 * 评审结论
 * @author YHJ
 *
 */
public class ReviewConclusionHS extends ATObject implements Manageable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3023618670447921148L;
	
	private ObjectReference reviewOrderRef;
	
	private String content;//评述性结论
	
	private String reviewResult;//1.通过评审，修改后直接出正式文件；2.通过评审，提交会议评审或会议审查；3.未通过评审，需重新修改文件
	
	private String state;//评审结论状态
	
	private ManageInfo manageInfo;
	
	public static final String CLASSID = ReviewConclusionHS.class.getSimpleName();
	
	public ReviewConclusionHS(){
		setClassId(CLASSID);
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
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReviewResult() {
		return reviewResult;
	}

	public void setReviewResult(String reviewResult) {
		this.reviewResult = reviewResult;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ManageInfo getManageInfo() {
		return manageInfo;
	}

	public void setManageInfo(ManageInfo manageInfo) {
		this.manageInfo = manageInfo;
	}

}
