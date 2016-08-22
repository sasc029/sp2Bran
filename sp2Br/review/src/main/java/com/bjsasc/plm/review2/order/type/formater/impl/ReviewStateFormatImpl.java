package com.bjsasc.plm.review2.order.type.formater.impl;

import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.review2.util.ReviewOrderUtil;
import com.bjsasc.plm.type.attr.Attr;
import com.bjsasc.plm.type.formater.FormatService;
import com.bjsasc.plm.type.formater.Formater;

public class ReviewStateFormatImpl implements FormatService{
	public Object format(Object target, Attr attr, Formater formater) {
		List<String> sources = attr.getSources();
		//Map<String, String> params = formater.getFormaterParams();

		Object result = null;
		
		if(sources.size() == 0){
			return result;
		}
		
		Object attrValue = Helper.getTypeService().getAttrValue(sources.get(0), target);
		
		if(attrValue instanceof String){
			String str = (String)attrValue;
			result = ReviewOrderUtil.convertBusState(str);
		}
		
		return result;
	}
}
