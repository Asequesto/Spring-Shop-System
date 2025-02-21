package com.daily.shop_system.repository;

import com.daily.shop_system.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);

    CartItem getCartItemById(Long cartId);
}
