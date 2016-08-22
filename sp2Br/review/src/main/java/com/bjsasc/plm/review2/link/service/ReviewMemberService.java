package com.bjsasc.plm.review2.link.service;

import java.util.List;

import com.bjsasc.plm.core.review2.Reviewed;
import com.bjsasc.plm.review2.link.model.ReviewOrderMemberLink;
import com.bjsasc.plm.review2.order.model.ReviewOrder;

public interface ReviewMemberService {

	/**
	 * ��ѯ���е�����
	 * @return
	 */
	public List<ReviewOrderMemberLink> findAllReviewMemberLink();
	/**
	 * ��������
	 * @param reviewMember
	 */
	public void saveReviewMemberLink(ReviewOrderMemberLink reviewMember);
	/**
	 * �޸�����
	 * @param reviewMember
	 */
	public void updateReviewMemberLink(ReviewOrderMemberLink reviewMember);
	/**
	 * ����ReviewMemberLink��innerId��ѯ��������
	 * @param innerId
	 * @return
	 */
	public ReviewOrderMemberLink findReviewMemberLinkByInnerId(String innerId);
	/**
	 * ɾ����¼
	 */
	public void delReviewMemberLink(String[] ids);
	
	/**
	 * ɾ����¼
	 */
	public void delReviewMemberLink(String id);
	/**
	 * ����
	 */
	public void delReviewMemberLink(ReviewOrder order, Reviewed reviewed);
	
	/**
	 * ���ݶ���idɾ����¼
	 */
	public void delReviewMemberLinkByObjId(String[] ids);
	
	/**
	 * ���ݶ���id��ѯ��¼
	 */
	public List<ReviewOrderMemberLink> getReviewMemberLinkByToObjId(String id);
	
	/**
	 * ����order��ѯ����Reviewed
	 * @param order
	 * @return
	 */
	public List<Reviewed> getReviewedListByOrder(ReviewOrder reviewOrder);
}
