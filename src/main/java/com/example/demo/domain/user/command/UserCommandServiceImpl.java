package com.example.demo.domain.user.command;

import com.example.demo.core.generic.AbstractCommandServiceImpl;
import com.example.demo.core.generic.AbstractQueryServiceImpl;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class UserCommandServiceImpl extends AbstractCommandServiceImpl<User> implements UserCommandService {

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserCommandServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
    super(repository);
    this.passwordEncoder = passwordEncoder;
  }


  @Override
  public User register(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return save(user);
  }

  public Stream<Character> getRandomSpecialChars(int count) {
    Random random = new SecureRandom();
    IntStream specialChars = random.ints(count, 33, 45);
    return specialChars.mapToObj(data -> (char) data);
  }

}
