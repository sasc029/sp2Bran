package com.bjsasc.plm.review2.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bjsasc.plm.core.util.DateTimeUtil;
import com.cascc.platform.aa.util.mail.AvidmMsgSender;


/**
 * 函审任务提醒工具类
 */
public class ReviewTaskReminderUtil {
	
	private static Logger log = Logger.getLogger(ReviewTaskReminderUtil.class);
	
	/**
	 * 邮件发送方法
	 */
	public static void sendEmailReminder(String creatorIID,String maiTitle) {
		Map<String, String> ctx = new HashMap<String, String>();
		// EJB端发送
		String sendMode = AvidmMsgSender.AVIDM_EJB; // 表示直接指定发送用户innerid的发送
		// 发送者
		ctx.put(AvidmMsgSender.SENDER_USERINNERID, com.cascc.platform.aa.API.getAdministratorIID());
		// 接收者
		String receiverIID = creatorIID;

		// 邮件标题
		String mailTitle = maiTitle;

		// 邮件拼写
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML>\n");
		sb.append("<HEAD>\n");
		sb.append("<TITLE>\n");
		sb.append("函审任务提醒");
		sb.append("</TITLE>\n");
		sb.append("</HEAD>\n");
		sb.append("<BODY>\n");
		sb.append("<pre>您好 :</pre>");
		sb.append("<pre>&nbsp;&nbsp;&nbsp;&nbsp;您有新任务 :</pre>");
		sb.append("<pre>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + maiTitle + "</pre>");
		sb.append("<pre>请尽快处理</pre>");
		sb.append("<div align=\"right\">" + DateTimeUtil.getDateTimeDisplay(System.currentTimeMillis()) + "</div>");
		sb.append("</BODY>\n");
		sb.append("</HTML>\n");

		// 邮件正文
		String mailBody = sb.toString();

		try {
			AvidmMsgSender.avSendMessage(ctx, sendMode, AvidmMsgSender.MSG_TYPE_MAIL, mailTitle, mailBody, receiverIID, null);
		} catch (Exception ex) {
			log.error("Send Email is Error, receiverIID = " + receiverIID, ex);
		}
	}
}