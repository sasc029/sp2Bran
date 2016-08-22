package com.bjsasc.plm.review2.object.service;

import com.bjsasc.plm.core.util.SpringUtil;

public class ReviewObjectHelper {

	private static ReviewObjectService service = null;
	
	public static ReviewObjectService getService() {
		if (service == null) {
			service = (ReviewObjectService)SpringUtil.getBean("plm_review_object");
		}
		return service;
	}
}
