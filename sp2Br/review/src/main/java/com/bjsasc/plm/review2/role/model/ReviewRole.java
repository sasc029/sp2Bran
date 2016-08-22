package com.bjsasc.plm.review2.role.model;

import com.bjsasc.plm.core.managed.model.ManageInfo;
import com.bjsasc.plm.core.managed.model.Manageable;
import com.bjsasc.plm.core.type.ATObject;

/**
 * 评审角色管理
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
	 * 角色编号
	 */
	private String roleID;
	
	/**
	 * 角色名称
	 */
	private String roleName;
	
	/**
	 * 权限描述
	 */
	private String roleDescription;
	
	/**
	 * 组合属性
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
	 * 设置组合属性
	 */
	public void setManageInfo(ManageInfo manageInfo) {
		this.manageInfo = manageInfo;

	}
	
	/**
	 * 获取组合属性
	 */
	public ManageInfo getManageInfo() {
		return this.manageInfo;
	}
	

}
