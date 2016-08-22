package com.bjsasc.plm.review2.expert.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.avidm.core.site.SiteHelper;
import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.persist.PersistHelper;
import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.core.util.PlmException;
import com.bjsasc.plm.review2.constant.ExpertConstant;
import com.bjsasc.plm.review2.expert.model.ExpertNode;
import com.cascc.avidm.util.UUIDService;

public class ExpertNodeServiceImpl implements ExpertNodeService {
	Logger logger = Logger.getLogger(ExpertNodeServiceImpl.class);
	
	@Override
	public ExpertNode createNode(ExpertNode en) {
		 PersistHelper.getService().save(en);
		 return en;
	}
	
	@Override
	public List<ExpertNode> getChildNodeListBySiteInnerId(String parentNodeId,String siteInnerId) {
		
		DetachedCriteria dc=DetachedCriteria.forClass(ExpertNode.class);
		dc.add(Restrictions.eq("parentID", parentNodeId));
		dc.add(Restrictions.eq("siteRef.innerId", siteInnerId));
		List<ExpertNode> list = new ArrayList<ExpertNode>();
		try {
			list = PersistHelper.getService().findByCriteria(dc);
			if(list != null && list.size()>0){
				return list;
			}else{
				 if(parentNodeId.equals(ExpertConstant.EXPERT_NODE_ROOT)){
					list.add(this.initRootExpertNode(siteInnerId,parentNodeId));
					return list;
				}else{
					return list;
				}
			}
		} catch (Exception e) {
			logger.debug("获取子分类节点失败");
			List<Object> errorMessage = new ArrayList<Object>();
			errorMessage.add(e); 
			throw new PlmException("com.bjsasc.plm.core.review.expert.service.childnode",errorMessage);
		}
	}
	@Override
	public List<ExpertNode> getChildNodeList(String parentNodeId) {
		DetachedCriteria dc=DetachedCriteria.forClass(ExpertNode.class);
		dc.add(Restrictions.eq("parentID", parentNodeId));
		List<ExpertNode> list = new ArrayList<ExpertNode>();
		try {
			list = PersistHelper.getService().findByCriteria(dc);
			return list;
		} catch (Exception e) {
			logger.debug("获取子分类节点失败");
			List<Object> errorMessage = new ArrayList<Object>();
			errorMessage.add(e); 
			throw new PlmException("com.bjsasc.plm.core.review.expert.service.childnode",errorMessage);
		}
	}
	@Override
	public List<ExpertNode> getChildNodeList(String parentNodeId,String siteInnerId) {
		User user = Helper.getSessionService().getUser();
		
		DetachedCriteria dc=DetachedCriteria.forClass(ExpertNode.class);
		dc.add(Restrictions.eq("parentID", parentNodeId));
		dc.add(Restrictions.eq("siteRef.innerId", siteInnerId));
		dc.add(Restrictions.eq("creatorRef.innerId", user.getInnerId()));
		List<ExpertNode> list = new ArrayList<ExpertNode>();
		try {
			list = PersistHelper.getService().findByCriteria(dc);
			if(list != null && list.size()>0){
				return list;
			}else{
				if(parentNodeId.equals(ExpertConstant.NODE_ROOT)){
					list.add(this.getRootExpertNode(siteInnerId,parentNodeId));
					return list;
				}else{
					return list;
				}
			}
		} catch (Exception e) {
			logger.debug("获取子分类节点失败");
			List<Object> errorMessage = new ArrayList<Object>();
			errorMessage.add(e); 
			throw new PlmException("com.bjsasc.plm.core.review.expert.service.childnode",errorMessage);
		}
	}

	@Override
	public int checkNodeName(String name, String parentID) {
		DetachedCriteria dc=DetachedCriteria.forClass(ExpertNode.class);
		dc.add(Restrictions.eq("name", name));
		dc.add(Restrictions.eq("parentID", parentID));
		dc.add(Restrictions.ne("parentID", ExpertConstant.NODE_ROOT));
		dc.add(Restrictions.ne("parentID", ExpertConstant.EXPERT_NODE_ROOT));
		List<ExpertNode> list = new ArrayList<ExpertNode>();
		try {
			list = PersistHelper.getService().findByCriteria(dc);
		} catch (Exception e) {
			logger.debug("获取子分类节点失败");
			List<Object> errorMessage = new ArrayList<Object>();
			errorMessage.add(e); 
			throw new PlmException("com.bjsasc.plm.core.review.expert.service.childnode",errorMessage);
		}
		return list.size();
	}

	@Override
	public void update(ExpertNode en) {
		PersistHelper.getService().update(en);
	}
    
	@Override
	public ExpertNode getRootExpertNode(String siteInnerId,String rootType) {
		ExpertNode expertNode = null ;
		User user = Helper.getSessionService().getUser();
		Site site = SiteHelper.getSiteService().findSiteById(siteInnerId);
		
		DetachedCriteria dc=DetachedCriteria.forClass(ExpertNode.class);
		dc.add(Restrictions.eq("creatorRef.innerId", user.getInnerId()));
		dc.add(Restrictions.eq("siteRef.innerId", siteInnerId));
		dc.add(Restrictions.eq("parentID", rootType));
		List<ExpertNode> list = new ArrayList<ExpertNode>();
		try {
			list =PersistHelper.getService().findByCriteria(dc);
		} catch (Exception e) {
			logger.debug("获取根分类失败");
			List<Object> errorMessage = new ArrayList<Object>();
			errorMessage.add(e); 
			throw new PlmException("com.bjsasc.plm.core.review.expert.service.rootnode",errorMessage);
		}
		if(list.size()>0){
			expertNode = list.get(0);
		}else if(ExpertConstant.NODE_ROOT.equals(rootType)){
			ExpertNode en = new ExpertNode();
			en.setClassId("ExpertNode");
			en.setInnerId(UUIDService.getUUID());
			en.setName("我的专家库");
			en.setCreateStamp(System.currentTimeMillis());
			en.setModifyStamp(System.currentTimeMillis());
			en.setParentID(ExpertConstant.NODE_ROOT);
			en.setCreator(user);
			en.setModifier(user);
			en.setSite(site);
			en.setNodeType(ExpertConstant.NODE_TYPE_PERSONAL);
			ExpertNodeHelper.getExpertNodeService().createNode(en);
			expertNode = en;
		}
		return expertNode;
	}
	public ExpertNode initRootExpertNode(String siteInnerId,String rootType) {
		ExpertNode expertNode = null ;
		User user = Helper.getSessionService().getUser();
		Site site = SiteHelper.getSiteService().findSiteById(siteInnerId);
		
		DetachedCriteria dc=DetachedCriteria.forClass(ExpertNode.class);
		dc.add(Restrictions.eq("parentID", rootType));
		dc.add(Restrictions.eq("siteRef.innerId", siteInnerId));
		List<ExpertNode> list = new ArrayList<ExpertNode>();
		try {
			list = PersistHelper.getService().findByCriteria(dc);
		} catch (Exception e) {
			logger.debug("获取根分类失败");
			List<Object> errorMessage = new ArrayList<Object>();
			errorMessage.add(e); 
			throw new PlmException("com.bjsasc.plm.core.review.expert.service.rootnode",errorMessage);
		}
		if(list != null && list.size()>0){
			expertNode = list.get(0);
		}else if(ExpertConstant.EXPERT_NODE_ROOT.equals(rootType)){
			ExpertNode en = new ExpertNode();
			en.setClassId("ExpertNode");
			en.setInnerId(UUIDService.getUUID());
			en.setName("公共专家库");
			en.setCreateStamp(System.currentTimeMillis());
			en.setModifyStamp(System.currentTimeMillis());
			en.setParentID(ExpertConstant.EXPERT_NODE_ROOT);
			en.setNodeType(ExpertConstant.NODE_TYPE_COMMON);
			en.setCreator(user);
			en.setModifier(user);
			en.setSite(site);
			ExpertNodeHelper.getExpertNodeService().createNode(en);
			expertNode = en;
	   }
		return expertNode;
	}
	@Override
	public ExpertNode newExpertNode() {
		ExpertNode expertNode = new ExpertNode();
		return expertNode;
	}

	@Override
	public List<ExpertNode> getNodeList(String siteInnerId, String nodeType) {
		User user = Helper.getSessionService().getUser();
		DetachedCriteria dc=DetachedCriteria.forClass(ExpertNode.class);
		dc.add(Restrictions.eq("creatorRef.innerId", user.getInnerId()));
		dc.add(Restrictions.eq("siteRef.innerId", siteInnerId));
		dc.add(Restrictions.eq("nodeType", nodeType));
		List<ExpertNode> list = new ArrayList<ExpertNode>();
		list = PersistHelper.getService().findByCriteria(dc);
		return list;
	}

}
