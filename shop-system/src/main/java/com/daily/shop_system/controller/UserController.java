package com.daily.shop_system.controller;


import com.daily.shop_system.dto.UserDto;
import com.daily.shop_system.mapper.UserMapper;
import com.daily.shop_system.model.User;
import com.daily.shop_system.requests.CreateUserRequest;
import com.daily.shop_system.requests.UserUpdateRequest;
import com.daily.shop_system.response.ApiResponse;
import com.daily.shop_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userMapper.userToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("Success", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found, Fail!", e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        User user = userService.createUser(request);
        UserDto userDto = userMapper.userToUserDto(user);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found, Fail!", null));
        }
        return ResponseEntity.ok(new ApiResponse("Success", userDto));
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request,@PathVariable Long userId) {
        User user = userService.updateUser(request, userId);
        UserDto userDto = userMapper.userToUserDto(user);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found, Fail!", null));
        }
        return ResponseEntity.ok(new ApiResponse("Success", userDto));
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok(new ApiResponse("Success", null));
    }


}
