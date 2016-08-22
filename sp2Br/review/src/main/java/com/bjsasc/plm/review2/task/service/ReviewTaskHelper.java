package com.bjsasc.plm.review2.task.service;

import com.bjsasc.plm.core.util.SpringUtil;

public class ReviewTaskHelper {
	private static ReviewTaskService service = null;
	public static ReviewTaskService getService() {
		if (service == null) {
			service = (ReviewTaskService)SpringUtil.getBean("plm_rv_task");
		}
		return service;
	}
}
