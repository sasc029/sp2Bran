package com.bjsasc.plm.review2.expert.service;

import com.bjsasc.plm.core.util.SpringUtil;

/**
 * 评审专家管理 Helper
 */
public class ReviewExpertHelper {
	
	private static ReviewExpertService service = null;
	
	public static ReviewExpertService getService() {
		if (service == null) {
			service = (ReviewExpertService)SpringUtil.getBean("plm_review_expert");
		}
		return service;
	}
}
