package org.example.be.auth.controller;

import lombok.RequiredArgsConstructor;
import org.example.be.jwt.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final JwtUtil jwtUtil;

  @PostMapping("/login")
  public String login(@RequestParam(name = "user_id") Long userId){
    return jwtUtil.generateToken(userId);
  }
}
