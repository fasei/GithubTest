package com.zt.wc.collectcrashinfo.utils;

import com.zt.wc.collectcrashinfo.bean.MailSenderInfo;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 邮件发送工具类，只需配置好MainSenderInfo即可.
 *
 * Created by 王超 on 2016/12/14.
 */
public class EMailUtils {

    public static boolean sendEMail(MailSenderInfo mailSenderInfo, String content) throws MessagingException, GeneralSecurityException {
        boolean sendMailState = false;
        if (mailSenderInfo == null) {
            return sendMailState;
        }
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailSenderInfo.getMailServerHost());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  //使用JSSE的SSL socketfactory来取代默认的socketfactory
        properties.put("mail.smtp.socketFactory.fallback", "false");  // 只处理SSL的连接,对于非SSL的连接不做处理
        properties.put("mail.smtp.port", mailSenderInfo.getMailServerPort());
        properties.put("mail.smtp.socketFactory.port", mailSenderInfo.getMailServerPort());

        Session session = Session.getInstance(properties);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        try {
            // 发件人
            Address address = new InternetAddress(mailSenderInfo.getUserName());
            message.setFrom(address);
            // 收件人,支持多人发送
            List<String> toAddressList = mailSenderInfo.getToAddress();
            if (toAddressList != null && toAddressList.size() > 0) {
                Address[] addresses = new Address[toAddressList.size()];
                for (int i = 0; i < toAddressList.size(); i++) {
                    addresses[i] = new InternetAddress(toAddressList.get(i));
                }
                message.setRecipients(MimeMessage.RecipientType.TO, addresses);
            } else {
                Address toAddress = new InternetAddress("919536816@qq.com"); //发送给自己
                message.setRecipient(MimeMessage.RecipientType.TO, toAddress); // 设置收件人,并设置其接收类型为TO
            }

            // 主题
            message.setSubject(mailSenderInfo.getSubject());
            message.setText("Dear:\n\n"+content);
//            message.setHeader("X-Mailer",""+content);

//            // 添加文本
            Multipart multipart = new MimeMultipart();
            BodyPart text = new MimeBodyPart();
//            text.setContent(content,"text/html; charset = utf-8");
            text.setText("Dear:\n"+content);
            multipart.addBodyPart(text);
            message.setContent(multipart);
            message.saveChanges();
            //开始发送
            Transport transport = session.getTransport("smtp");
            transport.connect(mailSenderInfo.getMailServerHost(), mailSenderInfo.getUserName(), mailSenderInfo.getPassword());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            sendMailState = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendMailState;
    }

    public static void main(String arg[]) {
        try {
            sendEMail(new MailSenderInfo(),"/**************************Golog***************************/\n" +
                    "/********Time:2016-12-16 14:32:47\n" +
                    "/********Phone Branch:Meizu\n" +
                    "/********Phone Type:m1\n" +
                    "java.lang.RuntimeException: Unable to start activity ComponentInfo{com.zt.wc.githubtest/com.zt.wc.githubtest.MainActivity}: java.lang.NullPointerException: java.lang.NullPointerException\n" +
                    "at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2392)\n" +
                    "at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2443)\n" +
                    "at android.app.ActivityThread.access$800(ActivityThread.java:157)\n" +
                    "at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1354)\n" +
                    "at android.os.Handler.dispatchMessage(Handler.java:110)\n" +
                    "at android.os.Looper.loop(Looper.java:193)\n" +
                    "at android.app.ActivityThread.main(ActivityThread.java:5348)\n" +
                    "at java.lang.reflect.Method.invokeNative(Native Method)\n" +
                    "at java.lang.reflect.Method.invoke(Method.java:515)\n" +
                    "at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:829)\n" +
                    "at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:645)\n" +
                    "Caused by: java.lang.NullPointerException: java.lang.NullPointerException\n" +
                    "at com.zt.wc.githubtest.MainActivity.onCreate(MainActivity.java:33)\n" +
                    "at android.app.Activity.performCreate(Activity.java:5451)\n" +
                    "at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1106)\n" +
                    "at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2346)\n" +
                    "at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2443) \n" +
                    "at android.app.ActivityThread.access$800(ActivityThread.java:157) \n" +
                    "at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1354) \n" +
                    "at android.os.Handler.dispatchMessage(Handler.java:110) \n" +
                    "at android.os.Looper.loop(Looper.java:193) \n" +
                    "at android.app.ActivityThread.main(ActivityThread.java:5348) \n" +
                    "at java.lang.reflect.Method.invokeNative(Native Method) \n" +
                    "at java.lang.reflect.Method.invoke(Method.java:515) \n" +
                    "at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:829) \n" +
                    "at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:645) \n" +
                    "\n" +
                    "\n" +
                    "/**************************Endlog**************************/");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
