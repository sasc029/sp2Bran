package com.bjsasc.plm.review2.soap;

import java.util.Map;

import com.bjsasc.avidm.core.transfer.event.TransferEvent;
import com.bjsasc.platform.objectmodel.business.persist.ObjectReference;
import com.bjsasc.plm.collaboration.adapter.base.MessageDealService;
import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.folder.FolderInfo;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.object.model.ReviewObjectContext;
import com.bjsasc.plm.review2.object.service.ReviewObjectContextHelper;
import com.cascc.platform.uuidservice.UUID;

/**
 * A5∆¿…Û¥¶¿Ì¿‡
 * @author YHJ
 *
 */
public class ReviewRequestMessageDealService implements MessageDealService{

	@Override
	public void dealEvent(TransferEvent event) {
		Map<String, Object> reviewDataMap = ReviewDataConvertUtil.convertToDataMap(event.getReqParamMap(), event.getTransferType());
		ReviewObject reviewObject = (ReviewObject) reviewDataMap.get("ReviewObject");
		String sourceSiteInnerId = reviewObject.getSourceSiteInnerId();
		
		ReviewObjectContext rvContext = ReviewObjectContextHelper.getService().findReviewObjectContextBySiteInnerId(sourceSiteInnerId);
		
		reviewObject.setContextInfo(rvContext.getProduct().getContextInfo());
		FolderInfo folderInfo = new FolderInfo();
		folderInfo.setFolderRef(ObjectReference.newObjectReference(rvContext.getFolder()));
		folderInfo.setFolderName(rvContext.getFolder().getName());
		reviewObject.setFolderInfo(folderInfo);
		reviewObject.setDomainInfo(rvContext.getFolder().getDomainInfo());
		reviewObject.setInnerId(UUID.getUID());
		
		Helper.getPersistService().save(reviewObject);
		
	}

}
