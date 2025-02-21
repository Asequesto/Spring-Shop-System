package com.daily.shop_system.service;

import com.daily.shop_system.dto.OrderDto;
import com.daily.shop_system.enums.OrderStatus;
import com.daily.shop_system.mapper.OrderMapper;
import com.daily.shop_system.model.*;
import com.daily.shop_system.repository.OrderRepository;
import com.daily.shop_system.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final OrderMapper orderMapper;

    public OrderDto placeOrder(Long userId){
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setOrderTotalAmount(countTotalAmount(orderItemList));
        Order orderSaved = orderRepository.save(order);

        cartService.clearCart(cart.getId());
        return orderMapper.toDto(orderSaved);
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private BigDecimal countTotalAmount(List<OrderItem> orderItemList){
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItemList){
            totalAmount = totalAmount.add(orderItem.getUnitPrice());
        }
        return totalAmount;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()){
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            orderItemList.add(orderItem);
        }
        return orderItemList;

    }

    public OrderDto getOrder(Long orderId){
        return orderMapper.toDto(orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found")));
    }

    public List<OrderDto> getUserOrders(Long userId){
        return orderMapper.toDtoList(orderRepository.findByUserId(userId));
    }

}
