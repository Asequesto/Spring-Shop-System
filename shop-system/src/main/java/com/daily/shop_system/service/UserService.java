package com.daily.shop_system.service;

import com.daily.shop_system.model.User;
import com.daily.shop_system.repository.UserRepository;
import com.daily.shop_system.requests.CreateUserRequest;
import com.daily.shop_system.requests.UserUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
    }

    public User createUser(CreateUserRequest request){
        User check_user = userRepository.findByEmail(request.getEmail());
        if(check_user != null){
            return null;
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return userRepository.save(user);
    }

    public User updateUser(UserUpdateRequest request, Long userId){
        User user = getUserById(userId);
        if(user == null) return null;
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return userRepository.save(user);
    }

    public void deleteUserById(Long userId){
        userRepository.deleteById(userId);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
