package com.example.coffee_shop_manage_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationDto {

 @NotBlank(message = "Full name is required")
 @Size(min = 2, max = 31, message = "Full name must be between 2 and 31 characters")
 @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full name can only contain letters and spaces")
 String fullName;

 @NotBlank(message = "Username is required")
 @Size(min = 3, max = 11, message = "Username must be between 3 and 11 characters")
 @Pattern(regexp = "^\\w+$", message = "Username can only contain letters, numbers, and underscores")
 String username;

 @NotBlank(message = "Password is required")
 @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
 @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one number")
 String password;
}
