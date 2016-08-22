package com.bjsasc.plm.review2.task.model;

import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;
import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.core.type.ATObject;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;

public class ReviewTask extends ATObject implements Manageable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 539734342121004023L;
	
	public static final String CLASSID = ReviewTask.class.getSimpleName();
	
	public static String RVTASK_STATE_DEAL="waitDeal";
	public static String RVTASK_STATE_END="endTask";
	
	private String taskName; //��������
	
	private ObjectReference reviewObjectRef;//�������
	
	private ObjectReference reviewOrderRef;//����
	
	private ObjectReference executorRef;//����ִ����
	
	private String taskState; //����״̬
	
	private String taskRole;//������ί������ר�ң����ʦ���鳤�������ˣ�
	
	public ReviewTask(){
		setClassId(CLASSID);
	}
	
	/**
	 * �������
	 */
	private ManageInfo manageInfo;
	
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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
	
	public void setExecutor(User executor){
		this.executorRef = ObjectReference.newObjectReference(executor);
	}
	
	public User getExecutor(){
		if(this.executorRef != null){
			return (User)this.executorRef.getObject();
		}
		
		return null;
	}
	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
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

	public String getTaskRole() {
		return taskRole;
	}

	public void setTaskRole(String taskRole) {
		this.taskRole = taskRole;
	}

	
}
