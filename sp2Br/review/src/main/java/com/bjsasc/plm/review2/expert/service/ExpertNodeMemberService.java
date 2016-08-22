package com.bjsasc.plm.review2.expert.service;

import java.util.List;
import java.util.Map;

import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.review2.expert.model.ExpertNode;
import com.bjsasc.plm.review2.expert.model.ExpertNodeMember;
import com.bjsasc.plm.review2.expert.model.ReviewExpert;

public interface ExpertNodeMemberService {
	/**
	 * 创建对象的实例
	 * @return
	 */
	public ExpertNodeMember newExpertNodeMember();
	
	/**
	 * 获取当前用户的所有专家
	 * @return
	 */
	public List<ExpertNodeMember> getExpertNodeMemberList(User user);
		
	/**
	 * 创建专家分类成员
	 * @return
	 */
	public ExpertNodeMember createExpertNodeMember(ExpertNodeMember expert, ExpertNode expertNode);
	
	/**
	 * 根据分类标识查找专家列表
	 * @param innerID
	 * @return
	 */
	public List<Map<String, String>> getExpertNodeMemberList(String innerID);
	
	/**
	 * 查找所有专家列表
	 * @param innerID
	 * @return
	 */
	public List<ReviewExpert> getAllExpertNodeMemberList();
	
	/**
	 * 查找所有专家列表
	 * @param innerID
	 * @return
	 */
	public List<ExpertNodeMember> getAllExpertNodeMemberListByExpertRef(String innerid);
	
	/**
	 * 通过专家标识和专家类标识获取对象
	 * @param innerId
	 * @param classId
	 * @return
	 */
	public ExpertNodeMember getExpertNodeMember(String innerId, String classId);
	
	/**
	 * 检查该用户下是否有此专家存在
	 * @param expertJson
	 * @param userInnerId
	 * @return
	 */
	public int checkExpertNodeMember(String expertJson, String siteInnerId);
	
	/**
	 * 收藏专家
	 * @param InnerId
	 * @return
	 */
	public void collectOrRemoveExpert( String InnerId,String flag);
	/**
	 * 查询当前用户已收藏的专家
	 * @param InnerId
	 * @return
	 */
	public  List<Map<String, String>> getExpertNodeMemberListAsCollect();
}
