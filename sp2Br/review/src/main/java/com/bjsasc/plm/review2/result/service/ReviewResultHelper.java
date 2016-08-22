package com.bjsasc.plm.review2.result.service;

import com.bjsasc.plm.core.util.SpringUtil;

public class ReviewResultHelper {
	private static ReviewResultService service = null;
	public static ReviewResultService getService() {
		if (service == null) {
			service = (ReviewResultService)SpringUtil.getBean("plm_rv_result");
		}
		return service;
	}
}
