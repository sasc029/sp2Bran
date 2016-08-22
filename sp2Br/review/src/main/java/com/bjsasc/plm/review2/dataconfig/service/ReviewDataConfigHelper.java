package com.bjsasc.plm.review2.dataconfig.service;

import com.bjsasc.plm.core.util.SpringUtil;

public class ReviewDataConfigHelper {
	private static ReviewDataConfigService service = null;
	public static ReviewDataConfigService getService() {
		if (service == null) {
			service = (ReviewDataConfigService)SpringUtil.getBean("plm_review_reviewDataConfig");
		}
		return service;
	}
}
