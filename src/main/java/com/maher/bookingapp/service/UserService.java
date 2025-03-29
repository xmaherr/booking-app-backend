package com.maher.bookingapp.service;

import com.maher.bookingapp.Exceptions.EmailAlreadyExistsException;
import com.maher.bookingapp.Exceptions.WrongEmailOrPassword;
import com.maher.bookingapp.dto.LoginRequest;
import com.maher.bookingapp.dto.UserRequest;
import com.maher.bookingapp.dto.UserResponse;
import com.maher.bookingapp.entity.User;
import com.maher.bookingapp.repository.UserRepository;
import com.maher.bookingapp.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse registerUser(UserRequest request) {
        if (emailExists(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .role(request.getRole())
                .name(request.getName())
                .build();

        User savedUser = userRepository.save(user);

       return  UserResponse.builder().id(savedUser.getId())
               .email(savedUser.getEmail())
               .role(savedUser.getRole())
               .build();
    }

    public String loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new WrongEmailOrPassword("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new WrongEmailOrPassword("Invalid email or password");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    private boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
