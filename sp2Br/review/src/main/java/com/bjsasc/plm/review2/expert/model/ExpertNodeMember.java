package com.bjsasc.plm.review2.expert.model;

import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;
import com.bjsasc.plm.core.type.ATObject;

public class ExpertNodeMember extends ATObject implements Manageable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1232009346955195757L;

	private ObjectReference nodeRef;
	

	private ObjectReference expertRef;
	
	private ManageInfo manageInfo;
	
	private String  isCollect;
	
	
	public ExpertNodeMember(){
		setClassId(ExpertNodeMember.class.getSimpleName());
	}
	public ObjectReference getNodeRef() {
		return nodeRef;
	}

	public void setNodeRef(ObjectReference nodeRef) {
		this.nodeRef = nodeRef;
	}

	
	public ExpertNode getNode() {
		if (nodeRef != null) {
			return (ExpertNode) nodeRef.getObject();
		}
		return null;
	}

	public void setNode(ExpertNode node) {
		setNodeRef(node != null ? ObjectReference.newObjectReference(node) : null);
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
	
	public ManageInfo getManageInfo() {
		return manageInfo;
	}

	public void setManageInfo(ManageInfo manageInfo) {
		this.manageInfo = manageInfo;
	}
	public String getIsCollect() {
		return isCollect;
	}
	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}
	
    	
}
