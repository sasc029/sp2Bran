package com.bjsasc.plm.review2.task.listener;

import org.apache.log4j.Logger;

import com.bjsasc.avidm.core.transfer.util.ThreadLoginUtil;
import com.bjsasc.plm.client.docserverclient.taskevent.TransEvent;
import com.bjsasc.plm.core.persist.PersistHelper;
import com.bjsasc.plm.core.persist.model.Persistable;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.bjsasc.plm.review2.order.service.ReviewOrderHelper;
import com.cascc.platform.event.AbstractListener;
import com.cascc.platform.event.Event;
import com.cascc.platform.event.Listener;

/**
 * 监听评审单被签署后的事件
 * @author 
 *
 */
public class AfterManagedReviewFlowSignedListener extends AbstractListener implements Listener {
	private static final long serialVersionUID = -7318441469723042277L;
	private static Logger logger = Logger.getLogger(AfterManagedReviewFlowSignedListener.class);

	public void raiseEvent(Event event) {
		if(event instanceof TransEvent){
			TransEvent e = (TransEvent)event;
		
			String signBindingId = e.getDocOid();
			String taskID = e.gettaskID();
			String fileId = e.getFileID();
			String signResult = e.getResult();
			String fileUploadState = e.getFileUploadState();
			
			logger.debug("收到签署完成事件："
					+"signBindingId="+signBindingId
					+",taskID="+taskID
					+",fileId="+fileId
					+",signResult"+signResult
					+",fileUploadState"+fileUploadState);
			
			if(signBindingId != null){
				Persistable p = PersistHelper.getService().getObject(signBindingId);
				if(p!=null && p instanceof ReviewOrder){
					ReviewOrder managedReview =(ReviewOrder)p;
					ThreadLoginUtil.setCurrentUser();
					ReviewOrderHelper.getService().sendReviewCertificate(managedReview);
				}
			}
		}
	}
	
}
