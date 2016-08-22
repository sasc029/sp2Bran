package com.bjsasc.plm.review2.soap;

import java.util.List;
import java.util.Map;

import com.bjsasc.avidm.core.transfer.event.TransferEvent;
import com.bjsasc.plm.collaboration.adapter.base.MessageDealService;
import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.review2.div.model.ReviewOutDivision;
import com.bjsasc.plm.review2.div.service.ReviewOutDivisionHelper;

/**
 * A5评审同步外部组织处理类
 * @author YHJ
 *
 */
public class ReviewOutDivisionMessageDealService implements MessageDealService{

	@Override
	public void dealEvent(TransferEvent event) {
		Map<String, Object> reviewDataMap = ReviewDataConvertUtil.convertToDataMap(event.getReqParamMap(), event.getTransferType());
		List<ReviewOutDivision> outDivList = (List<ReviewOutDivision>) reviewDataMap.get("ReviewOutDivision");
		List<ReviewOutDivision> divList = ReviewOutDivisionHelper.getReviewOutDivisionService().getRvOutDivisionListBySiteInnerId(event.getSourceSite().getInnerId());
		if(!divList.isEmpty()){
			Helper.getPersistService().delete(divList);
		}
		for(ReviewOutDivision outDiv:outDivList){
			Helper.getPersistService().save(outDiv);
		}
	}

}
