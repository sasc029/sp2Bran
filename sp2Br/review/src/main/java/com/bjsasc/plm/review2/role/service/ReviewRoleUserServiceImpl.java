package com.bjsasc.plm.review2.role.service;

import java.util.ArrayList;
import java.util.List;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.avidm.core.site.SiteHelper;
import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.review2.role.model.ReviewRole;
import com.bjsasc.plm.review2.role.model.ReviewRoleUser;
import com.cascc.platform.aa.AAProvider;
import com.cascc.platform.aa.api.UserService;
import com.cascc.platform.aa.api.data.AAUserData;

public class ReviewRoleUserServiceImpl implements ReviewRoleUserService {

	@Override
	public void addReviewRoleUsers(String roleId,String siteId, String[] arrayUserIds,
			String[] arrayUserNames) {
		ReviewRole role = ReviewRoleHelper.getReviewRoleService().getReviewRoleByInnerId(roleId);
		Site site = SiteHelper.getSiteService().findSiteById(siteId);
		if(arrayUserIds!=null&&arrayUserIds.length>0){
			List<ReviewRoleUser> roleUsers = new ArrayList<ReviewRoleUser>();
			UserService userService = AAProvider.getUserService();
			for(int i=0; i<arrayUserIds.length; i++){
				String userId = arrayUserIds[i];
				if(!isExistReviewRoleUser(userId, roleId)){
					AAUserData user = userService.getUser(null, userId);
					ReviewRoleUser roleUser = new ReviewRoleUser();
					roleUser.setSite(site);
					roleUser.setUser(user);
					roleUser.setRole(role);
					roleUsers.add(roleUser);
				}
			}
			if(roleUsers.size()>0){
				Helper.getPersistService().save(roleUsers);
			}
		}
	}

	@Override
	public List<ReviewRoleUser> getReviewRolesUsers(String roleId ,String sourceSiteInnerId) {
		String hql = "";
		if("null".equals(sourceSiteInnerId)){
			 hql = "from ReviewRoleUser as user where user.roleRef.innerId=? order by user.manageInfo.createTime asc";
		}else{
			hql = "from ReviewRoleUser as user where user.roleRef.innerId=? and siteRef.innerId='"+sourceSiteInnerId+"' order by user.manageInfo.createTime asc";
		}
		List<ReviewRoleUser> users = Helper.getPersistService().find(hql,roleId==null?"":roleId);
		if(users!=null){
			return users;
		}
		return null;
	}

	@Override
	public void deleteReviewRoleUsers(String[] ids) {
		for(int i=0; i<ids.length; i++){
			ReviewRoleUser user = (ReviewRoleUser)Helper.getPersistService().getObject(ReviewRoleUser.CLASSID,ids[i]);	
			deleteReviewRoleUser(user);
		}
	}

	@Override
	public void deleteReviewRoleUser(ReviewRoleUser user) {
		if(user!=null){
			Helper.getPersistService().delete(user);
		}
	}

	@Override
	public ReviewRoleUser getReviewRoleUserByUserIdAndRoleId(String userId,String roleId) {
		String hql = "from ReviewRoleUser as user where user.userRef.innerId=? and user.roleRef.innerId=?";
		List<ReviewRoleUser> users = Helper.getPersistService().find(hql,userId==null?"":userId,roleId==null?"":roleId);
		if(users!=null&&users.size()>0){
			return users.get(0);
		}
		return null;
		
	}

	@Override
	public boolean isExistReviewRoleUser(String userId,String roleId) {
		ReviewRoleUser user = getReviewRoleUserByUserIdAndRoleId(userId, roleId);
		if(user!=null){
			return true;
		}
		return false;
	}

	public List<ReviewRoleUser> getAllReviewPrincipal(String reviewRoleId,String sourceSiteInnerId) {
		String hqlRR = "from ReviewRole as r where r.roleID= ?";
		List<ReviewRole> rr = Helper.getPersistService().find(hqlRR,reviewRoleId);
		if(rr!=null&&rr.size()>0){
			String hql = "from ReviewRoleUser as u where u.roleRef.innerId= ?";
			if(!"".equals(sourceSiteInnerId)){
				hql +=" and siteRef.innerId='"+sourceSiteInnerId+"'";
			}
			List<ReviewRoleUser> rruList = Helper.getPersistService().find(hql,rr.get(0).getInnerId());
			return rruList;
		}
		return null;
	}

	@Override
	public ReviewRoleUser getReviewRoleUserByInnerId(String innerId) {
		String hql = "from ReviewRoleUser as user where user.innerId=? ";
		List<ReviewRoleUser> users = Helper.getPersistService().find(hql,innerId);
		if(users!=null&&users.size()>0){
			return users.get(0);
		}
		return null;
	}
}
