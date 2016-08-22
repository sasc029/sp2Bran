package com.bjsasc.plm.review2.item.service;

import java.util.List;

import com.bjsasc.plm.review2.item.model.ReviewItem;


public interface ReviewItemService {

	/**
	 * ��ѯ���е�����
	 * @return
	 */
	public List<ReviewItem> findAllReviewItem();
	/**
	 * ��������
	 * @param reviewItem
	 */
	public void saveReviewItem(ReviewItem reviewItem);
	/**
	 * �޸�����
	 * @param reviewItem
	 */
	public void updateReviewItem(ReviewItem reviewItem);
	/**
	 * ����ReviewItem��innerId��ѯ��������
	 * @param innerId
	 * @return
	 */
	public ReviewItem findReviewItemByInnerId(String innerId);
	/**
	 * ɾ����¼
	 */
	public void delReviewItem(String[] ids);
	/**
	 * ��������id��ȡ��������
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
