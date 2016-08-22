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

	// ��ȡrequest
	protected HttpServletRequest request = ServletActionContext.getRequest();

	// ��ȡresponse
	protected HttpServletResponse response = ServletActionContext.getResponse();
	
	// ��ȡsession ע��strut2�н�session��װ����Map<String,Object>��ʽ
	protected Map session = context.getSession();
}
