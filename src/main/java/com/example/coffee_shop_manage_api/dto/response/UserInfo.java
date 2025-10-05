package com.example.coffee_shop_manage_api.dto.response;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.coffee_shop_manage_api.global.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements UserDetails {
 String fullName;
 String username;
 UserRole role;

 public String getFullName() {
  return fullName;
 }

 public UserRole getRole() {
  return role;
 }

 @Override
 public Collection<? extends GrantedAuthority> getAuthorities() {
  return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
 }

 @Override
 public String getPassword() {
  return null;
 }

 @Override
 public String getUsername() {
  return username;
 }

 @Override
 public boolean isAccountNonExpired() {
  return true;
 }

 @Override
 public boolean isAccountNonLocked() {
  return true;
 }

 @Override
 public boolean isCredentialsNonExpired() {
  return true;
 }

 @Override
 public boolean isEnabled() {
  return true;
 }
}
