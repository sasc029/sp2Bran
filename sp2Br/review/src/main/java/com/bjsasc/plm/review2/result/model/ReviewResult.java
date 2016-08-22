package com.bjsasc.plm.review2.result.model;

import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;
import com.bjsasc.plm.core.type.ATObject;
import com.bjsasc.plm.review2.expert.model.OuterReviewExpert;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.cascc.platform.aa.api.data.AAUserData;

public class ReviewResult extends ATObject implements Manageable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3767979398331232977L;
	
	public static final String RESULT_TYPE_OUTER="out";//域外
	
	public static final String RESULT_TYPE_DOMAIN="domain";//域内
	
	private ObjectReference reviewObjectRef;//评审对象
	
	private ObjectReference reviewOrderRef;//评审单
	
	private ObjectReference executorRef;//任务执行人
	
	private ObjectReference outerExpertRef;
	
	private String isExpertConfirm;//三个值表示：0表示未确认，1表示通过，2表示未通过
	
	private String expertCfmContent;//评委确认内容
	
	private String isLeaderConfirm;//组长是否确认，三个值表示：0表示未确认，1表示通过，2表示未通过
	
	private String leaderCfmContent;//组长确认内容
	
	private String resultType;//域内，域外的
	
	public ReviewResult(){
		setClassId(ReviewResult.class.getSimpleName());
	}
	

	@Override
	public void setManageInfo(ManageInfo manageInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ManageInfo getManageInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectReference getReviewObjectRef() {
		return reviewObjectRef;
	}

	public void setReviewObjectRef(ObjectReference reviewObjectRef) {
		this.reviewObjectRef = reviewObjectRef;
	}
	
	public ReviewObject getReviewObject() {
		if (reviewObjectRef != null) {
			return (ReviewObject) reviewObjectRef.getObject();
		}
		return null;
	}

	public void setReviewObject(ReviewObject reviewObject) {
		setReviewObjectRef(reviewObject != null ? ObjectReference.newObjectReference(reviewObject) : null);
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
	

	public ObjectReference getExecutorRef() {
		return executorRef;
	}

	public void setExecutorRef(ObjectReference executorRef) {
		this.executorRef = executorRef;
	}
	
	public AAUserData getExecutor() {
		if (executorRef != null) {
			return (AAUserData) executorRef.getObject();
		}
		return null;
	}

	public void setExecutor(AAUserData aaUserData) {
		setExecutorRef(aaUserData != null ? ObjectReference.newObjectReference(aaUserData) : null);
	}

	public String getIsExpertConfirm() {
		return isExpertConfirm;
	}

	public void setIsExpertConfirm(String isExpertConfirm) {
		this.isExpertConfirm = isExpertConfirm;
	}

	public String getExpertCfmContent() {
		return expertCfmContent;
	}

	public void setExpertCfmContent(String expertCfmContent) {
		this.expertCfmContent = expertCfmContent;
	}

	public String getIsLeaderConfirm() {
		return isLeaderConfirm;
	}

	public void setIsLeaderConfirm(String isLeaderConfirm) {
		this.isLeaderConfirm = isLeaderConfirm;
	}

	public String getLeaderCfmContent() {
		return leaderCfmContent;
	}

	public void setLeaderCfmContent(String leaderCfmContent) {
		this.leaderCfmContent = leaderCfmContent;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public ObjectReference getOuterExpertRef() {
		return outerExpertRef;
	}

	public void setOuterExpertRef(ObjectReference outerExpertRef) {
		this.outerExpertRef = outerExpertRef;
	}
	
	public OuterReviewExpert getOuterExpert() {
		if (outerExpertRef != null) {
			return (OuterReviewExpert) outerExpertRef.getObject();
		}
		return null;
	}

	public void setOuterExpert(OuterReviewExpert expert) {
		setOuterExpertRef(expert != null ? ObjectReference.newObjectReference(expert) : null);
	}

}
