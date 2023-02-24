package com.example.demo.domain.user.command;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.dto.UserDTO;
import com.example.demo.domain.user.dto.UserMapper;
import com.example.demo.domain.user.dto.UserRegisterDTO;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;

import com.example.demo.domain.user.query.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/user")
public class UserCommandController {

  private final UserCommandService userCommandService;

  private final UserMapper userMapper;

  @Autowired
  public UserCommandController(UserCommandService userCommandService, UserMapper userMapper) {
    this.userCommandService = userCommandService;
    this.userMapper = userMapper;
  }

  @PostMapping("/register")
  @Operation(summary = "Register User")
  public ResponseEntity<UserDTO> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
    User user = userCommandService.register(userMapper.fromUserRegisterDTO(userRegisterDTO));
    return new ResponseEntity<>(userMapper.toDTO(user), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  @PreAuthorize(
      "hasAuthority('USER_MODIFY') && @userPermissionEvaluator.isUserAboveAge(authentication.principal.user,18)")
  @Operation(summary = "Update User")
  public ResponseEntity<UserDTO> updateById(@PathVariable UUID id, @Valid @RequestBody UserDTO userDTO) {
    User user = userCommandService.updateById(id, userMapper.fromDTO(userDTO));
    return new ResponseEntity<>(userMapper.toDTO(user), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('USER_DELETE')")
  @Operation(summary = "Delete User")
  public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
    userCommandService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
