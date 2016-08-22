package com.bjsasc.plm.review2.distribute.service;

import com.bjsasc.plm.core.util.SpringUtil;


public class ReviewDistributeHelper {
	private static ReviewDistributeService reviewDistributeService;

	public static ReviewDistributeService getReviewDistributeService() {
		if (reviewDistributeService == null) {
			reviewDistributeService = (ReviewDistributeService) SpringUtil.getBean("plm_rv_reviewDistributeService");
		}
		return reviewDistributeService;
	}
}
