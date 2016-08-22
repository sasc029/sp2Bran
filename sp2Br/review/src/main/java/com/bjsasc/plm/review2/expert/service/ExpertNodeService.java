package com.bjsasc.plm.review2.expert.service;

import java.util.List;

import com.bjsasc.plm.review2.expert.model.ExpertNode;


public interface ExpertNodeService {
	/** 
	 * 创建专家分类节点
	 * @param  分类节点对象
	 * @return
	 */
	public ExpertNode createNode(ExpertNode en);
	
	/**
	 * 根据父节点ID查找子节点列表
	 * @param parentNodeId
	 * @param userInnerId
	 * @return
	 */
	public List<ExpertNode> getChildNodeList(String parentNodeId);
	
	/**
	 * 根据站点ID和节点类型查找节点列表
	 * @param siteInnerId
	 * @param nodeType
	 * @return
	 */
	public List<ExpertNode> getNodeList(String siteInnerId,String nodeType);
	/**
	 * 根据父节点ID和用户Id查找子节点列表
	 * @param parentNodeId
	 * @param userInnerId
	 * @return
	 */
	public List<ExpertNode> getChildNodeList(String parentNodeId, String userInnerId);
	/**
	 * 根据父节点ID和站点Id查找子节点列表
	 * @param parentNodeId
	 * @param siteInnerId
	 * @return
	 */
	public List<ExpertNode> getChildNodeListBySiteInnerId(String parentNodeId, String siteInnerId);
	/**
	 * 检查同一父节点下子节点名称是否重复
	 * @param name
	 * @param parentID
	 * @return
	 */
	public int checkNodeName(String name, String parentID);
	
	/**
	 * 更新对象
	 * @param 
	 */
	public void update(ExpertNode en);
	
	
	/**
	 * 创建一个分类节点实例
	 * @return
	 */
	public ExpertNode newExpertNode();
	
	/**
	 * 通过用户标识获取根分类
	 * @param userInnerId
	 * @return
	 */
	public ExpertNode getRootExpertNode(String userInnerId,String rootType);
	
}
