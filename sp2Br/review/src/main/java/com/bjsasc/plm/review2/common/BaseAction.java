package com.bjsasc.plm.review2.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2927213300118714193L;

	protected ActionContext context = ActionContext.getContext();

	// 获取request
	protected HttpServletRequest request = ServletActionContext.getRequest();

	// 获取response
	protected HttpServletResponse response = ServletActionContext.getResponse();
	
	// 获取session 注：strut2中将session封装成了Map<String,Object>形式
	protected Map session = context.getSession();
}
