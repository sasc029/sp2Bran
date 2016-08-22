package com.bjsasc.plm.review2.conclusion.service;

import com.bjsasc.plm.core.util.SpringUtil;

public class ReviewConclusionHelper {
	private static ReviewConclusionService service = null;
	public static ReviewConclusionService getService() {
		if (service == null) {
			service = (ReviewConclusionService)SpringUtil.getBean("plm_review_reviewConclusion");
		}
		return service;
	}
}
