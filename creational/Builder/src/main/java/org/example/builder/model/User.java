package org.example.builder.model;

import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ToString
public class User {
    Long id;
    String name;
    String email;
    Integer age;
    String address;

    private User(UserBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.age = builder.age;
        this.address = builder.address;
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserBuilder {
        Long id;
        String name;
        String email;
        Integer age;
        String address;

        public UserBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setAge(Integer age) {
            this.age = age;
            return this;
        }

        public UserBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
