package com.bjsasc.plm.review2.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bjsasc.plm.core.util.DateTimeUtil;
import com.cascc.platform.aa.util.mail.AvidmMsgSender;


/**
 * �����������ѹ�����
 */
public class ReviewTaskReminderUtil {
	
	private static Logger log = Logger.getLogger(ReviewTaskReminderUtil.class);
	
	/**
	 * �ʼ����ͷ���
	 */
	public static void sendEmailReminder(String creatorIID,String maiTitle) {
		Map<String, String> ctx = new HashMap<String, String>();
		// EJB�˷���
		String sendMode = AvidmMsgSender.AVIDM_EJB; // ��ʾֱ��ָ�������û�innerid�ķ���
		// ������
		ctx.put(AvidmMsgSender.SENDER_USERINNERID, com.cascc.platform.aa.API.getAdministratorIID());
		// ������
		String receiverIID = creatorIID;

		// �ʼ�����
		String mailTitle = maiTitle;

		// �ʼ�ƴд
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML>\n");
		sb.append("<HEAD>\n");
		sb.append("<TITLE>\n");
		sb.append("������������");
		sb.append("</TITLE>\n");
		sb.append("</HEAD>\n");
		sb.append("<BODY>\n");
		sb.append("<pre>���� :</pre>");
		sb.append("<pre>&nbsp;&nbsp;&nbsp;&nbsp;���������� :</pre>");
		sb.append("<pre>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + maiTitle + "</pre>");
		sb.append("<pre>�뾡�촦��</pre>");
		sb.append("<div align=\"right\">" + DateTimeUtil.getDateTimeDisplay(System.currentTimeMillis()) + "</div>");
		sb.append("</BODY>\n");
		sb.append("</HTML>\n");

		// �ʼ�����
		String mailBody = sb.toString();

		try {
			AvidmMsgSender.avSendMessage(ctx, sendMode, AvidmMsgSender.MSG_TYPE_MAIL, mailTitle, mailBody, receiverIID, null);
		} catch (Exception ex) {
			log.error("Send Email is Error, receiverIID = " + receiverIID, ex);
		}
	}
}