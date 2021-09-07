package com.onegateafrica.service;

import com.onegateafrica.entity.Opt;
import com.onegateafrica.entity.Users;
import com.onegateafrica.repository.UsersRepository;

import net.bytebuddy.utility.RandomString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JavaMailSender mailSender;



  public ResponseEntity<Users> register(Users user) throws UnsupportedEncodingException, MessagingException {
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Users> existingUser = usersRepository.findByEmail(user.getEmail());
    if (existingUser.isPresent()) {
      return new ResponseEntity<>(HttpStatus.FOUND);
    }
    if (user.getEmail() == null || user.getPassword() == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encodedPassword);

      String randomCode = RandomString.make(64);
      user.setVerificationCode(randomCode);
      user.setEnabled(false);
      usersRepository.save(user);
      sendVerificationEmail(user);
      return ResponseEntity.ok(user);
    }
  }

  public ResponseEntity<Users> registerNumTel(Users user) {
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Users> existingUser = usersRepository.findByEmail(user.getEmail());
    if (existingUser.isPresent()) {
      return new ResponseEntity<>(HttpStatus.FOUND);
    }
    if (user.getEmail() == null || user.getPassword() == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encodedPassword);
      user.setEnabled(true);
      usersRepository.save(user);
      return ResponseEntity.ok(user);
    }
  }

  private void sendVerificationEmail(Users user) throws MessagingException, UnsupportedEncodingException {

    String toAddress = user.getEmail();
    String fromAddress = "alahamadi17@gmail.com";
    String senderName = "LOCA";
    String subject = "Please verify your registration";
    String content = "Dear Mr/Madame,<br>"
        + "Please click the link below to verify your registration:<br>"
        + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
        + "Thank you,<br>"
        + "Your company name.";

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setFrom(fromAddress, senderName);
    helper.setTo(toAddress);
    helper.setSubject(subject);

    String verifyURL = "http://localhost:3000/verify/" + user.getVerificationCode() + "/" + user.getId();

    content = content.replace("[[URL]]", verifyURL);

    helper.setText(content, true);

    mailSender.send(message);

   System.out.println("Email has been sent");
  }

  public ResponseEntity<Users> verify(String verificationCode) {
    if (verificationCode == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Users> user = usersRepository.findByVerificationCode(verificationCode);

    if (user.isPresent()) {
      user.get().setVerificationCode(null);
      user.get().setEnabled(true);
      usersRepository.save(user.get());
      return ResponseEntity.ok(user.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }


  public boolean existsEmail(String email) {

    return usersRepository.existsByEmail(email);
  }

  //oga1605
  public ResponseEntity<Users> registerPersonel(Users user,String nom,String prenom) throws UnsupportedEncodingException, MessagingException {
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Users> existingUser = usersRepository.findByEmail(user.getEmail());
    if (existingUser.isPresent()) {
      return new ResponseEntity<>(HttpStatus.FOUND);
    }
    if (user.getEmail() == null || user.getPassword() == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encodedPassword);

      String randomCode = RandomString.make(64);
      user.setVerificationCode(randomCode);
      user.setEnabled(false);
      usersRepository.save(user);
      sendVerificationEmailAdminAgence(user,nom,prenom);
      return ResponseEntity.ok(user);
    }
  }

  private void sendVerificationEmailAdminAgence(Users user,String nom,String prenom) throws MessagingException, UnsupportedEncodingException {

    String toAddress = user.getEmail();
    String fromAddress = "alahamadi17@gmail.com";
    String senderName = "LOCA";
    String subject = "Please verify your registration";
    String content = "Dear Mr/Madame,<br>"
        + "Please click the link below to verify your registration:<br>"
        + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
        + "Thank you,<br>"
        + "Your company name.";

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setFrom(fromAddress, senderName);
    helper.setTo(toAddress);
    helper.setSubject(subject);

    String verifyURL = "http://localhost:3000/ListPersonnels/" + user.getVerificationCode() + "/" + user.getId()+"/"+nom+"/"+prenom;

    content = content.replace("[[URL]]", verifyURL);

    helper.setText(content, true);

    mailSender.send(message);

    System.out.println("Email has been sent");
  }

}
