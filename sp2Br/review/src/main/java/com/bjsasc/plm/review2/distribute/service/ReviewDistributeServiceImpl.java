package com.bjsasc.plm.review2.distribute.service;

import java.util.List;

import com.bjsasc.plm.core.Helper;
import com.bjsasc.plm.review2.distribute.model.ReviewDistribute;

public class ReviewDistributeServiceImpl implements ReviewDistributeService {

	@Override
	public ReviewDistribute getRvDistributeByInnerId(String innerId) {
		String hql = "from ReviewDistribute r where r.innerId=?";
		List<ReviewDistribute> rt = Helper.getPersistService().find(hql,innerId);
		if(!rt.isEmpty()){
			return rt.get(0);
		}
		return null;
	}

	@Override
	public void delReviewDistribute(String innerId) {
		String[] ids = innerId.split(",");
		for(int i=0; i<ids.length; i++){
			ReviewDistribute rvDis = this.getRvDistributeByInnerId(ids[i]);
			Helper.getPersistService().delete(rvDis);
		}
	}

	@Override
	public List<ReviewDistribute> getRvDistributeListByOrderId(String orderId) {
		String hql = "from ReviewDistribute r where r.reviewOrderRef.innerId=? order by manageInfo.createTime desc";
		List<ReviewDistribute> rt = Helper.getPersistService().find(hql,orderId);
		return rt;
	}
}
