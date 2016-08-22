package com.bjsasc.plm.review2.object.action;

import java.io.IOException;
import java.util.List;

import com.bjsasc.avidm.core.site.Site;
import com.bjsasc.avidm.core.site.SiteHelper;
import com.bjsasc.avidm.core.site.SiteService;
import com.bjsasc.plm.Helper;
import com.bjsasc.plm.core.context.model.ProductContext;
import com.bjsasc.plm.core.folder.Folder;
import com.bjsasc.plm.core.folder.FolderHelper;
import com.bjsasc.plm.review2.common.BaseAction;
import com.bjsasc.plm.review2.object.model.ReviewObjectContext;
import com.bjsasc.plm.review2.object.service.ReviewObjectContextHelper;
import com.bjsasc.plm.review2.object.service.ReviewObjectContextService;

/**
 * ������������Ļ���������action
 *
 */
public class ReviewObjectContextAction extends BaseAction {
	
	private static final long serialVersionUID = -490798782789829633L;
	private ReviewObjectContextService service=ReviewObjectContextHelper.getService();
	private SiteService siteService = SiteHelper.getSiteService();
	private ReviewObjectContext reviewObjectContext;
	private String innerId;
	/**
	 * ��ʼ��ҳ��
	 * @return
	 */
	public String initPage() {
		/*��ѯ����վ��*/
		List<Site> siteList = siteService.findAllOutSite();
		request.setAttribute("siteList", siteList);
		
		/*��ѯ���б��ز�Ʒ*/
		List productList=Helper.getContextService().getAllContexts(ProductContext.class);
		request.setAttribute("productList", productList);
		
		/*������޸ģ���ѯҪ�޸ĵļ�¼*/
		innerId=request.getParameter("innerId");
		reviewObjectContext=service.findReviewObjectContextByInnerId(innerId);
		request.setAttribute("reviewObjectContext", reviewObjectContext);
		
		return "init";
	}
	
	/**
	 * ����/�޸ļ�¼ ���innerId��Ϊ�գ����޸ļ�¼������������¼
	 * @throws IOException
	 */
	public void saveData() throws IOException{
		String innerId=request.getParameter("innerId");
		reviewObjectContext=innerId !=null && !"".equals(innerId)?service.findReviewObjectContextByInnerId(innerId):new ReviewObjectContext();
		
		//�����ļ��е�classId�������ȡ���ز�Ʒ��Ϣ
		String folderclassId = request.getParameter("folderclassId");
		//�����ļ���innerId
		String folderinnerId = request.getParameter("folderinnerId");
		Folder folder = FolderHelper.getService().getFolder(folderclassId,folderinnerId);
		reviewObjectContext.setFolder(folder);
		
		String productOID=request.getParameter("productOID").toString();
		ProductContext productContext=(ProductContext) Helper.getPersistService().getObject(productOID);
		reviewObjectContext.setProduct(productContext);
		
		String siteId=request.getParameter("siteId").toString();
		Site site=SiteHelper.getSiteService().findSiteById(siteId);
		reviewObjectContext.setSourceSite(site);
		
		if(isExists(siteId) && (innerId==null || "".equals(innerId))){/*�Ѿ�����*/
			response.getWriter().write("isExists");
			return;
		}
		
		if(innerId!=null && !"".equals(innerId)){
			service.updateReviewObjectContext(reviewObjectContext);
		}else{
			service.saveReviewObjectContext(reviewObjectContext);
		}
		
		response.getWriter().write("success");
	}
	
	/**
	 * �ж�վ�����������������Ƿ��Ѿ�����
	 * @param siteId
	 * @param productOID
	 * @return
	 */
	public boolean isExists(String siteId){
		boolean result=false;
		List<ReviewObjectContext> reviewObjectContextList=service.findAllReviewObjectContext();
		if(!reviewObjectContextList.isEmpty()){
			for(ReviewObjectContext reviewObjectContext:reviewObjectContextList){
				if(siteId.equals(reviewObjectContext.getSourceSite().getInnerId())){
					result=true;
				}
			}
		}
		return result;
	}
	/**
	 * ɾ��
	 * @throws IOException
	 */
	public void reviewObjectContextDelete() throws IOException{
		innerId=request.getParameter("innerId");
		reviewObjectContext=service.findReviewObjectContextByInnerId(innerId);
		service.deleteReviewObjectContext(reviewObjectContext);
		response.getWriter().write("success");
	}
}
