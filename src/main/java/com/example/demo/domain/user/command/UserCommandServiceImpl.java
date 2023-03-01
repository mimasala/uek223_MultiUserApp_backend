package com.example.demo.domain.user.command;

import com.example.demo.core.generic.AbstractCommandServiceImpl;
import com.example.demo.domain.recommender.Gorse;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Service
public class UserCommandServiceImpl extends AbstractCommandServiceImpl<User> implements UserCommandService {

  private final PasswordEncoder passwordEncoder;
  private final Gorse client;

  @Autowired
  public UserCommandServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, Gorse client) {
    super(repository);
    this.passwordEncoder = passwordEncoder;
    this.client = client;
  }

  @Override
  public User register(User user) throws IOException {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setUserId(UUID.randomUUID());
    user = save(user);

    client.insertUser(new io.gorse.gorse4j.User(
            user.getId().toString(), Collections.emptyList()
    ));

    return user;
  }
}
