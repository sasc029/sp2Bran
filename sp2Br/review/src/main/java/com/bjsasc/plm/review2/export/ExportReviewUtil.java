package com.bjsasc.plm.review2.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.bjsasc.platform.filecomponent.model.PtFileItemBean;
import com.bjsasc.plm.core.attachment.AttachHelper;
import com.bjsasc.plm.core.attachment.FileHolder;
import com.bjsasc.plm.core.session.SessionHelper;
import com.bjsasc.plm.review2.order.model.ReviewOrder;
import com.cascc.avidm.util.SysConfig;

public class ExportReviewUtil {

	public static String getFilePath(ReviewOrder managedReview) {
		
		boolean isCheck = SessionHelper.getService().isCheckPermission();
		//关闭权限检查
		SessionHelper.getService().setCheckPermission(false) ;
		
		String filePath = SysConfig.getTempDir() + File.separator +"review" +File.separator;
		File destFile = new File(filePath);
		if(!destFile.exists()){
			destFile.mkdirs();
		}
		OutputStream os = null;
		InputStream is = null;
		PtFileItemBean file = AttachHelper.getAttachService().getMainFile(managedReview);
	    filePath += file.getFileName();
		try{
			is = AttachHelper.getAttachService().getInputStream((FileHolder)managedReview, file.getInnerId());
			os = new FileOutputStream(new File(filePath));
			
			int bytesRead = 0;
			byte[] buffer = new byte[1024];

			while ((bytesRead = is.read(buffer, 0, 1024)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		}catch(Exception e){
			throw new RuntimeException("ExportReviewUtil中调用getFilePath异常",e);
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					throw new RuntimeException("ExportReviewUtil中关闭is异常",e);
				}
			}
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					throw new RuntimeException("ExportReviewUtil中关闭os异常",e);
				}
			}
		}
		SessionHelper.getService().setCheckPermission(isCheck) ;
		return filePath;
	}
	
	
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

}
