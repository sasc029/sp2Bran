package com.bjsasc.plm.review2.role.service;

import java.util.List;

import com.bjsasc.plm.review2.role.model.ReviewRoleUser;


public interface ReviewRoleUserService {
	
	/**
	 * 添加函审角色用户
	 * @param roleId
	 * @param arrayUserIds
	 * @param arrayUserNames
	 * @author wangdong
	 */
	public void addReviewRoleUsers(String roleId,String siteId, String[] arrayUserIds, String[] arrayUserNames);
	
	/**
	 * 通过角色innerId获取相应的用户列表
	 * @param roleId
	 * @return
	 * @author wangdong
	 */
	public List<ReviewRoleUser> getReviewRolesUsers(String roleId,String sourceSiteInnerId);
	
	/**
	 * 删除函审角色用户
	 * @param user
	 * @author wangdong
	 */
	public void deleteReviewRoleUser(ReviewRoleUser user);
	
	/**
	 * 批量删除函审角色用户
	 * @param ids
	 * @author wangdong
	 */
	public void deleteReviewRoleUsers(String[] ids);
	
	/**
	 * 通过用户innerId以及角色innerId来获取函审角色用户
	 * @param userId
	 * @param roleId
	 * @return
	 * @author wangdong
	 */
	public ReviewRoleUser getReviewRoleUserByUserIdAndRoleId(String userId, String roleId);
	
	/**
	 * 判断是否已经存在该角色用户
	 * @param userId
	 * @param roleId
	 * @return
	 * @author wangdong
	 */
	public boolean isExistReviewRoleUser(String userId, String roleId);
	
	/**
	 * 通过角色id获取角色
	 * */
	public List<ReviewRoleUser> getAllReviewPrincipal(String reviewRoleId,String sourceSiteInnerId);
	
	public ReviewRoleUser getReviewRoleUserByInnerId(String innerId);
}
