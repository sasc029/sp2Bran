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
	 * ��ȡ����ǩ����Ϣ
	 * 
	 * @author liyu
	 * @param managedReview
	 * @return
	 */
	public static Map<String, SignContent> getManagedReviewSignInfo(ReviewOrder managedReview) {
		
		Map<String, SignContent> map = new HashMap<String, SignContent>();
		
		
		List<Reviewed> list = ReviewMemberHelper.getService().getReviewedListByOrder(managedReview);
		
		//����ר��
		ReviewExpert expert = (ReviewExpert)ReviewCommitteeMemberHelper.getMemberService().getLeader(managedReview.getInnerId()).getExpert();
		DomainReviewExpert reviewExpert=null;
		if(expert instanceof DomainReviewExpert){
			reviewExpert = (DomainReviewExpert)expert;
		}
		//�������
		ReviewConclusionHS conclusion = ReviewConclusionHelper.getService().findReviewConclusionByReviewOrder(managedReview.getInnerId()).get(0);
		//��������
		List<ReviewItem> items = ReviewItemHelper.getService().getReviewItemByReview(managedReview.getInnerId());
		String itemContents="";
		String contents="";
		for(int i=0;i<items.size();i++){
			itemContents+=(i+1)+"��"+items.get(i).getItemContents()+";";
			contents+=(i+1)+"��"+items.get(i).getDealContents()+";";
		}
		/*
		 * ����֤����
		 */
		//���
		String number = "";
		//�ܱ�
		String securityLevel = "";
		//�׶α��
		String phaseMark = "";
		//�����ļ����
		String reviewNumber = "";
		//�����ļ�����
		String reviewName = "";
		//���ʦ
		String designerName = "";
		
		for(int i=0,l=list.size();i<l;i++){
			Reviewed reviewed = list.get(i);
			if(reviewed instanceof ReviewObject){
				ReviewObject reviewObj = (ReviewObject)reviewed;
				number = reviewObj.getPin()+".PS";
				//�ܱ�
				securityLevel = reviewObj.getScurityLevel();
				//�׶α��
				phaseMark = reviewObj.getPhaseName();
				//�����ļ����
				reviewNumber = reviewObj.getPin();
				//�����ļ�����
				reviewName = reviewObj.getDocName();
				//���ʦ
				designerName = reviewObj.getCreateUserName();
			}
		}
		//������ʱ��
		String reviewTime = DateUtil.toDateTime(managedReview.getStartTime());
		//�������
		String reviewConclusion = conclusion.getContent();
		//�����鳤
		String reviewLeader = reviewExpert.getUser().getName();
		//��������
		String schedule = itemContents;
		//����������ʵ���
		String implementation = contents;
		//��������ȷ��
		String reviewTask = conclusion.getReviewResult();
		//������Ŀ������ǩ��
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
	 * ��ȡ���󵥱��ǩ����Ϣ
	 * 
	 * @author liyu
	 * @param managedReview
	 * @return
	 */
	public static Map<String, SignContent> getManagedReviewTableInfo(ReviewOrder managedReview) {
		
		Map<String, SignContent> map = new HashMap<String, SignContent>();
		
		//�����ǩ����Ϣƴ���ַ���
		StringBuilder tableInfo=new StringBuilder();
		String sheet = "##";
		String line = "$$";
		String point = ".";
		
		//�˴���ȡ���еı��ǩ��ĺ������
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
						//���
						tableInfo.append(Integer.toString(i) + point);
						//�������
						String verdict = opn.getOpinionContent();
						tableInfo.append(verdict + sheet);
						//�����ǩ��
						String sign = opn.getExecutor().getName();
						tableInfo.append(sign + sheet);
						//�������ʱ��
						String time = DateUtil.toDateTime(managedReview.getFinishTime());
						tableInfo.append(time + sheet);
						//���ʦ���
						String answer = opn.getAnswerContent();
						tableInfo.append(answer + sheet);
						//ר��ȷ��
						String confirm = rvResult.getExpertCfmContent();
						tableInfo.append(confirm + sheet);
						
						tableInfo.append(line);
					}
				}else{
					if("0".equals(rvResult.getIsExpertConfirm())){
						i++;
						//���
						tableInfo.append(Integer.toString(i) + point);
						//�������
						String verdict = "δ����";
						tableInfo.append(verdict + sheet);
						//�����ǩ��
						String sign = rvResult.getExecutor().getName();
						tableInfo.append(sign + sheet);
						//�������ʱ��
						String time = DateUtil.toDateTime(managedReview.getFinishTime());
						tableInfo.append(time + sheet);
						//���ʦ���
						String answer = "";
						tableInfo.append(answer + sheet);
						//ר��ȷ��
						String confirm = "";
						tableInfo.append(confirm + sheet);
						
						tableInfo.append(line);
					}else{
						i++;
						//���
						tableInfo.append(Integer.toString(i) + point);
						//�������
						String verdict = rvResult.getExpertCfmContent();
						tableInfo.append(verdict + sheet);
						//�����ǩ��
						String sign = rvResult.getExecutor().getName();
						tableInfo.append(sign + sheet);
						//�������ʱ��
						String time = DateUtil.toDateTime(managedReview.getFinishTime());
						tableInfo.append(time + sheet);
						//���ʦ���
						String answer = "";
						tableInfo.append(answer + sheet);
						//ר��ȷ��
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
						//���
						tableInfo.append(Integer.toString(i) + point);
						//�������
						String verdict = opn.getOpinionContent();
						tableInfo.append(verdict + sheet);
						//�����ǩ��
						String sign = opn.getOuterExpert().getName();
						tableInfo.append(sign + sheet);
						//�������ʱ��
						String time = DateUtil.toDateTime(managedReview.getFinishTime());
						tableInfo.append(time + sheet);
						//���ʦ���
						String answer = opn.getAnswerContent();
						tableInfo.append(answer + sheet);
						//ר��ȷ��
						String confirm = rvResult.getExpertCfmContent();
						tableInfo.append(confirm + sheet);
						
						tableInfo.append(line);
					}
				}else{
					if("0".equals(rvResult.getIsExpertConfirm())){
						i++;
						//���
						tableInfo.append(Integer.toString(i) + point);
						//�������
						String verdict = "δ����";
						tableInfo.append(verdict + sheet);
						//�����ǩ��
						String sign = rvResult.getOuterExpert().getName();
						tableInfo.append(sign + sheet);
						//�������ʱ��
						String time = DateUtil.toDateTime(managedReview.getFinishTime());
						tableInfo.append(time + sheet);
						//���ʦ���
						String answer = "";
						tableInfo.append(answer + sheet);
						//ר��ȷ��
						String confirm = "";
						tableInfo.append(confirm + sheet);
						
						tableInfo.append(line);
					}else{
						i++;
						//���
						tableInfo.append(Integer.toString(i) + point);
						//�������
						String verdict = rvResult.getExpertCfmContent();
						tableInfo.append(verdict + sheet);
						//�����ǩ��
						String sign = rvResult.getOuterExpert().getName();
						tableInfo.append(sign + sheet);
						//�������ʱ��
						String time = DateUtil.toDateTime(managedReview.getFinishTime());
						tableInfo.append(time + sheet);
						//���ʦ���
						String answer = "";
						tableInfo.append(answer + sheet);
						//ר��ȷ��
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
			 return "�½�";
		 }else if(ReviewOrderConstant.TASK_MR_START.equalsIgnoreCase(str)){
			 return "������";
		 }else if(ReviewOrderConstant.TASK_MR_REVIEW_OVER.equalsIgnoreCase(str)){
			 return "�ѽ���";
		 }else{
			 return "������";
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
