package com.daily.shop_system.service;

import com.daily.shop_system.model.Cart;
import com.daily.shop_system.model.CartItem;
import com.daily.shop_system.model.User;
import com.daily.shop_system.repository.CartItemRepository;
import com.daily.shop_system.repository.CartRepository;
import com.daily.shop_system.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    public Cart getCart(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public void clearCart(Long id){
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);
    }

    public BigDecimal getTotalPrice(Long id){
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    public Cart initializeCart(User user){
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    public Cart getCartByUserId(Long userId) {
        return cartRepository.getCartByUserId(userId);
    }
}
