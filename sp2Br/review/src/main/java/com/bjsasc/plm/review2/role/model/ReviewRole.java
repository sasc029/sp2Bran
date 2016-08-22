package com.bjsasc.plm.review2.role.model;

import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;
import com.bjsasc.plm.core.type.ATObject;

/**
 * �����ɫ����
 * @author YHJ
 *
 */
public class ReviewRole extends ATObject implements Manageable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7486130669981212269L;
	
	public static final String CLASSID = ReviewRole.class.getSimpleName();
	
	/**
	 * ��ɫ���
	 */
	private String roleID;
	
	/**
	 * ��ɫ����
	 */
	private String roleName;
	
	/**
	 * Ȩ������
	 */
	private String roleDescription;
	
	/**
	 * �������
	 */
	private ManageInfo manageInfo;
	
	public ReviewRole(){
		setClassId(CLASSID);
	}
	
	

	public String getRoleID() {
		return roleID;
	}



	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}



	public String getRoleName() {
		return roleName;
	}



	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}



	public String getRoleDescription() {
		return roleDescription;
	}



	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}



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
	

}
