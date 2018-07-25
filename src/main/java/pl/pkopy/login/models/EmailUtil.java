package pl.pkopy.login.models;



import pl.pkopy.login.models.forms.UserForm;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EmailUtil {




    public static void sendEmail(Session session, String toEmail, String subject, String body){
        try{
            MimeMessage msg = new MimeMessage(session);
            
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress("no_reply@pkopy.pl", "Widgets registration"));

            msg.setReplyTo(InternetAddress.parse("no_reply@pkopy.pl", false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void sendEmailAuth(UserForm userForm, String registerKey){

        String fromEmail = "test@pkopy.pl";
        String password = "pkopy7603!@#";

        String smtpHostServer = "s51.linuxpl.com";
        String emailID = userForm.getEmail();

        Properties props = System.getProperties();

        props.put("mail.smtp.host", smtpHostServer);
//        props.put("mail.smtps.port", "465");
        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtps.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };



        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, emailID,"SimpleEmail Testing Subject", "http://40.115.96.228:8080/login/register/"+ registerKey);
    }
}
