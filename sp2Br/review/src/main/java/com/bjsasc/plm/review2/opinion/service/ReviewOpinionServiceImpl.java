package com.bjsasc.plm.review2.opinion.service;

import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.persist.PersistHelper;
import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.core.system.principal.UserHelper;
import com.bjsasc.plm.review2.committee.model.ReviewCommitteeMemberHS;
import com.bjsasc.plm.review2.expert.model.DomainReviewExpert;
import com.bjsasc.plm.review2.expert.model.OuterReviewExpert;
import com.bjsasc.plm.review2.expert.model.ReviewExpert;
import com.bjsasc.plm.review2.opinion.model.ReviewOpinion;
import com.bjsasc.plm.review2.order.model.ReviewOrder;

public class ReviewOpinionServiceImpl implements ReviewOpinionService {
	/**
	 * 查询所有数据
	 */
	@Override
	public List<ReviewOpinion> getOpinionsByReview(String rvOrderId) {
		String hql = "from ReviewOpinion where reviewOrderRef.innerId=? order by orderFlag asc";
		return (List<ReviewOpinion>)Helper.getPersistService().find(hql,rvOrderId);
	}

	@Override
	public ReviewOpinion newOpinion() {
		ReviewOpinion opinion = new ReviewOpinion();
		opinion.setOpinionState(ReviewOpinion.OPNSTATE_NEW+"");
		opinion.setAnswerTime(0);
		return opinion;
	}

	@Override
	public ReviewOpinion createOpinion(ReviewOpinion op) {
			PersistHelper.getService().save(op);
			return op;
	}
	@Override
	public void delReviewOpinions(String[] ids) {
		for(int i=0; i<ids.length; i++){
			ReviewOpinion ro = getOpinionsById(ids[i]);
			delReviewOpinions(ro);
		}
	}
	
	public void delReviewOpinions(ReviewOpinion ro) {
		Helper.getPersistService().delete(ro);
	}

	@Override
	public ReviewOpinion getOpinionsById(String innerId) {
		String hql = "from ReviewOpinion where innerId=?";
		List<ReviewOpinion> opns = PersistHelper.getService().find(hql,innerId);
		if(!opns.isEmpty()){
			return opns.get(0);
		}
		return null;
	}

	@Override
	public boolean canConfirmOpinion(ReviewOrder rv, User user) {
		boolean b = false;
		//只有评审组长才能确认
		String strhql = "from ReviewCommitteeMemberHS t where t.reviewOrderRef.innerId=? and t.userLevel=?";
		List list = PersistHelper.getService().find(strhql, rv.getInnerId(),ReviewCommitteeMemberHS.USERLEVEL_OFFICER);
		if(list!=null && list.size()>0){
			ReviewExpert member = (ReviewExpert)((ReviewCommitteeMemberHS)list.get(0)).getExpert();
			if(member instanceof DomainReviewExpert){
				User newUser = UserHelper.getService().getUser(((DomainReviewExpert) member).getUser().getInnerId());
			    if(newUser.getInnerId().equals(user.getInnerId())){
			    	b = true;
			    }
			}else if(member instanceof OuterReviewExpert){
				if(user.getInnerId().equals(rv.getManageInfo().getCreateBy().getInnerId())){
					b = true;
				}
			}
		}
		return b;
	}

	@Override
	public void updateReviewOpinions(ReviewOpinion op) {
		Helper.getPersistService().update(op);
	}

	@Override
	public List<ReviewOpinion> getOpinions(String rvOrderId,String opnState,String UserInnerId) {
		String hql = "from ReviewOpinion where reviewOrderRef.innerId=? and opinionState=? and manageInfo.createByRef.innerId=?";
		return (List<ReviewOpinion>)Helper.getPersistService().find(hql,rvOrderId,opnState,UserInnerId);
	}
	
	@Override
	public List<ReviewOpinion> getOpinions(String rvOrderId,String UserInnerId) {
		String hql = "from ReviewOpinion where reviewOrderRef.innerId=? and manageInfo.createByRef.innerId=?";
		return (List<ReviewOpinion>)Helper.getPersistService().find(hql,rvOrderId,UserInnerId);
	}
	@Override
	public List<ReviewOpinion> getOpinions(String rvOrderId,String opnState,String type,String UserInnerId) {
		String hql = "from ReviewOpinion where reviewOrderRef.innerId=? and opinionState=? and type=? and manageInfo.createByRef.innerId=?";
		return (List<ReviewOpinion>)Helper.getPersistService().find(hql,rvOrderId,opnState,type,UserInnerId);
	}

	@Override
	public List<ReviewOpinion> getOpinions(String taskId) {
		String hql = "from ReviewOpinion where reviewTaskRef.innerId=?";
		return (List<ReviewOpinion>)Helper.getPersistService().find(hql,taskId);
	}

	@Override
	public List<ReviewOpinion> getOpinionsByOutUserId(String rvOrderId, String outerUserId) {
		String hql = "from ReviewOpinion where reviewOrderRef.innerId=? and outerExpertRef.innerId=?";
		return (List<ReviewOpinion>)Helper.getPersistService().find(hql,rvOrderId,outerUserId);
	}
}
