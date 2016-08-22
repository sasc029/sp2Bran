package com.bjsasc.plm.review2.distribute.service;

import java.util.List;

import com.bjsasc.plm.review2.distribute.model.ReviewDistribute;


public interface ReviewDistributeService {
	/**
	 * 根据评审单innerId获取会议评审分发信息列表
	 * @return
	 * @author 
	 */
	public List<ReviewDistribute> getRvDistributeListByOrderId(String orderId);
	
	/**
	 * 根据InnerId获取对象
	 * @return
	 * @author 
	 */
	public ReviewDistribute getRvDistributeByInnerId(String innerId);
	
	/**
	 * 删除对象
	 * @return
	 * @author 
	 */
	public void delReviewDistribute(String innerId);
	
}
