package com.bjsasc.plm.review2.soap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.bjsasc.plm.collaboration.util.ObjectToMapUtil;
import com.bjsasc.plm.review2.div.model.ReviewOutDivision;

public class ReplyReviewOutDivisionMessageConvertImpl extends  AbstractMessageConvert implements MessageConvert{

	@Override
	public List<DmcConvertData> convertTo4XMessage(TransferObject obj) {
		List<DmcConvertData> datas = new ArrayList<DmcConvertData>();
		DmcConvertData data = new DmcConvertData();
		ObjectMessage om = super.convertTo4XObjectMesage(obj);
		data.setOm(om);
		datas.add(data);
		return datas;
	}

	@Override
	public List<TransferObject> convertToA5Message(ObjectMessage om,
			List<StoreObject> soList, List<StoreFile> arg2) {
		String targetSiteInnerId = om.getEndIID();
		Site targetSite = SiteHelper.getSiteService().findSiteById(targetSiteInnerId);
		Site sourceSite = SiteHelper.getSiteService().findSiteById(om.getSendDomainIID());
		List<ReviewOutDivision> outDivList = new ArrayList<ReviewOutDivision>();
		if(soList != null && !soList.isEmpty()) {
			for(int i = 0; i < soList.size(); i++) {
				StoreObject so = (StoreObject) soList.get(i);
				List<DataMap> domainDataMapList = (List<DataMap>)so.getDataObject();
				for(DataMap dataMap : domainDataMapList) {
					ReviewOutDivision outdiv = ReviewDataConvertUtil.parseDataMapToA5OutDiv(dataMap, om.getSendDomainIID());
				    outDivList.add(outdiv);
				}
			}
		}
		Map<String,String> reqParamMap = getOutDivParamMap(outDivList);
		List<TransferObject> transferObjList = new ArrayList<TransferObject>();
		TransferObject object = createTransferObject(om.getMsgIID(), "回复同步外部组织信息", om.getMsgType(), null,
				null, sourceSite, targetSite, TransferConstant.REQTYPE_ASYN, reqParamMap);
		
		transferObjList.add(object);
		return transferObjList;
	}
	
	private Map<String,String> getOutDivParamMap(List<ReviewOutDivision> outdivs){
		Map<String,String> map = new HashMap<String,String>();
		try {
			map.put("ReviewOutDivision", ObjectToMapUtil.listObjectToJson(outdivs));
		} catch (Exception e) {
			throw new RuntimeException("ReplyReviewOutDivisionMessageConvertImpl调用getOutDivParamMap失败",e);
		}
		return map;
	}

}
