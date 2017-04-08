package javaUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

import org.apache.commons.mail.ByteArrayDataSource;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;

public class EmailSender {
	public String addTo;
	public String addCc;
	public String from;
	public String subject;
	public String body;
	private String smtpUsername = "ÿoursmtpUsername";
	private String smtpPassword = "yoursmtpPassword";
	private String setFrom = "no-reply@yourcompany.com";
	private String setDescription = "Courtesy of Your Company";
	//
	public EmailSender(String addTo, String addCc, String from, String subject, String body) {
		this.addTo = addTo.trim();
		this.addCc = addCc.trim();
		this.from = from.trim();
		this.subject = subject;
		this.body = body;
	}
	/*
	 * @param 
	 */
	public StatusCode sendHTMLEmailNoAttachments() {
		StatusCode sc = new StatusCode(200);
		sc.error = "";
		HtmlEmail sendEmail = new HtmlEmail();
        sendEmail.setHostName("smtp.gmail.com");
        sendEmail.setSmtpPort(465);
        sendEmail.setAuthenticator(new DefaultAuthenticator(smtpUsername, smtpPassword));
        sendEmail.setSSLOnConnect(true);
        //process recipients
        addTo = addTo.replaceAll("\\s","");
        //System.out.println("addTo=" + addTo + " from:" + from);
        if (addTo.equals("") || from.equals("")) {
          sc.status = 404;
          sc.message = "email recipient or email sender blank";
          System.out.println("sendHTMLEmailNoAttachments - send email failed :  "+sc.message);
          return sc;
        }
        // process CSV recipients
        String[] recipients = addTo.trim().split(",");
        if (recipients.length == 0) {
        	recipients[0] = addTo;
        }
        // process CSV addCc
        String[] cC = addCc.trim().split(",");
        if (!addCc.equals("")) {
           if (cC.length == 0) {
            	cC[0] = addCc;
            }
        }
        
        try {
        	sendEmail.setFrom(setFrom, from);
            sendEmail.setSubject(subject);
            sendEmail.setHtmlMsg(body);
            for (int i = 0; i < recipients.length; i++)
            {
            	System.out.println("recipient=" + recipients[i]);
                sendEmail.addTo(recipients[i]);
                
            }
            if (!addCc.equals("")) {
            	for (int i = 0; i < cC.length; i++)
                {
            		//System.out.println("cC=" + cC[i]);
                    sendEmail.addCc(cC[i]);
                   
                }
            }
            
            sendEmail.send();
            System.out.println("Email Sender - EmailNoAttachments - email sent succesfully");
        } catch (Exception e) {
        	sc.status = Http.Status.INTERNAL_SERVER_ERROR;
            sc.error = e.getMessage(); 
            e.printStackTrace();
            System.out.println("Email Sender - EmailNoAttachments " + e.getStackTrace());
        }
        return sc;
	}
	/*
	 * @param String{} attachments
	 */
	public StatusCode sendHTMLEmailWithAttachments(String attachmentsPath, String[] attachments) {
		StatusCode sc = new StatusCode(200);
		sc.error = "";
		HtmlEmail sendEmail = new HtmlEmail();
        sendEmail.setHostName("smtp.gmail.com");
        sendEmail.setSmtpPort(465);
        sendEmail.setAuthenticator(new DefaultAuthenticator(smtpUsername, smtpPassword));
        sendEmail.setSSLOnConnect(true);
        //process recipients
        addTo = addTo.replaceAll("\\s","");
        //System.out.println("addTo=" + addTo + " from:" + from);
        if (addTo.equals("") || from.equals("")) {
          sc.status = 404;
          sc.message = "email recipient or email sender blank";
          System.out.println("sendHTMLEmailWithAttachments - send email failed :  " + sc.message);
          return sc;
        }
        try {
        	sendEmail.setFrom(setFrom, from);
            sendEmail.setSubject(subject);
            sendEmail.setHtmlMsg(body);
            // process CSV recipients
            String[] recipients = addTo.trim().split(",");
            if (recipients.length == 0) {
            	recipients[0] = addTo;
            }
            // process CSV addCc
            String[] cC = addCc.trim().split(",");
            if (!addCc.equals("")) {
               if (cC.length == 0) {
                	cC[0] = addCc;
                }
            }
            //
            for (int i = 0; i < recipients.length; i++)
            {
            	//System.out.println("recipient=" + recipients[i]);
                sendEmail.addTo(recipients[i]);
                
            }
            if (!addCc.equals("")) {
            	for (int i = 0; i < cC.length; i++)
                {
            		//System.out.println("cC=" + cC[i]);
                    sendEmail.addCc(cC[i]);
                   
                }
            }
         // iterate through attachments
            
            for(String s: attachments) {
            	if (s == null || s.equals("")) break;
            	EmailAttachment attachment = new EmailAttachment();
                String f = attachmentsPath + s;
                attachment.setPath(f);
                attachment.setDisposition(EmailAttachment.ATTACHMENT);
                attachment.setDescription(setDescription);
                String name = s.substring(s.lastIndexOf("/") + 1, s.length());
                name = name.substring(0, name.lastIndexOf("."));
                attachment.setName(name);
                // add attachment
                sendEmail.attach(attachment);
            }
            
            sendEmail.send();
            System.out.println("Send HTML with attachments - email sent succesfully");
        } catch (Exception e) {
        	sc.status = 400;               //INTERNAL_SERVER_ERROR
            sc.error = e.getMessage();
            e.printStackTrace();
            System.out.println("Send HTML with attachments - email could not sent " + e.getMessage());
        }
        return sc;
	}
	/*
	 * @param 
	 */
	public StatusCode sendNoHTMLEmail(String attachmentsPath, String[] attachments) {
		StatusCode sc = new StatusCode(200);
		sc.error = "";
		MultiPartEmail sendEmail = new MultiPartEmail();
        sendEmail.setHostName("smtp.gmail.com");
        sendEmail.setSmtpPort(465);
        sendEmail.setAuthenticator(new DefaultAuthenticator(smtpUsername, smtpPassword));
        sendEmail.setSSLOnConnect(true);
        try {
        	sendEmail.setFrom(setFrom, from);
            sendEmail.setSubject(subject);
            sendEmail.setMsg(body);
            // process CSV recipients
            String[] recipients = addTo.trim().split(",");
            if (recipients.length == 0) {
            	recipients[0] = addTo;
            }
            // process CSV addCc
            String[] cC = addCc.trim().split(",");
            if (!addCc.equals("")) {
               if (cC.length == 0) {
                	cC[0] = addCc;
                }
            }
            //
            for (int i = 0; i < recipients.length; i++)
            {
            	sendEmail.addTo(recipients[i]);
                
            }
            if (!addCc.equals("")) {
            	for (int i = 0; i < cC.length; i++)
                {
            		sendEmail.addCc(cC[i]);
                }
            }
            // process attachments
            for(String s: attachments) {
            	if (s == null || s.equals("")) break;
            	EmailAttachment attachment = new EmailAttachment();
                String f = attachmentsPath + s;
                attachment.setPath(f);
                attachment.setDisposition(EmailAttachment.ATTACHMENT);
                attachment.setDescription(setDescription);
                String name = s.substring(s.lastIndexOf("/") + 1, s.length());
                name = name.substring(0, name.lastIndexOf("."));
                attachment.setName(name);
                // add attachment
                sendEmail.attach(attachment);
            }
            // send no HTML email
            sendEmail.send();
            System.out.println("Email Sender - sendEmail - email sent succesfully");
        } catch (Exception e) {
        	sc.status = 400;                    // INTERNAL_SERVER_ERROR;
            sc.error = e.getMessage(); 
            System.out.println("Email Sender - sendEmail " + e.getStackTrace());
        }
        return sc;
	}
}
