package com.bjsasc.plm.review2.link.service;

import com.bjsasc.plm.core.util.SpringUtil;

public class ReviewMemberHelper {
	private static ReviewMemberService service = null;
	public static ReviewMemberService getService() {
		if (service == null) {
			service = (ReviewMemberService)SpringUtil.getBean("plm_review_member");
		}
		return service;
	}
}
