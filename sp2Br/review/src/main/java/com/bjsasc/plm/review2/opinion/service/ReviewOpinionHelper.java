package com.bjsasc.plm.review2.opinion.service;

import com.bjsasc.plm.core.util.SpringUtil;

public class ReviewOpinionHelper {
	private static ReviewOpinionService service = null;
	public static ReviewOpinionService getService() {
		if (service == null) {
			service = (ReviewOpinionService)SpringUtil.getBean("plm_rv_opinion");
		}
		return service;
	}
}
