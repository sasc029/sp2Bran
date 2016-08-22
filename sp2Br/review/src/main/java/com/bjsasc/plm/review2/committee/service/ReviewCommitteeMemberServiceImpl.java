package com.bjsasc.plm.review2.committee.service;

import java.util.ArrayList;
import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.review2.committee.model.ReviewCommitteeMemberHS;
import com.bjsasc.plm.review2.expert.model.ReviewExpert;
import com.bjsasc.plm.review2.opinion.model.ReviewOpinion;
import com.bjsasc.plm.review2.order.model.ReviewOrder;

public class ReviewCommitteeMemberServiceImpl implements ReviewCommitteeMemberService{
	
	@Override
	public ReviewCommitteeMemberHS createCommitteeMember(String userLevel, int userorder,ReviewOrder order, ReviewExpert expert) {
		ReviewCommitteeMemberHS member = new ReviewCommitteeMemberHS();
		member.setUserLevel(userLevel);
		member.setUserorder(userorder);
		member.setReviewOrder(order);
		member.setExpert(expert);
		return member;
	}

	@Override
	public List<ReviewCommitteeMemberHS> getMembersByReviewOrderId(String reviewOrderId) {
		String hql = "from ReviewCommitteeMemberHS t where t.reviewOrderRef.innerId=? order by t.userorder asc";
		return Helper.getPersistService().find(hql, reviewOrderId);
	}

	@Override
	public void addCommitteeMembers(List<ReviewCommitteeMemberHS> members) {
		Helper.getPersistService().save(members);
	}

	@Override
	public void deleteCommitteeMembers(List<ReviewCommitteeMemberHS> members) {
		Helper.getPersistService().delete(members);
	}

	@Override
	public List<ReviewOpinion> getOptions(ReviewCommitteeMemberHS member) {
		List<ReviewOpinion> options = new ArrayList<ReviewOpinion>();
		String hql = "from ReviewOpinion t where t.reviewCommitteeRef.innerId=? and t.reviewOrderRef=?";
		options  = Helper.getPersistService().find(hql,member.getInnerId(),member.getReviewOrder().getInnerId());
		return options;
	}

	@Override
	public void deleteOptions(List<ReviewOpinion> ops) {
		Helper.getPersistService().delete(ops);
	}

	@Override
	public int getNextUserOrder(String reviewOrderId) {
		String hql = "from ReviewCommitteeMemberHS t where t.reviewOrderRef.innerId=? order by t.userorder desc";
		List<ReviewCommitteeMemberHS>  members = Helper.getPersistService().find(hql, reviewOrderId);
		if(members!=null && members.size()>0){
			return members.get(0).getUserorder();
		}
		return 1;
	}

	@Override
	public boolean upUserOrder(ReviewCommitteeMemberHS member, String reviewOrderId) {
		return dealUserOrder(member, "asc", reviewOrderId);
	}
	
	@Override
	public boolean goTopUserOrder(ReviewCommitteeMemberHS member, String reviewOrderId) {
		return dealUserOrders(member, "asc", reviewOrderId);
	}
	
	private boolean dealUserOrders(ReviewCommitteeMemberHS member, String sortType, String reviewOrderId){
		List<ReviewCommitteeMemberHS> members = findReviewCommitteeMember(sortType, reviewOrderId);
		if(members!=null && members.size()>0){
			if(members.size()==1){
				return false;
			}else{
				for(int i=0; i<members.size(); i++){
					ReviewCommitteeMemberHS memberTemp = members.get(i);
					if(member.getInnerId().equals(memberTemp.getInnerId())){
						if("desc".equals(sortType)){
							memberTemp.setUserorder(members.size());
						}else{
							memberTemp.setUserorder(1);
						}
						this.updateUserMember(memberTemp);
						break;
					}else{
						int temOrder = memberTemp.getUserorder();
						if("desc".equals(sortType)){
							memberTemp.setUserorder(temOrder-1);
						}else{
							memberTemp.setUserorder(temOrder+1);
						}
						this.updateUserMember(memberTemp);
					}
				}
				return true;
			}
		}
		return false;
	}
	
	private boolean dealUserOrder(ReviewCommitteeMemberHS member, String sortType, String reviewOrderId){
		List<ReviewCommitteeMemberHS> members = findReviewCommitteeMember(sortType, reviewOrderId);
		if(members!=null && members.size()>0){
			if(members.size()==1){
				return false;
			}else{
				int index = -1;
				for(int i=0; i<members.size(); i++){
					ReviewCommitteeMemberHS memberTemp = members.get(i);
					if(member.getInnerId().equals(memberTemp.getInnerId())){
						index = i-1;
						break;
					}
				}
				if(index!=-1){
					ReviewCommitteeMemberHS memberUp = members.get(index);
					int upOrder = memberUp.getUserorder();
					memberUp.setUserorder(member.getUserorder());
					member.setUserorder(upOrder);
					this.updateUserMember(memberUp);
					this.updateUserMember(member);
					return true;
				}
			}
		}
		return false;
	}
	
	private List<ReviewCommitteeMemberHS> findReviewCommitteeMember(String orderType, String reviewOrderId){
		String hql  = "from ReviewCommitteeMemberHS t where t.reviewOrderRef.innerId=? order by t.userorder "+orderType;
		return Helper.getPersistService().find(hql, reviewOrderId);
	}
	@Override
	public boolean downUserOrder(ReviewCommitteeMemberHS member, String reviewOrderId) {
		return dealUserOrder(member, "desc", reviewOrderId);
	}
	@Override
	public boolean goDownUserOrder(ReviewCommitteeMemberHS member, String reviewOrderId) {
		return dealUserOrders(member, "desc", reviewOrderId);
	}
	@Override
	public void updateUserMember(ReviewCommitteeMemberHS member) {
		Helper.getPersistService().update(member);
	}
	
	public ReviewCommitteeMemberHS getLeader(String reviewOrderId) {
		String hql = "from ReviewCommitteeMemberHS t where t.reviewOrderRef.innerId=? and t.userLevel=?";
		List<ReviewCommitteeMemberHS> rcmList = Helper.getPersistService().find(hql, reviewOrderId,ReviewCommitteeMemberHS.USERLEVEL_OFFICER);
		if(rcmList!=null && rcmList.size()>0){
			return rcmList.get(0);
		}
		return null;
	}

	@Override
	public ReviewCommitteeMemberHS getCommitteeMember(String reviewOrderId, String expertId) {
		String hql = "from ReviewCommitteeMemberHS t where t.reviewOrderRef.innerId=? and t.expertRef.innerId=?";
		List<ReviewCommitteeMemberHS> rcmList = Helper.getPersistService().find(hql, reviewOrderId,expertId);
		if(rcmList!=null && rcmList.size()>0){
			return rcmList.get(0);
		}
		return null;
	}
}
