package com.example.coffee_shop_manage_api.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.coffee_shop_manage_api.global.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class User {
 @Id
 @GeneratedValue(strategy = GenerationType.UUID)
 String id;
 @Column(nullable = false, length = 30)
 String fullName;
 @Column(nullable = false, unique = true, length = 10)
 String username;
 @Column(nullable = false, unique = true, length = 60)
 String password;
 @Column(nullable = false, unique = true, length = 13)
 String phoneNumber;
 @Enumerated(EnumType.STRING)
 @Column(nullable = false)
 UserRole role = UserRole.GUEST;
 @CreationTimestamp
 @Column(name = "created_at", updatable = false)
 LocalDateTime createdAt;
 @UpdateTimestamp
 @Column(name = "updated_at")
 LocalDateTime updatedAt;

 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
 @JsonIgnore
 List<Order> orders;

 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
 @JsonIgnore
 List<Reservation> reservations;

}
