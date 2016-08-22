package com.bjsasc.plm.review2.object.service;

import com.bjsasc.plm.core.util.SpringUtil;

public class ReviewObjectContextHelper {
	private static ReviewObjectContextService service = null;
	public static ReviewObjectContextService getService() {
		if (service == null) {
			service = (ReviewObjectContextService)SpringUtil.getBean("plm_review_objectContext");
		}
		return service;
	}
}
