package com.bjsasc.plm.review2.conclusion.model;

import com.bjsasc.plm.core.type.ATObject;
import com.bjsasc.plm.core.identifier.UniqueIdentified;
import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;

/**
 * ∆¿…ÛΩ·¬€ƒ£∞Â
 * @author YHJ
 *
 */
public class ConclusionTemplateHS extends ATObject implements UniqueIdentified,Manageable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 300231871890335198L;
	/**
	 * 
	 */
	public static final String CLASSID = ConclusionTemplateHS.class.getSimpleName();
	
	public ConclusionTemplateHS(){
		setClassId(CLASSID);
	}
	
	
	private String number;	
	private String name;
	private String contents;
	private ManageInfo manageInfo;
		
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ManageInfo getManageInfo() {
		return manageInfo;
	}
	public void setManageInfo(ManageInfo manageInfo) {
		this.manageInfo = manageInfo;
	}

}
