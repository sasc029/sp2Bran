package com.bjsasc.plm.review2.distribute.service;

import java.util.List;

import com.bjsasc.plm.review2.distribute.model.ReviewDistribute;


public interface ReviewDistributeService {
	/**
	 * ��������innerId��ȡ��������ַ���Ϣ�б�
	 * @return
	 * @author 
	 */
	public List<ReviewDistribute> getRvDistributeListByOrderId(String orderId);
	
	/**
	 * ����InnerId��ȡ����
	 * @return
	 * @author 
	 */
	public ReviewDistribute getRvDistributeByInnerId(String innerId);
	
	/**
	 * ɾ������
	 * @return
	 * @author 
	 */
	public void delReviewDistribute(String innerId);
	
}
