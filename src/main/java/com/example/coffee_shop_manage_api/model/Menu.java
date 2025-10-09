package com.example.coffee_shop_manage_api.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.coffee_shop_manage_api.global.MenuStatus;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
 @NotBlank(message = "Menu name is required")
 @Size(max = 50, message = "Menu name must not exceed 50 characters")
 String menuName;

 @Column(columnDefinition = "TEXT")
 @Size(max = 1000, message = "Description must not exceed 1000 characters")
 String description;

 @Column(nullable = false, length = 20)
 @NotBlank(message = "Cost is required")
 @Size(max = 20, message = "Cost must not exceed 20 characters")
 String costs;

 @Column(nullable = false, length = 5)
 @NotBlank(message = "Size is required")
 @Size(max = 7, message = "Size must not exceed 7 characters")
 String sizes;

 @Enumerated(EnumType.STRING)
 @Column(nullable = false)
 @NotNull(message = "Menu status is required")
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
