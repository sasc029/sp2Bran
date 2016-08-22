package com.bjsasc.plm.review2.item.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.platform.webframework.util.DateUtil;
import com.bjsasc.plm.Helper;
import com.bjsasc.plm.core.persist.PersistHelper;
import com.bjsasc.plm.core.review2.Reviewed;
import com.bjsasc.plm.core.session.SessionHelper;
import com.bjsasc.plm.core.util.DateTimeUtil;
import com.bjsasc.plm.review2.committee.model.ReviewCommitteeMemberHS;
import com.bjsasc.plm.review2.committee.service.ReviewCommitteeMemberHelper;
import com.bjsasc.plm.review2.common.BaseAction;
import com.bjsasc.plm.review2.dataconfig.model.ReviewDataConfig;
import com.bjsasc.plm.review2.dataconfig.service.ReviewDataConfigHelper;
import com.bjsasc.plm.review2.expert.model.DomainReviewExpert;
import com.bjsasc.plm.review2.item.model.ReviewItem;
import com.bjsasc.plm.review2.item.service.ReviewItemHelper;
import com.bjsasc.plm.review2.item.service.ReviewItemService;
import com.bjsasc.plm.review2.link.service.ReviewMemberHelper;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.plm.review2.order.service.ReviewOrderHelper;
import com.bjsasc.plm.review2.role.model.ReviewRoleUser;
import com.bjsasc.plm.review2.role.service.ReviewRoleHelper;
import com.bjsasc.plm.review2.role.service.ReviewRoleUserHelper;
import com.bjsasc.plm.review2.util.ExpertUtil;
import com.bjsasc.ui.json.DataUtil;
import com.cascc.platform.aa.AAProvider;
import com.cascc.platform.aa.api.UserService;

public class ReviewItemAction extends BaseAction {
	
	private static final long serialVersionUID = -490798782789829633L;
	private ReviewItemService service = ReviewItemHelper.getService();
	private UserService userService = AAProvider.getUserService();
	private ReviewItem reviewItem;
	
	public void getManagerReviewItems() throws Exception{
		String userId = SessionHelper.getService().getUser().getAaUserInnerId();
		String reviewItemState = request.getParameter("reviewItemState");
		String roleId = ReviewRoleHelper.getReviewRoleService().getReviewRoleByRoleId("review_principal").getInnerId();
		ReviewRoleUser roleUser = ReviewRoleUserHelper.getReviewRoleService().getReviewRoleUserByUserIdAndRoleId(userId, roleId);
		List<ReviewItem> list =new ArrayList<ReviewItem>();
		if(roleUser!=null){
			String sourceSiteInnerId=roleUser.getSite().getInnerId();
			List<ReviewItem> list1 = service.getReviewItemBySiteId(sourceSiteInnerId,reviewItemState);
			list.addAll(list1);
		}else{
			List<ReviewItem> list1 = service.getReviewItemByExecutor(userId,reviewItemState);
			List<ReviewItem> list2 = service.getReviewItemBySender(userId,reviewItemState);
			
			list.addAll(list1);
			list.addAll(list2);
		}
		
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for(int i=0;i<list.size();i++){
			ReviewItem reviewItem=(ReviewItem)list.get(i);
			Map<String, Object> listMap = new HashMap<String, Object>();
		    listMap.put("INNERID", reviewItem.getInnerId());
		    listMap.put("CLASSID", reviewItem.getClassId());
		    listMap.put("OID",Helper.getOid(reviewItem.getClassId(), reviewItem.getInnerId()));
		    listMap.put("NAME", reviewItem.getItemName());
		    listMap.put("ITEMSOURCE", this.convertName(reviewItem.getItemSource()));
		    listMap.put("EXECUTOR",reviewItem.getUser()==null?"":reviewItem.getUser().getName());
		    listMap.put("SENDER",reviewItem.getSender()==null?"":reviewItem.getSender().getName());
		    listMap.put("STATE",getItemStateStr(reviewItem.getState(),reviewItem));
		    listMap.put("ITEMSTATE", reviewItem.getState());
		    listMap.put("ITEMTYPE", this.convertName(reviewItem.getItemType()));
		    listMap.put("DEADLINE", reviewItem.getDeadLine()==0?"":DateUtil.toDate(reviewItem.getDeadLine()));
		    listMap.put("CONTENTS", "<img src='/avidm/plm/images/baselib/property.gif' alt='落实情况'/>");
		    if(roleUser!=null){
		    	dataList.add(listMap);
		    }else{
		    	if(!ReviewItem.TASKSTATE_NEW.equals(reviewItem.getState())){
			    	dataList.add(listMap);
			    }
		    }
		}
		String json= DataUtil.listToJson(dataList);
		response.getWriter().print(json);
	}
	
	public void getReviewItems() throws Exception{
		String reviewOrderId = request.getParameter("reviewOrderId");
		ReviewOrder review = ReviewOrderHelper.getService().findManagedReviewByInnerId(reviewOrderId);
		ReviewCommitteeMemberHS rcm =ReviewCommitteeMemberHelper.getMemberService().getLeader(review.getInnerId());
		String userId = SessionHelper.getService().getUser().getAaUserInnerId();
		boolean isPrincipal = ExpertUtil.isUserForPSFZR(userId,review); //是否为评审负责人
		boolean isLeader = false;//是否是该评审单的评审组长
		if(rcm!= null){
			if(((DomainReviewExpert)rcm.getExpert()).getUser().getInnerID().equals(userId)){
				isLeader = true;
			}
		}
		
		List<ReviewItem> list = service.getReviewItemByReview(reviewOrderId);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for(int i=0;i<list.size();i++){
			ReviewItem reviewItem=(ReviewItem)list.get(i);
			if(!isLeader && !isPrincipal && !reviewItem.getUser().getInnerId().equals(userId)){
				continue;
			}
			Map<String, Object> listMap = new HashMap<String, Object>();
		    listMap.put("INNERID", reviewItem.getInnerId());
		    listMap.put("CLASSID", reviewItem.getClassId());
		    listMap.put("OID",Helper.getOid(reviewItem.getClassId(), reviewItem.getInnerId()));
		    listMap.put("NAME", reviewItem.getItemName());
		    listMap.put("STATE",getItemStateStr(reviewItem.getState(),reviewItem));
		    listMap.put("ITEMSTATE", reviewItem.getState());
		    listMap.put("ITEMSOURCE", this.convertName(reviewItem.getItemSource()));
		    listMap.put("FORMATTER", reviewItem.getFormatter().equals("file")?"文件":"实物");
		    listMap.put("ITEMTYPE",this.convertName(reviewItem.getItemType()));
		    listMap.put("DEADLINE", reviewItem.getDeadLine()==0?"":DateUtil.toDate(reviewItem.getDeadLine()));
		    listMap.put("CREATE_TIME", DateUtil.toDate(reviewItem.getManageInfo().getCreateTime()));
		    listMap.put("EXECUTOR",reviewItem.getUser()==null?"":reviewItem.getUser().getName());
		    listMap.put("SENDER",reviewItem.getSender()==null?"":reviewItem.getSender().getName());
		    listMap.put("CONTENTS", "<img src='/avidm/plm/images/baselib/property.gif' alt='落实情况'/>");
		    if(isLeader || isPrincipal){
		    	dataList.add(listMap);
		    }else{
		    	if(!reviewItem.getState().equals(ReviewItem.TASKSTATE_NEW)){
		    		dataList.add(listMap);
		    	}
		    }
		}
		String json= DataUtil.listToJson(dataList);
		response.getWriter().print(json);
	}
    public void createReviewItem() throws Exception{
    	UserService userService = AAProvider.getUserService();
    	String reviewoid = request.getParameter("oid");
    	String itemSource = request.getParameter("itemSource");
    	String itemType = request.getParameter("itemType");
    	String startTime = request.getParameter("startTime");
    	String endTime = request.getParameter("endTime");
    	String name= request.getParameter("NAME");
    	String contents = request.getParameter("CONTENTS");
    	String executorId = request.getParameter("EXECUTORID");
    	if(executorId.indexOf(",")!=-1){
    		executorId = executorId.substring(0,executorId.indexOf(","));
    	}
    	String formatter = request.getParameter("FORMATTER");
    	String note = request.getParameter("note");
    	
    	ReviewOrder review = (ReviewOrder)PersistHelper.getService().getObject(reviewoid);
    	List<Reviewed> rvObjs=ReviewMemberHelper.getService().getReviewedListByOrder(review);
        ReviewObject obj = (ReviewObject)rvObjs.get(0);
    	ReviewItem reviewitem = service.newReviewItem();
    	reviewitem.setItemName(name);
    	reviewitem.setUser(userService.getUser(null, executorId));
    	reviewitem.setFormatter(formatter);
    	reviewitem.setReviewOrder(review);
    	reviewitem.setProductIID(obj.getProductIID());
    	reviewitem.setProductName(obj.getProductName());
    	reviewitem.setPhaseId(obj.getPhaseId());
    	reviewitem.setPhaseName(obj.getPhaseName());
    	reviewitem.setProfessionID(obj.getProfessionID());
    	reviewitem.setProfessionName(obj.getProfessionName());
    	reviewitem.setItemSource(itemSource);
    	reviewitem.setItemType(itemType);
    	reviewitem.setNote(note);
    	reviewitem.setSourceSiteInnerId(obj.getSourceSiteInnerId());
    	//时间
    	String TIME_FORMAT = "yyyy-MM-dd";
    	SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
        long  startTimeToLong = sf.parse(startTime).getTime();
        long  endTimeToLong = sf.parse(endTime).getTime();
        reviewitem.setStartTime(startTimeToLong);
    	reviewitem.setDeadLine(endTimeToLong);
    	reviewitem.setItemContents(contents);
    	
    	Map<String,String> result=new HashMap<String,String>();
    	if(endTimeToLong<startTimeToLong){
    		result.put("SUCCESS", "false");
    	}else{
    		PersistHelper.getService().save(reviewitem);
    		result.put("SUCCESS", "true");
    	}
    	response.getWriter().print(DataUtil.mapToSimpleJson(result));
    }
    public void editReviewItem() throws Exception{
	    String rioid = request.getParameter("ptoid");
	    ReviewItem riItem =(ReviewItem)PersistHelper.getService().getObject(rioid);
    	    
    	String startTime = request.getParameter("startTime");
    	String endTime = request.getParameter("endTime");
    	String executorId = request.getParameter("EXECUTORID");
    	String name= request.getParameter("NAME");
    	String contents = request.getParameter("CONTENTS");
    	String formatter = request.getParameter("FORMATTER");
    	String note = request.getParameter("note");
    	
    	//时间
    	String TIME_FORMAT = "yyyy-MM-dd";
    	SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
    	long startTimeToLong = sf.parse(startTime).getTime();
    	long endTimeToLong = sf.parse(endTime).getTime();
    	
    	riItem.setDeadLine(endTimeToLong);
    	riItem.setStartTime(startTimeToLong);
    	riItem.setItemName(name);
    	riItem.setFormatter(formatter);
    	riItem.setItemContents(contents);
    	riItem.setUser(userService.getUser(null, executorId));
    	riItem.setNote(note);
    	Map<String,String> result=new HashMap<String,String>();
	    if(endTimeToLong<startTimeToLong){
	    	result.put("SUCCESS", "false");
	    }else{
	    	PersistHelper.getService().update(riItem);
	    	result.put("SUCCESS", "true");
	    }	    	
    	
    	response.getWriter().print(DataUtil.mapToSimpleJson(result));
    }
    
    public void startReviewItem() throws Exception{
    	String userInnerId = SessionHelper.getService().getUser().getAaUserInnerId();
    	String itemId = request.getParameter("itemId");
    	Map<String,String> m=new HashMap<String,String>();
    	ReviewItem ri = service.findReviewItemByInnerId(itemId);
 
    	ri.setState(ReviewItem.TASKSTATE_RUNNING);
    	ri.setSender(userService.getUser(null, userInnerId));
    	PersistHelper.getService().update(ri);
    	m.put("SUCCESS", "true");
    	response.getWriter().print(DataUtil.mapToSimpleJson(m));
    }
    //批量下发
    public void sendReviewItem() throws Exception{
    	String userInnerId = SessionHelper.getService().getUser().getInnerId();
    	String id = request.getParameter("ids");
    	String[] ids = id.split(",");
    	
    	boolean isSend = true;
    	Map<String,String> m=new HashMap<String,String>();
    	for(int i=0;i<ids.length;i++) {
    		ReviewItem  ri=service.findReviewItemByInnerId(ids[i]);
    		//只能下发新建状态的
    		if(!ri.getState().equals(ReviewItem.TASKSTATE_NEW)||ri.getUser()==null){
    			isSend = false;
    			break;
    		}
    	}
    	
    	if(isSend){
	    	for(int i=0;i<ids.length;i++) {
	    		ReviewItem  ri=service.findReviewItemByInnerId(ids[i]);
	    		if(ri.getState().equals(ReviewItem.TASKSTATE_NEW)){
	    	    	ri.setState(ReviewItem.TASKSTATE_RUNNING);
	    	    	ri.setSender(userService.getUser(null, userInnerId));
	    	    	PersistHelper.getService().update(ri);
	    		}
	    	}
	    	m.put("SUCCESS", "true");
    	}else{
    		m.put("SUCCESS", "false");
    	}
    	response.getWriter().print(DataUtil.mapToSimpleJson(m));
    }
    public void delReviewItem() throws Exception{
    	//删除待办事项
    	String id = request.getParameter("ids");
    	String[] ids = id.split(",");
    	
    	boolean isDel = true;
    	Map<String,String> m=new HashMap<String,String>();
    	for(int i=0;i<ids.length;i++) {
    		ReviewItem  ri=service.findReviewItemByInnerId(ids[i]);
    		//只能删除新建状态的
    		if(!ri.getState().equals(ReviewItem.TASKSTATE_NEW)){
    			isDel = false;
    			break;
    		}
    	}
    	if(isDel){
    		service.delReviewItem(ids);
    		m.put("SUCCESS", "true");
    	}else{
    		m.put("SUCCESS", "false");
    	}
    	response.getWriter().print(DataUtil.mapToSimpleJson(m));
    }
    
    /**
     * 添加落实情况
     * */
    public void addContents() throws IOException{
    	String rpId = request.getParameter("rpId");
    	String contents = request.getParameter("contents");
    	String flag = request.getParameter("flag");
    	ReviewItem ri = service.findReviewItemByInnerId(rpId);
    	if("submit".equals(flag)){
    		ri.setState(ReviewItem.TASKSTATE_REPLY);
        	ri.setDealContents(contents);
    	}else{
    		ri.setIsConfirm("1");
    		ri.setState(ReviewItem.TASKSTATE_REPLYAFFIRM);
    		ri.setEndTime(DateTimeUtil.getLongTime(new Date(), "yyyy-MM-dd"));
    	}
		
    	service.updateReviewItem(ri);
    	response.getWriter().print(SUCCESS);
    }
    public boolean isSendItemTask(String reviewId,String executorId){
    	boolean isSend = true;
    	List<ReviewItem> items = service.getReviewItemByReviewAndExecutor(reviewId, executorId);
    	for(ReviewItem item:items){
    		if(!item.getState().equals(ReviewItem.TASKSTATE_REPLYAFFIRM)&&!item.getState().equals(ReviewItem.TASKSTATE_NEW)){
    			isSend = false;
    			break;
    		}
    	}
    	return isSend;
    }
    public String getItemStateStr(String state,ReviewItem ri){
		if(state.equals(ReviewItem.TASKSTATE_NEW)){
			return "新建";
		}else if(state.equals(ReviewItem.TASKSTATE_RUNNING)){
			long nowTime =DateTimeUtil.getLongTime(new Date(), "yyyy-MM-dd");
			if(nowTime>ri.getDeadLine()){
				return "到期未确认";
			}
			return "确认中";
		}else if(state.equals(ReviewItem.TASKSTATE_REPLY)){
			long nowTime =DateTimeUtil.getLongTime(new Date(), "yyyy-MM-dd");
			if(nowTime>ri.getDeadLine()){
				return "到期未确认";
			}
			return "确认中";
		}else if(state.equals(ReviewItem.TASKSTATE_REPLYAFFIRM)){
			if(ri.getEndTime()>ri.getDeadLine()){
				return "延期完成";
			}else{
				return "按期完成";
			}
		}else{
			return "按期完成";
		}
	}
    public String convertName(String str){
    	ReviewDataConfig rvDataCfg= ReviewDataConfigHelper.getService().findRvDataCfgByDataType(str);
    	return rvDataCfg.getDataName();
    }
	public ReviewItem getReviewItem() {
		return reviewItem;
	}

	public void setReviewItem(ReviewItem reviewItem) {
		this.reviewItem = reviewItem;
	}
	
}