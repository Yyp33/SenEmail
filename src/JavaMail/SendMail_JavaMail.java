package JavaMail;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import JavaMail.MyAuthenticator;

public class SendMail_JavaMail {
	
	public static void main(String[] args) throws MessagingException, InvalidPropertiesFormatException, IOException{
		
		String MyEmailAccount = "******@163.com";
		String MyEmailPssword = "*********";
		String toEmailAccount = "*******@163.com";
		
		if(sendEmail(MyEmailAccount, MyEmailPssword,toEmailAccount)){
			System.out.println("send success!");
		}else{
			System.out.println("send fail!");
		}
		
	}
	
	public static boolean sendEmail(String from,String password,String to) throws IOException, MessagingException{
		
		URL url = SendMail_JavaMail.class.getClassLoader().getResource("SendEmail.properties");
		InputStream in = url.openStream();
		Properties properties = new Properties();
		properties.load(in);
		
		//1.设置发送邮件邮箱属性
		
		Session session =  Session.getDefaultInstance(properties, new MyAuthenticator(from, password));
		
		session.setDebug(true);
		
		MimeMessage message = creatMessage(session, from, to);
		
		//通过会话获取邮件传输对象
		Transport transport =  session.getTransport();
		
		transport.send(message,message.getAllRecipients());
		
		transport.close();
		
		return true;
	}
	
	public static MimeMessage creatMessage(Session session,String sendAccount,String receiveAccount) throws UnsupportedEncodingException, MessagingException{
	MimeMessage message = new MimeMessage(session);
		
		message.setFrom(new InternetAddress(sendAccount,"淘宝网"));
		
		message.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(receiveAccount, "淘宝会员"));
		
		message.setSubject("打折优惠");
		
		message.setContent("XX用户你好, 今天全场5折, 快来抢购, 错过今天再等一年。。。","text/html;charset=UTF-8");
		
		message.setSentDate(new Date());
		
		message.saveChanges();
		
		return message;
	}
}
