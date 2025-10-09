package com.example.coffee_shop_manage_api.global;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ResponsePagination <T>{
 List<T> results;
 int page;
 int size;
 long totalElements;
 int totalPages;
}
