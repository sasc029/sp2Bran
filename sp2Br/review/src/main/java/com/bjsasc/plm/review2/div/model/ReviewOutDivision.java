package com.bjsasc.plm.review2.div.model;

import com.bjsasc.plm.core.type.ATObject;

public class ReviewOutDivision extends ATObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 263291679822850369L;
	
	private String outDivId;
	
	private String outDivInnerId;
	
	private String outDivNam;
	
	private String parentDiv;
	
	private String siteInnerId;
	
    public static final String CLASSID = ReviewOutDivision.class.getSimpleName();
	
	public ReviewOutDivision(){
		setClassId(CLASSID);
	}

	public String getOutDivId() {
		return outDivId;
	}

	public void setOutDivId(String outDivId) {
		this.outDivId = outDivId;
	}

	public String getOutDivNam() {
		return outDivNam;
	}

	public void setOutDivNam(String outDivNam) {
		this.outDivNam = outDivNam;
	}

	public String getParentDiv() {
		return parentDiv;
	}

	public void setParentDiv(String parentDiv) {
		this.parentDiv = parentDiv;
	}

	public String getSiteInnerId() {
		return siteInnerId;
	}

	public void setSiteInnerId(String siteInnerId) {
		this.siteInnerId = siteInnerId;
	}
	
	public String getOutDivInnerId() {
		return outDivInnerId;
	}

	public void setOutDivInnerId(String outDivInnerId) {
		this.outDivInnerId = outDivInnerId;
	}
}
