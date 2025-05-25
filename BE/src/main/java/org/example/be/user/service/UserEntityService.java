package org.example.be.user.service;

import lombok.RequiredArgsConstructor;
import org.example.be.user.repository.UserEntityRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEntityService {

  private final UserEntityRepository userRepository;

}
