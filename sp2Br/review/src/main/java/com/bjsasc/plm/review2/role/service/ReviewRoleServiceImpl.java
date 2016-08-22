package com.bjsasc.plm.review2.role.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.util.XmlFileUtil;
import com.bjsasc.plm.review2.role.model.ReviewRole;
import com.bjsasc.plm.review2.role.model.ReviewRoleUser;

public class ReviewRoleServiceImpl implements ReviewRoleService {

	@Override
	public ReviewRole createReviewRole(String roleId, String roleName, String roleDescription) {
		ReviewRole role = new ReviewRole();
		role.setRoleID(roleId);
		role.setRoleName(roleName);
		role.setRoleDescription(roleDescription);
		return role;
	}

	@Override
	public void saveReviewRoleList(List<ReviewRole> roles) {
		Helper.getPersistService().save(roles);
	}

	@Override
	public List<ReviewRole> getAllRoleList() {
		String hql = "from ReviewRole as role order by role.manageInfo.createTime asc";
		List<ReviewRole> roles = (List<ReviewRole>)Helper.getPersistService().find(hql);
		return roles;
	}

	@Override
	public ReviewRole getReviewRoleByRoleId(String roleId) {
		String hql = "from ReviewRole where roleID=?";
		List<ReviewRole> roles = (List<ReviewRole>)Helper.getPersistService().find(hql,roleId);
		if(roles!=null&&roles.size()>0){
			return roles.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<ReviewRole> getInitRoleList() {
		Document doc = XmlFileUtil.loadByFilePath(System.getProperty("AVIDM_HOME") + File.separator+"plm"+File.separator+"review"+File.separator+"config"+File.separator+"reviewRoleInit.xml");
		Element rootElement = doc.getRootElement();
		List<Element> list = rootElement.elements();
		List<ReviewRole> roles = new ArrayList<ReviewRole>();
		if(list!=null){
			for(Element e:list){
				String roleId="";
				String roleName = "";
				String roleDescription = "";
				roleId=e.attributeValue("id");
				ReviewRole roleExsit = this.getReviewRoleByRoleId(roleId);
				if(roleExsit==null){
					List<Element> childrenList = e.elements();
					for(Element ec:childrenList){
						if(ec.attributeValue("name").equals("roleName")){
							roleName = ec.attributeValue("value");
						}
						if(ec.attributeValue("name").equals("roleDescription")){
							roleDescription = ec.attributeValue("value");
						}
					}
					roles.add(ReviewRoleHelper.getReviewRoleService().createReviewRole(roleId, roleName, roleDescription));
				}
			}
		}
		return roles;
	}

	@Override
	public void saveReviewRole(ReviewRole role) {
		Helper.getPersistService().save(role);
	}

	@Override
	public void deleteReviewRole(ReviewRole role) {
		Helper.getPersistService().delete(role);
	}

	@Override
	public boolean checkReviewRoleId(String roleId) {
		ReviewRole role = this.getReviewRoleByRoleId(roleId);
		if(role!=null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public ReviewRole getReviewRoleByInnerId(String innerId) {
		String oid = Helper.getOid(ReviewRole.CLASSID,innerId);
		ReviewRole role = this.getReviewRoleByOid(oid);
		return role;
	}
	
	@Override
	public void updateReviewRole(ReviewRole role){
		Helper.getPersistService().update(role);
	}

	@Override
	public void deleteReviewRoles(String[] ids) {
		for(int i=0; i<ids.length; i++){
			ReviewRole role = (ReviewRole)Helper.getPersistService().getObject(ReviewRole.CLASSID,ids[i]);	
			List<ReviewRoleUser> list = ReviewRoleUserHelper.getReviewRoleService().getAllReviewPrincipal(role.getInnerId(),"");
			if(list != null && list.size()>0){
				for(ReviewRoleUser rru : list){
					 ReviewRoleUserHelper.getReviewRoleService().deleteReviewRoleUser(rru);
				}
			}
			deleteReviewRole(role);
		}
	}

	@Override
	public ReviewRole getReviewRoleByOid(String oid) {
		ReviewRole role = (ReviewRole)Helper.getPersistService().getObject(oid);
		return role;
	}
}
