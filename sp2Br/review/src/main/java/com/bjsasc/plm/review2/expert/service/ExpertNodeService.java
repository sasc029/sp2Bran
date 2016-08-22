package com.bjsasc.plm.review2.expert.service;

import java.util.List;

import com.bjsasc.plm.review2.expert.model.ExpertNode;


public interface ExpertNodeService {
	/** 
	 * ����ר�ҷ���ڵ�
	 * @param  ����ڵ����
	 * @return
	 */
	public ExpertNode createNode(ExpertNode en);
	
	/**
	 * ���ݸ��ڵ�ID�����ӽڵ��б�
	 * @param parentNodeId
	 * @param userInnerId
	 * @return
	 */
	public List<ExpertNode> getChildNodeList(String parentNodeId);
	
	/**
	 * ����վ��ID�ͽڵ����Ͳ��ҽڵ��б�
	 * @param siteInnerId
	 * @param nodeType
	 * @return
	 */
	public List<ExpertNode> getNodeList(String siteInnerId,String nodeType);
	/**
	 * ���ݸ��ڵ�ID���û�Id�����ӽڵ��б�
	 * @param parentNodeId
	 * @param userInnerId
	 * @return
	 */
	public List<ExpertNode> getChildNodeList(String parentNodeId, String userInnerId);
	/**
	 * ���ݸ��ڵ�ID��վ��Id�����ӽڵ��б�
	 * @param parentNodeId
	 * @param siteInnerId
	 * @return
	 */
	public List<ExpertNode> getChildNodeListBySiteInnerId(String parentNodeId, String siteInnerId);
	/**
	 * ���ͬһ���ڵ����ӽڵ������Ƿ��ظ�
	 * @param name
	 * @param parentID
	 * @return
	 */
	public int checkNodeName(String name, String parentID);
	
	/**
	 * ���¶���
	 * @param 
	 */
	public void update(ExpertNode en);
	
	
	/**
	 * ����һ������ڵ�ʵ��
	 * @return
	 */
	public ExpertNode newExpertNode();
	
	/**
	 * ͨ���û���ʶ��ȡ������
	 * @param userInnerId
	 * @return
	 */
	public ExpertNode getRootExpertNode(String userInnerId,String rootType);
	
}
