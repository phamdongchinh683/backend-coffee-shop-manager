package com.example.coffee_shop_manage_api.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuResponse {
 String id;
 String menuName;
 String cost;
 String size;
}
