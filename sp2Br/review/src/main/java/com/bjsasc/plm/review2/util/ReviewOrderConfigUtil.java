package com.bjsasc.plm.review2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class ReviewOrderConfigUtil {
	private static Logger logger = Logger.getLogger(ReviewOrderConfigUtil.class);

	private static String CONFIG_FILE_PATH = System.getProperty("AVIDM_HOME") + File.separator + "plm" + File.separator + "review"+ File.separator+ "config"+File.separator+"reviewNumberInit.xml";

	private static Map<String,String> configMap = new HashMap<String,String>();

	static {
		loadConfig(); // ≥ı ºªØ≈‰÷√
	}
	
	/**
	 * º”‘ÿXML≈‰÷√
	 * 
	 */
	public static void loadConfig() {
		try {
			InputStream is = new FileInputStream(new File(CONFIG_FILE_PATH));
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			List reviewNum = root.elements();
			for(int i = 0; i < reviewNum.size(); i++) {
				Element reviewNumEle = (Element) reviewNum.get(i);
			    if("reviewNumber".equals(reviewNumEle.attributeValue("id"))){
			    	String reviewNumber = reviewNumEle.getText();
			    	configMap.put("reviewNumber", reviewNumber);
			    }else if("committeeNum".equals(reviewNumEle.attributeValue("id"))){
			    	String committeeNum = reviewNumEle.getText();
			    	configMap.put("committeeNum", committeeNum);
			    }
							
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("∫Ø…Û≈‰÷√Œƒº˛≥ı ºªØ¥ÌŒÛ£°", e);
		}
	}
    
	public static String getLocalReviewConfig(String type) {
		String localCfg = (String)configMap.get(type);
		return localCfg;
	}
}
