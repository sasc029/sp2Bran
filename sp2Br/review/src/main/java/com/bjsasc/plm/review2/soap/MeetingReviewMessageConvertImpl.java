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
 * ������������󣬷�����Ϣ������A3����ĵ�
 * @author YHJ
 *
 */
public class MeetingReviewMessageConvertImpl extends  AbstractMessageConvert implements MessageConvert{
	
	private static Logger logger = Logger.getLogger(MeetingReviewMessageConvertImpl.class);

	@Override
	public List<DmcConvertData> convertTo4XMessage(TransferObject obj) {
		List<DmcConvertData> datas = new ArrayList<DmcConvertData>();
		ObjectMessage om = super.convertTo4XObjectMesage(obj);
		om.setMsgName("��������֤���鷢��������Ϣ");
		
		Map reqParmaMap = obj.getReqParamMap();
		Map dataMap = null;
		try {
			dataMap = ObjectToMapUtil.mapToObject(reqParmaMap);
		} catch (Exception e) {
			logger.error("MeetingReviewMessageConvertImplת��A3�ĵ�����ʧ��",e);
			throw new RuntimeException("MeetingReviewMessageConvertImplת��A3�ĵ�����ʧ��",e);
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
