package com.bjsasc.plm.review2.expert.model;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.core.type.ATObject;
import com.bjsasc.plm.core.system.principal.User;

public class ExpertNode extends ATObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6909048203932202881L;
	private long modifyStamp;
    private String name;
    private long createStamp;
    private ObjectReference creatorRef;
    private ObjectReference modifierRef;
    private String parentID;
    private String nodeType;
    private ObjectReference siteRef;
    
    public ExpertNode(){
    	setClassId(ExpertNode.class.getSimpleName());
    }
    
    public void setModifyStamp(long modifyStamp){
        this.modifyStamp = modifyStamp;
    }
    public long getModifyStamp(){
        return modifyStamp;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setCreateStamp(long createStamp){
        this.createStamp = createStamp;
    }
    public long getCreateStamp(){
        return createStamp;
    }
    public void setCreatorRef(ObjectReference creatorRef){
        this.creatorRef = creatorRef;
    }
    public ObjectReference getCreatorRef(){
        return creatorRef;
    }
    public void setCreator(User creator){
        setCreatorRef(creator!= null ? ObjectReference.newObjectReference(creator) : null);
    }
    public User getCreator(){
        if( creatorRef != null){
            return (User)creatorRef.getObject();
        }
        return null;
    }
    public void setModifierRef(ObjectReference modifierRef){
        this.modifierRef = modifierRef;
    }
    public ObjectReference getModifierRef(){
        return modifierRef;
    }
    public void setModifier(User modifier){
        setModifierRef(modifier!= null ? ObjectReference.newObjectReference(modifier) : null);
    }
    public User getModifier(){
        if( modifierRef != null){
            return (User)modifierRef.getObject();
        }
        return null;
    }
    public void setParentID(String parentID){
        this.parentID = parentID;
    }
    public String getParentID(){
        return parentID;
    }

	public ObjectReference getSiteRef() {
		return siteRef;
	}

	public void setSiteRef(ObjectReference siteRef) {
		this.siteRef = siteRef;
	}
	
	public Site getSite() {
		if (siteRef != null) {
			return (Site) siteRef.getObject();
		}
		return null;
		
	}
	public void setSite(Site site) {
		setSiteRef(site != null ? ObjectReference.newObjectReference(site) : null);
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
}