package com.example.demo.domain.user.command;

import com.example.demo.core.generic.AbstractCommandService;
import com.example.demo.domain.user.User;

import java.io.IOException;

public interface UserCommandService extends AbstractCommandService<User> {
    User register(User user) throws IOException;
}
