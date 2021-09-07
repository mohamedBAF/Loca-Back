package com.onegateafrica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onegateafrica.repository.AdminOGARepository;

@Service
public class AdminOGAService {

  @Autowired
  AdminOGARepository adminOGARepository;

}
