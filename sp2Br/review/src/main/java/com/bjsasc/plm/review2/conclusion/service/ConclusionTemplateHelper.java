package com.bjsasc.plm.review2.conclusion.service;

import com.bjsasc.plm.core.util.SpringUtil;

public class ConclusionTemplateHelper {
	private static ConclusionTemplateService service = null;
	public static ConclusionTemplateService getService() {
		if (service == null) {
			service = (ConclusionTemplateService)SpringUtil.getBean("plm_review_conclusionTemplate");
		}
		return service;
	}
}
