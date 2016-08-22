package com.bjsasc.plm.review2.expert.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.avidm.core.site.SiteHelper;
import com.bjsasc.plm.KeyS;
import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.managed.ManageHelper;
import com.bjsasc.plm.core.persist.PersistHelper;
import com.bjsasc.plm.core.persist.model.Persistable;
import com.bjsasc.plm.core.session.SessionHelper;
import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.review2.constant.ExpertConstant;
import com.bjsasc.plm.review2.expert.model.DomainReviewExpert;
import com.bjsasc.plm.review2.expert.model.ExpertNode;
import com.bjsasc.plm.review2.expert.model.ExpertNodeMember;
import com.bjsasc.plm.review2.expert.model.OuterReviewExpert;
import com.bjsasc.plm.review2.expert.model.ReviewExpert;
import com.bjsasc.plm.review2.expert.service.ExpertNodeHelper;
import com.bjsasc.plm.review2.expert.service.ExpertNodeMemberHelper;
import com.bjsasc.plm.review2.expert.service.ExpertNodeServiceImpl;
import com.bjsasc.plm.util.JsonUtil;
import com.bjsasc.ui.json.DataUtil;
import com.cascc.avidm.util.UUIDService;

/**
 *专家分类 页面交互接口实现类
 * @author Administrator
 *
 */
public class ExpertManagerImpl implements ExpertManager {

	Logger logger = Logger.getLogger(ExpertNodeServiceImpl.class);

//	public ExpertNode getRootNode(){
//		return ExpertNodeHelper.getExpertNodeService().getRootExpertNode(getUserId());
//	}
	
	public List<ExpertNode> getChildNodeList(String parentID,String siteInnerId) {
		return ExpertNodeHelper.getExpertNodeService().getChildNodeListBySiteInnerId(parentID, siteInnerId);
	}
	
	public List<ExpertNode> getPersonalChildNodeList(String parentID,String siteInnerId) {
		return ExpertNodeHelper.getExpertNodeService().getChildNodeList(parentID, siteInnerId);
	}
	
	private String getUserId(){
		User user = Helper.getSessionService().getUser();
		return user.getInnerId();
	} 
	
	public ExpertNode get(String innerId, String classId) {
		return (ExpertNode)Helper.getPersistService().getObject(classId, innerId);
	}
	
	public ReviewExpert getReviewExp(String innerId, String classId) {
		return (ReviewExpert)Helper.getPersistService().getObject(classId, innerId);
	}
	public ExpertNode createNode(String parentID, String name,String siteInnerId,String nodeType) {
        ExpertNode en = ExpertNodeHelper.getExpertNodeService().newExpertNode();
        en.setName(name);
        en.setParentID(parentID);
        en.setInnerId(UUIDService.getUUID());
        en.setCreateStamp(System.currentTimeMillis());
        en.setModifyStamp(System.currentTimeMillis());
        User user = Helper.getSessionService().getUser();
        en.setCreator(user);
        en.setModifier(user);
        en.setNodeType(nodeType);
        Site site = SiteHelper.getSiteService().findSiteById(siteInnerId);
        en.setSite(site);
		return ExpertNodeHelper.getExpertNodeService().createNode(en);
	}
	
	public int getNodeNameJson(String parentID, String name) {
		int cnt = 0;
    	cnt = ExpertNodeHelper.getExpertNodeService().checkNodeName(name, parentID);
    	return cnt;
	}
	
	public void update(String name, String innerID) {
    	ExpertNode en = this.get(innerID,ExpertNode.class.getSimpleName());
    	en.setName(name);
    	en.setModifyStamp(System.currentTimeMillis());
		ExpertNodeHelper.getExpertNodeService().update(en);
	}

	
	public int deleteNode(String innerId) {
		String classId = ExpertNode.class.getSimpleName();
		int cnt = recurGetChildNode(innerId);
		if(cnt==0){
			//如果有专家存在，则不处理
			return 0;
		}else{
			//删除分类操作
			recurDelNode(innerId, classId);
			return 1;
		}
	}
	/**
	 * 根据父InnerId递归查询子分类判断是否有专家存在，如果任何一级目录下有专家存在，都不能执行删除操作
	 * @param innerId
	 * @return
	 */
	public int recurGetChildNode(String innerId){
		//判断分类及其子分类下是否有专家存在
		List enmList = ExpertNodeMemberHelper.getService().getExpertNodeMemberList(innerId);
		if(enmList.size()>0){
			return 0;
		}
		//查询出所有子节点
		List<ExpertNode> enList = ExpertNodeHelper.getExpertNodeService().getChildNodeList(innerId);
		for(int i=0; i<enList.size(); i++){
			ExpertNode en = enList.get(i);
			String childInnerId = en.getInnerId();
			//递归调用
			int cnt = recurGetChildNode(childInnerId);
			if(cnt==0){
				return 0;
			}
		}
		return 1;
	}
	/**
	 * 递归删除分类及其下级所有分类
	 * @param innerId
	 * @param classId
	 */
	public void recurDelNode(String nodeInnerId, String classId){
		List<ExpertNode> wnList = ExpertNodeHelper.getExpertNodeService().getChildNodeList(nodeInnerId);
		if(wnList.size()==0){
			ExpertNode ws = (ExpertNode)Helper.getPersistService().getObject(classId, nodeInnerId);
			PersistHelper.getService().delete(ws);
		}
		for(int i=0; i<wnList.size(); i++){
			ExpertNode wn = (ExpertNode)wnList.get(i);
			String childInnerId = wn.getInnerId();
			//递归调用
			recurDelNode(childInnerId,classId);
			//删父
			List<ExpertNode> parentList = ExpertNodeHelper.getExpertNodeService().getChildNodeList(nodeInnerId);
			if(parentList.size()==0){
				ExpertNode expertNode = (ExpertNode)Helper.getPersistService().getObject(classId, nodeInnerId);
				PersistHelper.getService().delete(expertNode);
			}
		}
	}

	@Override
	public String getExpertJson(String innerID) {
		List<Map<String, String>> enmList =new ArrayList<Map<String,String>>();
		if("collect".equals(innerID)){
			enmList = ExpertNodeMemberHelper.getService().getExpertNodeMemberListAsCollect();
		}else{
			enmList =  ExpertNodeMemberHelper.getService().getExpertNodeMemberList(innerID);
		}
		
		List<Map<String, String>> enmMapList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < enmList.size(); i++) {
			ExpertNodeMember expertNodeMember = (ExpertNodeMember) enmList.get(i);
			Map<String, String> map = new HashMap<String, String>();
			ReviewExpert expert = expertNodeMember.getExpert();
			String userName = "";
			String workUnit = "";
			String expertType = "";
			String expert_innerId = "";
			String expert_classId = "";
			if(expert instanceof DomainReviewExpert){
				DomainReviewExpert dExpert = (DomainReviewExpert)expert;
				userName = dExpert.getUser().getName();
				workUnit = dExpert.getUser().getUserOrgname();
				expertType = "域内专家";
				expert_innerId = dExpert.getInnerId();
			    expert_classId = DomainReviewExpert.class.getSimpleName();
			}else if(expert instanceof OuterReviewExpert){
				OuterReviewExpert outer = (OuterReviewExpert)expert;
				userName = outer.getName();
				workUnit = outer.getWorkUnit();
				expertType = "域外专家";
				expert_innerId = outer.getInnerId();
			    expert_classId = OuterReviewExpert.class.getSimpleName();
			}
			
			if("collect".equals(innerID)){
				map.put("FLAG", "remove");
				map.put("COLLECT", "<img src='/avidm/plm/images/common/favorite_remove.gif' alt='移除收藏'/>");
			}else{
				if("false".equals(expertNodeMember.getIsCollect())){
				  map.put("FLAG", "add");
				  map.put("COLLECT", "<img src='/avidm/plm/images/common/favorite.gif' alt='添加收藏'/>");
				}else{
				  map.put("FLAG", "remove");
				  map.put("COLLECT", "<img src='/avidm/plm/images/common/favorite_remove.gif' alt='移除收藏'/>");
				}
			}
				map.put("INNERID", expertNodeMember.getInnerId());
				map.put("CLASSID", expertNodeMember.getClassId());
				map.put("NAME", userName);
				map.put("WORKUNIT", workUnit);
				map.put("EXPERTTYPE", expertType);
				map.put("EXPERT_INNERID", expert_innerId);
				map.put("EXPERT_CLASSID", expert_classId);
				map.put("COLLECTSTATE","true".equals(expertNodeMember.getIsCollect())?"已收藏":"未收藏" );
			
			    enmMapList.add(map);
		}
		
    	String wsJson = DataUtil.listToJson(enmMapList.size(), enmMapList);
		return wsJson;
	}

	@Override
	public String getAllExpertJson() {
		List<ReviewExpert> list = ExpertNodeMemberHelper.getService().getAllExpertNodeMemberList();
		List<Map<String, String>> enmMapList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < list.size(); i++) {
			ReviewExpert expert = (ReviewExpert)list.get(i);
			Map<String, String> map = new HashMap<String, String>();
			String userName = "";
			String workUnit = "";
			if(expert instanceof DomainReviewExpert){
				DomainReviewExpert dExpert = (DomainReviewExpert)expert;
				userName = dExpert.getUser().getName();
				workUnit = dExpert.getUser().getUserOrgname();
			}else if(expert instanceof OuterReviewExpert){
				OuterReviewExpert outer = (OuterReviewExpert)expert;
				userName = outer.getName();
				workUnit = outer.getWorkUnit();
			}
			
			map.put("INNERID", expert.getInnerId());
			map.put("CLASSID", expert.getClassId());
			map.put("NAME", userName);
			map.put("WORKUNIT", workUnit);

			enmMapList.add(map);
		}
		return DataUtil.listToJson(enmMapList.size(),enmMapList);
	}
	
	public String getExpertByUserJson(String siteInnerId) {
		List<Map<String, String>> list =new ArrayList<Map<String, String>>();
		List<ExpertNode> nodeList = ExpertNodeHelper.getExpertNodeService().getNodeList(siteInnerId, ExpertConstant.NODE_TYPE_PERSONAL);
		for(ExpertNode node:nodeList){
			List<Map<String, String>> tempList = ExpertNodeMemberHelper.getService().getExpertNodeMemberList(node.getInnerId());
			if(!tempList.isEmpty()){
				list.addAll(tempList);
			}
		}
		List<Map<String, String>> enmMapList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < list.size(); i++) {
			ExpertNodeMember member = (ExpertNodeMember)list.get(i);
			ReviewExpert expert = member.getExpert();
			Map<String, String> map = new HashMap<String, String>();
			String userName = "";
			String workUnit = "";
			if(expert instanceof DomainReviewExpert){
				DomainReviewExpert dExpert = (DomainReviewExpert)expert;
				userName = dExpert.getUser().getName();
				workUnit = dExpert.getUser().getUserOrgname();
			}else if(expert instanceof OuterReviewExpert){
				OuterReviewExpert outer = (OuterReviewExpert)expert;
				userName = outer.getName();
				workUnit = outer.getWorkUnit();
			}
			
			map.put("INNERID", expert.getInnerId());
			map.put("CLASSID", expert.getClassId());
			map.put("NAME", userName);
			map.put("WORKUNIT", workUnit);

			enmMapList.add(map);
		}
		return DataUtil.listToJson(enmMapList.size(),enmMapList);
	}
	
	@Override
	public void moveExpert(String innerId, String nodeInnerId) {
		String innerIds [] = innerId.split(",");
		ExpertNode expertNode = (ExpertNode)Helper.getPersistService().getObject(ExpertNode.class.getSimpleName(), nodeInnerId);
		for(int i=0; i<innerIds.length; i++){
			String enInnerId = innerIds[i];
			ExpertNodeMember expertNodeMember = ExpertNodeMemberHelper.getService().getExpertNodeMember(enInnerId,ExpertNodeMember.class.getSimpleName());
			expertNodeMember.setNode(expertNode);
			PersistHelper.getService().update(expertNodeMember);
		}
	}

	@Override
	public String deleteExpertTypeMember(String data) {
		List<Map<String, Object>> itemList = JsonUtil.toList(data);
		List<ExpertNodeMember> enmList = new ArrayList<ExpertNodeMember>();
		List<DomainReviewExpert> domainList = new ArrayList<DomainReviewExpert>();
		List<OuterReviewExpert> outerList = new ArrayList<OuterReviewExpert>();
		for(int i=0; i<itemList.size(); i++){
			Map<String, Object> tempMap = (Map<String, Object>)itemList.get(i);
			String itemClassId = tempMap.get(KeyS.CLASSID).toString();
			String itemInnerId = tempMap.get(KeyS.INNERID).toString();
			String expertInnerId = tempMap.get("EXPERTINNERID").toString();
			String expertFlag = tempMap.get("TYPEFLAG").toString();
			Persistable obj = PersistHelper.getService().getByClassId(itemClassId, itemInnerId);
			ExpertNodeMember expertNodeMember = (ExpertNodeMember)obj;
			enmList.add(expertNodeMember);
			if(DomainReviewExpert.DOMAIN_EXPERT_FLAG.equals(expertFlag)){
				DomainReviewExpert domainReviewExpert = (DomainReviewExpert)Helper.getPersistService().getObject(DomainReviewExpert.class.getSimpleName(),expertInnerId);
			    domainList.add(domainReviewExpert);
			}else{
				OuterReviewExpert outerReviewExpert = (OuterReviewExpert)Helper.getPersistService().getObject(OuterReviewExpert.class.getSimpleName(),expertInnerId);
			    outerList.add(outerReviewExpert);
			}
		}
		for(int j =0; j <enmList.size(); j++){
			ExpertNodeMember expert = (ExpertNodeMember)enmList.get(j);
			PersistHelper.getService().delete(expert);
		}
		for(int k =0; k <domainList.size(); k++){
			DomainReviewExpert expert = (DomainReviewExpert)domainList.get(k);
			PersistHelper.getService().delete(expert);
		}
		for(int l =0; l <outerList.size(); l++){
			OuterReviewExpert expert = (OuterReviewExpert)outerList.get(l);
			PersistHelper.getService().delete(expert);
		}
		return "{success:true}";
	}
	@Override
	public String deleteExpertNodeMember(String data) {
		List<Map<String, Object>> itemList = JsonUtil.toList(data);
		List<ExpertNodeMember> enmList = new ArrayList<ExpertNodeMember>();
		for(int i=0; i<itemList.size(); i++){
			Map<String, Object> tempMap = (Map<String, Object>)itemList.get(i);
			String itemClassId = tempMap.get(KeyS.CLASSID).toString();
			String itemInnerId = tempMap.get(KeyS.INNERID).toString();
			Persistable obj = PersistHelper.getService().getByClassId(itemClassId, itemInnerId);
			ExpertNodeMember expertNodeMember = (ExpertNodeMember)obj;
			enmList.add(expertNodeMember);
		}
		for(int k =0; k <enmList.size(); k++){
			ExpertNodeMember expert = (ExpertNodeMember)enmList.get(k);
			PersistHelper.getService().delete(expert);
		}
		return "{success:true}";
	}
	@Override
	public String addExpertNodeMember(String innerId, String data) {
		List<Map<String, Object>> itemList = JsonUtil.toList(data);
		for(int i=0;i<itemList.size();i++){
			Map<String,Object> map = (Map<String,Object>)itemList.get(i);
			String exp_innerId =  map.get("EXPERTINNERID").toString(); //专家innerId
			String exp_classId =  map.get("EXPERTCLASSID").toString();
			ExpertNode exnode=this.get(innerId, ExpertNode.class.getSimpleName());
			ExpertNodeMember expertNodeMember = ExpertNodeMemberHelper.getService().newExpertNodeMember();
			expertNodeMember.setExpert(this.getReviewExp(exp_innerId, exp_classId));
			ManageHelper.getService().init(expertNodeMember);
			ExpertNodeMemberHelper.getService().createExpertNodeMember(expertNodeMember, exnode);
		}
		return null;
	}
	@Override
	public String addExpertNodeMember(String innerId, ReviewExpert expert) {
		ExpertNode exnode=this.get(innerId, ExpertNode.class.getSimpleName());
		ExpertNodeMember expertNodeMember = ExpertNodeMemberHelper.getService().newExpertNodeMember();
		expertNodeMember.setExpert(expert);
		ManageHelper.getService().init(expertNodeMember);
		ExpertNodeMemberHelper.getService().createExpertNodeMember(expertNodeMember, exnode);
		return null;
	}
	public int getCheckExpertCount(String expertJson,String siteInnerId) {
		int cnt = 0;
    	cnt = ExpertNodeMemberHelper.getService().checkExpertNodeMember(expertJson, siteInnerId);
		return cnt;
	}

	@Override
	public String getDomainExpertByUserJson() {
		User user = SessionHelper.getService().getUser();//获取当前登录用户
		List<ExpertNodeMember> list  = ExpertNodeMemberHelper.getService().getExpertNodeMemberList(user);
		List<Map<String, String>> enmMapList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < list.size(); i++) {
			ReviewExpert expert = list.get(i).getExpert();
			Map<String, String> map = new HashMap<String, String>();
			String userInnerId = "";
			String userName = "";
			String workUnit = "";
			if(expert instanceof DomainReviewExpert){
				DomainReviewExpert dExpert = (DomainReviewExpert)expert;
				userInnerId = dExpert.getUser().getInnerId();
				userName = dExpert.getUser().getName();
				workUnit = dExpert.getUser().getUserOrgname();
			}else{
				continue;
			}
			
			map.put("INNERID", userInnerId);
			map.put("NAME", userName);
			map.put("WORKUNIT", workUnit);

			enmMapList.add(map);
		}
		return DataUtil.listToJson(enmMapList.size(),enmMapList);
	}

	@Override
	public String getCommonExpertJson(String innerID) {
		List<Map<String, String>> enmList =new ArrayList<Map<String,String>>();
		enmList =  ExpertNodeMemberHelper.getService().getExpertNodeMemberList(innerID);
		
		List<Map<String, String>> enmMapList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < enmList.size(); i++) {
			ExpertNodeMember expertNodeMember = (ExpertNodeMember) enmList.get(i);
			Map<String, String> map = new HashMap<String, String>();
			ReviewExpert expert = expertNodeMember.getExpert();
			String userName = "";
			String workUnit = "";
			String userType = "";
			String typeFlag = "";
			String expertInnerId = "";
			String expertClassId = "";
			if(expert instanceof DomainReviewExpert){
				DomainReviewExpert dExpert = (DomainReviewExpert)expert;
				userName = dExpert.getUser().getName();
				workUnit = dExpert.getUser().getUserOrgname();
				userType = "域内专家";
				typeFlag = DomainReviewExpert.DOMAIN_EXPERT_FLAG;
				expertInnerId = dExpert.getInnerId();
				expertClassId = DomainReviewExpert.class.getSimpleName();
			}else if(expert instanceof OuterReviewExpert){
				OuterReviewExpert outer = (OuterReviewExpert)expert;
				userName = outer.getName();
				workUnit = outer.getWorkUnit();
				userType = "域外专家";
				typeFlag = OuterReviewExpert.OUTER_EXPERT_FLAG;
				expertInnerId =outer.getInnerId();
				expertClassId = OuterReviewExpert.class.getSimpleName();
			}
			map.put("INNERID", expertNodeMember.getInnerId());
			map.put("CLASSID", expertNodeMember.getClassId());
			map.put("NAME", userName);
			map.put("WORKUNIT", workUnit);
			map.put("EXPERTTYPE", userType);
			map.put("TYPEFLAG", typeFlag);
			map.put("EXPERTINNERID", expertInnerId);
			map.put("EXPERTCLASSID", expertClassId);
			
			enmMapList.add(map);
		}
		
    	String wsJson = DataUtil.listToJson(enmMapList.size(), enmMapList);
		return wsJson;
	}
}
