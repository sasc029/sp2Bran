package com.bjsasc.plm.review2.object.model;

import com.bjsasc.plm.core.context.model.ContextInfo;
import com.bjsasc.plm.core.context.model.Contexted;
import com.bjsasc.plm.core.domain.DomainInfo;
import com.bjsasc.plm.core.domain.Domained;
import com.bjsasc.plm.core.folder.FolderInfo;
import com.bjsasc.plm.core.folder.Foldered;
import com.bjsasc.plm.core.persist.model.Persistable;
import com.bjsasc.plm.core.review2.Reviewed;
import com.bjsasc.plm.core.type.ATObject;

/**
 * �������
 * @author YHJ
 *
 */
public class ReviewObject extends ATObject implements Persistable, Contexted, Domained, Foldered, Reviewed{

	/**
	 * 
	 */
	private static final long serialVersionUID = -104529701343654473L;
	
	private String docId;//�����ļ�IID
	
	private String reviewState;//����״̬
	
	private String reviewFlag;//�Ƿ�ɽ��л������� 1:���ԣ�0:������
	
	private String pin;//�����ļ����
	
	private String docName;//�����ļ�����
	
	private String version;//�汾
	
	private String state;//״̬
	
	private String scurityLevel;//�ܼ�
	
	private String phaseName;//�׶�
	
	private String phaseId;
	
    private String professionID;//רҵ
	
	private String professionName;
	
	private String dataUrl;//
	
	private String createUserIId;
	
	private String createUserName;//���ʦ
	
	private long createTime;
	
	private String productIID;
	
	private String productId;
	
	private String productName;
	
	private String sourceSiteInnerId;//��Դ���ĸ�վ��
	
	/** ������������Ϣ  */
	private ContextInfo contextInfo;
	
	private FolderInfo folderInfo;
	
	private DomainInfo domainInfo;

	public ReviewObject(){
		setClassId(ReviewObject.class.getSimpleName());
	}


	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getScurityLevel() {
		return scurityLevel;
	}

	public void setScurityLevel(String scurityLevel) {
		this.scurityLevel = scurityLevel;
	}

	public String getPhaseName() {
		return phaseName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}

	public String getCreateUserIId() {
		return createUserIId;
	}

	public void setCreateUserIId(String createUserIId) {
		this.createUserIId = createUserIId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSourceSiteInnerId() {
		return sourceSiteInnerId;
	}

	public void setSourceSiteInnerId(String sourceSiteInnerId) {
		this.sourceSiteInnerId = sourceSiteInnerId;
	}

	public ContextInfo getContextInfo() {
		return contextInfo;
	}

	public void setContextInfo(ContextInfo contextInfo) {
		this.contextInfo = contextInfo;
	}

	public String getProductIID() {
		return productIID;
	}

	public void setProductIID(String productIID) {
		this.productIID = productIID;
	}


	public void setFolderInfo(FolderInfo info) {
		this.folderInfo = info;
		
	}

	public FolderInfo getFolderInfo() {
		return this.folderInfo;
	}

	public DomainInfo getDomainInfo() {
		return domainInfo;
	}
	
	public void setDomainInfo(DomainInfo domainInfo) {
		this.domainInfo = domainInfo;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getPin() {
		return pin;
	}


	public void setPin(String pin) {
		this.pin = pin;
	}


	public String getDataUrl() {
		return dataUrl;
	}


	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}
	
	public String getReviewState() {
		return reviewState;
	}


	public void setReviewState(String reviewState) {
		this.reviewState = reviewState;
	}

	public String getReviewFlag() {
		return reviewFlag;
	}


	public void setReviewFlag(String reviewFlag) {
		this.reviewFlag = reviewFlag;
	}


	public String getPhaseId() {
		return phaseId;
	}


	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
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
}
