package com.bjsasc.plm.review2.dataconfig.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.plm.Helper;
import com.bjsasc.plm.review2.common.BaseAction;
import com.bjsasc.plm.review2.dataconfig.model.ReviewDataConfig;
import com.bjsasc.plm.review2.dataconfig.service.ReviewDataConfigHelper;
import com.bjsasc.plm.review2.dataconfig.service.ReviewDataConfigService;
import com.bjsasc.ui.json.DataUtil;

/**
 * 评审结论模板action
 *
 */
@SuppressWarnings("serial")
public class ReviewDataConfigAction  extends BaseAction{

	//系统运行参数配置 service
	private ReviewDataConfigService service = ReviewDataConfigHelper.getService();
	
	//初始化数据库表数据
	public void initDataConfig(){
		try{
			List<ReviewDataConfig> lists = ReviewDataConfigHelper.getService().getInitDataList();
			for(ReviewDataConfig reviewDataConfig:lists){
				ReviewDataConfigHelper.getService().saveReviewDataConfig(reviewDataConfig);
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("SUCCESS", "true");
			String json = DataUtil.mapToSimpleJson(map);
			response.getWriter().print(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void getRvDataCfgList() throws IOException{
		List<ReviewDataConfig> list = service.findRvDataCfgByDataSource("-1");
		response.getWriter().print(this.dataConfigInfoListToJson(list));
	}
	
	/**
	 * List转换为Json
	 */
	public String dataConfigInfoListToJson(List<ReviewDataConfig> list){
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			ReviewDataConfig dataCfg =  list.get(i);
			Map<String, Object> attrMap = new HashMap<String, Object>();
			attrMap.put("INNERID", dataCfg.getInnerId());
			attrMap.put("DATANAME", dataCfg.getDataName());
			attrMap.put("DATATYPE", dataCfg.getDataType());
			attrMap.put("DATASOURCE", dataCfg.getDataSource());
			dataList.add(attrMap);
		}
		return DataUtil.listToJson(dataList);
	}
	
	public void getChildRvDataCfgList() throws Exception{
		String dataType = request.getParameter("dataType");
		List<ReviewDataConfig> list = service.findRvDataCfgByDataSource(dataType);
		response.getWriter().print(this.dataConfigInfoListToJson(list));
	}
	public void createRvDataConfig() throws Exception{
		String dataName = request.getParameter("dataName");
		String dataType = request.getParameter("dataType");
		String dataFlag = request.getParameter("dataFlag");
		String dataSource = "";
		ReviewDataConfig rvDataCfg = new ReviewDataConfig();
		if(service.findRvDataCfgByDataType(dataType)!=null){
			response.getWriter().print("false");
			return;
		}
		if(!"".equals(dataFlag) && dataFlag!=null && !"null".equals(dataFlag)){
			dataSource = dataFlag;
		}else{
			dataSource = "-1";
		}
		rvDataCfg.setDataName(dataName);
		rvDataCfg.setDataType(dataType);
		rvDataCfg.setDataSource(dataSource);
		
		Helper.getPersistService().save(rvDataCfg);
	}
	
	public void delDataConfig() throws IOException{
		String id = request.getParameter("ids");
		boolean flag = true;
		String ids[] = id.split(",");
		for(int i=0;i<ids.length;i++){
			ReviewDataConfig rvDataCfg = service.findReviewDataConfigByInnerId(ids[i]);
			if(!service.findRvDataCfgByDataSource(rvDataCfg.getDataType()).isEmpty()){
				flag = false;
				break;
			}
		}
		if(flag){
			service.delReviewDataConfig(ids);
			response.getWriter().print(SUCCESS);
		}else{
			response.getWriter().print("fail");
		}
	}
}
