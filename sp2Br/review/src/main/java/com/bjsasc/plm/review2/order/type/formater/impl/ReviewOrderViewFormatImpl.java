package com.bjsasc.plm.review2.order.type.formater.impl;

import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.plm.type.attr.Attr;
import com.bjsasc.plm.type.formater.FormatService;
import com.bjsasc.plm.type.formater.Formater;

public class ReviewOrderViewFormatImpl implements FormatService{
	public Object format(Object target, Attr attr, Formater formater) {
		String attrId = attr.getId();
		ReviewOrder rvOrder = (ReviewOrder) target;
		String result = null;
		if(attrId.equals("RVORDER_NAME")) {
		    result = getRvOrderLink(rvOrder.getInnerId(), rvOrder.getName());
		}
		return result;
	}
	/*
	 * ≈‰÷√∫Ø…Ûµ•√˚≥∆¡¥Ω”
	 */
	private String getRvOrderLink(String orderId, String orderName){
		StringBuffer str = new StringBuffer();
		str.append("<a href='javascript:void(0)' title='").append(orderName).append("' onclick='viewData(\"").append(orderId)
		   .append("\")'>").append(orderName).append("</a>");
		return str.toString();
	}
	
}
