package com.bjsasc.plm.review2.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.platform.webframework.util.DateUtil;
import com.bjsasc.plm.client.docserverclient.datatype.SignContent;
import com.bjsasc.plm.core.review2.Reviewed;
import com.bjsasc.plm.core.system.principal.User;
import com.bjsasc.plm.core.system.principal.UserHelper;
import com.bjsasc.plm.core.system.principal.UserService;
import com.bjsasc.plm.review2.committee.service.ReviewCommitteeMemberHelper;
import com.bjsasc.plm.review2.conclusion.model.ReviewConclusionHS;
import com.bjsasc.plm.review2.conclusion.service.ReviewConclusionHelper;
import com.bjsasc.plm.review2.constant.ReviewOrderConstant;
import com.bjsasc.plm.review2.expert.model.DomainReviewExpert;
import com.bjsasc.plm.review2.expert.model.ReviewExpert;
import com.bjsasc.plm.review2.item.model.ReviewItem;
import com.bjsasc.plm.review2.item.service.ReviewItemHelper;
import com.bjsasc.plm.review2.link.service.ReviewMemberHelper;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.opinion.model.ReviewOpinion;
import com.bjsasc.plm.review2.opinion.service.ReviewOpinionHelper;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.plm.review2.result.model.ReviewResult;
import com.bjsasc.plm.review2.result.service.ReviewResultHelper;

public class ReviewOrderUtil {

	/**
	 * 获取函审单签署信息
	 * 
	 * @author liyu
	 * @param managedReview
	 * @return
	 */
	public static Map<String, SignContent> getManagedReviewSignInfo(ReviewOrder managedReview) {
		
		Map<String, SignContent> map = new HashMap<String, SignContent>();
		
		
		List<Reviewed> list = ReviewMemberHelper.getService().getReviewedListByOrder(managedReview);
		
		//评审专家
		ReviewExpert expert = (ReviewExpert)ReviewCommitteeMemberHelper.getMemberService().getLeader(managedReview.getInnerId()).getExpert();
		DomainReviewExpert reviewExpert=null;
		if(expert instanceof DomainReviewExpert){
			reviewExpert = (DomainReviewExpert)expert;
		}
		//评审结论
		ReviewConclusionHS conclusion = ReviewConclusionHelper.getService().findReviewConclusionByReviewOrder(managedReview.getInnerId()).get(0);
		//待办事项
		List<ReviewItem> items = ReviewItemHelper.getService().getReviewItemByReview(managedReview.getInnerId());
		String itemContents="";
		String contents="";
		for(int i=0;i<items.size();i++){
			itemContents+=(i+1)+"、"+items.get(i).getItemContents()+";";
			contents+=(i+1)+"、"+items.get(i).getDealContents()+";";
		}
		/*
		 * 函审证明书
		 */
		//编号
		String number = "";
		//密别
		String securityLevel = "";
		//阶段标记
		String phaseMark = "";
		//函审文件编号
		String reviewNumber = "";
		//函审文件名称
		String reviewName = "";
		//设计师
		String designerName = "";
		
		for(int i=0,l=list.size();i<l;i++){
			Reviewed reviewed = list.get(i);
			if(reviewed instanceof ReviewObject){
				ReviewObject reviewObj = (ReviewObject)reviewed;
				number = reviewObj.getPin()+".PS";
				//密别
				securityLevel = reviewObj.getScurityLevel();
				//阶段标记
				phaseMark = reviewObj.getPhaseName();
				//函审文件编号
				reviewNumber = reviewObj.getPin();
				//函审文件名称
				reviewName = reviewObj.getDocName();
				//设计师
				designerName = reviewObj.getCreateUserName();
			}
		}
		//函审发起时间
		String reviewTime = DateUtil.toDateTime(managedReview.getStartTime());
		//函审结论
		String reviewConclusion = conclusion.getContent();
		//函审组长
		String reviewLeader = reviewExpert.getUser().getName();
		//待办事项
		String schedule = itemContents;
		//待办事项落实情况
		String implementation = contents;
		//函审任务确认
		String reviewTask = conclusion.getReviewResult();
		//函审项目负责人签名
		String directorSign = managedReview.getManageInfo().getCreateBy().getName();
		
		map.put(ReviewSignAttrConfig.NUMBER, new SignContent(1, number));
		map.put(ReviewSignAttrConfig.SECURITYLEVEL, new SignContent(1, securityLevel));
		map.put(ReviewSignAttrConfig.PHASEMARK, new SignContent(1, phaseMark));
		map.put(ReviewSignAttrConfig.REVIEWNUMBER, new SignContent(1, reviewNumber));
		map.put(ReviewSignAttrConfig.REVIEWNAME, new SignContent(1, reviewName));
		map.put(ReviewSignAttrConfig.DESIGNER, new SignContent(1, designerName));
		map.put(ReviewSignAttrConfig.REVIEWSTART, new SignContent(1, reviewTime));
		map.put(ReviewSignAttrConfig.REVIEWCONCLUSION, new SignContent(1, reviewConclusion));
		map.put(ReviewSignAttrConfig.REVIEWLEADER, new SignContent(1, reviewLeader));
		map.put(ReviewSignAttrConfig.SCHEDULE, new SignContent(1, schedule));
		map.put(ReviewSignAttrConfig.IMPLEMENTATION, new SignContent(1, implementation));
		map.put(ReviewSignAttrConfig.REVIEWTASK, new SignContent(1, reviewTask));
		map.put(ReviewSignAttrConfig.DIRECTORSIGN, new SignContent(1, directorSign));
			
		return map;
	}
	
	/**
	 * 获取函审单表格签署信息
	 * 
	 * @author liyu
	 * @param managedReview
	 * @return
	 */
	public static Map<String, SignContent> getManagedReviewTableInfo(ReviewOrder managedReview) {
		
		Map<String, SignContent> map = new HashMap<String, SignContent>();
		
		//将表格签署信息拼成字符串
		StringBuilder tableInfo=new StringBuilder();
		String sheet = "##";
		String line = "$$";
		String point = ".";
		
		//此处获取所有的表格签署的函审意见
		UserService userService = UserHelper.getService();
		List<ReviewResult> rvResultList = ReviewResultHelper.getService().getResultsByReview(managedReview.getInnerId());
		int i = 0;
		for(ReviewResult rvResult : rvResultList){
			if(ReviewResult.RESULT_TYPE_DOMAIN.equals(rvResult.getResultType())){
				User domainUser = userService.getUser(rvResult.getExecutor().getInnerId());
				List<ReviewOpinion> listOpn = ReviewOpinionHelper.getService().getOpinions(managedReview.getInnerId(), domainUser.getInnerId());
				if(!listOpn.isEmpty()){
					for(ReviewOpinion opn:listOpn){
						i++;
						//序号
						tableInfo.append(Integer.toString(i) + point);
						//函审意见
						String verdict = opn.getOpinionContent();
						tableInfo.append(verdict + sheet);
						//提出人签名
						String sign = opn.getExecutor().getName();
						tableInfo.append(sign + sheet);
						//函审完成时间
						String time = DateUtil.toDateTime(managedReview.getFinishTime());
						tableInfo.append(time + sheet);
						//设计师解答
						String answer = opn.getAnswerContent();
						tableInfo.append(answer + sheet);
						//专家确认
						String confirm = rvResult.getExpertCfmContent();
						tableInfo.append(confirm + sheet);
						
						tableInfo.append(line);
					}
				}else{
					if("0".equals(rvResult.getIsExpertConfirm())){
						i++;
						//序号
						tableInfo.append(Integer.toString(i) + point);
						//函审意见
						String verdict = "未处理";
						tableInfo.append(verdict + sheet);
						//提出人签名
						String sign = rvResult.getExecutor().getName();
						tableInfo.append(sign + sheet);
						//函审完成时间
						String time = DateUtil.toDateTime(managedReview.getFinishTime());
						tableInfo.append(time + sheet);
						//设计师解答
						String answer = "";
						tableInfo.append(answer + sheet);
						//专家确认
						String confirm = "";
						tableInfo.append(confirm + sheet);
						
						tableInfo.append(line);
					}else{
						i++;
						//序号
						tableInfo.append(Integer.toString(i) + point);
						//函审意见
						String verdict = rvResult.getExpertCfmContent();
						tableInfo.append(verdict + sheet);
						//提出人签名
						String sign = rvResult.getExecutor().getName();
						tableInfo.append(sign + sheet);
						//函审完成时间
						String time = DateUtil.toDateTime(managedReview.getFinishTime());
						tableInfo.append(time + sheet);
						//设计师解答
						String answer = "";
						tableInfo.append(answer + sheet);
						//专家确认
						String confirm = "";
						tableInfo.append(confirm + sheet);
						
						tableInfo.append(line);
					}
				}
			}else{
				String outUserId = rvResult.getOuterExpert().getInnerId();
				List<ReviewOpinion> listOpn = ReviewOpinionHelper.getService().getOpinionsByOutUserId(managedReview.getInnerId(), outUserId);
				if(!listOpn.isEmpty()){
					for(ReviewOpinion opn:listOpn){
						i++;
						//序号
						tableInfo.append(Integer.toString(i) + point);
						//函审意见
						String verdict = opn.getOpinionContent();
						tableInfo.append(verdict + sheet);
						//提出人签名
						String sign = opn.getOuterExpert().getName();
						tableInfo.append(sign + sheet);
						//函审完成时间
						String time = DateUtil.toDateTime(managedReview.getFinishTime());
						tableInfo.append(time + sheet);
						//设计师解答
						String answer = opn.getAnswerContent();
						tableInfo.append(answer + sheet);
						//专家确认
						String confirm = rvResult.getExpertCfmContent();
						tableInfo.append(confirm + sheet);
						
						tableInfo.append(line);
					}
				}else{
					if("0".equals(rvResult.getIsExpertConfirm())){
						i++;
						//序号
						tableInfo.append(Integer.toString(i) + point);
						//函审意见
						String verdict = "未处理";
						tableInfo.append(verdict + sheet);
						//提出人签名
						String sign = rvResult.getOuterExpert().getName();
						tableInfo.append(sign + sheet);
						//函审完成时间
						String time = DateUtil.toDateTime(managedReview.getFinishTime());
						tableInfo.append(time + sheet);
						//设计师解答
						String answer = "";
						tableInfo.append(answer + sheet);
						//专家确认
						String confirm = "";
						tableInfo.append(confirm + sheet);
						
						tableInfo.append(line);
					}else{
						i++;
						//序号
						tableInfo.append(Integer.toString(i) + point);
						//函审意见
						String verdict = rvResult.getExpertCfmContent();
						tableInfo.append(verdict + sheet);
						//提出人签名
						String sign = rvResult.getOuterExpert().getName();
						tableInfo.append(sign + sheet);
						//函审完成时间
						String time = DateUtil.toDateTime(managedReview.getFinishTime());
						tableInfo.append(time + sheet);
						//设计师解答
						String answer = "";
						tableInfo.append(answer + sheet);
						//专家确认
						String confirm = "";
						tableInfo.append(confirm + sheet);
						
						tableInfo.append(line);
					}
				}
			}
		}
		
		map.put(ReviewSignAttrConfig.REVIEWVERDICT, new SignContent(1, tableInfo.toString()));
		
		return map;
	}
	public static String convertBusState(String str){
		 if(ReviewOrderConstant.TASK_MR_NEW.equalsIgnoreCase(str)){
			 return "新建";
		 }else if(ReviewOrderConstant.TASK_MR_START.equalsIgnoreCase(str)){
			 return "已启动";
		 }else if(ReviewOrderConstant.TASK_MR_REVIEW_OVER.equalsIgnoreCase(str)){
			 return "已结束";
		 }else{
			 return "审阅中";
		 }
	 }
	public static boolean isOverTask(String orderId){
		boolean flag = false;
		List<ReviewResult> rvResults = ReviewResultHelper.getService().getResultsByReview(orderId);
		if(!rvResults.isEmpty()){
			int count=0;
			for(ReviewResult rvResult:rvResults){
				if(!"0".equals(rvResult.getIsLeaderConfirm())){
					count=count+1;
				}
			}
			if(count>2){
				flag = true;
			}
		}
		return flag;
	}
}
