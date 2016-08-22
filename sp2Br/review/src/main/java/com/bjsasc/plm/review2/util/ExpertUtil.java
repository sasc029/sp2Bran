package com.bjsasc.plm.review2.util;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.platform.spring.PlatformApplicationContext;
import com.bjsasc.plm.core.review2.Reviewed;
import com.bjsasc.plm.review2.constant.ExpertConstant;
import com.bjsasc.plm.review2.constant.ReviewRoleConstant;
import com.bjsasc.plm.review2.expert.manager.ExpertManager;
import com.bjsasc.plm.review2.expert.model.ExpertNode;
import com.bjsasc.plm.review2.link.service.ReviewMemberHelper;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.plm.review2.role.model.ReviewRoleUser;
import com.bjsasc.plm.review2.role.service.ReviewRoleUserHelper;
import com.bjsasc.plm.url.Url;
/**
 * 专家分类节点助手类
 * @author
 *
 */
public class ExpertUtil {

	
    private static ExpertManager expertManager = null;
	
	public static ExpertManager getManager(){
		if(null == expertManager){
			expertManager = (ExpertManager) PlatformApplicationContext.getBean("plm_expertManager");
		}
		return expertManager;
	}
	
	public static String convertTime(long time,boolean withH){
		SimpleDateFormat format = null;
		if(withH){
			format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		}else{
			format = new SimpleDateFormat("yyyy-MM-dd");
		}
		System.out.println("分支合并测试4");
		Date d = new Date(time);
		return format.format(d);
	}
	/**
	 * 展开树所有节点
	 * @param parentId
	 * @param contextId
	 * @return
	 */
	public static List getTreeExpertTypeNodeList(String parentId,String siteInnerId){
		List childs = ExpertUtil.getManager().getChildNodeList(parentId,siteInnerId);
		List lists = new ArrayList();
		for(int i = 0; i < childs.size(); i++){
			ExpertNode en = (ExpertNode) childs.get(i);
			Map map = buildTreeExpertTypeMap(en,siteInnerId);
			lists.add(map);
		}
		return lists;
	}
	private static Map buildTreeExpertTypeMap(ExpertNode enNode,String siteInnerId){
		String innerId = enNode.getInnerId();
		List childNodes = new ArrayList();
		Map node = new HashMap();
		node.put("text", enNode.getName());
		node.put("innerId", enNode.getInnerId());
		node.put("__viewicon", "true");
		//node.put("icon", "e-tree-folder");
		node.put("children", childNodes);
		node.put("expanded", true);
		node.put("ChildUrl", Url.APP+"/review/expert/childExpertsType.jsp?parentid="+ enNode.getInnerId()+"&sourceSiteInnerId="+siteInnerId);
		List<ExpertNode> nodes = ExpertUtil.getManager().getChildNodeList(innerId,siteInnerId);
		if(nodes!=null && nodes.size()>0){
			for(int i=0; i<nodes.size();i++){
				ExpertNode wn = nodes.get(i);
				childNodes.add(buildTreeExpertTypeMap(wn,siteInnerId));
			}
		}else{
			node.put("__viewicon", false);
			node.put("expanded", true);
		}
		return node;
	}
	/**
	 * 展开树所有节点
	 * @param parentId
	 * @param contextId
	 * @return
	 */
	public static List getTreeNodeList(String parentId,String siteInnerId){
		List childs = ExpertUtil.getManager().getPersonalChildNodeList(parentId,siteInnerId);
		List lists = new ArrayList();
		for(int i = 0; i < childs.size(); i++){
			ExpertNode en = (ExpertNode) childs.get(i);
			Map map = buildTreeMap(en,siteInnerId);
			lists.add(map);
		}
		if(parentId.equals(ExpertConstant.NODE_ROOT)){
			Map node = new HashMap();
			node.put("text", "我的收藏");
			node.put("__viewicon", "true");
			node.put("innerId", "collect");
			node.put("expanded", true);
			node.put("__viewicon", false);
			node.put("expanded", true);
			lists.add(node);
		}
		return lists;
	}
	
	/**
	 * 构造tree节点
	 * @param wsNode
	 * @param contextId
	 * @return
	 */
	private static Map buildTreeMap(ExpertNode enNode,String siteInnerId){
		String innerId = enNode.getInnerId();
		List childNodes = new ArrayList();
		Map node = new HashMap();
		node.put("text", enNode.getName());
		node.put("innerId", enNode.getInnerId());
		node.put("__viewicon", "true");
		//node.put("icon", "e-tree-folder");
		node.put("children", childNodes);
		node.put("expanded", true);
		node.put("ChildUrl", Url.APP+"/review/expertnode/childNodes.jsp?parentid="+ enNode.getInnerId()+"&sourceSiteInnerId="+siteInnerId);
		List<ExpertNode> nodes = ExpertUtil.getManager().getPersonalChildNodeList(innerId,siteInnerId);
		if(nodes!=null && nodes.size()>0){
			for(int i=0; i<nodes.size();i++){
				ExpertNode wn = nodes.get(i);
				childNodes.add(buildTreeMap(wn,siteInnerId));
			}
		}else{
			node.put("__viewicon", false);
			node.put("expanded", true);
		}
		return node;
	}
	
	public static boolean isUserForPSFZR(String userId,ReviewOrder rvOrder) {
		List<Reviewed> rvObjs=ReviewMemberHelper.getService().getReviewedListByOrder(rvOrder);
		ReviewObject rvObj = (ReviewObject)rvObjs.get(0);
		List<ReviewRoleUser> list =ReviewRoleUserHelper.getReviewRoleService().getAllReviewPrincipal(ReviewRoleConstant.REVIEW_PSFZR,rvObj.getSourceSiteInnerId());
		if(list != null && list.size()>0){
			for(ReviewRoleUser r : list){
				if(r.getUser().getInnerId().equals(userId)){
					return true;
				}
			}
		}
		return false;
	}
	public static boolean isUserForPSFZR(String userId) {
		List<ReviewRoleUser> list =ReviewRoleUserHelper.getReviewRoleService().getAllReviewPrincipal(ReviewRoleConstant.REVIEW_PSFZR,"");
		if(list != null && list.size()>0){
			for(ReviewRoleUser r : list){
				if(r.getUser().getInnerId().equals(userId)){
					return true;
				}
			}
		}
		return false;
	}
}
