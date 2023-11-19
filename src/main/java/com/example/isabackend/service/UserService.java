package com.example.isabackend.service;

import com.example.isabackend.model.User;
import com.example.isabackend.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserRepository userRepository;

    public User findOne(Integer id) {
        return userRepository.findById(id).orElseGet(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User exam) {
        return userRepository.save(exam);
    }

    public void remove(Integer id) {
        userRepository.deleteById(id);
    }

    public void sendRegistrationMail(User user){
        String subject = "Please verify your registration";
        String senderName = "ISA Group 7";
        String mailContent = "<p> Dear " + user.getFirstName() + ", </p>";
        mailContent += "<p> Please click the link below to verify your registration: </p>";

        String verifyURL = "http://localhost:8090" + "/api/v1/auth/verify?code=" + user.getVerificationCode();
        mailContent += "<h3><a href=\""+ verifyURL + "\">VERIFY</a></h3>";
        mailContent += "<p>Thank you <br> ISA Group 7</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try{
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(mailContent, true);
            mailSender.send(message);
        }
        catch (MessagingException e){
            e.printStackTrace();
        }
    }

    public boolean verify(String verificationCode){
        User user = userRepository.findByVerificationCode(verificationCode);
        if(user == null || user.isVerified()){
            return false;
        } else{
            userRepository.verifyUser(user.getId());
            System.out.println(user.getId());
            return true;
        }
    }

}
