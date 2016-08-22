package com.bjsasc.plm.review2.div.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.avidm.core.site.SiteHelper;
import com.bjsasc.avidm.core.transfer.TransferObjectHelper;
import com.bjsasc.avidm.core.transfer.util.TransferConstant;
import com.bjsasc.platform.sitemgr.bean.SiteData;
import com.bjsasc.platform.sitemgr.service.SiteService;
import com.bjsasc.platform.sitemgr.util.SiteServiceUtil;
import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.review2.common.BaseAction;
import com.bjsasc.plm.review2.div.model.ReviewOutDivision;
import com.bjsasc.plm.review2.div.service.ReviewOutDivisionHelper;
import com.bjsasc.plm.review2.div.service.ReviewOutDivisionService;
import com.bjsasc.plm.review2.link.service.ReviewMemberHelper;
import com.bjsasc.plm.review2.object.model.ReviewObject;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.ui.json.DataUtil;
import com.cascc.platform.aa.AAProvider;
import com.cascc.platform.aa.api.data.AADivisionData;
import com.cascc.platform.aa.api.data.ac.ExtendDivisionData;
import com.cascc.platform.aa.api.util.AADomainUtil;
import com.cascc.platform.util.PlatformException;
import com.cascc.platform.uuidservice.UUID;

/**
 * �ⲿ��λaction
 * @author 
 *
 */
public class ReviewOutDivisionAction  extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3780034094192760501L;
	private ReviewOutDivisionService service = ReviewOutDivisionHelper.getReviewOutDivisionService();
	
	/**
	 *ͬ���ⲿ��λ
	 */
	public void synOutDiv() throws IOException{
		String siteIID = request.getParameter("siteIID");
		Site targetSite = SiteHelper.getSiteService().findSiteById(siteIID);
		TransferObjectHelper.getTransferService().sendRequest(UUID.getUID(), "ͬ���ⲿ��λ��Ϣ", "reviewOutDivRequest", "", targetSite, null, null,
					TransferConstant.REQTYPE_ASYN);
		response.getWriter().print("success");
	}
	public void getAllOutDiv() throws Exception{
		String orderOid = request.getParameter("orderOid");
		ReviewOrder reviewOrder = (ReviewOrder)Helper.getPersistService().getObject(orderOid);
		ReviewObject reviewObj = (ReviewObject)ReviewMemberHelper.getService().getReviewedListByOrder(reviewOrder).get(0);
		String siteInerId = reviewObj.getSourceSiteInnerId();
		List<ReviewOutDivision> outDivList = service.getRvOutDivisionListBySiteInnerId(siteInerId);
		List<AADivisionData> aaDivList = this.listDivs(siteInerId);
		
		List<Map<String, String>> unitMapList = new ArrayList<Map<String, String>>();
		
		for(ReviewOutDivision outDiv:outDivList){
			Map<String, String> map = new HashMap<String, String>();
			
			map.put("INNERID", outDiv.getOutDivInnerId());
			map.put("NAME", outDiv.getOutDivNam());
			map.put("UNITTYPE", "�ⲿ��λ");
			map.put("TYPE", "outUnit");

			unitMapList.add(map);
		}
		for(AADivisionData aaDiv:aaDivList){
			Map<String, String> map = new HashMap<String, String>();
			
			map.put("INNERID", aaDiv.getInnerId());
			map.put("NAME", aaDiv.getName());
			map.put("UNITTYPE", "�ڲ���λ");
			map.put("TYPE", "insideUnit");

			unitMapList.add(map);
		}
		response.getWriter().print(DataUtil.listToJson(unitMapList.size(),unitMapList));
	}
	
	/**
	 * �հ�ײ�ķ������޷����ù��ڴ˸���
	 * @return
	 */
	private List<AADivisionData> listDivs(String siteInnerId) {
		try {
			SiteService siteService = SiteServiceUtil.getSiteService();
			List<ExtendDivisionData> allDiv = AAProvider.getDivisionService()
					.listTreeExtendDivisions(null, null,
							AADomainUtil.PRIPTYPE_ORG, null);
			// ��ȡ����վ�㣬������������վ���Ӧ�����֯�������˵�
			SiteData site = siteService.getSiteDataBysiteId(siteInnerId);
			List<AADivisionData> divs = new ArrayList<AADivisionData>();
			for (ExtendDivisionData extend : allDiv) {
				if (site.getDomainIId()==extend.getDomainRef()) {
					AADivisionData div = extend;
					divs.add(div);
				}
			}
			return divs;
		} catch (Exception e) {
			throw new PlatformException("pt.aa.AASynImpl.listLocalDivs",
					"��ȡ��վ��������֯��������ȥ��վ�㵼�����֯��������Ϣʧ��", e);
		}
	}
}
