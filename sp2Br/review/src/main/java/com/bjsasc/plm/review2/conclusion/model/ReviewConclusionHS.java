package com.bjsasc.plm.review2.conclusion.model;

import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;
import com.bjsasc.plm.core.type.ATObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;

/**
 * �������
 * @author YHJ
 *
 */
public class ReviewConclusionHS extends ATObject implements Manageable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3023618670447921148L;
	
	private ObjectReference reviewOrderRef;
	
	private String content;//�����Խ���
	
	private String reviewResult;//1.ͨ�������޸ĺ�ֱ�ӳ���ʽ�ļ���2.ͨ�������ύ��������������飻3.δͨ�������������޸��ļ�
	
	private String state;//�������״̬
	
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
