package com.bjsasc.plm.review2.conclusion.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.plm.review2.common.BaseAction;
import com.bjsasc.plm.review2.conclusion.model.ConclusionTemplateHS;
import com.bjsasc.plm.review2.conclusion.service.ConclusionTemplateHelper;
import com.bjsasc.plm.review2.conclusion.service.ConclusionTemplateService;
import com.bjsasc.ui.json.DataUtil;

/**
 * �������ģ��action
 *
 */
@SuppressWarnings("serial")
public class ConclusionTemplateAction  extends BaseAction{

	//ϵͳ���в������� service
	private ConclusionTemplateService service = ConclusionTemplateHelper.getService();
	
	private ConclusionTemplateHS conclusionTemplate;
	
	public ConclusionTemplateHS getConclusionTemplate() {
		return conclusionTemplate;
	}

	public void setConclusionTemplate(ConclusionTemplateHS conclusionTemplate) {
		this.conclusionTemplate = conclusionTemplate;
	}

	public void selectConclusionTemplate() throws IOException{
		List list = service.findAllConclusionTemplate();
		response.getWriter().print(this.ModelConfigInfoListToJson(list));
	}
	
	/**
	 * Listת��ΪJson
	 */
	public String ModelConfigInfoListToJson(List list){
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			ConclusionTemplateHS conclusionTemplate = (ConclusionTemplateHS) list.get(i);
			Map<String, Object> attrMap = new HashMap<String, Object>();
			attrMap.put("innerId", conclusionTemplate.getInnerId());
			attrMap.put("number", conclusionTemplate.getNumber());
			attrMap.put("name", conclusionTemplate.getName());
			attrMap.put("contents", conclusionTemplate.getContents());
			dataList.add(attrMap);
		}
		return DataUtil.listToJson(dataList);
	}
	
	/**
	 * �������ר�Ҷ���
	 */
	public void addConclusionTemplate() throws IOException{
		if(isExists(conclusionTemplate.getNumber())){/*�Ѿ�����*/
			response.getWriter().write("isExists");
			return;
		}
		service.saveConclusionTemplate(conclusionTemplate);
		
		response.getWriter().print(SUCCESS);
	}
	
	/**
	 * ɾ���������ģ��
	 */
	public void delConclusionTemplate() throws IOException{
		String id = request.getParameter("ids");
		String ids[] = id.split(",");
		service.delConclusionTemplate(ids);
		response.getWriter().print(SUCCESS);
	}
	
	/**
	 * �༭�������ģ��
	 */
	public String editConclusionTemplate() throws IOException{
		//�ж����޸Ļ���Ҫ�鿴����ģ������
		String type = request.getParameter("type");
		String innerId = request.getParameter("id");
		conclusionTemplate = service.findConclusionTemplateByInnerId(innerId);
		if(type.equals("edit")){
			return "edit";
		}else{
			return "show";
		}
		
	}
	
	/**
	 * �༭�������ģ��
	 */
	public void editConclusionTemplateSave() throws IOException{
		service.updateConclusionTemplate(conclusionTemplate);
		response.getWriter().print(SUCCESS);
	}
	
	/**
	 * �ж��������ģ���Ƿ��Ѿ�����
	 * @param userName
	 * @return
	 */
	public boolean isExists(String number){
		boolean result=false;
		List<ConclusionTemplateHS> conclusionTemplateList=service.findAllConclusionTemplate();
		if(!conclusionTemplateList.isEmpty()){
			for(ConclusionTemplateHS conclusionTemplate:conclusionTemplateList){
				if(number.equals(conclusionTemplate.getNumber())){
					result=true;
				}
			}
		}
		return result;
	}
}
