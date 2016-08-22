package com.bjsasc.plm.review2.div.service;

import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.review2.div.model.ReviewOutDivision;

public class ReviewOutDivisionServiceImpl implements ReviewOutDivisionService {

	@Override
	public List<ReviewOutDivision> getAllRvOutDivisionList() {
		String hql = "from ReviewOutDivision ";
		List<ReviewOutDivision> outDivs = (List<ReviewOutDivision>)Helper.getPersistService().find(hql);
		return outDivs;
	}

	@Override
	public ReviewOutDivision getRvOutDivisionByInnerId(String innerId) {
		String hql = "from ReviewOutDivision r where r.innerId=?";
		List<ReviewOutDivision> rt = Helper.getPersistService().find(hql,innerId);
		if(!rt.isEmpty()){
			return rt.get(0);
		}
		return null;
	}

	@Override
	public ReviewOutDivision getRvOutDivByDivInnerIdAndSiteInnerId(String outDivInnerId, String siteInnerId) {
		String hql = "from ReviewOutDivision r where r.outDivInnerId=? and r.siteInnerId =?";
		List<ReviewOutDivision> rt = Helper.getPersistService().find(hql,outDivInnerId,siteInnerId);
		if(!rt.isEmpty()){
			return rt.get(0);
		}
		return null;
	}

	@Override
	public List<ReviewOutDivision> getRvOutDivisionListBySiteInnerId(String siteInnerId) {
		String hql = "from ReviewOutDivision where siteInnerId = ?";
		List<ReviewOutDivision> outDivs = (List<ReviewOutDivision>)Helper.getPersistService().find(hql,siteInnerId);
		return outDivs;
	}
}
