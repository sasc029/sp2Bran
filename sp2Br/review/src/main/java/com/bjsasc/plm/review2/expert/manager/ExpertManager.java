package com.bjsasc.plm.review2.expert.manager;

import java.util.List;

import com.bjsasc.plm.review2.expert.model.ExpertNode;
import com.bjsasc.plm.review2.expert.model.ReviewExpert;


/**
 * ҳ����������ӿ�
 *
 */
public interface ExpertManager {
	
	/**
	 * ��ȡ������
	 * @return
	 */
//	public ExpertNode getRootNode();
	
	/**
	 * ���ݸ�ID��ȡ�ӷ����б�
	 * @param parentID
	 * @return
	 */
	public List<ExpertNode> getChildNodeList(String parentID,String siteInnerId);
	
	public List<ExpertNode> getPersonalChildNodeList(String parentID,String siteInnerId);
	/**
	 * ɾ������ڵ�
	 * @param innerId
	 * @return
	 * @throws
	 */
	public int deleteNode(String innerId);
	
	/**
	 * ͨ��innerID��classId��ȡ����
	 * @param innerID
	 * @param classID
	 * @return
	 */
	public ExpertNode get(String innerID, String classID);
	
	/** 
	 * ��������ڵ�
	 * @param parentID 
	 * @param name 
	 * @return
	 */
	public ExpertNode createNode(String parentID, String name,String siteInnerId,String nodeType);
	
	/**
	 * �ж�ͬ��Ŀ¼�·��������Ƿ��ظ�
	 * @param parentID 
	 * @param name 
	 * @return
	 */
	public int getNodeNameJson(String parentID, String name);
	
	/**
	 * ���¶���
	 * @param name
	 * @param innerID
	 * @throws
	 */
	public void update(String name, String innerID);

	/**
	 * ��ȡ������ר���б�
	 * @param innerID
	 * @return
	 * @throws
	 */
	public String getExpertJson(String innerID);
	
	/**
	 * ��ȡ�����¹���ר���б�
	 * @param innerID
	 * @return
	 * @throws
	 */
	public String getCommonExpertJson(String innerID);
	
	/**
	 * ��ȡ����ר���б�
	 * @return
	 * @throws
	 */
	public String getAllExpertJson();
	
	/**
	 * ��ȡ��ǰ�û���ר���б�
	 * @return
	 * @throws
	 */
	public String getExpertByUserJson(String siteInnerId);
	
	/**
	 * ��ȡ��ǰ�û�������ר���б�
	 * @return
	 * @throws
	 */
	public String getDomainExpertByUserJson();
	
	/**
	 * ������ƶ�ר��
	 * @param innerId
	 * @param nodeInnerId
	 * @throws
	 */
	public void moveExpert(String innerId,String nodeInnerId);
	
	/**
	 * �������Ƴ�ר��--���ר��ѡ��
	 * @param data
	 * @return
	 * @throws
	 */
	public String deleteExpertNodeMember(String data);
	/**
	 * ������ɾ��ר��--���ר��ѡ��
	 * @param data
	 * @return
	 * @throws
	 */
	public String deleteExpertTypeMember(String data);
	
	/**
	 * ���������ר��
	 * @param data
	 * @return
	 * @throws
	 */
	public String addExpertNodeMember(String innerId,String expertJson);
	
	/**
	 * ����ר�ҷ��������ר��
	 * @param data
	 * @return
	 * @throws
	 */
	public String addExpertNodeMember(String innerId,ReviewExpert expert);
	
	/**
	 * �������Ƿ��д�ר�Ҵ���
	 * @param data
	 * @return
	 * @throws
	 */
	public int getCheckExpertCount(String expertJson,String siteInnerId);
}
