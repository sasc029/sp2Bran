package com.bjsasc.plm.review2.item.service;

import java.util.List;

import com.bjsasc.plm.review2.item.model.ReviewItem;


public interface ReviewItemService {

	/**
	 * 查询所有的数据
	 * @return
	 */
	public List<ReviewItem> findAllReviewItem();
	/**
	 * 新增数据
	 * @param reviewItem
	 */
	public void saveReviewItem(ReviewItem reviewItem);
	/**
	 * 修改数据
	 * @param reviewItem
	 */
	public void updateReviewItem(ReviewItem reviewItem);
	/**
	 * 根据ReviewItem的innerId查询单条数据
	 * @param innerId
	 * @return
	 */
	public ReviewItem findReviewItemByInnerId(String innerId);
	/**
	 * 删除记录
	 */
	public void delReviewItem(String[] ids);
	/**
	 * 根据评审单id获取代办事项
	 */
	public List<ReviewItem> getReviewItemByReview(String reviewIid);
	
	public ReviewItem newReviewItem();
	
	public List<ReviewItem> getNotFinishStateReviewItemByReview(String reviewIid);
	
	public List<ReviewItem> getReviewItemByReviewAndExecutor(String reviewIid,String executorId);
	
	public List<ReviewItem> getReviewItemByExecutor(String executorId,String isConfirm);
	
	public List<ReviewItem> getReviewItemBySender(String senderId,String isConfirm);
	
	public List<ReviewItem> getReviewItemByReviewAndCreatorId(String reviewIid,String creatorId);
	
	public List<ReviewItem> getReviewItemBySiteId(String sourceSiteInnerId,String isConfirm);
}
