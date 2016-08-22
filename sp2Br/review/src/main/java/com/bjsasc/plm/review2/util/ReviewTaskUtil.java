package com.bjsasc.plm.review2.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.plm.core.persist.PersistHelper;
import com.bjsasc.plm.core.system.principal.UserHelper;
import com.bjsasc.plm.review2.committee.model.ReviewCommitteeMemberHS;
import com.bjsasc.plm.review2.committee.service.ReviewCommitteeMemberHelper;
import com.bjsasc.plm.review2.constant.ReviewTaskConstant;
import com.bjsasc.plm.review2.expert.model.DomainReviewExpert;
import com.bjsasc.plm.review2.opinion.model.ReviewOpinion;
import com.bjsasc.plm.review2.opinion.service.ReviewOpinionHelper;
import com.bjsasc.plm.review2.opinion.service.ReviewOpinionService;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.plm.review2.order.service.ReviewOrderHelper;
import com.bjsasc.plm.review2.task.model.ReviewTask;
import com.bjsasc.plm.review2.task.service.ReviewTaskHelper;
import com.bjsasc.plm.review2.task.service.ReviewTaskService;


/**
 * �����������ѹ�����
 */
public class ReviewTaskUtil {
	private static ReviewOpinionService opnService = ReviewOpinionHelper.getService();
	private static ReviewTaskService taskService = ReviewTaskHelper.getService();
	/**
	 * ���������ύ
	 */
	public static void submitCheckTask(List<ReviewOpinion> opns){
		ReviewOpinion reviewOpinion = opns.get(0);
		ReviewOrder managedReview = ReviewOrderHelper.getService().findManagedReviewByInnerId(reviewOpinion.getReviewOrder().getInnerId());
		String executorId= ReviewObjectUtil.getDesignerIID(managedReview);
		
		/*taskService.closeRvTaskById(reviewOpinion.getReviewTask().getInnerId());
		ReviewTask rt = taskService.createReviewTask(managedReview.getName()+"(������)", ReviewTaskConstant.TASK_TYPE_ANSWER, managedReview.getInnerId(), executorId, "-1");
		for(ReviewOpinion rvOpn:opns){
			rvOpn.setReviewTask(rt);
			rvOpn.setOpinionState(String.valueOf(ReviewOpinion.OPNSTATE_SUBMIT));
			PersistHelper.getService().update(rvOpn);
		}*/
	}
	
	/**
	 * ��������ύ
	 */
	public static void submitAnswerTask(List<ReviewOpinion> reviewOpinionList){
		ReviewOpinion ro  = reviewOpinionList.get(0);
		String aaUserId = ro.getManageInfo().getCreateBy().getAaUserInnerId();
		ReviewOrder managedReview = ReviewOrderHelper.getService().findManagedReviewByInnerId(ro.getReviewOrder().getInnerId());
	/*	taskService.closeRvTaskById(ro.getReviewTask().getInnerId());
		ReviewTask rt = taskService.createReviewTask(managedReview.getName()+"(ȷ�����ʦ������)", ReviewTaskConstant.TASK_TYPE_EXPERT_CONFIRM, managedReview.getInnerId(), aaUserId, "-1");
		for(ReviewOpinion opn:reviewOpinionList){
			opn.setReviewTask(rt);
			opn.setOpinionState(String.valueOf(ReviewOpinion.OPNSTATE_ANSWEROVER));
			PersistHelper.getService().update(opn);
		}*/
	}
	
	/**
	 * ר��ȷ�����ʦ������
	 */
	public static void conFirmTask(ReviewTask rvTask){
		List<ReviewOpinion> opns = ReviewOpinionHelper.getService().getOpinions(rvTask.getInnerId());
		ReviewOrder managedReview = ReviewOrderHelper.getService().findManagedReviewByInnerId(opns.get(0).getReviewOrder().getInnerId());
		
		String executorId = getLeaderAAuserId(managedReview.getInnerId());
		taskService.closeRvTaskById(rvTask.getInnerId());
		ReviewTask rt = taskService.createReviewTask(managedReview.getName()+"(ȷ����ί���)", ReviewTaskConstant.TASK_TYPE_LEADER_CONFIRM, managedReview.getInnerId(), executorId, "-1");
		//��ѯ���������������
		/*for(ReviewOpinion rvOpn:opns){
			rvOpn.setReviewTask(rt);
			rvOpn.setConContents("��ȷ��");
			rvOpn.setOpinionState(String.valueOf(ReviewOpinion.OPNSTATE_CONFIRM));
			PersistHelper.getService().update(rvOpn);
		}*/
	}
	public static void noOpnConFirmTask(String taskInnerId,String orderInnerId){
		ReviewOrder managedReview = ReviewOrderHelper.getService().findManagedReviewByInnerId(orderInnerId);
		
		String executorId = getLeaderAAuserId(managedReview.getInnerId());
		taskService.closeRvTaskById(taskInnerId);
		taskService.createReviewTask(managedReview.getName()+"(ȷ����ί���)", ReviewTaskConstant.TASK_TYPE_LEADER_CONFIRM, managedReview.getInnerId(), executorId, "-1");
	}
	/**
	 * �鳤ȷ�������ύ
	 */
	public static void leaderConFirmTask(String taskId,String orderInnerId){
		List<ReviewOpinion> opns = ReviewOpinionHelper.getService().getOpinions(taskId);
		ReviewOrder rvOrder = ReviewOrderHelper.getService().findManagedReviewByInnerId(orderInnerId);
		
		String aaUserId = getLeaderAAuserId(rvOrder.getInnerId());
		taskService.closeRvTaskById(taskId);
		
		if(isSendTaskForLeader(orderInnerId)){
			taskService.createReviewTask(rvOrder.getName()+"(�鳤��д�������)", ReviewTaskConstant.TASK_TYPE_LEADER_CONCLUSION, orderInnerId, aaUserId, "-1");
		}
		if(!opns.isEmpty()){
			/*for(ReviewOpinion rvOpn:opns){
				rvOpn.setOpinionState(String.valueOf(ReviewOpinion.OPNSTATE_LEADER_CONFIRM));
				rvOpn.setLeadContents("�鳤��ȷ��");
				PersistHelper.getService().update(rvOpn);
			}*/
		}
	}
	public static void leaderConfirmTask(List<ReviewOpinion> reviewOpinionList){
		Map<String,ReviewOpinion> map = new HashMap<String,ReviewOpinion>();
		if(reviewOpinionList != null && reviewOpinionList.size()>0){
			String rvOrderId = reviewOpinionList.get(0).getReviewOrder().getInnerId();
			ReviewOrder rvOrder = ReviewOrderHelper.getService().findManagedReviewByInnerId(rvOrderId);
			for(ReviewOpinion rvOpn:reviewOpinionList){
				/*String taskId = rvOpn.getReviewTask().getInnerId();
				if(!map.containsKey(taskId)){
					map.put(taskId, rvOpn);
				}*/
			}
			for(String rv:map.keySet()){
				boolean flag = true;
				List<ReviewOpinion> rvOpns = opnService.getOpinions(rv);
				for(ReviewOpinion rvOpn:rvOpns){
					if(!String.valueOf(ReviewOpinion.OPNSTATE_LEADER_CONFIRM).equals(rvOpn.getOpinionState())){
						flag=false;
						break;
					}
				}
				if(flag){
					taskService.closeRvTaskById(rv);
				}
			}
			if(isSendTaskForLeader(rvOrderId)){
				String aaUserId = getLeaderAAuserId(rvOrderId);
				taskService.createReviewTask(rvOrder.getName()+"(�鳤��д�������)", ReviewTaskConstant.TASK_TYPE_LEADER_CONCLUSION, rvOrderId, aaUserId, "-1");
			}
		}
	}
	//�ж��Ƿ���鳤������д������ۺʹ������������
	public static boolean isSendTaskForLeader(String rvOrderId){
		boolean isSend = true;
		List<ReviewTask> taskList = ReviewTaskHelper.getService().getRvTasksByReview(rvOrderId);
		for(ReviewTask task : taskList){
			if(!task.getTaskState().equals(ReviewTask.RVTASK_STATE_END)){
				isSend = false;
				break;
			}
		}
		return isSend;
	}
	public static String getLeaderAAuserId(String rvOrderId){
		String executorId="";
		ReviewOrder managedReview = ReviewOrderHelper.getService().findManagedReviewByInnerId(rvOrderId);
		//��ȡ�����鳤
		ReviewCommitteeMemberHS rctm = ReviewCommitteeMemberHelper.getMemberService().getLeader(rvOrderId);
		if(rctm.getExpert() instanceof DomainReviewExpert){
			DomainReviewExpert dre =(DomainReviewExpert)rctm.getExpert();
			executorId = UserHelper.getService().getUser(dre.getUser().getInnerId()).getAaUserInnerId();
		}else{
			executorId = ReviewObjectUtil.getDesignerIID(managedReview);
		}
		return executorId;
	}
}