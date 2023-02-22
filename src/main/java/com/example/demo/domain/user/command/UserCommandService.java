package com.example.demo.domain.user.command;

import com.example.demo.core.generic.AbstractCommandService;
import com.example.demo.core.generic.AbstractQueryService;
import com.example.demo.domain.user.User;

public interface UserCommandService extends AbstractCommandService<User> {
  User register(User user);
}
