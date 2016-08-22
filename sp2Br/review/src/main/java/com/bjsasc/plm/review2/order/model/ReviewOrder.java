package com.bjsasc.plm.review2.order.model;

import com.bjsasc.platform.filecomponent.model.PtFileItemBean;
import com.bjsasc.platform.objectmodel.business.lifeCycle.LifeCycleInfo;
import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.core.approve.Approved;
import com.bjsasc.plm.core.attachment.AttachHelper;
import com.bjsasc.plm.core.attachment.FileHolder;
import com.bjsasc.plm.core.context.model.ContextInfo;
import com.bjsasc.plm.core.context.model.Contexted;
import com.bjsasc.plm.core.context.template.FileTemplate;
import com.bjsasc.plm.core.domain.DomainInfo;
import com.bjsasc.plm.core.domain.Domained;
import com.bjsasc.plm.core.folder.FolderInfo;
import com.bjsasc.plm.core.folder.Foldered;
import com.bjsasc.plm.core.identifier.UniqueIdentified;
import com.bjsasc.plm.core.lifecycle.LifeCycleManaged;
import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;
import com.bjsasc.plm.core.type.ATObject;
import com.bjsasc.plm.sign.Signable;

/**
 * 评审单对象
 * @author YHJ
 *
 */
public class ReviewOrder extends ATObject implements Contexted, Domained,Manageable,UniqueIdentified,Foldered,LifeCycleManaged,Approved, FileHolder, Signable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1493276371652360912L;
	
	public static final String CLASSID = ReviewOrder.class.getSimpleName();
	
	
	private ContextInfo contextInfo;
	private DomainInfo domainInfo;
	private String number;	
	private String name;
	private ManageInfo manageInfo;
	private FolderInfo folderInfo;
	private String reviewType;
	private String reviewScope;
	private long reviewTime;
	private long startTime;
	private long finishTime;
	private String reviewPlace;
	private String state;
	private String notes;
	private ObjectReference templateRef;//文件模板引用
	private PtFileItemBean file = null;

	private String reviewSource; //缺陷来源
	
	private LifeCycleInfo lifeCycleInfo;
	
	public String getReviewSource() {
		return reviewSource;
	}

	public void setReviewSource(String reviewSource) {
		this.reviewSource = reviewSource;
	}

	public ReviewOrder(){
		setClassId(CLASSID);
	}

	public void setManageInfo(ManageInfo manageInfo) {
		this.manageInfo = manageInfo;
	}

	public ManageInfo getManageInfo() {
		return this.manageInfo;
	}


	public ObjectReference getTemplateRef() {
		return templateRef;
	}

	public void setTemplateRef(ObjectReference templateRef) {
		this.templateRef = templateRef;
	}
	
	/**
	 * 获取FileTemplate对象
	 */
	public FileTemplate getTemplate() {
		if (templateRef != null) {
			return (FileTemplate) templateRef.getObject();
		}
		return null;
	}

	/**
	 * 设置FileTemplate对象 
	 */
	public void setTemplate(FileTemplate template) {
		setTemplateRef(template != null ? ObjectReference.newObjectReference(template) : null);
	}
	

	public long getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(long reviewTime) {
		this.reviewTime = reviewTime;
	}

	public String getReviewPlace() {
		return reviewPlace;
	}

	public void setReviewPlace(String reviewPlace) {
		this.reviewPlace = reviewPlace;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setFolderInfo(FolderInfo info) {
		this.folderInfo = info;
		
	}

	public FolderInfo getFolderInfo() {
		
		return this.folderInfo;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public DomainInfo getDomainInfo() {
		return domainInfo;
	}
	public void setDomainInfo(DomainInfo domainInfo) {
		this.domainInfo = domainInfo;
		
	}
	public void setContextInfo(ContextInfo contextInfo) {
		this.contextInfo = contextInfo;
		
	}
	public ContextInfo getContextInfo() {
		return contextInfo;
	}

	public String getReviewType() {
		return reviewType;
	}

	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}

	public String getReviewScope() {
		return reviewScope;
	}

	public void setReviewScope(String reviewScope) {
		this.reviewScope = reviewScope;
	}

	public LifeCycleInfo getLifeCycleInfo() {
		
		return this.lifeCycleInfo;
	}

	public void setLifeCycleInfo(LifeCycleInfo info) {
		
		this.lifeCycleInfo = info;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	public void setFile(PtFileItemBean file){
		this.file = file;
	}
	
	public PtFileItemBean getFile(){
		if(file == null){
			file = AttachHelper.getAttachService().getMainFile(this); 
		}
		return file;
	}
	
}
