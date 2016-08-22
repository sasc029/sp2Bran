package com.bjsasc.plm.review2.item.service;

import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.persist.PersistHelper;
import com.bjsasc.plm.review2.item.model.ReviewItem;

public class ReviewItemServiceImpl implements ReviewItemService {
	/**
	 * 查询所有数据
	 * @param reviewItem
	 */
	@Override
	public List<ReviewItem> findAllReviewItem() {
		String hql = "from ReviewItem";
		return (List<ReviewItem>)Helper.getPersistService().find(hql);
	}
	
	/**
	 * 查询单条数据
	 * @param innerId
	 * @return
	 */
	@Override
	public ReviewItem findReviewItemByInnerId(String innerId) {
		List<ReviewItem> reviewItem=Helper.getPersistService().find(" from ReviewItem WHERE innerId=?", innerId);
		if(!reviewItem.isEmpty()){
			return reviewItem.get(0);
		}
		return null;
	}

	@Override
	public void delReviewItem(String[] ids) {
		for(int i=0; i<ids.length; i++){
			ReviewItem reviewItem = findReviewItemByInnerId(ids[i]);
			deleteReviewItem(reviewItem);
		}
	}
	
	/**
	 * 删除记录
	 * @param reviewItem
	 */
	public void deleteReviewItem(ReviewItem reviewItem) {
		Helper.getPersistService().delete(reviewItem);
	}

	/**
	 * 新增数据
	 * @param reviewItem
	 */
	@Override
	public void saveReviewItem(ReviewItem reviewItem) {
		Helper.getPersistService().save(reviewItem);
	}

	/**
	 * 修改数据
	 * @param reviewItem
	 */
	@Override
	public void updateReviewItem(ReviewItem reviewItem) {
		Helper.getPersistService().update(reviewItem);
	}

	@Override
	public List<ReviewItem> getReviewItemByReview(String reviewIid) {
			String strhql = "from ReviewItem t where t.reviewOrderRef.innerId=? order by t.manageInfo.createTime desc";	
			List<ReviewItem> tasks = PersistHelper.getService().find(strhql, reviewIid);
			return tasks;
	}
	
	@Override
	public List<ReviewItem> getNotFinishStateReviewItemByReview(String reviewIid) {
			String strhql = "from ReviewItem t where t.reviewOrderRef.innerId=? and t.state!=? order by t.manageInfo.createTime desc";	
			List<ReviewItem> tasks = PersistHelper.getService().find(strhql, reviewIid,ReviewItem.TASKSTATE_REPLYAFFIRM);
			return tasks;
	}
    
	@Override
	public List<ReviewItem> getReviewItemByReviewAndExecutor(String reviewIid,String executorId) {
			String strhql = "from ReviewItem t where t.reviewOrderRef.innerId=? and t.executorRef.innerId=? order by t.manageInfo.createTime desc";	
			List<ReviewItem> tasks = PersistHelper.getService().find(strhql, reviewIid,executorId);
			return tasks;
	}
	
	@Override
	public List<ReviewItem> getReviewItemByExecutor(String executorId,String isConfirm) {
			if("all".equals(isConfirm)){
				String strhql = "from ReviewItem t where t.executorRef.innerId=? order by t.manageInfo.createTime desc";
				List<ReviewItem> tasks = PersistHelper.getService().find(strhql, executorId);
				return tasks;
			}else{
				String strhql = "from ReviewItem t where t.executorRef.innerId=? and t.isConfirm=? order by t.manageInfo.createTime desc";
				List<ReviewItem> tasks = PersistHelper.getService().find(strhql, executorId,isConfirm);
				return tasks;
			}
	}
	
	@Override
	public ReviewItem newReviewItem() {
		    ReviewItem item = new ReviewItem();
		    item.setClassId(ReviewItem.CLASSID);
		    item.setState(ReviewItem.TASKSTATE_NEW);
		    item.setItemResult("0");
		    item.setIsConfirm("0");
			return item;
	}

	@Override
	public List<ReviewItem> getReviewItemByReviewAndCreatorId(String reviewIid,
			String creatorId) {
		String strhql = "from ReviewItem t where t.reviewOrderRef.innerId=? and t.manageInfo.createByRef.innerId=? order by t.manageInfo.createTime desc";	
		List<ReviewItem> tasks = PersistHelper.getService().find(strhql, reviewIid,creatorId);
		return tasks;
	}

	@Override
	public List<ReviewItem> getReviewItemBySender(String senderId,String isConfirm) {
			
		if("all".equals(isConfirm)){
			String strhql = "from ReviewItem t where t.senderRef.innerId=? order by t.manageInfo.createTime desc";
			List<ReviewItem> tasks = PersistHelper.getService().find(strhql, senderId);
			return tasks;
		}else{
			String strhql = "from ReviewItem t where t.senderRef.innerId=? and t.isConfirm=? order by t.manageInfo.createTime desc";
			List<ReviewItem> tasks = PersistHelper.getService().find(strhql, senderId,isConfirm);
			return tasks;
		}
	}

	@Override
	public List<ReviewItem> getReviewItemBySiteId(String sourceSiteInnerId,String isConfirm) {
		if("all".equals(isConfirm)){
			String strhql = "from ReviewItem t where t.sourceSiteInnerId=? order by t.manageInfo.createTime desc";
			List<ReviewItem> tasks = PersistHelper.getService().find(strhql, sourceSiteInnerId);
			return tasks;
		}else{
			String strhql = "from ReviewItem t where t.sourceSiteInnerId=? and t.isConfirm=? order by t.manageInfo.createTime desc";
			List<ReviewItem> tasks = PersistHelper.getService().find(strhql, sourceSiteInnerId,isConfirm);
			return tasks;
		}
	}
}