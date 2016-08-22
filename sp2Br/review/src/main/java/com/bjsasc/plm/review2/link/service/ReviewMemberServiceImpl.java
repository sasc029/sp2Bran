package com.bjsasc.plm.review2.link.service;

import java.util.ArrayList;
import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.Key;
import com.bjsasc.plm.core.persist.PersistHelper;
import com.bjsasc.plm.core.persist.model.Persistable;
import com.bjsasc.plm.core.review2.Reviewed;
import com.bjsasc.plm.review2.link.model.ReviewOrderMemberLink;
import com.bjsasc.plm.review2.order.model.ReviewOrder;

public class ReviewMemberServiceImpl implements ReviewMemberService {
	/**
	 * 查询所有数据
	 */
	@Override
	public List<ReviewOrderMemberLink> findAllReviewMemberLink() {
		String hql = "from ReviewOrderMemberLink";
		return (List<ReviewOrderMemberLink>)Helper.getPersistService().find(hql);
	}
	
	/**
	 * 查询单条数据
	 * @param innerId
	 * @return
	 */
	@Override
	public ReviewOrderMemberLink findReviewMemberLinkByInnerId(String innerId) {
		List<ReviewOrderMemberLink> reviewMember=Helper.getPersistService().find(" from RevieReviewOrderMemberLink WHERE innerId=?", innerId);
		if(!reviewMember.isEmpty()){
			return reviewMember.get(0);
		}
		return null;
	}

	@Override
	public void delReviewMemberLink(String[] ids) {
		for(int i=0; i<ids.length; i++){
			ReviewOrderMemberLink reviewMember = findReviewMemberLinkByInnerId(ids[i]);
			deleteReviewMemberLink(reviewMember);
		}
	}
	
	/**
	 * 删除记录
	 * @param reviewMember
	 */
	public void deleteReviewMemberLink(ReviewOrderMemberLink reviewMember) {
		Helper.getPersistService().delete(reviewMember);
	}
	
	public void delReviewMemberLinkByObjId(String[] ids) {
		for(int i=0; i<ids.length; i++){
			String strhql = "from ReviewOrderMemberLink t where t.toObjectRef.innerId=?";
			List list = PersistHelper.getService().find(strhql, ids[i]);
			ReviewOrderMemberLink reviewMember = (ReviewOrderMemberLink) list.get(0);
			deleteReviewMemberLink(reviewMember);
		}
	}
	
	/**
	 * 新增数据
	 * @param reviewMember
	 */
	@Override
	public void saveReviewMemberLink(ReviewOrderMemberLink reviewMember) {
		Helper.getPersistService().save(reviewMember);
	}

	/**
	 * 修改数据
	 * @param reviewMember
	 */
	@Override
	public void updateReviewMemberLink(ReviewOrderMemberLink reviewMember) {
		Helper.getPersistService().update(reviewMember);
	}

	@Override
	public void delReviewMemberLink(String id) {
		List<ReviewOrderMemberLink> reviewMember=Helper.getPersistService().find(" from ReviewOrderMemberLink WHERE fromObjectRef.innerId=?", id);
		for(ReviewOrderMemberLink rml : reviewMember){
			Helper.getPersistService().delete(rml);
		}
	}

	@Override
	public void delReviewMemberLink(ReviewOrder order, Reviewed reviewed) {
		ReviewOrderMemberLink reviewl=this.getReviewMemberLink( order,reviewed);
		delReviewMemberLink(reviewl.getInnerId());
		
	}
	public ReviewOrderMemberLink getReviewMemberLink( ReviewOrder order,Reviewed reviewed) {
		ReviewOrderMemberLink link = null;
		String strhql = "from ReviewOrderMemberLink t where t.toObjectRef.innerId=? and t.fromObjectRef.innerId=?";
		List list = PersistHelper.getService().find(strhql, reviewed.getInnerId(),order.getInnerId());
		if(!list.isEmpty()){
			link = (ReviewOrderMemberLink)list.get(0);
		}
		return link;
	}

	@Override
	public List<ReviewOrderMemberLink> getReviewMemberLinkByToObjId(String id) {
		String strhql = "from ReviewOrderMemberLink t where t.toObjectRef.innerId=?";
		List<ReviewOrderMemberLink> list = PersistHelper.getService().find(strhql, id);
		return list;
	}

	@Override
	public List<Reviewed> getReviewedListByOrder(ReviewOrder reviewOrder) {
		List<Persistable> list =  PersistHelper.getService().navigate(reviewOrder, Key.LINK_FROM, ReviewOrderMemberLink.CLASSID, true);
		List<Reviewed> newList = new ArrayList<Reviewed>();
		for(int i=0,l=list.size();i<l; i++){
			Reviewed rv = (Reviewed)list.get(i); 
			newList.add(rv);
		}
		return newList;
	}
	
}
