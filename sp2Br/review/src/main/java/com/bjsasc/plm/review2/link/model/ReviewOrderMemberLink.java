package com.bjsasc.plm.review2.link.model;


import com.bjsasc.plm.core.review2.Reviewed;
import com.bjsasc.plm.core.type.ATLink;
import com.bjsasc.plm.review2.order.model.ReviewOrder;

/**
 * 评审单对象关系
 * @author YHJ
 *
 */
public class ReviewOrderMemberLink extends ATLink{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6137869667955939704L;
	public static final String CLASSID = ReviewOrderMemberLink.class.getSimpleName();
	
	private String objType;
	
	public ReviewOrderMemberLink(ReviewOrder order, Reviewed reviewed){
		setClassId(CLASSID);
		this.setToObject(reviewed);
		this.setFromObject(order);
	}
	
	public ReviewOrderMemberLink(){
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}
	
	
	
}
