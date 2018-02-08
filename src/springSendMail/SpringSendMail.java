package springSendMail;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import commons.SystemContent;

public class SpringSendMail {
	
	public static Properties properties;
	
	
	
	/**
	 * 初始化，读取配置文件
	 * @throws IOException
	 */
	public static void init() throws IOException{
		URL url = SpringSendMail.class.getClassLoader().getResource("SendEmail.properties");
		InputStream in = url.openStream();
		properties.load(in);
	}
	/**
	 * 创建邮件发送器
	 * @param username
	 * @param password
	 * @return
	 */
	public static JavaMailSenderImpl creatMail(String username,String password){
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		
		sender.setHost(properties.getProperty(SystemContent.HOST));
		sender.setPort(Integer.parseInt(properties.getProperty(SystemContent.port)));
		sender.setProtocol(properties.getProperty(SystemContent.PROTOCOL));
		sender.setUsername(username);
		sender.setPassword(password);
		Properties p = new Properties();
		p.setProperty("mail.smtp.auth", "true");
		p.setProperty("mail.smtp.timeout", "1000");
		sender.setJavaMailProperties(p);
		return sender;
	}
	/**
	 * 创建邮件发送的消息，并且发送邮件
	 * @param mailSender
	 * @param to
	 * @param subject
	 * @param content
	 * @throws MessagingException 
	 * @throws AddressException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void sendMail(JavaMailSenderImpl mailSender,String to,String subject,String content) throws AddressException, MessagingException, UnsupportedEncodingException{
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message,true,"UTF-8");
		messageHelper.setFrom(mailSender.getUsername(),mailSender.getPassword());
		messageHelper.setTo(to);
		messageHelper.setSubject(subject);
		messageHelper.setText(content);
		
		mailSender.send(message);
	}
	
}
