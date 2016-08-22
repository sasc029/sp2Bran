package com.bjsasc.plm.review2.role.service;

import com.bjsasc.plm.core.util.SpringUtil;


public class ReviewRoleHelper {
	private static ReviewRoleService reviewRoleService;

	public static ReviewRoleService getReviewRoleService() {
		if (reviewRoleService == null) {
			reviewRoleService = (ReviewRoleService) SpringUtil.getBean("plm_rv_reviewroleservice");
		}
		return reviewRoleService;
	}
}
