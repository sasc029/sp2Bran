package com.bjsasc.plm.review2.dataconfig.model;

import com.bjsasc.plm.core.type.ATObject;

/**
 * �������ģ��
 * @author YHJ
 *
 */
public class ReviewDataConfig extends ATObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 300231871890335198L;
	/**
	 * 
	 */
	public static final String CLASSID = ReviewDataConfig.class.getSimpleName();
	
	public ReviewDataConfig(){
		setClassId(CLASSID);
	}
	
	
	private String dataType;//����	
	private String dataName;//����
	private String dataSource;//��������

	
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
}
