package com.example.demo.domain.user.query;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.command.UserCommandService;
import com.example.demo.domain.user.dto.UserDTO;
import com.example.demo.domain.user.dto.UserMapper;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/user")
public class UserQueryController {

    private final UserQueryService userQueryService;
    private final UserMapper userMapper;

    @Autowired
    public UserQueryController(UserQueryService userQueryService, UserMapper userMapper) {
        this.userQueryService = userQueryService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> retrieveById(@PathVariable UUID id) {
        User user = userQueryService.findById(id);
        return new ResponseEntity<>(userMapper.toDTO(user), HttpStatus.OK);
    }
}
