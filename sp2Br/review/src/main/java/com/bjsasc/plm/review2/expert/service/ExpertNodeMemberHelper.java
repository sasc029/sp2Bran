package com.bjsasc.plm.review2.expert.service;

import com.bjsasc.plm.core.util.SpringUtil;

public class ExpertNodeMemberHelper {
    private static ExpertNodeMemberService service = null;
	
	public static ExpertNodeMemberService getService(){
		if(null == service){
			service = (ExpertNodeMemberService)SpringUtil.getBean("plm_expertnodemember_service");
		}
		
		return service;
	}
}
