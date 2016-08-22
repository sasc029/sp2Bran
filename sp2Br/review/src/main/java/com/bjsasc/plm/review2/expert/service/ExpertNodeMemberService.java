package com.bjsasc.plm.review2.expert.service;

import java.util.List;
import java.util.Map;

import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.review2.expert.model.ExpertNode;
import com.bjsasc.plm.review2.expert.model.ExpertNodeMember;
import com.bjsasc.plm.review2.expert.model.ReviewExpert;

public interface ExpertNodeMemberService {
	/**
	 * ���������ʵ��
	 * @return
	 */
	public ExpertNodeMember newExpertNodeMember();
	
	/**
	 * ��ȡ��ǰ�û�������ר��
	 * @return
	 */
	public List<ExpertNodeMember> getExpertNodeMemberList(User user);
		
	/**
	 * ����ר�ҷ����Ա
	 * @return
	 */
	public ExpertNodeMember createExpertNodeMember(ExpertNodeMember expert, ExpertNode expertNode);
	
	/**
	 * ���ݷ����ʶ����ר���б�
	 * @param innerID
	 * @return
	 */
	public List<Map<String, String>> getExpertNodeMemberList(String innerID);
	
	/**
	 * ��������ר���б�
	 * @param innerID
	 * @return
	 */
	public List<ReviewExpert> getAllExpertNodeMemberList();
	
	/**
	 * ��������ר���б�
	 * @param innerID
	 * @return
	 */
	public List<ExpertNodeMember> getAllExpertNodeMemberListByExpertRef(String innerid);
	
	/**
	 * ͨ��ר�ұ�ʶ��ר�����ʶ��ȡ����
	 * @param innerId
	 * @param classId
	 * @return
	 */
	public ExpertNodeMember getExpertNodeMember(String innerId, String classId);
	
	/**
	 * �����û����Ƿ��д�ר�Ҵ���
	 * @param expertJson
	 * @param userInnerId
	 * @return
	 */
	public int checkExpertNodeMember(String expertJson, String siteInnerId);
	
	/**
	 * �ղ�ר��
	 * @param InnerId
	 * @return
	 */
	public void collectOrRemoveExpert( String InnerId,String flag);
	/**
	 * ��ѯ��ǰ�û����ղص�ר��
	 * @param InnerId
	 * @return
	 */
	public  List<Map<String, String>> getExpertNodeMemberListAsCollect();
}
