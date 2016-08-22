package com.bjsasc.plm.review2.soap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bjsasc.avidm.core.transfer.TransferObject;
import com.bjsasc.plm.collaboration.a4x.AbstractMessageConvert;
import com.bjsasc.plm.collaboration.a4x.MessageConvert;
import com.bjsasc.plm.collaboration.a4x.model.DmcConvertData;
import com.bjsasc.plm.collaboration.a4x.model.ObjectMessage;
import com.bjsasc.plm.collaboration.a4x.model.StoreFile;
import com.bjsasc.plm.collaboration.a4x.model.StoreObject;
import com.bjsasc.plm.collaboration.a4x.util.MessageCreateUtil;
import com.bjsasc.plm.collaboration.util.ObjectToMapUtil;

/**
 * 结束会议评审后，发起消息，生成A3设计文档
 * @author YHJ
 *
 */
public class MeetingReviewMessageConvertImpl extends  AbstractMessageConvert implements MessageConvert{
	
	private static Logger logger = Logger.getLogger(MeetingReviewMessageConvertImpl.class);

	@Override
	public List<DmcConvertData> convertTo4XMessage(TransferObject obj) {
		List<DmcConvertData> datas = new ArrayList<DmcConvertData>();
		ObjectMessage om = super.convertTo4XObjectMesage(obj);
		om.setMsgName("会议评审证明书发送请求消息");
		
		Map reqParmaMap = obj.getReqParamMap();
		Map dataMap = null;
		try {
			dataMap = ObjectToMapUtil.mapToObject(reqParmaMap);
		} catch (Exception e) {
			logger.error("MeetingReviewMessageConvertImpl转换A3文档对象失败",e);
			throw new RuntimeException("MeetingReviewMessageConvertImpl转换A3文档对象失败",e);
		}
		
		String docIID = (String) dataMap.get("docIID");
		String productIID = (String) dataMap.get("productIID");
		List dataMapList = ReviewDataConvertUtil.replyReviewToDataMapList(docIID, productIID);
		List fileList = new ArrayList();
		String filePath = obj.getFilePath();
		if (filePath != null && !"".equals(filePath)) {
			fileList.add(filePath);
		}
		DmcConvertData data = MessageCreateUtil.createMessage(om, dataMapList, fileList);
		datas.add(data);
		return datas;
	}

	@Override
	public List<TransferObject> convertToA5Message(ObjectMessage om,
			List<StoreObject> soList, List<StoreFile> soFileList) {
		// TODO Auto-generated method stub
		return null;
	}

}
