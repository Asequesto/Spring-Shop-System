package com.daily.shop_system.mapper;

import com.daily.shop_system.dto.UserDto;
import com.daily.shop_system.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);

}
