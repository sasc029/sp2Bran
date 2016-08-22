package com.bjsasc.plm.review2.role.service;

import com.bjsasc.plm.core.util.SpringUtil;

public class ReviewRoleUserHelper {
	private static ReviewRoleUserService reviewRoleUserService;

	public static ReviewRoleUserService getReviewRoleService() {
		if (reviewRoleUserService == null) {
			reviewRoleUserService = (ReviewRoleUserService) SpringUtil.getBean("plm_rv_reviewroleuserservice");
		}
		return reviewRoleUserService;
	}
}
