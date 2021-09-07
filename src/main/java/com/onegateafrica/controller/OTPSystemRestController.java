package com.onegateafrica.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onegateafrica.dto.OTPSystemDTO;
import com.onegateafrica.entity.Users;
import com.onegateafrica.repository.UsersRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@RestController
@RequestMapping("/oauth")

@CrossOrigin(origins = "http://localhost:3000")
public class OTPSystemRestController {


  public final static String NOT_FOUND = "NOT_FOUND";
  public final static String FOUND = "FOUND";

  @Autowired
  private UsersRepository usersRepository;

  private Map<String, OTPSystemDTO> otpData = new HashMap<>();
//  private final static String ACCOUNT_SID = "AC2a7e8e41716e93e9935cb689ec923e2c";
//  private final static String AUTH_ID = "8bdd2a9513dff204db8855b2f913cef6";
  private final static String ACCOUNT_SID = "AC673b070545b59beb958493dd675b7a1b";
  private final static String AUTH_ID = "1a9f5cce2f8f6c75925c230f13095efe";

  static {
    Twilio.init(ACCOUNT_SID, AUTH_ID);
  }

  @PostMapping("/mobilenumbers/otp")
  public ResponseEntity<Object> sendOTP(@RequestBody String mobilenumber) {

    Optional<Users> existingUser = usersRepository.findByEmail(mobilenumber);
    if (existingUser.isPresent()) {
      return new ResponseEntity<>(FOUND, HttpStatus.OK);
    }
    OTPSystemDTO otpSystem = new OTPSystemDTO();
    otpSystem.setMobilenumber(mobilenumber);

    otpSystem.setOtp(String.valueOf(((int) (Math.random() * (10000 - 1000))) + 1000));
    otpSystem.setExpiretime(System.currentTimeMillis() + 60000);

    otpData.put(mobilenumber, otpSystem);


    Message.creator(new PhoneNumber("+216" + mobilenumber), new PhoneNumber("+15183484412"), "Your OTP is: " + otpSystem.getOtp()).create();
//    Message.creator(new PhoneNumber("+216" + mobilenumber), new PhoneNumber("+15415277007"), "Your OTP is: " + otpSystem.getOtp()).create();
    return new ResponseEntity<>("OTP is send successufully", HttpStatus.OK);
  }


  @PutMapping("/mobilenumbers/otp")
  public ResponseEntity<Object> verifyOTP(@RequestBody OTPSystemDTO requestBodyOTPSystem) {

    if (requestBodyOTPSystem.getOtp() == null || requestBodyOTPSystem.getOtp().trim().length() <= 0) {
      return new ResponseEntity<>("Please provide OTP", HttpStatus.OK);
    }

    Map.Entry<String, OTPSystemDTO> entry = otpData.entrySet().iterator().next();
    String key = entry.getKey();
    String x = key.substring(0, 8);
//    System.out.println(x);
//    System.out.println(x.equals(requestBodyOTPSystem.getMobilenumber()));
//    System.out.println(requestBodyOTPSystem.getMobilenumber());
    if (x.equals(requestBodyOTPSystem.getMobilenumber())) {

      OTPSystemDTO otpSystem = otpData.get(key);
      if (otpSystem != null) {
        if (otpSystem.getExpiretime() >= System.currentTimeMillis()) {
          if (requestBodyOTPSystem.getOtp().equals(otpSystem.getOtp())) {
            otpData.remove(requestBodyOTPSystem.getMobilenumber());
            return new ResponseEntity<>("OTP is verified Successfully", HttpStatus.OK);
          }
          return new ResponseEntity<>("Invalid OTP", HttpStatus.OK);
        }
        return new ResponseEntity<>("OTP is expired ", HttpStatus.OK);
      }
      return new ResponseEntity<>("Somthing went wrong .. !!", HttpStatus.OK);
    }
    return new ResponseEntity<>("Mobile number not found", HttpStatus.OK);

  }
}