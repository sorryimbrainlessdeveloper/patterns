package org.example.builder.service;

import lombok.RequiredArgsConstructor;
import org.example.builder.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    public User createUser() {
        return new User.UserBuilder()
                .setId(1L)
                .setName("John Doe")
                .setEmail("john.doe@example.com")
                .setAge(30)
                .setAddress("123 Main St")
                .build();
    }
}
