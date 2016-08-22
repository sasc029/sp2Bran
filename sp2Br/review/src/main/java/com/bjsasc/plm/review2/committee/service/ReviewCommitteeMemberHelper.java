package com.bjsasc.plm.review2.committee.service;

import com.bjsasc.plm.core.util.SpringUtil;

public class ReviewCommitteeMemberHelper {
	private static ReviewCommitteeMemberService memberService;

	public static ReviewCommitteeMemberService getMemberService() {
		if (memberService == null) {
			memberService = (ReviewCommitteeMemberService) SpringUtil.getBean("plm_rv_reviewcommitteememberservice");
		}
		return memberService;
	}
}
