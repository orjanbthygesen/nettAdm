package assignment15;

import java.util.*;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;
import javax.activation.*;

public class SendEmail
{
	 private static String HOST = "smtp.gmail.com";
	   private static String USER = "nettadmerbest@gmail.com";
	   private static String PASSWORD = "kommunikasjonsteknologi";
	   private static String PORT = "465";
	   private static String FROM = "nettadmerbest@gmail.com";
	   private static String CC1 = "orjanboe@stud.ntnu.no";
	   private static String CC2 = "magnusao@stud.ntnu.no";
	   // private static String TO = "ttm4128@item.ntnu.no";
	   private static String TO = "orjanbthygesen@gmail.com";
	   private static String STARTTLS = "true";
	   private static String AUTH = "true";
	   private static String DEBUG = "true";
	   private static String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
	   private static String SUBJECT = "[TTM4128][orjanboe&magnusao]An Email for Ruth";


   public static synchronized void send(String TEXT){

	   //Use Properties object to set environment properties
	   Properties props = new Properties();

	   props.put("mail.smtp.host", HOST);
	   props.put("mail.smtp.port", PORT);
	   props.put("mail.smtp.user", USER);

	   props.put("mail.smtp.auth", AUTH);
	   props.put("mail.smtp.starttls.enable", STARTTLS);
	   props.put("mail.smtp.debug", DEBUG);

	   props.put("mail.smtp.socketFactory.port", PORT);
	   props.put("mail.smtp.socketFactory.class", SOCKET_FACTORY);
	   props.put("mail.smtp.socketFactory.fallback", "false");

	   try {

	   //Obtain the default mail session
	   Session session = Session.getDefaultInstance(props, null);
	   session.setDebug(true);

	   //Construct the mail message
	   MimeMessage message = new MimeMessage(session);
	   message.setContent(TEXT,"text/html");
	   message.setSubject(SUBJECT);
	   message.setFrom(new InternetAddress(FROM));
	   message.addRecipient(RecipientType.TO, new InternetAddress(TO));
	   message.addRecipient(RecipientType.CC, new InternetAddress(CC1));
	   message.addRecipient(RecipientType.CC, new InternetAddress(CC2));
	   message.saveChanges();

 	   
	   //Use Transport to deliver the message
	   Transport transport = session.getTransport("smtp");
	   transport.connect(HOST, USER, PASSWORD);
	   transport.sendMessage(message, message.getAllRecipients());
	   transport.close();

	   } catch (Exception e) {
	   e.printStackTrace();
	   }
   }
//   public static void main(String[] args) {
//	String textToSend = "Test v 2.0";
//	   SendEmail.send(textToSend);
//   }
}