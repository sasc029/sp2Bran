package com.bjsasc.plm.review2.object.model;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.core.context.model.ProductContext;
import com.bjsasc.plm.core.folder.Folder;
import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;
import com.bjsasc.plm.core.type.ATObject;
/**
 * @author YHJ
 *������������Ļ���������
 */
public class ReviewObjectContext extends ATObject implements Manageable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -78144126016655306L;

	/**
	 * �������
	 */
	private ManageInfo manageInfo;
	/**
	 * ��Ʒ��Ϣ
	 */
	private ObjectReference productRef;
	/**
	 * �����ļ���(Folder)
	 */
	private ObjectReference folderRef;
	/**
	 * Դվ��(Site)
	 */
	private ObjectReference sourceSiteRef;
	
	
	public ReviewObjectContext() {
		setClassId(ReviewObjectContext.class.getSimpleName());
	}
	
	
	public ObjectReference getFolderRef() {
		return folderRef;
	}

	public void setFolderRef(ObjectReference folderRef) {
		this.folderRef = folderRef;
	}

	public Folder getFolder() {
		if (folderRef != null) {
			return (Folder) folderRef.getObject();
		}
		return null;
	}

	public void setFolder(Folder folder) {
		setFolderRef(folder != null ? ObjectReference.newObjectReference(folder) : null);
	}
	
	public ManageInfo getManageInfo() {
		return manageInfo;
	}
	public void setManageInfo(ManageInfo manageInfo) {
		this.manageInfo = manageInfo;
	}
	
	public ObjectReference getProductRef() {
		return productRef;
	}
	public void setProductRef(ObjectReference productRef) {
		this.productRef = productRef;
	}
	public ObjectReference getSourceSiteRef() {
		return sourceSiteRef;
	}
	public void setSourceSiteRef(ObjectReference sourceSiteRef) {
		this.sourceSiteRef = sourceSiteRef;
	}
	
	

	public ProductContext getProduct() {
		if (productRef != null) {
			return (ProductContext) productRef.getObject();
		}
		return null;
	}
	public void setProduct(ProductContext product) {
		setProductRef(product != null ? ObjectReference.newObjectReference(product) : null);
	}
	public Site getSourceSite() {
		if (sourceSiteRef != null) {
			return (Site) sourceSiteRef.getObject();
		}
		return null;
		
	}
	public void setSourceSite(Site site) {
		setSourceSiteRef(site != null ? ObjectReference.newObjectReference(site) : null);
	}
}
