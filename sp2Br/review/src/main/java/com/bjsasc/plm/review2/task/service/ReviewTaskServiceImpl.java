package com.bjsasc.plm.review2.task.service;

import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.persist.PersistHelper;
import com.bjsasc.plm.core.system.principal.UserHelper;
import com.bjsasc.plm.review2.object.service.ReviewObjectHelper;
import com.bjsasc.plm.review2.order.service.ReviewOrderHelper;
import com.bjsasc.plm.review2.task.model.ReviewTask;
import com.bjsasc.plm.review2.util.ReviewTaskReminderUtil;

public class ReviewTaskServiceImpl implements ReviewTaskService {

	@Override
	public List<ReviewTask> getRvTasksByReview(String rvOrderId) {
		String hql = "from ReviewTask where reviewOrderRef.innerId=?";
		return (List<ReviewTask>)Helper.getPersistService().find(hql,rvOrderId);
	}

	@Override
	public List<ReviewTask> getRvTasksByState(String taskState,String userId) {
		String hql = "from ReviewTask where taskState=? and executorRef.innerId=?";
		return (List<ReviewTask>)Helper.getPersistService().find(hql,taskState,userId);
	}

	@Override
	public ReviewTask createReviewTask(String name,String taskRole,String rvOrderId,String aaUserId,String objectId) {
		ReviewTask rvTask= new ReviewTask();
		rvTask.setTaskName(name);
		rvTask.setTaskState(ReviewTask.RVTASK_STATE_DEAL);
		rvTask.setTaskRole(taskRole);
		rvTask.setReviewOrder(ReviewOrderHelper.getService().findManagedReviewByInnerId(rvOrderId));
		rvTask.setReviewObject(ReviewObjectHelper.getService().findReviewObjectById(objectId));
		rvTask.setExecutor(UserHelper.getService().getUser(aaUserId));
		PersistHelper.getService().save(rvTask);
		ReviewTaskReminderUtil.sendEmailReminder(aaUserId, name);
		return rvTask;
	}

	@Override
	public ReviewTask getRvTasksById(String taskId) {
		String hql = "from ReviewTask t where t.innerId=?";
		List<ReviewTask> rt = Helper.getPersistService().find(hql,taskId);
		if(!rt.isEmpty()){
			return rt.get(0);
		}
		return null;
	}

	@Override
	public void closeRvTaskById(String taskId) {
		ReviewTask rt=this.getRvTasksById(taskId);
		rt.setTaskState(ReviewTask.RVTASK_STATE_END);
		PersistHelper.getService().update(rt);
	}

	@Override
	public List<ReviewTask> getRvTasksByExecutorAndOrder(String userId,String orderId) {
		String hql = "from ReviewTask where executorRef.innerId=? and reviewOrderRef.innerId=?";
		return (List<ReviewTask>)Helper.getPersistService().find(hql,userId,orderId);
	}
}
