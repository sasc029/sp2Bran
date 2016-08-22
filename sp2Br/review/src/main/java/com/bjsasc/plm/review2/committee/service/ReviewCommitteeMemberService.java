package com.bjsasc.plm.review2.committee.service;

import java.util.List;

import com.bjsasc.plm.review2.committee.model.ReviewCommitteeMemberHS;
import com.bjsasc.plm.review2.expert.model.ReviewExpert;
import com.bjsasc.plm.review2.opinion.model.ReviewOpinion;
import com.bjsasc.plm.review2.order.model.ReviewOrder;

public interface ReviewCommitteeMemberService {
	
	public ReviewCommitteeMemberHS createCommitteeMember(String userLevel, int userorder, ReviewOrder order,  ReviewExpert expert);
	
	
	public List<ReviewCommitteeMemberHS> getMembersByReviewOrderId(String reviewOrderId);
	
	
	public void addCommitteeMembers(List<ReviewCommitteeMemberHS> members);
	
	
	public void deleteCommitteeMembers(List<ReviewCommitteeMemberHS> members);
	
	
	public List<ReviewOpinion> getOptions(ReviewCommitteeMemberHS member);
	
	public void deleteOptions(List<ReviewOpinion> ops);
	
	public int getNextUserOrder(String reviewOrderId);
	
	public boolean upUserOrder(ReviewCommitteeMemberHS member,String reviewOrderId);
	public boolean goTopUserOrder(ReviewCommitteeMemberHS member, String reviewOrderId);
	
	public boolean downUserOrder(ReviewCommitteeMemberHS member, String reviewOrderId);
	public boolean goDownUserOrder(ReviewCommitteeMemberHS member, String reviewOrderId);
	
	public void updateUserMember(ReviewCommitteeMemberHS member);
	
	/**
	 * 通过评审单Id查询组长
	 * */
	public ReviewCommitteeMemberHS getLeader(String reviewOrderId);
	
	public ReviewCommitteeMemberHS getCommitteeMember(String reviewOrderId,String expertId);
}
