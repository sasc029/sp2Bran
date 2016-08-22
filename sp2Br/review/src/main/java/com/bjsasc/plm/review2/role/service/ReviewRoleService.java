package com.bjsasc.plm.review2.role.service;

import java.util.List;

import com.bjsasc.plm.review2.role.model.ReviewRole;


public interface ReviewRoleService {
	/**
	 * 创建评审管理对象
	 * @param roless
	 * @author wangdong
	 */
	public ReviewRole createReviewRole(String roleId, String roleName, String roleDescription);
	
	/**
	 * 保存评审管理对象
	 * @param roles
	 * @author wangdong
	 */
	public void saveReviewRoleList(List<ReviewRole> roles);
	
	/**
	 * 获取所有函审角色列表
	 * @return
	 * @author wangdong
	 */
	public List<ReviewRole> getAllRoleList();
	
	/**
	 * 通过RoleId来获取函审角色
	 * @param roleId
	 * @return
	 * @author wangdong
	 */
	public ReviewRole getReviewRoleByRoleId(String roleId);
	
	/**
	 * 获取初始化列表，从xml文件中
	 * @return
	 * @author wangdong
	 */
	public List<ReviewRole> getInitRoleList();
	
	/**
	 * 判断是否已经存在编号为roleId的角色
	 * @param roleId
	 * @return
	 * @author wangdong
	 */
	public boolean checkReviewRoleId(String roleId);
	
	/**
	 * 保存函审角色
	 * @param role
	 * @author wangdong
	 */
	public void saveReviewRole(ReviewRole role);
	
	/**
	 * 删除函审角色
	 * @param role
	 * @author wangdong
	 */
	public void deleteReviewRole(ReviewRole role);
	
	/**
	 * 通过innerId来获取函审角色
	 * @param innerId
	 * @return
	 * @author wangdong
	 */
	public ReviewRole getReviewRoleByInnerId(String innerId);
	
	/**
	 * 通过OID获取函审角色
	 * @param oid
	 * @return
	 * @author wangdong
	 */
	public ReviewRole getReviewRoleByOid(String oid);
	/**
	 * 修改函审角色
	 * @param role
	 * @author wangdong
	 */
	public void updateReviewRole(ReviewRole role);
	
	/**
	 * 批量删除函审角色
	 * @param ids
	 * @author wangdong
	 */
	public void deleteReviewRoles(String[] ids);
}
