package com.bjsasc.plm.review2.expert.manager;

import java.util.List;

import com.bjsasc.plm.review2.expert.model.ExpertNode;
import com.bjsasc.plm.review2.expert.model.ReviewExpert;


/**
 * 页面操作交互接口
 *
 */
public interface ExpertManager {
	
	/**
	 * 获取根分类
	 * @return
	 */
//	public ExpertNode getRootNode();
	
	/**
	 * 根据父ID获取子分类列表
	 * @param parentID
	 * @return
	 */
	public List<ExpertNode> getChildNodeList(String parentID,String siteInnerId);
	
	public List<ExpertNode> getPersonalChildNodeList(String parentID,String siteInnerId);
	/**
	 * 删除分类节点
	 * @param innerId
	 * @return
	 * @throws
	 */
	public int deleteNode(String innerId);
	
	/**
	 * 通过innerID和classId获取对象
	 * @param innerID
	 * @param classID
	 * @return
	 */
	public ExpertNode get(String innerID, String classID);
	
	/** 
	 * 创建分类节点
	 * @param parentID 
	 * @param name 
	 * @return
	 */
	public ExpertNode createNode(String parentID, String name,String siteInnerId,String nodeType);
	
	/**
	 * 判断同级目录下分类名称是否重复
	 * @param parentID 
	 * @param name 
	 * @return
	 */
	public int getNodeNameJson(String parentID, String name);
	
	/**
	 * 更新对象
	 * @param name
	 * @param innerID
	 * @throws
	 */
	public void update(String name, String innerID);

	/**
	 * 获取分类下专家列表
	 * @param innerID
	 * @return
	 * @throws
	 */
	public String getExpertJson(String innerID);
	
	/**
	 * 获取分类下公共专家列表
	 * @param innerID
	 * @return
	 * @throws
	 */
	public String getCommonExpertJson(String innerID);
	
	/**
	 * 获取所有专家列表
	 * @return
	 * @throws
	 */
	public String getAllExpertJson();
	
	/**
	 * 获取当前用户的专家列表
	 * @return
	 * @throws
	 */
	public String getExpertByUserJson(String siteInnerId);
	
	/**
	 * 获取当前用户的域内专家列表
	 * @return
	 * @throws
	 */
	public String getDomainExpertByUserJson();
	
	/**
	 * 分类间移动专家
	 * @param innerId
	 * @param nodeInnerId
	 * @throws
	 */
	public void moveExpert(String innerId,String nodeInnerId);
	
	/**
	 * 分类中移除专家--多个专家选择
	 * @param data
	 * @return
	 * @throws
	 */
	public String deleteExpertNodeMember(String data);
	/**
	 * 分类中删除专家--多个专家选择
	 * @param data
	 * @return
	 * @throws
	 */
	public String deleteExpertTypeMember(String data);
	
	/**
	 * 分类中添加专家
	 * @param data
	 * @return
	 * @throws
	 */
	public String addExpertNodeMember(String innerId,String expertJson);
	
	/**
	 * 公共专家分类中添加专家
	 * @param data
	 * @return
	 * @throws
	 */
	public String addExpertNodeMember(String innerId,ReviewExpert expert);
	
	/**
	 * 分类中是否有此专家存在
	 * @param data
	 * @return
	 * @throws
	 */
	public int getCheckExpertCount(String expertJson,String siteInnerId);
}
