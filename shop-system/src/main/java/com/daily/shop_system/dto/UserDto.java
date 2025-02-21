package com.daily.shop_system.dto;

import com.daily.shop_system.model.Cart;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> order;
    private CartDto cart;

}
