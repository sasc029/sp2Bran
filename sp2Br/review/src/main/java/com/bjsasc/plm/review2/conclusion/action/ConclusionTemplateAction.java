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
 * 评审结论模板action
 *
 */
@SuppressWarnings("serial")
public class ConclusionTemplateAction  extends BaseAction{

	//系统运行参数配置 service
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
	 * List转换为Json
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
	 * 添加域外专家对象
	 */
	public void addConclusionTemplate() throws IOException{
		if(isExists(conclusionTemplate.getNumber())){/*已经存在*/
			response.getWriter().write("isExists");
			return;
		}
		service.saveConclusionTemplate(conclusionTemplate);
		
		response.getWriter().print(SUCCESS);
	}
	
	/**
	 * 删除评审结论模板
	 */
	public void delConclusionTemplate() throws IOException{
		String id = request.getParameter("ids");
		String ids[] = id.split(",");
		service.delConclusionTemplate(ids);
		response.getWriter().print(SUCCESS);
	}
	
	/**
	 * 编辑评审结论模板
	 */
	public String editConclusionTemplate() throws IOException{
		//判断是修改还是要查看结论模版内容
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
	 * 编辑评审结论模板
	 */
	public void editConclusionTemplateSave() throws IOException{
		service.updateConclusionTemplate(conclusionTemplate);
		response.getWriter().print(SUCCESS);
	}
	
	/**
	 * 判断评审结论模板是否已经存在
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
