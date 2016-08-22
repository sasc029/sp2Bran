package com.bjsasc.plm.review2.role.service;

import java.util.List;

import com.bjsasc.plm.review2.role.model.ReviewRole;


public interface ReviewRoleService {
	/**
	 * ��������������
	 * @param roless
	 * @author wangdong
	 */
	public ReviewRole createReviewRole(String roleId, String roleName, String roleDescription);
	
	/**
	 * ��������������
	 * @param roles
	 * @author wangdong
	 */
	public void saveReviewRoleList(List<ReviewRole> roles);
	
	/**
	 * ��ȡ���к����ɫ�б�
	 * @return
	 * @author wangdong
	 */
	public List<ReviewRole> getAllRoleList();
	
	/**
	 * ͨ��RoleId����ȡ�����ɫ
	 * @param roleId
	 * @return
	 * @author wangdong
	 */
	public ReviewRole getReviewRoleByRoleId(String roleId);
	
	/**
	 * ��ȡ��ʼ���б���xml�ļ���
	 * @return
	 * @author wangdong
	 */
	public List<ReviewRole> getInitRoleList();
	
	/**
	 * �ж��Ƿ��Ѿ����ڱ��ΪroleId�Ľ�ɫ
	 * @param roleId
	 * @return
	 * @author wangdong
	 */
	public boolean checkReviewRoleId(String roleId);
	
	/**
	 * ���溯���ɫ
	 * @param role
	 * @author wangdong
	 */
	public void saveReviewRole(ReviewRole role);
	
	/**
	 * ɾ�������ɫ
	 * @param role
	 * @author wangdong
	 */
	public void deleteReviewRole(ReviewRole role);
	
	/**
	 * ͨ��innerId����ȡ�����ɫ
	 * @param innerId
	 * @return
	 * @author wangdong
	 */
	public ReviewRole getReviewRoleByInnerId(String innerId);
	
	/**
	 * ͨ��OID��ȡ�����ɫ
	 * @param oid
	 * @return
	 * @author wangdong
	 */
	public ReviewRole getReviewRoleByOid(String oid);
	/**
	 * �޸ĺ����ɫ
	 * @param role
	 * @author wangdong
	 */
	public void updateReviewRole(ReviewRole role);
	
	/**
	 * ����ɾ�������ɫ
	 * @param ids
	 * @author wangdong
	 */
	public void deleteReviewRoles(String[] ids);
}
