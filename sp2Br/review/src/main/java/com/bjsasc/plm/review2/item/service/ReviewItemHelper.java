package com.bjsasc.plm.review2.item.service;

import com.bjsasc.plm.core.util.SpringUtil;

public class ReviewItemHelper {
	private static ReviewItemService service = null;
	public static ReviewItemService getService() {
		if (service == null) {
			service = (ReviewItemService)SpringUtil.getBean("plm_review_reviewItem");
		}
		return service;
	}
}
