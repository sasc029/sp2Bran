package com.bjsasc.plm.review2.result.service;

import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.persist.PersistHelper;
import com.bjsasc.plm.review2.expert.model.OuterReviewExpert;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.plm.review2.result.model.ReviewResult;
import com.cascc.platform.aa.AAProvider;
import com.cascc.platform.aa.api.UserService;
import com.cascc.platform.aa.api.data.AAUserData;

public class ReviewResultServiceImpl implements ReviewResultService {
	/**
	 * 查询所有数据
	 */
	@Override
	public List<ReviewResult> getResultsByReview(String rvOrderId) {
		String hql = "from ReviewResult where reviewOrderRef.innerId=? ";
		return (List<ReviewResult>)Helper.getPersistService().find(hql,rvOrderId);
	}

	@Override
	public ReviewResult newResult() {
		ReviewResult result = new ReviewResult();
		result.setIsExpertConfirm("0");
		result.setIsLeaderConfirm("0");
		return result;
	}
    
	@Override
	public ReviewResult createReviewResult(ReviewResult rvResult) {
			PersistHelper.getService().save(rvResult);
			return rvResult;
	}

	@Override
	public ReviewResult getReviewResultById(String innerId) {
		String hql = "from ReviewResult where innerId=?";
		List<ReviewResult> opns = PersistHelper.getService().find(hql,innerId);
		if(!opns.isEmpty()){
			return opns.get(0);
		}
		return null;
	}

	@Override
	public ReviewResult createReviewResult(String resultType,String executorId, ReviewOrder rvOrder, ReviewObject rvObj,
			OuterReviewExpert outerExpert) {
		ReviewResult rvResult = this.newResult();
		UserService userService = AAProvider.getUserService();
		
		AAUserData user = userService.getUser(null, executorId);
		rvResult.setReviewOrder(rvOrder);
		rvResult.setReviewObject(rvObj);
		rvResult.setExecutor(user);
		rvResult.setOuterExpert(outerExpert);
		rvResult.setResultType(resultType);
		
		this.createReviewResult(rvResult);
		
		return rvResult;
	}

	@Override
	public List<ReviewResult> getResultsByRvIdAndExecutor(String rvOrderId, String executorId) {
		String hql = "from ReviewResult where reviewOrderRef.innerId=? and executorRef.innerId=?";
		return (List<ReviewResult>)Helper.getPersistService().find(hql,rvOrderId,executorId);
	}

}
