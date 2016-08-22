package com.bjsasc.plm.review2.result.action;

import java.util.ArrayList;
import java.util.List;

import com.bjsasc.plm.Helper;
import com.bjsasc.plm.core.persist.PersistHelper;
import com.bjsasc.plm.core.session.SessionHelper;
import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.core.system.principal.UserHelper;
import com.bjsasc.plm.core.system.principal.UserService;
import com.bjsasc.plm.review2.common.BaseAction;
import com.bjsasc.plm.review2.constant.ReviewTaskConstant;
import com.bjsasc.plm.review2.opinion.model.ReviewOpinion;
import com.bjsasc.plm.review2.opinion.service.ReviewOpinionHelper;
import com.bjsasc.plm.review2.result.model.ReviewResult;
import com.bjsasc.plm.review2.result.service.ReviewResultHelper;
import com.bjsasc.plm.review2.result.service.ReviewResultService;
import com.bjsasc.plm.review2.task.model.ReviewTask;
import com.bjsasc.plm.review2.task.service.ReviewTaskHelper;
import com.bjsasc.plm.review2.util.ReviewOrderUtil;

public class ReviewResultAction extends BaseAction {

	private static final long serialVersionUID = -490798782789829633L;
	ReviewResultService service = ReviewResultHelper.getService();
	private User user = SessionHelper.getService().getUser();
	private UserService userService = UserHelper.getService();
	private ReviewResult reviewResult;
	
	public void expertConfirmContents(){
		String innerId = request.getParameter("innerId");
		String result = request.getParameter("result");
		String flag = request.getParameter("flag");
		String expertCfmContent = request.getParameter("Txt");
		if(result.length()>200){
			result = result.substring(0,200);
		}
		ReviewResult rvResult = service.getReviewResultById(innerId);
		String orderId = rvResult.getReviewOrder().getInnerId();
		String userId = UserHelper.getService().getUser(rvResult.getExecutor().getInnerId()).getInnerId();
		if("expert".equals(flag)){
			rvResult.setExpertCfmContent(expertCfmContent);
			rvResult.setIsExpertConfirm(result);
		}else{
			rvResult.setLeaderCfmContent(expertCfmContent);
			rvResult.setIsLeaderConfirm(result);
		}
		PersistHelper.getService().update(rvResult);
		if(ReviewOrderUtil.isOverTask(orderId)){
			this.closeAllTask(orderId);
		}else{
			ReviewTask rvTask = ReviewTaskHelper.getService().getRvTasksByExecutorAndOrder(userId, orderId).get(0);
			if(!rvTask.getTaskRole().equals(ReviewTaskConstant.TASK_ROLE_CHARGEMAN) && !rvTask.getTaskRole().equals(ReviewTaskConstant.TASK_ROLE_DESIGNER)){
				ReviewTaskHelper.getService().closeRvTaskById(rvTask.getInnerId());
			}
		}
	}
	
	public void leaderConfirmContents(){
		String orderOid = request.getParameter("orderOid");
		String orderId = Helper.getInnerId(orderOid);
		String result = request.getParameter("result");
		String expertCfmContent = request.getParameter("Txt");
		if(result.length()>200){
			result = result.substring(0,200);
		}
		List<ReviewResult> rvResults = service.getResultsByReview(orderId);
		List<ReviewResult> listResult = new ArrayList<ReviewResult>();
		if(!rvResults.isEmpty()){
			for(ReviewResult rvResult:rvResults){
				if(!"0".equals(rvResult.getIsExpertConfirm())&&"0".equals(rvResult.getIsLeaderConfirm())){
					listResult.add(rvResult);
					rvResult.setLeaderCfmContent(expertCfmContent);
					rvResult.setIsLeaderConfirm(result);
					PersistHelper.getService().update(rvResult);
				}
			}
		}
		if(ReviewOrderUtil.isOverTask(orderId)){
			this.closeAllTask(orderId);
		}else{
			for(ReviewResult rvRes:listResult){
				String userId = UserHelper.getService().getUser(rvRes.getExecutor().getInnerId()).getInnerId();
				ReviewTask rvTask = ReviewTaskHelper.getService().getRvTasksByExecutorAndOrder(userId, orderId).get(0);
				if(!rvTask.getTaskRole().equals(ReviewTaskConstant.TASK_ROLE_CHARGEMAN) && !rvTask.getTaskRole().equals(ReviewTaskConstant.TASK_ROLE_DESIGNER)){
					ReviewTaskHelper.getService().closeRvTaskById(rvTask.getInnerId());
				}
			}
		}
	}
	public void checkReviewOpinion() throws Exception{
		String innerId = request.getParameter("innerId");
		String reviewId = request.getParameter("reviewId");
		ReviewResult rvResult = service.getReviewResultById(innerId);
		boolean flag = true;
		if(ReviewResult.RESULT_TYPE_DOMAIN.equals(rvResult.getResultType())){
			User domainUser = userService.getUser(rvResult.getExecutor().getInnerId());
			List<ReviewOpinion> listOpn = ReviewOpinionHelper.getService().getOpinions(reviewId, domainUser.getInnerId());
			for(ReviewOpinion opn:listOpn){
				if(String.valueOf(ReviewOpinion.OPNSTATE_NEW).equals(opn.getOpinionState())){
					flag=false;
					break;
				}
			}
		}else{
			String outUserId = rvResult.getOuterExpert().getInnerId();
			List<ReviewOpinion> listOpn = ReviewOpinionHelper.getService().getOpinionsByOutUserId(reviewId, outUserId);
			for(ReviewOpinion opn:listOpn){
				if(String.valueOf(ReviewOpinion.OPNSTATE_NEW).equals(opn.getOpinionState())){
					flag=false;
					break;
				}
			}
		}
		if(flag){
			response.getWriter().print("success");
		}else{
			response.getWriter().print("fail");
		}
	}
	public void closeAllTask(String orderId){
		List<ReviewTask> tasks = ReviewTaskHelper.getService().getRvTasksByReview(orderId);
		for(ReviewTask task:tasks){
			if(!ReviewTaskConstant.TASK_ROLE_CHARGEMAN.equals(task.getTaskRole())){
				ReviewTaskHelper.getService().closeRvTaskById(task.getInnerId());
			}
		}
	}
	
	public ReviewResult getReviewResult() {
		return reviewResult;
	}
	public void setReviewResult(ReviewResult reviewResult) {
		this.reviewResult = reviewResult;
	}
}