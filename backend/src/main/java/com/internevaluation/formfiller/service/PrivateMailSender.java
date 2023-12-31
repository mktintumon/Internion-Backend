package com.internevaluation.formfiller.service;

import com.internevaluation.formfiller.entity.ListOfUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
@Service
public class PrivateMailSender {
    //    private PrivateMailService privateMailService;
    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendPrivateMail(ListOfUser listOfUser) throws MessagingException {
        if (listOfUser != null && listOfUser.getListUser() != null) {
            for (int i=0;i<listOfUser.getListUser().size();i++){
                sendEmailWithAttachment(listOfUser.getListUser().get(i),buildEmail(listOfUser.getUsername(),listOfUser.getListUser().get(i)),listOfUser.getUsername(),listOfUser.getFilename());
            }
        }
    }

    private String buildEmail(String senderName, String receiverName) {
        return "Document Sent from "+senderName+ ". " + " (Document password is the sendername)";
    }

    @Async
    public void sendEmailWithAttachment(String to, String text,String sender,String filename){
        try {
            MimeMessage message=javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setTo(to);
            helper.setSubject("Internion (Document)");
            helper.setText(buildEmail(sender,to));
            byte[] pdfBytes = Files.readAllBytes(Paths.get("C:\\Users\\Mohit\\Desktop\\Internion-Backend\\backend\\src\\main\\java\\com\\internevaluation\\formfiller\\datafolder\\"+sender+"\\"+filename+".pdf"));
            Resource pdfAttachment = new ByteArrayResource(pdfBytes);
            helper.addAttachment("document.pdf", pdfAttachment);

            javaMailSender.send(message);
        }catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendEmailWithAttachmentToSingleUser(String to, String text,String sender,String filename){
        try {
            MimeMessage message=javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setTo(to);
            helper.setSubject("Internion (Document)");
            helper.setText(sendAdminMail(sender,filename));
            byte[] pdfBytes = Files.readAllBytes(Paths.get("C:\\Users\\Mohit\\Desktop\\Internion-Backend\\backend\\src\\main\\java\\com\\internevaluation\\formfiller\\datafolder\\"+sender+"\\"+filename+".pdf"));
            Resource pdfAttachment = new ByteArrayResource(pdfBytes);
            helper.addAttachment("document.pdf", pdfAttachment);
            javaMailSender.send(message);
        }catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }


    public String sendAdminMail(String sender, String filename){
        return sender+" has requested you to approve his document : "+filename;
    }
}
