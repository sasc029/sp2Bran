package com.bjsasc.plm.review2.task.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bjsasc.platform.filecomponent.model.PtFileItemBean;
import com.bjsasc.plm.Helper;
import com.bjsasc.plm.client.docserverclient.datatype.SignContent;
import com.bjsasc.plm.core.attachment.AttachHelper;
import com.bjsasc.plm.core.persist.PersistHelper;
import com.bjsasc.plm.core.persist.model.Persistable;
import com.bjsasc.plm.core.review2.Reviewed;
import com.bjsasc.plm.core.session.SessionHelper;
import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.core.system.task.StandardTask;
import com.bjsasc.plm.core.system.task.Task;
import com.bjsasc.plm.core.system.task.TaskHelper;
import com.bjsasc.plm.core.system.task.TaskState;
import com.bjsasc.plm.review2.committee.model.ReviewCommitteeMemberHS;
import com.bjsasc.plm.review2.committee.service.ReviewCommitteeMemberHelper;
import com.bjsasc.plm.review2.common.BaseAction;
import com.bjsasc.plm.review2.conclusion.model.ReviewConclusionHS;
import com.bjsasc.plm.review2.conclusion.service.ReviewConclusionHelper;
import com.bjsasc.plm.review2.constant.ReviewConclusionConstant;
import com.bjsasc.plm.review2.constant.ReviewObjectConstant;
import com.bjsasc.plm.review2.constant.ReviewOrderConstant;
import com.bjsasc.plm.review2.constant.ReviewRoleConstant;
import com.bjsasc.plm.review2.constant.ReviewTaskConstant;
import com.bjsasc.plm.review2.expert.model.DomainReviewExpert;
import com.bjsasc.plm.review2.expert.model.OuterReviewExpert;
import com.bjsasc.plm.review2.item.model.ReviewItem;
import com.bjsasc.plm.review2.item.service.ReviewItemHelper;
import com.bjsasc.plm.review2.link.service.ReviewMemberHelper;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.object.service.ReviewObjectHelper;
import com.bjsasc.plm.review2.opinion.model.ReviewOpinion;
import com.bjsasc.plm.review2.opinion.service.ReviewOpinionHelper;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.plm.review2.order.service.ReviewOrderHelper;
import com.bjsasc.plm.review2.result.model.ReviewResult;
import com.bjsasc.plm.review2.result.service.ReviewResultHelper;
import com.bjsasc.plm.review2.result.service.ReviewResultService;
import com.bjsasc.plm.review2.role.model.ReviewRoleUser;
import com.bjsasc.plm.review2.role.service.ReviewRoleUserHelper;
import com.bjsasc.plm.review2.task.model.ReviewTask;
import com.bjsasc.plm.review2.task.service.ReviewTaskHelper;
import com.bjsasc.plm.review2.util.ReviewObjectUtil;
import com.bjsasc.plm.review2.util.ReviewOrderConfigUtil;
import com.bjsasc.plm.review2.util.ReviewOrderUtil;
import com.bjsasc.plm.review2.util.ReviewTaskReminderUtil;
import com.bjsasc.plm.sign.SignHelper;
import com.bjsasc.plm.sign.SignUtil;

/**
 * ��������action
 *
 */
public class ReviewTaskAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3032861471010845097L;
	
	private List<ReviewOpinion> reviewOpinionList;
	
	private ReviewResultService resultService = ReviewResultHelper.getService();

	public List<ReviewOpinion> getReviewOpinionList() {
		return reviewOpinionList;
	}

	public void setReviewOpinionList(List<ReviewOpinion> reviewOpinionList) {
		this.reviewOpinionList = reviewOpinionList;
	}

	//��������
	public void startReviewTask() throws Exception{
		String reviewOrderOid =  request.getParameter("oid");
		ReviewOrder managedReview= (ReviewOrder)Helper.getPersistService().getObject(reviewOrderOid);
		String executorId = ReviewObjectUtil.getDesignerIID(managedReview);
		List<Reviewed> rvObjs=ReviewMemberHelper.getService().getReviewedListByOrder(managedReview);
		
		boolean isLeader = false;
		List<ReviewCommitteeMemberHS> committee = ReviewCommitteeMemberHelper.getMemberService().getMembersByReviewOrderId(Helper.getInnerId(reviewOrderOid));
		int num = Integer.parseInt(ReviewOrderConfigUtil.getLocalReviewConfig("committeeNum"));
		if(committee.size()<num){
			response.getWriter().print("selectCommittee");
			return;
		}
		for(ReviewCommitteeMemberHS c : committee){
			if(c.getUserLevel().equals(ReviewCommitteeMemberHS.USERLEVEL_OFFICER)){
				isLeader = true;
				break;
			}
		}
        if(!isLeader){
        	response.getWriter().print("selectLeader");
			return;
        }
		//��ȡ��������
        ReviewObject Obj = (ReviewObject)rvObjs.get(0);
		List<ReviewRoleUser> cbzlList = ReviewRoleUserHelper.getReviewRoleService().getAllReviewPrincipal(ReviewRoleConstant.REVIEW_CBZL,Obj.getSourceSiteInnerId());
		if(cbzlList== null || cbzlList.size()==0){
			response.getWriter().print("failcbzl");
			return;
		}
			
		List<ReviewCommitteeMemberHS> list = ReviewCommitteeMemberHelper.getMemberService().getMembersByReviewOrderId(managedReview.getInnerId());
		List<ReviewCommitteeMemberHS> outerExpertList = new ArrayList<ReviewCommitteeMemberHS>();
		List<ReviewCommitteeMemberHS> domainExpertList = new ArrayList<ReviewCommitteeMemberHS>();
		for(ReviewCommitteeMemberHS rcm : list){
			if(isOuterExpert(rcm)){
				OuterReviewExpert outerExpert = (OuterReviewExpert)rcm.getExpert();
				outerExpertList.add(rcm);
				resultService.createReviewResult(ReviewResult.RESULT_TYPE_OUTER, executorId, managedReview, Obj, outerExpert);
			}else{
				domainExpertList.add(rcm);
			}
		}
		if(!outerExpertList.isEmpty()){
			ReviewTaskHelper.getService().createReviewTask(managedReview.getName(), ReviewTaskConstant.TASK_ROLE_DESIGNER, managedReview.getInnerId(),executorId,Obj.getInnerId() );
			//�����������ʼ�
			for(int i =0;i<cbzlList.size();i++){
				ReviewRoleUser reviewRoleUser = cbzlList.get(i);
				ReviewTaskReminderUtil.sendEmailReminder(reviewRoleUser.getUser().getInnerId(), managedReview.getName()+"(��������ר�Һ���)");
			}
		}
		for(ReviewCommitteeMemberHS rcm : domainExpertList){
			DomainReviewExpert dre = (DomainReviewExpert)rcm.getExpert();
			//String executorOid = Helper.getOid(UserHelper.getService().getUser(dre.getUser().getInnerId()));
			 String executorIId = dre.getUser().getInnerId();
			 resultService.createReviewResult(ReviewResult.RESULT_TYPE_DOMAIN, executorIId, managedReview, Obj, null);
			 if(rcm.getUserLevel().equals(ReviewCommitteeMemberHS.USERLEVEL_OFFICER)){
				 ReviewTaskHelper.getService().createReviewTask(managedReview.getName(), ReviewTaskConstant.TASK_ROLE_CHARGEMAN, managedReview.getInnerId(), executorIId, Obj.getInnerId());
			 }else{
				 ReviewTaskHelper.getService().createReviewTask(managedReview.getName(), ReviewTaskConstant.TASK_ROLE_EXPERT, managedReview.getInnerId(), executorIId, Obj.getInnerId());
			 }
			//this.createReviewTask(managedReview, executorOid,ReviewTaskConstant.TASK_TYPE_CHECK,"(��������)");
			//ReviewTaskReminderUtil.sendEmailReminder(dre.getUser().getInnerId(), managedReview.getName()+"(��������)");
		}
		managedReview.setState(ReviewOrderConstant.TASK_MR_START);
		managedReview.setStartTime(new Date().getTime());
		
		for(int i=0;i<rvObjs.size();i++){
			ReviewObject rvObj=(ReviewObject)rvObjs.get(i);
			rvObj.setReviewState(ReviewObjectConstant.RVOBJ_STATE_DEALING);
			ReviewObjectHelper.getService().updateReviewObject(rvObj);
		}
		ReviewOrderHelper.getService().updateManagedReview(managedReview);
		response.getWriter().print("success");
	}
	
	public boolean isOuterExpert(ReviewCommitteeMemberHS rcm){
		if(rcm.getExpert() instanceof OuterReviewExpert){
			return true;
		}else{
			return false;
		}
	}
	
	public void submitReviewTask() throws Exception{
		String taskOid =  request.getParameter("oid");
		User user=SessionHelper.getService().getUser();
		StandardTask task=(StandardTask)Helper.getPersistService().getObject(taskOid);
		List<Persistable> list = TaskHelper.getResService().getTaskResources(task);//ȡ����
		Persistable p =list.get(0);
		ReviewOrder managedReview = ReviewOrderHelper.getService().findManagedReviewByInnerId(p.getInnerId());
		if(managedReview!=null){
			if(task.getTaskType().equals(ReviewTaskConstant.TASK_TYPE_CHECK)){
				boolean flag = true;
				List<ReviewOpinion> opinions = ReviewOpinionHelper.getService().getOpinionsByReview(managedReview.getInnerId());
				for(ReviewOpinion opinion:opinions){
				   if(opinion.getManageInfo().getCreateByRef().getInnerId().equals(user.getInnerId())){
					   flag = false;
				   }	
				}
				if(flag){
					response.getWriter().print("true");
				}
			}else if(task.getTaskType().equals(ReviewTaskConstant.TASK_TYPE_LEADER_CONFIRM)){//�ж��鳤�Ƿ���д��������
				boolean isCheck = true;//����Ƿ�����������Ѿ������鳤ȷ��״̬
				List<Task> taskList = TaskHelper.getResService().getTasksByResource(managedReview);
				for(Task t : taskList){
					//����������δ���
					if(((StandardTask)t).getTaskType().equals(ReviewTaskConstant.TASK_TYPE_CHECK) && (!t.getState().equals(TaskState.COMPLETED))){
						isCheck = false;
						break;
					}
				}
				if(isCheck){
					List<ReviewOpinion> reviewOpinionList = ReviewOpinionHelper.getService().getOpinionsByReview(managedReview.getInnerId());
					for(ReviewOpinion reviewOpinion : reviewOpinionList){
						if(reviewOpinion.getOpinionStateInt() < ReviewOpinion.OPNSTATE_LEADER_CONFIRM){//�������δ�����鳤ȷ��
							isCheck = false;
							break;
						}
					}
					if(isCheck){
						List<ReviewItem> items=ReviewItemHelper.getService().getReviewItemByReviewAndCreatorId(managedReview.getInnerId(), user.getInnerId());
					    if(items==null || items.size()<1){
					    	response.getWriter().print("success");
					    }
					}
			    }
			}
		}
	}
	
	public void leaderSubmitReviewTask() throws Exception{
		String reviewOrderOid =  request.getParameter("oid");
		ReviewOrder managedReview = (ReviewOrder)Helper.getPersistService().getObject(reviewOrderOid);
		
		List<ReviewConclusionHS> reviewConclusionList = ReviewConclusionHelper.getService().findReviewConclusionByReviewOrder(managedReview.getInnerId());
		//��δ��д�������
		if(reviewConclusionList == null || reviewConclusionList.size()==0){
			response.getWriter().print("fail");
			return;
		}
		//���������δ�ύ
		ReviewConclusionHS reviewConclusion = reviewConclusionList.get(0);
		if(reviewConclusion == null || !reviewConclusion.getState().equals(ReviewConclusionConstant.RC_STATE_LEADER_SUBMIT)){
			response.getWriter().print("fail");
			return;
		} 
		
		//�ر��鳤������
		User user = SessionHelper.getService().getUser();
		List<ReviewTask> rvTasks = ReviewTaskHelper.getService().getRvTasksByExecutorAndOrder(user.getInnerId(), managedReview.getInnerId());
		if(!rvTasks.isEmpty()){
			ReviewTask rvTask =rvTasks.get(0);
			ReviewTaskHelper.getService().closeRvTaskById(rvTask.getInnerId());
		}
		List<Reviewed> rvObjs=ReviewMemberHelper.getService().getReviewedListByOrder(managedReview);
		ReviewObject obj = (ReviewObject)rvObjs.get(0);
		List<ReviewRoleUser> psfzrlList = ReviewRoleUserHelper.getReviewRoleService().getAllReviewPrincipal(ReviewRoleConstant.REVIEW_PSFZR,obj.getSourceSiteInnerId());
		List<String> resourceOids = new ArrayList<String>();
		resourceOids.add(Helper.getOid(managedReview));
		for(ReviewRoleUser r : psfzrlList){
			String executorOid = r.getUser().getInnerId();
			ReviewTaskHelper.getService().createReviewTask(managedReview.getName(), ReviewTaskConstant.TASK_ROLE_EXECUTOR, managedReview.getInnerId(), executorOid, obj.getInnerId());
		}
		
		response.getWriter().print("success");
	}
	
	public void confirmReviewTask() throws Exception{
		String reviewOrderId =  request.getParameter("oid");
		ReviewOrder managedReview= (ReviewOrder)Helper.getPersistService().getObject(reviewOrderId);
		ReviewObject rvObj = (ReviewObject)ReviewMemberHelper.getService().getReviewedListByOrder(managedReview).get(0);
		
		List<ReviewConclusionHS> listrc = ReviewConclusionHelper.getService().findReviewConclusionByReviewOrder(managedReview.getInnerId());
		ReviewConclusionHS rcl = listrc.get(0);
		if(!rcl.getState().equals(ReviewConclusionConstant.RC_STATE_PRINCIPAL_SUBMIT)){
			response.getWriter().print("notSubmitRc");
			return;
		}
		
		//ȷ���ر��������������֤����
		this.signManagedReview(managedReview);
		ReviewTaskReminderUtil.sendEmailReminder(ReviewObjectUtil.getDesignerAAUserInnerId(managedReview), managedReview.getName()+"(�������)");
		//��ȡȫ����������
		List<ReviewRoleUser> cbzlList = ReviewRoleUserHelper.getReviewRoleService().getAllReviewPrincipal(ReviewRoleConstant.REVIEW_CBZL,rvObj.getSourceSiteInnerId());
		for(ReviewRoleUser rru : cbzlList){
			ReviewTaskReminderUtil.sendEmailReminder(rru.getUser().getInnerId(),managedReview.getName()+"(�������)");
		}
//		��ȡȫ������ר��
		List<ReviewCommitteeMemberHS> rcmList = ReviewCommitteeMemberHelper.getMemberService().getMembersByReviewOrderId(managedReview.getInnerId());
		for(ReviewCommitteeMemberHS rcm : rcmList){
			if(rcm.getExpert() instanceof DomainReviewExpert){
				ReviewTaskReminderUtil.sendEmailReminder(((DomainReviewExpert)rcm.getExpert()).getUser().getInnerId(),managedReview.getName()+"(�������)");
			}
		}
		//��ȡ��ǰ����ȫ������
		List<ReviewTask> taskList = ReviewTaskHelper.getService().getRvTasksByReview(managedReview.getInnerId());
		for(ReviewTask t : taskList){
			if(!t.getTaskState().equals(ReviewTask.RVTASK_STATE_END)){
				ReviewTaskHelper.getService().closeRvTaskById(t.getInnerId());
			}
		}
		
		rvObj.setReviewState(ReviewObjectConstant.RVOBJ_STATE_FINISH);
		ReviewObjectHelper.getService().updateReviewObject(rvObj);
		managedReview.setFinishTime(new Date().getTime());
		managedReview.setState(ReviewOrderConstant.TASK_MR_REVIEW_OVER);
		ReviewOrderHelper.getService().updateManagedReview(managedReview);
		response.getWriter().print("success");
	}
	
	/**
	 * ����ǩ��
	 */
	private void signManagedReview(ReviewOrder managedReview){
		//��ȡ�������ļ�
		PtFileItemBean file = AttachHelper.getAttachService().getMainFile(managedReview);
		String signType = SignUtil.getSignType(file);
		
		//��ȡ����ǩ����Ϣ
		Map<String, SignContent> signInfo = ReviewOrderUtil.getManagedReviewSignInfo(managedReview);
		Map<String, SignContent> tableInfo = ReviewOrderUtil.getManagedReviewTableInfo(managedReview);
		signInfo.putAll(tableInfo);
		
		SignHelper.getService().sign(file, signInfo, signType, PersistHelper.getService().getOid(ReviewOrder.CLASSID, managedReview.getInnerId()));
	}
}
