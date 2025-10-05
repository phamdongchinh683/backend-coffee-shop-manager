package com.example.coffee_shop_manage_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.coffee_shop_manage_api.global.MenuStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "menus")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Menu {

 @Id
 @GeneratedValue(strategy = GenerationType.UUID)
 String id;

 @Column(name = "menu_name", nullable = false, length = 50)
 String menuName;

 @Column(columnDefinition = "TEXT")
 String description;

 @Column(nullable = false, length = 20)
 String costs;

 @Column(nullable = false, length = 5)
 String sizes;

 @Enumerated(EnumType.STRING)
 @Column(nullable = false)
 MenuStatus status = MenuStatus.ACTIVE;

 @CreationTimestamp
 @Column(name = "created_at", updatable = false)
 LocalDateTime createdAt;

 @UpdateTimestamp
 @Column(name = "updated_at")
 LocalDateTime updatedAt;

 @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
 @JsonIgnore
 List<OrderItem> orderItems;

}
