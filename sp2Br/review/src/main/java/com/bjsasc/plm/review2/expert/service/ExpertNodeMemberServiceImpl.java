package com.bjsasc.plm.review2.expert.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.persist.PersistHelper;
import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.review2.constant.ExpertConstant;
import com.bjsasc.plm.review2.expert.model.DomainReviewExpert;
import com.bjsasc.plm.review2.expert.model.ExpertNode;
import com.bjsasc.plm.review2.expert.model.ExpertNodeMember;
import com.bjsasc.plm.review2.expert.model.OuterReviewExpert;
import com.bjsasc.plm.review2.expert.model.ReviewExpert;
import com.bjsasc.plm.util.JsonUtil;

public class ExpertNodeMemberServiceImpl implements ExpertNodeMemberService {

	@Override
	public ExpertNodeMember newExpertNodeMember() {
		ExpertNodeMember expert = new ExpertNodeMember();
		expert.setIsCollect("false");
		return expert;
	}

	@Override
	public List<ExpertNodeMember> getExpertNodeMemberList(User user) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertNodeMember.class);
		dc.add(Restrictions.eq("manageInfo.createByRef.innerId",
				user.getInnerId()));
		dc.addOrder(Order.asc("manageInfo.createTime"));
		List<ExpertNodeMember> enmList = new ArrayList<ExpertNodeMember>();
		enmList = PersistHelper.getService().findByCriteria(dc);

		return enmList;
	}
	
	@Override
	public  List<Map<String, String>> getExpertNodeMemberListAsCollect() {
		User user = Helper.getSessionService().getUser();
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertNodeMember.class);
		dc.add(Restrictions.eq("manageInfo.createByRef.innerId", user.getInnerId()));
		dc.add(Restrictions.eq("isCollect", "true"));
		dc.addOrder(Order.asc("manageInfo.createTime"));
		List<Map<String, String>> wsList = new ArrayList<Map<String, String>>();
		wsList = PersistHelper.getService().findByCriteria(dc);
		return wsList;
	}

	@Override
	public ExpertNodeMember createExpertNodeMember(ExpertNodeMember expertNodeMember, ExpertNode expertNode) {
		expertNodeMember.setNode(expertNode);
		PersistHelper.getService().save(expertNodeMember);
		return expertNodeMember;
	}

	@Override
	public List<Map<String, String>> getExpertNodeMemberList(String innerID) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertNodeMember.class);
			dc.add(Restrictions.eq("nodeRef.innerId", innerID));
		    dc.addOrder(Order.asc("manageInfo.createTime"));
		List<Map<String, String>> wsList = new ArrayList<Map<String, String>>();
		wsList = PersistHelper.getService().findByCriteria(dc);
		return wsList;
	}

	@Override
	public List<ReviewExpert> getAllExpertNodeMemberList() {
		User user = Helper.getSessionService().getUser();
        DetachedCriteria dc1 = DetachedCriteria.forClass(OuterReviewExpert.class);
        dc1.add(Restrictions.eq("manageInfo.createByRef.innerId", user.getInnerId()));
		@SuppressWarnings("unchecked")
		List<ReviewExpert> list1 = PersistHelper.getService().findByCriteria(dc1);
		
        DetachedCriteria dc2 = DetachedCriteria.forClass(DomainReviewExpert.class);
        dc2.add(Restrictions.eq("manageInfo.createByRef.innerId", user.getInnerId()));
		@SuppressWarnings("unchecked")
		List<ReviewExpert> list2 = PersistHelper.getService().findByCriteria(dc2);
		
		List<ReviewExpert> list = new ArrayList<ReviewExpert>();
		list.addAll(list1);
		list.addAll(list2);
		
		return list ;
	}

	@Override
	public ExpertNodeMember getExpertNodeMember(String innerId, String classId) {
		return (ExpertNodeMember) Helper.getPersistService().getObject(classId,
				innerId);
	}

	@Override
	public int checkExpertNodeMember(String data, String siteInnerId) {
        boolean flag = false;
		List<Map<String, Object>> itemList = JsonUtil.toList(data);
		List<ExpertNode> nodeList = ExpertNodeHelper.getExpertNodeService().getNodeList(siteInnerId, ExpertConstant.NODE_TYPE_PERSONAL);
		for(int i=0;i<itemList.size();i++){
			Map<String,Object> map = (Map<String,Object>)itemList.get(i);
			String exp_innerId =  map.get("EXPERTINNERID").toString();
			if(flag)break;
			for(int j=0;j<nodeList.size();j++){
				ExpertNode node = (ExpertNode)nodeList.get(j);
				List nodeMemberList = this.getExpertNodeMemberList(node.getInnerId());
				for(int k=0;k<nodeMemberList.size();k++){
					ExpertNodeMember expertNodeMember = (ExpertNodeMember) nodeMemberList.get(k);
					ReviewExpert expert = expertNodeMember.getExpert();
					if(exp_innerId.equals(expert.getInnerId())){
						flag = true;
						break;
					}
				}
			}
		}
		if(flag){
			return 1;
		}
		return 0;
	}

	@Override
	public void collectOrRemoveExpert(String innerId,String flag) {
		ExpertNodeMember expertNodeMember =this.getExpertNodeMember(innerId, ExpertNodeMember.class.getSimpleName());
	    expertNodeMember.setIsCollect(flag);
		PersistHelper.getService().update(expertNodeMember);
	}

	@Override
	public List<ExpertNodeMember> getAllExpertNodeMemberListByExpertRef(String innerid) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertNodeMember.class);
		dc.add(Restrictions.eq("expertRef.innerId", innerid));
	    dc.addOrder(Order.asc("manageInfo.createTime"));
	    List<ExpertNodeMember> wsList = new ArrayList<ExpertNodeMember>();
	    wsList = PersistHelper.getService().findByCriteria(dc);
		return wsList;
	}

}
