package com.bjsasc.plm.review2.soap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.avidm.core.site.SiteHelper;
import com.bjsasc.avidm.core.transfer.TransferObject;
import com.bjsasc.avidm.core.transfer.util.TransferConstant;
import com.bjsasc.plm.collaboration.a4x.AbstractMessageConvert;
import com.bjsasc.plm.collaboration.a4x.MessageConvert;
import com.bjsasc.plm.collaboration.a4x.model.DataMap;
import com.bjsasc.plm.collaboration.a4x.model.DmcConvertData;
import com.bjsasc.plm.collaboration.a4x.model.ObjectMessage;
import com.bjsasc.plm.collaboration.a4x.model.StoreFile;
import com.bjsasc.plm.collaboration.a4x.model.StoreObject;
import com.bjsasc.plm.collaboration.a4x.util.MessageCreateUtil;
import com.bjsasc.plm.collaboration.util.ObjectToMapUtil;
import com.bjsasc.plm.review2.object.model.ReviewObject;

public class ReviewRequestMessageConvertImpl extends  AbstractMessageConvert implements MessageConvert{
	
	private static Logger logger = Logger.getLogger(ReviewRequestMessageConvertImpl.class);

	@Override
	public List<DmcConvertData> convertTo4XMessage(TransferObject obj) {
		List<DmcConvertData> datas = new ArrayList<DmcConvertData>();
		ObjectMessage om = super.convertTo4XObjectMesage(obj);
		om.setMsgName("评审证明书发送请求消息");
		
		Map reqParmaMap = obj.getReqParamMap();
		Map dataMap = null;
		try {
			dataMap = ObjectToMapUtil.mapToObject(reqParmaMap);
		} catch (Exception e) {
			logger.error("ReviewRequestMessageConvertImpl转换A3评审证明书对象失败",e);
			throw new RuntimeException("ReviewRequestMessageConvertImpl转换A3评审证明书对象失败",e);
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
		List<DataMap> dataMapList = getDataMapFromStoreObject(soList);
		Map map = ReviewDataConvertUtil.parseReviewRequestDataListToMap(dataMapList);
		ReviewObject reviewObj = (ReviewObject) map.get("reviewObj");
		
		Map reqParamMap = getReviewDataMap(reviewObj);
		Site sourceSite = SiteHelper.getSiteService().findSiteById(reviewObj.getSourceSiteInnerId());
		Site targetSite = SiteHelper.getSiteService().findLocalSiteInfo();
		List<TransferObject> transferObjList = new ArrayList<TransferObject>();
		TransferObject object = createTransferObject(om.getMsgIID(), reviewObj.getDocId(), om.getMsgType(), null,
				null, sourceSite, targetSite, TransferConstant.REQTYPE_ASYN, reqParamMap);
		transferObjList.add(object);
		return transferObjList;
	}
	
	
	private Map<String,String> getReviewDataMap(ReviewObject reviewObj){
		Map<String,String> map = new HashMap<String,String>();
		try {
			map.put("ReviewObject", ObjectToMapUtil.objectToJson(reviewObj));
		} catch (Exception e) {
			throw new RuntimeException("ReviewRequestMessageConvertImpl调用getReviewDataMap失败",e);
		}
		return map;
	}

}
