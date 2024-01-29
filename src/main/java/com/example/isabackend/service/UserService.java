package com.example.isabackend.service;

import com.example.isabackend.dto.UserDTO;
import com.example.isabackend.model.Role;
import com.example.isabackend.model.User;
import com.example.isabackend.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User findOne(Integer id) {
        return userRepository.findById(id).orElseGet(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
    public User save(UserDTO userRequest) {
        User u = new User();
        u.setEmail(userRequest.getEmail());

        // pre nego sto postavimo lozinku u atribut hesiramo je kako bi se u bazi nalazila hesirana lozinka
        // treba voditi racuna da se koristi isi password encoder bean koji je postavljen u AUthenticationManager-u kako bi koristili isti algoritam
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        u.setFirstName(userRequest.getFirstName());
        u.setLastName(userRequest.getLastName());
        u.setProfession(userRequest.getProfession());
        u.setCity(userRequest.getCity());
        u.setCountry(userRequest.getCountry());
        u.setPhoneNumber(userRequest.getPhoneNumber());
        u.setCompanyInfo(userRequest.getCompanyInfo());
        u.setPenaltyPoints(0);

        byte[] randomBytes = new byte[48]; // 48 bytes will result in a 64-character Base64-encoded string
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        String randomString = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        u.setVerificationCode(randomString);
        u.setVerified(false);

        // u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
        List<Role> roles = roleService.findByName("ROLE_USER");
        u.setRoles(roles);

        this.sendRegistrationMail(u);

        return this.userRepository.save(u);
    }

    public User addPenaltyPoints(Integer id, Integer points) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Integer currentPoints = user.getPenaltyPoints();
            user.setPenaltyPoints(currentPoints + points);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Transactional
    public void makeUserAdmin(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Role> adminRoles = roleService.findByName("ROLE_SYSADMIN");

            // Set the user's roles to the new admin roles
            user.setRoles(adminRoles);

            // Save the user with the updated roles
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    @Transactional
    public void makeUserCompanyAdmin(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Role> adminRoles = roleService.findByName("ROLE_ADMIN");

            // Set the user's roles to the new admin roles
            user.setRoles(adminRoles);

            // Save the user with the updated roles
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    public User save(User exam) {
        return userRepository.save(exam);
    }

    public void remove(Integer id) {
        userRepository.deleteById(id);
    }

    @Async
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        } else {
            return user;
        }
    }
}
