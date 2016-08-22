package com.bjsasc.plm.review2.link.service;

import java.util.List;

import com.bjsasc.plm.core.review2.Reviewed;
import com.bjsasc.plm.review2.link.model.ReviewOrderMemberLink;
import com.bjsasc.plm.review2.order.model.ReviewOrder;

public interface ReviewMemberService {

	/**
	 * 查询所有的数据
	 * @return
	 */
	public List<ReviewOrderMemberLink> findAllReviewMemberLink();
	/**
	 * 新增数据
	 * @param reviewMember
	 */
	public void saveReviewMemberLink(ReviewOrderMemberLink reviewMember);
	/**
	 * 修改数据
	 * @param reviewMember
	 */
	public void updateReviewMemberLink(ReviewOrderMemberLink reviewMember);
	/**
	 * 根据ReviewMemberLink的innerId查询单条数据
	 * @param innerId
	 * @return
	 */
	public ReviewOrderMemberLink findReviewMemberLinkByInnerId(String innerId);
	/**
	 * 删除记录
	 */
	public void delReviewMemberLink(String[] ids);
	
	/**
	 * 删除记录
	 */
	public void delReviewMemberLink(String id);
	/**
	 * 根据
	 */
	public void delReviewMemberLink(ReviewOrder order, Reviewed reviewed);
	
	/**
	 * 根据对象id删除记录
	 */
	public void delReviewMemberLinkByObjId(String[] ids);
	
	/**
	 * 根据对象id查询记录
	 */
	public List<ReviewOrderMemberLink> getReviewMemberLinkByToObjId(String id);
	
	/**
	 * 根据order查询所有Reviewed
	 * @param order
	 * @return
	 */
	public List<Reviewed> getReviewedListByOrder(ReviewOrder reviewOrder);
}
