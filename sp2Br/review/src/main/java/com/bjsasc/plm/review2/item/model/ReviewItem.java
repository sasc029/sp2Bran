package com.bjsasc.plm.review2.item.model;

import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;
import com.bjsasc.plm.core.type.ATObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.cascc.platform.aa.api.data.AAUserData;

/**
 * ��������
 * @author YHJ
 *
 */
public class ReviewItem extends ATObject implements Manageable{
	
	public static final String TASKSTATE_NEW = "new";
	public static final String TASKSTATE_RUNNING = "running";
	public static final String TASKSTATE_REPLY = "reply";
	public static final String TASKSTATE_REPLYAFFIRM = "replyaffirm";
	public static final String CLASSID = ReviewItem.class.getSimpleName();
	/**
	 * 
	 */
	private static final long serialVersionUID = 5788329208214777473L;
	
	
	private String itemName;
	
	private String itemContents;//��������
	
	private ObjectReference reviewOrderRef;
	
	private ObjectReference executorRef;//����ִ����
	
	private ObjectReference senderRef;//�·���
	
	private String itemSource;//������Դ
	
	private String itemType;//��������
	
	private String productIID;
	
	private String productName;
	
	private String professionID;//רҵID
	
	private String professionName;
	
	private String phaseId;//�׶�id
	
	private String phaseName;
	
	private long startTime;//��ʼʱ��
	
	private long deadLine;//��ֹʱ��
	
	private long endTime;//���ʱ��
	
	private String formatter;//�����ʽ
	
	private String note;//��ע
	
	private String state;
	
	private String dealContents;
	
	private String isConfirm;
	
	private String sourceSiteInnerId;
	
	private String itemResult;
	
	private ManageInfo manageInfo;
	
	

	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
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
	public String getFormatter() {
		return formatter;
	}
	
	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
	
	public ManageInfo getManageInfo() {
		return manageInfo;
	}
	
	public void setManageInfo(ManageInfo manageInfo) {
		this.manageInfo = manageInfo;
	}
	
	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public ObjectReference getExecutorRef() {
		return executorRef;
	}
	public void setExecutorRef(ObjectReference executorRef) {
		this.executorRef = executorRef;
	}
	
	/**
	 * ��ȡAAUserData����
	 */
	public AAUserData getUser() {
		if (executorRef != null) {
			return (AAUserData) executorRef.getObject();
		}
		return null;
	}

	/**
	 * ����AAUserData���� 
	 */
	public void setUser(AAUserData user) {
		setExecutorRef(user != null ? ObjectReference.newObjectReference(user) : null);
	}

	public String getItemContents() {
		return itemContents;
	}

	public void setItemContents(String itemContents) {
		this.itemContents = itemContents;
	}

	public ObjectReference getSenderRef() {
		return senderRef;
	}

	public void setSenderRef(ObjectReference senderRef) {
		this.senderRef = senderRef;
	}
	
	/**
	 * ��ȡAAUserData����
	 */
	public AAUserData getSender() {
		if (senderRef != null) {
			return (AAUserData) senderRef.getObject();
		}
		return null;
	}

	/**
	 * ����AAUserData���� 
	 */
	public void setSender(AAUserData user) {
		setSenderRef(user != null ? ObjectReference.newObjectReference(user) : null);
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSource() {
		return itemSource;
	}

	public void setItemSource(String itemSource) {
		this.itemSource = itemSource;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getProductIID() {
		return productIID;
	}

	public void setProductIID(String productIID) {
		this.productIID = productIID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProfessionID() {
		return professionID;
	}

	public void setProfessionID(String professionID) {
		this.professionID = professionID;
	}

	public String getProfessionName() {
		return professionName;
	}

	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}

	public String getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}

	public String getPhaseName() {
		return phaseName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDealContents() {
		return dealContents;
	}

	public void setDealContents(String dealContents) {
		this.dealContents = dealContents;
	}

	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

	public long getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(long deadLine) {
		this.deadLine = deadLine;
	}

	public String getSourceSiteInnerId() {
		return sourceSiteInnerId;
	}

	public void setSourceSiteInnerId(String sourceSiteInnerId) {
		this.sourceSiteInnerId = sourceSiteInnerId;
	}

	public String getItemResult() {
		return itemResult;
	}

	public void setItemResult(String itemResult) {
		this.itemResult = itemResult;
	}
	
}
