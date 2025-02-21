package com.daily.shop_system.mapper;

import com.daily.shop_system.dto.OrderDto;
import com.daily.shop_system.model.Order;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "order_id", target = "id")
    @Mapping(source = "orderTotalAmount", target = "totalAmount")
    @Mapping(source = "orderStatus", target = "status")
    @Mapping(source = "user.id", target = "userId")
    OrderDto toDto(Order order);

    @InheritInverseConfiguration
    Order toModel(OrderDto orderDto);

    List<OrderDto> toDtoList(List<Order> orders);
    List<Order> toModelList(List<OrderDto> orderDtos);
}
