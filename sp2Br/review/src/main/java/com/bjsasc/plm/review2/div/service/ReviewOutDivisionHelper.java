package com.bjsasc.plm.review2.div.service;

import com.bjsasc.plm.core.util.SpringUtil;


public class ReviewOutDivisionHelper {
	private static ReviewOutDivisionService reviewOutDivisionService;

	public static ReviewOutDivisionService getReviewOutDivisionService() {
		if (reviewOutDivisionService == null) {
			reviewOutDivisionService = (ReviewOutDivisionService) SpringUtil.getBean("plm_rv_reviewOutDivService");
		}
		return reviewOutDivisionService;
	}
}
