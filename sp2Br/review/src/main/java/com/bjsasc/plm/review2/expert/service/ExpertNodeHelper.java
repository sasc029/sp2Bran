package com.bjsasc.plm.review2.expert.service;

import com.bjsasc.platform.spring.PlatformApplicationContext;

public class ExpertNodeHelper {
private static ExpertNodeService expertNodeService = null;
	
	public static ExpertNodeService getExpertNodeService(){
		if(null == expertNodeService){
			expertNodeService = (ExpertNodeService) PlatformApplicationContext.getBean("plm_expertNodeService");
		}
		return expertNodeService;
	}
}
