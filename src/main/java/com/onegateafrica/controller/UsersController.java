package com.onegateafrica.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onegateafrica.dto.UsersDTO;
import com.onegateafrica.entity.Users;
import com.onegateafrica.payload.response.JwtResponse;
import com.onegateafrica.security.jwt.JwtUtils;
import com.onegateafrica.security.services.UserDetailsImpl;
import com.onegateafrica.service.UsersService;
import com.onegateafrica.service.VehiculeService;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/oauth")

@RestController
public class UsersController {

  public final static String FOUND = "FOUND";
  public final static String BAD_REQUEST = "BAD_REQUEST";
  public final static String NOT_FOUND = "NOT_FOUND";

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private UsersService usersService;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtUtils jwtUtils;


  public UsersController(UsersService usersService) {
    super();
    this.usersService = usersService;
  }

  @PostMapping("/signin")
  public ResponseEntity<Object> authenticateUser(@RequestBody UsersDTO usersDTO) {
    Users user = modelMapper.map(usersDTO, Users.class);
    if (!usersService.existsEmail(user.getEmail())) {
      return new ResponseEntity<>(FOUND, HttpStatus.OK);
    }

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return new ResponseEntity<>(new JwtResponse(jwt,
        userDetails.getId(),
        userDetails.getEmail()
    ), HttpStatus.OK);
  }

  @PostMapping("/process_register")
  public ResponseEntity<Object> processRegister(@RequestBody UsersDTO usersDTO) throws UnsupportedEncodingException, MessagingException {
    Users userReq = modelMapper.map(usersDTO, Users.class);
    ResponseEntity<Users> user = usersService.register(userReq);

    if (user.getStatusCodeValue() == 200) {
      UsersDTO userRes = modelMapper.map(user.getBody(), UsersDTO.class);
      return new ResponseEntity<>(userRes, HttpStatus.OK);
    } else if (user.getStatusCodeValue() == 400) {
      return new ResponseEntity<>(BAD_REQUEST, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(FOUND, HttpStatus.OK);
    }
  }

  @PostMapping("/process_register_numTel")
  public ResponseEntity<Object> processRegisterNumTel(@RequestBody UsersDTO usersDTO) {

    Users userReq = modelMapper.map(usersDTO, Users.class);
    ResponseEntity<Users> user = usersService.registerNumTel(userReq);

    if (user.getStatusCodeValue() == 200) {
      UsersDTO userRes = modelMapper.map(user.getBody(), UsersDTO.class);
      return new ResponseEntity<>(userRes, HttpStatus.OK);
    } else if (user.getStatusCodeValue() == 400) {
      return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity<>(FOUND, HttpStatus.FOUND);
    }
  }

  @GetMapping("/verify/{code}")
  public ResponseEntity<Object> verifyUser(@PathVariable String code) {
    ResponseEntity<Users> user = usersService.verify(code);
    if (user.getStatusCodeValue() == 200) {
      UsersDTO usersDTO = modelMapper.map(user.getBody(), UsersDTO.class);
      return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(NOT_FOUND, HttpStatus.OK);
    }

  }


  //oga1605
  @PostMapping("/personelAgenceRegister/{nom}/{prenom}")
  public ResponseEntity<Object> personelAgenceRegister(@RequestBody UsersDTO usersDTO,@PathVariable String nom,@PathVariable String prenom) throws UnsupportedEncodingException, MessagingException {
    Users userReq = modelMapper.map(usersDTO, Users.class);
    ResponseEntity<Users> user = usersService.registerPersonel(userReq,nom,prenom);

    if (user.getStatusCodeValue() == 200) {
      UsersDTO userRes = modelMapper.map(user.getBody(), UsersDTO.class);
      return new ResponseEntity<>(userRes, HttpStatus.OK);
    } else if (user.getStatusCodeValue() == 400) {
      return new ResponseEntity<>(BAD_REQUEST, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(FOUND, HttpStatus.OK);
    }
  }
}


