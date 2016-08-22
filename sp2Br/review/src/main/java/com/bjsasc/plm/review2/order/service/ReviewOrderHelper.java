package com.bjsasc.plm.review2.order.service;

import com.bjsasc.plm.core.util.SpringUtil;

public class ReviewOrderHelper {
	private static ReviewOrderService service = null;
	public static ReviewOrderService getService() {
		if (service == null) {
			service = (ReviewOrderService)SpringUtil.getBean("plm_review_order");
		}
		return service;
	}
}
