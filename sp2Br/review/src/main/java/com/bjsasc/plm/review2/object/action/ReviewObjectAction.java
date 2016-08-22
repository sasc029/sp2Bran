package com.bjsasc.plm.review2.object.action;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.platform.webframework.util.DateUtil;
import com.bjsasc.plm.KeyS;
import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.core.context.model.Context;
import com.bjsasc.plm.core.context.template.FileTemplate;
import com.bjsasc.plm.core.context.template.TemplateHelper;
import com.bjsasc.plm.core.doc.Document;
import com.bjsasc.plm.core.doc.DocumentMaster;
import com.bjsasc.plm.core.part.Part;
import com.bjsasc.plm.core.part.PartMaster;
import com.bjsasc.plm.core.persist.model.Persistable;
import com.bjsasc.plm.core.review2.Reviewed;
import com.bjsasc.plm.review2.common.BaseAction;
import com.bjsasc.plm.review2.constant.ReviewObjectConstant;
import com.bjsasc.plm.review2.constant.ReviewOrderConstant;
import com.bjsasc.plm.review2.link.model.ReviewOrderMemberLink;
import com.bjsasc.plm.review2.link.service.ReviewMemberHelper;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.object.service.ReviewObjectHelper;
import com.bjsasc.plm.review2.object.service.ReviewObjectService;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.plm.util.Ajax;
import com.bjsasc.ui.json.DataUtil;

public class ReviewObjectAction extends BaseAction {
	
	private static final long serialVersionUID = -490798782789829633L;
	private ReviewObjectService service=ReviewObjectHelper.getService();
	
	public String getReviewObjectById()throws IOException{
		String innerId = request.getParameter("id");
		String reviewFlag = request.getParameter("reviewFlag");
		ReviewObject reviewObject= service.findReviewObjectById(innerId);
		request.setAttribute("reviewObject", reviewObject);
		if(ReviewObjectConstant.RVOBJ_FLAG_ELECTRON.equals(reviewFlag)){
			request.setAttribute("reviewType", ReviewOrderConstant.REVIEW_TYPE_ELECTRON);
		}else if(ReviewObjectConstant.RVOBJ_FLAG_METTING.equals(reviewFlag)){
			request.setAttribute("reviewType", ReviewOrderConstant.REVIEW_TYPE_MEETING);
		}
		
		return "addReviewOrder";
	}
	
	public void getUsableDocTemplate() throws IOException {
		Context context =Helper.getContextService().getRootContext(); //folder.getContextInfo().getContext();
		List<FileTemplate> fileTemplateList = TemplateHelper.getService().listContextTemplates(context, FileTemplate.class);
		response.getWriter().print(this.DoucmentTemplateListToJson(fileTemplateList));
	}
	
	/**
	 * List转换为Json
	 */
	public String DoucmentTemplateListToJson(List list){
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			FileTemplate fileTemplate = (FileTemplate) list.get(i);
			Map<String, Object> attrMap = new HashMap<String, Object>();
			attrMap.put("innerId", fileTemplate.getInnerId());
			attrMap.put("name", fileTemplate.getName());
			attrMap.put("fileName",  fileTemplate.getFile().getFileName());
			dataList.add(attrMap);
		}
		return DataUtil.listToJson(dataList);
	}
	
	public void getRevieweds() throws Exception{
		String reviewoid = request.getParameter("reviewoid");
		ReviewOrder review = (ReviewOrder)Helper.getPersistService().getObject(reviewoid);
		List<Reviewed> list = ReviewMemberHelper.getService().getReviewedListByOrder(review);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for(int i=0;i<list.size();i++){
			Map<String, Object> listMap = new HashMap<String, Object>();
			Reviewed reviewed=list.get(i);
			if(reviewed instanceof ReviewObject){
				ReviewObject rvobj=(ReviewObject)reviewed;
			    listMap.put("INNERID", rvobj.getInnerId());
			    listMap.put("CLASSID", rvobj.getClassId());
			    listMap.put("NUMBER", rvobj.getPin());
			    listMap.put("NAME", rvobj.getDocName());
			    listMap.put("VERSION", rvobj.getVersion());
			    listMap.put("CONTEXT", rvobj.getContextInfo().getContext().getName());
			    listMap.put("LIFECYCLE_STATE", rvobj.getState());
			    listMap.put("CREATE_TIME", DateUtil.toDateTime(rvobj.getCreateTime()));
			    listMap.put("CREATOR", rvobj.getCreateUserName());
			    listMap.put("RVOBJDATAURL", URLEncoder.encode(rvobj.getDataUrl(), "utf-8"));
		    }else if(reviewed instanceof Document){
		    	Document rvobj=(Document)reviewed;
			    listMap.put("INNERID", rvobj.getInnerId());
			    listMap.put("CLASSID", rvobj.getClassId());
			    DocumentMaster master = (DocumentMaster)rvobj.getMaster();
			    listMap.put("NUMBER", master.getNumber());
			    listMap.put("NAME", master.getName());
			    listMap.put("VERSION", rvobj.getIterationInfo().getVersionNo());
			    listMap.put("CONTEXT", rvobj.getContextInfo().getContext().getName());
			    listMap.put("LIFECYCLE_STATE", rvobj.getLifeCycleInfo().getStateName());
			    listMap.put("CREATE_TIME", DateUtil.toDateTime(rvobj.getManageInfo().getCreateTime()));
			    listMap.put("CREATOR", rvobj.getManageInfo().getCreateBy().getName());
		    }else if(reviewed instanceof Part){
		    	Part rvobj=(Part)reviewed;
			    listMap.put("INNERID", rvobj.getInnerId());
			    listMap.put("CLASSID", rvobj.getClassId());
			    PartMaster master = (PartMaster)rvobj.getMaster();
			    listMap.put("NUMBER", master.getNumber());
			    listMap.put("NAME", master.getName());
			    listMap.put("VERSION", rvobj.getIterationInfo().getVersionNo());
			    listMap.put("CONTEXT", rvobj.getContextInfo().getContext().getName());
			    listMap.put("LIFECYCLE_STATE", rvobj.getLifeCycleInfo().getStateName());
			    listMap.put("CREATE_TIME", DateUtil.toDateTime(rvobj.getManageInfo().getCreateTime()));
			    listMap.put("CREATOR", rvobj.getManageInfo().getCreateBy().getName());
		    }
			listMap.put("OPINION", "<img src='/avidm/plm/images/baselib/property.gif' alt='意见'/>");
		    dataList.add(listMap);
		}
		String json= DataUtil.listToJson(dataList);
		response.getWriter().print(json);
	}
	/**
	 * 添加评审对象
	 */
	public void addRevieweds() throws Exception{
		String reviewoid = request.getParameter("reviewoid");
		String data = request.getParameter(KeyS.DATA);  
		List<Map<String,Object>> listMap = DataUtil.JsonToList(data);
		ReviewOrder review=(ReviewOrder) Helper.getPersistService().getObject(reviewoid);
		for(Map<String,Object> item:listMap) {
			Persistable obj = (Persistable)Helper.getPersistService().getObject(item.get(KeyS.OID).toString());
			Reviewed Reviewed=	(Reviewed)obj;
			ReviewMemberHelper.getService().saveReviewMemberLink(new ReviewOrderMemberLink(review,Reviewed));	
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(Ajax.SUCCESS, "true");
		String jsonObject = DataUtil.mapToSimpleJson(map);
		response.getWriter().print(jsonObject);
	}
	/**
	 * 删除评审对象
	 */
	public void delRevieweds() throws Exception {
		String id = request.getParameter("ids");
		String ids[] = id.split(",");
		ReviewMemberHelper.getService().delReviewMemberLinkByObjId(ids);;
		response.getWriter().print(SUCCESS);
	}
	/**
	 * 检查评审对象
	 */
	public void checkObject() throws Exception {
		String id = request.getParameter("innerid");
		List<ReviewOrderMemberLink> list=ReviewMemberHelper.getService().getReviewMemberLinkByToObjId(id);
		if(list.size()>0){
		    response.getWriter().print("false");
		}else{
			response.getWriter().print("true");
		}
	}
}
