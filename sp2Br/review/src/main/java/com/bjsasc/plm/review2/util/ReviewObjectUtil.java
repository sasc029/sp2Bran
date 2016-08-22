package com.bjsasc.plm.review2.util;

import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.review2.Reviewed;
import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.core.system.principal.UserHelper;
import com.bjsasc.plm.review2.link.service.ReviewMemberHelper;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;

public class ReviewObjectUtil {
	
	public static String getDesignerOID(ReviewOrder reviewOrder){
		String createUserIId = "";
		String oid = "";
		List<Reviewed> list = ReviewMemberHelper.getService().getReviewedListByOrder(reviewOrder);
		for(int i=0,l=list.size(); i<l;i++){
		    Reviewed reviewed = list.get(i);
		    if(reviewed instanceof ReviewObject){
		    	ReviewObject reviewObject = (ReviewObject)reviewed;
		    	createUserIId = reviewObject.getCreateUserIId();
		    	User user =UserHelper.getService().getUser(createUserIId);
		    	oid = Helper.getOid(user);
		    }
		}
		return oid;
	}
	
	public static String getDesignerIID(ReviewOrder reviewOrder){
		String createUserIId = "";
		String innerId = "";
		List<Reviewed> list = ReviewMemberHelper.getService().getReviewedListByOrder(reviewOrder);
		for(int i=0,l=list.size(); i<l;i++){
		    Reviewed reviewed = list.get(i);
		    if(reviewed instanceof ReviewObject){
		    	ReviewObject reviewObject = (ReviewObject)reviewed;
		    	createUserIId = reviewObject.getCreateUserIId();
		    	innerId = createUserIId;
		    }
		}
		return innerId;
	}
	public static String getDesignerAAUserInnerId(ReviewOrder reviewOrder){
		String createUserIId = "";
		List<Reviewed> list = ReviewMemberHelper.getService().getReviewedListByOrder(reviewOrder);
		for(int i=0,l=list.size(); i<l;i++){
		    Reviewed reviewed = list.get(i);
		    if(reviewed instanceof ReviewObject){
		    	ReviewObject reviewObject = (ReviewObject)reviewed;
		    	createUserIId = reviewObject.getCreateUserIId();
		    }
		}
		return createUserIId;
	}
	
	public static String getDesignerUserName(ReviewOrder reviewOrder){
		String createUserName = "";
		List<Reviewed> list = ReviewMemberHelper.getService().getReviewedListByOrder(reviewOrder);
		for(int i=0,l=list.size(); i<l;i++){
		    Reviewed reviewed = list.get(i);
		    if(reviewed instanceof ReviewObject){
		    	ReviewObject reviewObject = (ReviewObject)reviewed;
		    	createUserName = reviewObject.getCreateUserName();
		    }
		}
		return createUserName;
	}


}
