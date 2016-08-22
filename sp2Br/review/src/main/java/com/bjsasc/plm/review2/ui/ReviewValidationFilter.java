package com.bjsasc.plm.review2.ui;


import java.util.List;

import com.bjsasc.plm.core.persist.model.Persistable;
import com.bjsasc.plm.core.session.SessionHelper;
import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.core.system.task.StandardTask;
import com.bjsasc.plm.core.system.task.Task;
import com.bjsasc.plm.core.system.task.TaskHelper;
import com.bjsasc.plm.core.system.task.TaskState;
import com.bjsasc.plm.operate.Action;
import com.bjsasc.plm.review2.committee.model.ReviewCommitteeMemberHS;
import com.bjsasc.plm.review2.committee.service.ReviewCommitteeMemberHelper;
import com.bjsasc.plm.review2.conclusion.model.ReviewConclusionHS;
import com.bjsasc.plm.review2.conclusion.service.ReviewConclusionHelper;
import com.bjsasc.plm.review2.constant.ReviewConclusionConstant;
import com.bjsasc.plm.review2.constant.ReviewOrderConstant;
import com.bjsasc.plm.review2.constant.ReviewTaskConstant;
import com.bjsasc.plm.review2.expert.model.DomainReviewExpert;
import com.bjsasc.plm.review2.item.model.ReviewItem;
import com.bjsasc.plm.review2.item.service.ReviewItemHelper;
import com.bjsasc.plm.review2.opinion.model.ReviewOpinion;
import com.bjsasc.plm.review2.opinion.service.ReviewOpinionHelper;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.plm.review2.util.ExpertUtil;
import com.bjsasc.plm.ui.UIDataInfo;
import com.bjsasc.plm.ui.validation.UIState;
import com.bjsasc.plm.ui.validation.ValidationFilter;

public class ReviewValidationFilter implements ValidationFilter{

	public UIState doActionFilter(Action action, UIDataInfo uiData) {
		Persistable object = uiData.getMainObject();
		if(object == null){
			return UIState.ENABLED;
		}
		String actionId = action.getId();
		String[] operation = actionId.split("\\.");
		int i = operation.length - 1;
		String operationId = operation[i];
		ReviewOrder review = null;
		ReviewCommitteeMemberHS rcm = null;
		List<ReviewItem> reviewItems= null;
		List<ReviewItem> notFinistItem= null;
		ReviewConclusionHS rcl = null;
		if(object instanceof ReviewOrder){
			review = (ReviewOrder)object;
			rcm=ReviewCommitteeMemberHelper.getMemberService().getLeader(review.getInnerId());
			reviewItems = ReviewItemHelper.getService().getReviewItemByReview(review.getInnerId());
			notFinistItem = ReviewItemHelper.getService().getNotFinishStateReviewItemByReview(review.getInnerId());
			List<ReviewConclusionHS> listrc = ReviewConclusionHelper.getService().findReviewConclusionByReviewOrder(review.getInnerId());
			if(listrc != null && listrc.size()>0){
				rcl = listrc.get(0);
			}
		}
		boolean flag = true;
		String userId = SessionHelper.getService().getUser().getAaUserInnerId();
		boolean isPrincipal = ExpertUtil.isUserForPSFZR(userId); //是否为评审负责人
		if(operation[i-1].equals("review")){//评审活动
			if(operationId.equals("starts")){//启动				
				if(!review.getState().equals(ReviewOrderConstant.TASK_MR_NEW)){
					flag = false;
				}else{
					List<ReviewCommitteeMemberHS> committee = ReviewCommitteeMemberHelper.getMemberService().getMembersByReviewOrderId(review.getInnerId());
					if(committee.size()<5){
						flag = false;
					}else{
						boolean isLerder =false;
						for(ReviewCommitteeMemberHS c : committee){
							if(c.getUserLevel().equals(ReviewCommitteeMemberHS.USERLEVEL_OFFICER)){
								isLerder = true;
							}
						}
						if(!isLerder){
							flag = false;
						}
					}
				}
			}else if(operationId.equals("submit")){
				User user = SessionHelper.getService().getUser();//获取当前登录用户
				ReviewCommitteeMemberHS rctm = ReviewCommitteeMemberHelper.getMemberService().getLeader(review.getInnerId());
				if(rctm!=null && ((DomainReviewExpert)rctm.getExpert()).getUser().getInnerId().equals(user.getAaUserInnerId()) ){
					List<Task> taskList = TaskHelper.getResService().getTasksByResource(review);
					for(Task t : taskList){
						StandardTask task=(StandardTask)t;
						if(!task.getState().equals(TaskState.COMPLETED)){
							flag = false;
							break;
						}	
					}
				}else{
					flag = false;
				}
			}else if(operationId.equals("closes")){
				if(notFinistItem!=null && notFinistItem.size()>0){
					flag = false;
				}
				if(flag){
					if(rcl == null){
						flag = false;
					}else if(!rcl.getState().equals(ReviewConclusionConstant.RC_STATE_PRINCIPAL_SUBMIT)){
						flag = false;
					}
					if(flag){
						if(review != null && review.getState().equals(ReviewOrderConstant.TASK_MR_REVIEW_OVER)){
							flag = false;
						}
						if(flag){
							if(!isPrincipal){
								flag = false;
							}
						}
					}
				}
			}else if(operationId.equals("rvConclusion")){
				List<Task> taskList = TaskHelper.getResService().getTasksByResource(review);
				if(taskList==null ||taskList.size()==0 ){
					flag = false;
				}else{
					for(Task t : taskList){
						if(((StandardTask)t).getTaskType().equals(ReviewTaskConstant.TASK_TYPE_CHECK) && (!t.getState().equals(TaskState.COMPLETED))){
							flag = false;
							break;
						}
					}
				}	
				if(flag){
					//获取该评审单当前用户的评审意见，修改评审意见状态
					List<ReviewOpinion> reviewOpinionList = ReviewOpinionHelper.getService().getOpinionsByReview(review.getInnerId());
					if(reviewOpinionList==null || reviewOpinionList.size()==0){
						flag = false;
					}else{
						for(ReviewOpinion reviewOpinion : reviewOpinionList){
							if(reviewOpinion.getOpinionStateInt()<ReviewOpinion.OPNSTATE_CONFIRMOVER){
								flag = false;
								break;
							}
						}
					}
				}
			}else if(operationId.equals("rvPlanitem")){
				if(!isPrincipal){//是否评审负责人
					if(rcm!=null){
						if(!((DomainReviewExpert)rcm.getExpert()).getUser().getInnerId().equals(userId)){//是否评审组长
							if(reviewItems!=null && reviewItems.size()>0){
								for(ReviewItem item : reviewItems){
									if(item.getUser()!=null){
										if(item.getUser().getInnerId().equals(userId)){//是否为执行人
											flag = true;
											break;
										}else{
											flag = false;
										}
									}else{
										flag = false;
									}
								}
							}
						}	
					}
				}
				
				List<Task> taskList = TaskHelper.getResService().getTasksByResource(review);
				if(taskList==null ||taskList.size()==0 ){
					flag = false;
				}else{
					for(Task t : taskList){
						if(((StandardTask)t).getTaskType().equals(ReviewTaskConstant.TASK_TYPE_CHECK) && (!t.getState().equals(TaskState.COMPLETED))){
							flag = false;
							break;
						}
					}
				}	
				if(flag){
					//获取该评审单当前用户的评审意见，修改评审意见状态
					List<ReviewOpinion> reviewOpinionList = ReviewOpinionHelper.getService().getOpinionsByReview(review.getInnerId());
					if(reviewOpinionList==null || reviewOpinionList.size()==0){
						flag = false;
					}else{
						for(ReviewOpinion reviewOpinion : reviewOpinionList){
							if(reviewOpinion.getOpinionStateInt()<ReviewOpinion.OPNSTATE_CONFIRMOVER){
								flag = false;
								break;
							}
						}
					}
				}
			}
		}else if(operation[i-1].equals("rvPlanitem")){
			ReviewItem task = (ReviewItem)object;
			//编辑
			if(operationId.equals("edit")){
				flag = false;
				if(task.getState().equals(ReviewItem.TASKSTATE_NEW)){
					flag = true;
				}
			}
			//下发
			else if(operationId.equals("start")){
				flag = false;
				if(task.getState().equals(ReviewItem.TASKSTATE_NEW)){
					flag = true;
				}
			}
		}
		if (flag) {
			return UIState.ENABLED;
		} else {
			return UIState.DISABLED;
		}
	}

}
