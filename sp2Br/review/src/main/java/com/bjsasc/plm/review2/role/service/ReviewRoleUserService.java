package com.bjsasc.plm.review2.role.service;

import java.util.List;

import com.bjsasc.plm.review2.role.model.ReviewRoleUser;


public interface ReviewRoleUserService {
	
	/**
	 * ��Ӻ����ɫ�û�
	 * @param roleId
	 * @param arrayUserIds
	 * @param arrayUserNames
	 * @author wangdong
	 */
	public void addReviewRoleUsers(String roleId,String siteId, String[] arrayUserIds, String[] arrayUserNames);
	
	/**
	 * ͨ����ɫinnerId��ȡ��Ӧ���û��б�
	 * @param roleId
	 * @return
	 * @author wangdong
	 */
	public List<ReviewRoleUser> getReviewRolesUsers(String roleId,String sourceSiteInnerId);
	
	/**
	 * ɾ�������ɫ�û�
	 * @param user
	 * @author wangdong
	 */
	public void deleteReviewRoleUser(ReviewRoleUser user);
	
	/**
	 * ����ɾ�������ɫ�û�
	 * @param ids
	 * @author wangdong
	 */
	public void deleteReviewRoleUsers(String[] ids);
	
	/**
	 * ͨ���û�innerId�Լ���ɫinnerId����ȡ�����ɫ�û�
	 * @param userId
	 * @param roleId
	 * @return
	 * @author wangdong
	 */
	public ReviewRoleUser getReviewRoleUserByUserIdAndRoleId(String userId, String roleId);
	
	/**
	 * �ж��Ƿ��Ѿ����ڸý�ɫ�û�
	 * @param userId
	 * @param roleId
	 * @return
	 * @author wangdong
	 */
	public boolean isExistReviewRoleUser(String userId, String roleId);
	
	/**
	 * ͨ����ɫid��ȡ��ɫ
	 * */
	public List<ReviewRoleUser> getAllReviewPrincipal(String reviewRoleId,String sourceSiteInnerId);
	
	public ReviewRoleUser getReviewRoleUserByInnerId(String innerId);
}
