package com.bjsasc.plm.review2.order.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.avidm.core.site.SiteHelper;
import com.bjsasc.avidm.core.transfer.TransferObject;
import com.bjsasc.avidm.core.transfer.TransferObjectHelper;
import com.bjsasc.avidm.core.transfer.util.TransferConstant;
import com.bjsasc.platform.objectmodel.business.transaction.HibernateUtil;
import com.bjsasc.plm.collaboration.util.ObjectToMapUtil;
import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.review2.Reviewed;
import com.bjsasc.plm.core.session.SessionHelper;
import com.bjsasc.plm.review2.distribute.model.ReviewDistribute;
import com.bjsasc.plm.review2.distribute.service.ReviewDistributeHelper;
import com.bjsasc.plm.review2.export.ExportReviewUtil;
import com.bjsasc.plm.review2.link.service.ReviewMemberHelper;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;

public class ReviewOrderServiceImpl implements ReviewOrderService {
	/**
	 * 查询所有数据
	 * @param managedReview
	 */
	@Override
	public List<ReviewOrder> findAllManagedReview() {
		String hql = "from ReviewOrder";
		return (List<ReviewOrder>)Helper.getPersistService().find(hql);
	}
	
	/**
	 * 查询单条数据
	 * @param innerId
	 * @return
	 */
	@Override
	public ReviewOrder findManagedReviewByInnerId(String innerId) {
		boolean isCheck = SessionHelper.getService().isCheckPermission();
		//关闭权限检查
		SessionHelper.getService().setCheckPermission(false) ;
		List<ReviewOrder> managedReview=Helper.getPersistService().find(" from ReviewOrder WHERE innerId=?", innerId);
		if(!managedReview.isEmpty()){
			SessionHelper.getService().setCheckPermission(isCheck);
			return managedReview.get(0);
		}
		SessionHelper.getService().setCheckPermission(isCheck);
		return null;
	}

	@Override
	public void delManagedReview(String[] ids) {
		for(int i=0; i<ids.length; i++){
			ReviewOrder managedReview = findManagedReviewByInnerId(ids[i]);
			deleteManagedReview(managedReview);
		}
	}
	
	/**
	 * 删除记录
	 * @param managedReview
	 */
	public void deleteManagedReview(ReviewOrder managedReview) {
		boolean isCheck = SessionHelper.getService().isCheckPermission();
		//关闭权限检查
		SessionHelper.getService().setCheckPermission(false) ;
		ReviewMemberHelper.getService().delReviewMemberLink(managedReview.getInnerId());
		Helper.getPersistService().delete(managedReview);
		SessionHelper.getService().setCheckPermission(isCheck);
	}

	/**
	 * 新增数据
	 * @param managedReview
	 */
	@Override
	public void saveManagedReview(ReviewOrder managedReview) {
		Helper.getPersistService().save(managedReview);
	}

	/**
	 * 修改数据
	 * @param managedReview
	 */
	@Override
	public void updateManagedReview(ReviewOrder managedReview) {
		boolean isCheck = SessionHelper.getService().isCheckPermission();
		//关闭权限检查
		SessionHelper.getService().setCheckPermission(false) ;
		Helper.getPersistService().update(managedReview);
		SessionHelper.getService().setCheckPermission(isCheck);
	}

	@Override
	public void sendReviewCertificate(ReviewOrder managedReview) {
		List<Reviewed> list = ReviewMemberHelper.getService().getReviewedListByOrder(managedReview);
		for(int i=0, l=list.size(); i<l; i++){
			Reviewed reviewed = list.get(i);
			if(reviewed instanceof ReviewObject){
				ReviewObject reviewObject = (ReviewObject)reviewed;
				TransferObject transferObject = new TransferObject();
				Site selfSite = SiteHelper.getSiteService().findLocalSiteInfo();
				transferObject.setObjIID(managedReview.getInnerId());
				transferObject.setObjName(managedReview.getName());
				transferObject.setTransferType("reviewCertificateRequest");
				transferObject.setObjType("review");
				transferObject.setSourceSite(selfSite);
				String targetSiteInnerId = reviewObject.getSourceSiteInnerId();
				Site targetSite = SiteHelper.getSiteService().findSiteById(targetSiteInnerId);
				transferObject.setTargetSite(targetSite);
				transferObject.setCreateTime(System.currentTimeMillis());
				transferObject.setTransferState(TransferConstant.TRANSOBJECT_STATE_NEW);
				Map<String, String> map = new HashMap<String, String>();
				map.put("docIID", reviewObject.getDocId());
				map.put("productIID", reviewObject.getProductIID());
				transferObject.setReqParamMap(map);
				transferObject.setRequestType(TransferConstant.REQTYPE_ASYN);
				String filePath = ExportReviewUtil.getFilePath(managedReview);
				transferObject.setFilePath(filePath);
				TransferObjectHelper.getTransferService().sendRequest(transferObject);
			}
		}
		
	}

	@Override
	public String getOrderId(String docId) {
		String orderId = docId +"-PS";
		Statement stmt = null;
		ResultSet rs = null;
		Connection connection = null;
		try {	
			connection = HibernateUtil.getSessionFactory("AvidmSystemDB").getCurrentSession().connection();
			String sql = "select to_char(PLM_REVIEW_ORDERID_SEQ.NEXTVAL,'0000') from DUAL";
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				orderId +="-"+ rs.getString(1).trim();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(connection != null){
					connection.close();
				}
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}

		return orderId;
	}

	@Override
	public void sendMeetingReview(ReviewOrder managedReview) {
		List<Reviewed> list = ReviewMemberHelper.getService().getReviewedListByOrder(managedReview);
		for(int i=0, l=list.size(); i<l; i++){
			Reviewed reviewed = list.get(i);
			if(reviewed instanceof ReviewObject){
				ReviewObject reviewObject = (ReviewObject)reviewed;
				TransferObject transferObject = new TransferObject();
				Site selfSite = SiteHelper.getSiteService().findLocalSiteInfo();
				transferObject.setObjIID(managedReview.getInnerId());
				transferObject.setObjName(managedReview.getName());
				transferObject.setTransferType("reviewMeetingRequest");
				transferObject.setObjType("reviewMeeting");
				transferObject.setSourceSite(selfSite);
				String targetSiteInnerId = reviewObject.getSourceSiteInnerId();
				Site targetSite = SiteHelper.getSiteService().findSiteById(targetSiteInnerId);
				transferObject.setTargetSite(targetSite);
				transferObject.setCreateTime(System.currentTimeMillis());
				transferObject.setTransferState(TransferConstant.TRANSOBJECT_STATE_NEW);
				Map<String, String> map = new HashMap<String, String>();
				map.put("docIID", reviewObject.getDocId());
				map.put("productIID", reviewObject.getProductIID());
				transferObject.setReqParamMap(map);
				transferObject.setRequestType(TransferConstant.REQTYPE_ASYN);
				String filePath = ExportReviewUtil.getFilePath(managedReview);
				transferObject.setFilePath(filePath);
				TransferObjectHelper.getTransferService().sendRequest(transferObject);
			}
		}
	}

	@Override
	public void sendReviewDistribute(ReviewOrder reviewOrder) {
		List<Reviewed> list = ReviewMemberHelper.getService().getReviewedListByOrder(reviewOrder);
		List<ReviewDistribute> distributeList = ReviewDistributeHelper.getReviewDistributeService().getRvDistributeListByOrderId(reviewOrder.getInnerId());
		for(int i=0, l=list.size(); i<l; i++){
			Reviewed reviewed = list.get(i);
			if(reviewed instanceof ReviewObject){
				ReviewObject reviewObject = (ReviewObject)reviewed;
				TransferObject transferObject = new TransferObject();
				Site selfSite = SiteHelper.getSiteService().findLocalSiteInfo();
				transferObject.setObjIID(reviewOrder.getInnerId());
				transferObject.setObjName(reviewOrder.getName());
				transferObject.setTransferType("reviewDistributeRequest");
				transferObject.setObjType("reviewDistribute");
				transferObject.setSourceSite(selfSite);
				String targetSiteInnerId = reviewObject.getSourceSiteInnerId();
				Site targetSite = SiteHelper.getSiteService().findSiteById(targetSiteInnerId);
				transferObject.setTargetSite(targetSite);
				transferObject.setCreateTime(System.currentTimeMillis());
				transferObject.setTransferState(TransferConstant.TRANSOBJECT_STATE_NEW);
				Map<String, String> map = getDistributeMap(reviewObject, distributeList,reviewOrder);
				transferObject.setReqParamMap(map);
				transferObject.setRequestType(TransferConstant.REQTYPE_ASYN);
				TransferObjectHelper.getTransferService().sendRequest(transferObject);
			}
		}
	}
	
	private Map<String, String> getDistributeMap(ReviewObject object, List<ReviewDistribute> distributeList,ReviewOrder reviewOrder){
		Map<String, String> map = new HashMap<String, String>();
		try {
			map.put("ReviewDistribute", ObjectToMapUtil.listObjectToJson(distributeList));
			map.put("docIID", object.getDocId());
			map.put("productIID", object.getProductIID());
			map.put("checkdate", String.valueOf(reviewOrder.getReviewTime()));
			map.put("checkplace", reviewOrder.getReviewPlace());
		} catch (Exception e) {
			throw new RuntimeException("getDistributeMap封装评审分发信息请求参数出错", e);
		}
		return map;
	}
}
