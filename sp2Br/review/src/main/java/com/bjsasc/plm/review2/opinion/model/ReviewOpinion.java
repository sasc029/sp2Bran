package com.bjsasc.plm.review2.opinion.model;

import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.core.attachment.FileHolder;
import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;
import com.bjsasc.plm.core.type.ATObject;
import com.bjsasc.plm.review2.expert.model.OuterReviewExpert;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.cascc.platform.aa.api.data.AAUserData;

/**
 * 评审意见
 * @author YHJ
 *
 */
public class ReviewOpinion extends ATObject implements Manageable, FileHolder{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4093174618731249671L;
	
	public static final String CLASSID = ReviewOpinion.class.getSimpleName();
	
	public static final int OPNSTATE_NEW = 1;
	public static final int OPNSTATE_SUBMIT = 2;
	public static final int OPNSTATE_CHECKOVER = 3;
	public static final int OPNSTATE_ANSWER = 4;
	public static final int OPNSTATE_ANSWEROVER = 5;
	public static final int OPNSTATE_CONFIRM = 6;
	public static final int OPNSTATE_CONFIRMOVER = 7;
	public static final int OPNSTATE_LEADER_CONFIRM = 8;
	public static final int OPNSTATE_LEADER_CONFIRMOVER = 9;
	public static final int AGREEOVER = 10;
	public static final String OPNTYPE_OUTER= "outer";
	public static final String OPNTYPE_DOMAIN= "domain";
	public static final String OPNTYPE_SUBMIT="submitSave";
	public static final String OPNTYPE_CONFIRM="confirmSave";
	
	private ObjectReference reviewobjectRef;
	
	private ObjectReference reviewOrderRef;
	
	private ObjectReference executorRef;//执行人
	
	private ObjectReference outerExpertRef;//域外实际执行人
	
	private ObjectReference answerUserRef;//解答人

	private ManageInfo manageInfo;//提出问题时间从这里边获取
	
	private String opinionContent;//意见内容
	
	private String opinionState;//意见状态
	
	private String opinionType;//意见类型，域内专家意见还是域外专家意见
	
	private long answerTime;//解答时间
	
	private String answerContent;//解答内容
	
	private String orderFlag;//序号
	
	public ReviewOpinion(){
		setClassId(ReviewOpinion.class.getSimpleName());
	}

	public ObjectReference getReviewobjectRef() {
		return reviewobjectRef;
	}

	public void setReviewobjectRef(ObjectReference reviewobjectRef) {
		this.reviewobjectRef = reviewobjectRef;
	}
	
	public ReviewObject getReviewObject() {
		if (reviewobjectRef != null) {
			return (ReviewObject) reviewobjectRef.getObject();
		}
		return null;
	}

	public void setReviewObject(ReviewObject reviewObject) {
		setReviewobjectRef(reviewObject != null ? ObjectReference.newObjectReference(reviewObject) : null);
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

	public ManageInfo getManageInfo() {
		return manageInfo;
	}

	public void setManageInfo(ManageInfo manageInfo) {
		this.manageInfo = manageInfo;
	}

	public String getOpinionState() {
		return opinionState;
	}

	public int getOpinionStateInt(){
		return Integer.parseInt(getOpinionState());
	}
	
	public void setOpinionState(String opinionState) {
		this.opinionState = opinionState;
	}

	public String getOpinionType() {
		return opinionType;
	}

	public void setOpinionType(String opinionType) {
		this.opinionType = opinionType;
	}

	public long getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(long answerTime) {
		this.answerTime = answerTime;
	}


	public ObjectReference getAnswerUserRef() {
		return answerUserRef;
	}

	public void setAnswerUserRef(ObjectReference answerUserRef) {
		this.answerUserRef = answerUserRef;
	}

	
	public AAUserData getAnswerUser() {
		if (answerUserRef != null) {
			return (AAUserData) answerUserRef.getObject();
		}
		return null;
	}

	public void setAnswerUser(AAUserData aaUserData) {
		setAnswerUserRef(aaUserData != null ? ObjectReference.newObjectReference(aaUserData) : null);
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
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
	
	public String getOpinionContent() {
		return opinionContent;
	}

	public void setOpinionContent(String opinionContent) {
		this.opinionContent = opinionContent;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
	
}
