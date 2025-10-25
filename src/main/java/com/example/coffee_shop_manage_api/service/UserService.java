package com.example.coffee_shop_manage_api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.coffee_shop_manage_api.common.AbstractCommonService;
import com.example.coffee_shop_manage_api.configuration.JwtHelper;
import com.example.coffee_shop_manage_api.dto.request.UserCreationDto;
import com.example.coffee_shop_manage_api.dto.request.UserLoginDto;
import com.example.coffee_shop_manage_api.dto.response.UserInfo;
import com.example.coffee_shop_manage_api.global.UserRole;
import com.example.coffee_shop_manage_api.model.User;
import com.example.coffee_shop_manage_api.repository.UserRepository;

@Service
public class UserService extends AbstractCommonService<User, String> implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  private JwtHelper jwtHelper;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    super(userRepository);
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User register(UserCreationDto userCreationDto) {
    if (userRepository.existsByUsername(userCreationDto.getUsername())) {
      throw new RuntimeException("Username already exists");
    }

    User user = new User();
    String hashPassword = passwordEncoder.encode(userCreationDto.getPassword());
    user.setFullName(userCreationDto.getFullName());
    user.setUsername(userCreationDto.getUsername());
    user.setPhoneNumber(userCreationDto.getPhoneNumber());
    user.setPassword(hashPassword);
    user.setRole(UserRole.GUEST);
    return userRepository.save(user);
  }

  public String login(UserLoginDto userLoginDto) {
    User user = userRepository.findByUsername(userLoginDto.getUsername())
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
      throw new RuntimeException("Invalid password");
    }

    UserInfo userInfo = new UserInfo(user.getId(), user.getFullName(), user.getUsername(), user.getRole());

    final String jwt = jwtHelper.generateToken(userInfo);

    return jwt;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(username);

    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }

    User foundUser = user.get();
    return new UserInfo(foundUser.getId(), foundUser.getFullName(),
        foundUser.getUsername(), foundUser.getRole());
  }

}
