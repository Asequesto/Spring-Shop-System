package com.daily.shop_system.repository;

import com.daily.shop_system.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart getCartByUserId(Long userId);
}
