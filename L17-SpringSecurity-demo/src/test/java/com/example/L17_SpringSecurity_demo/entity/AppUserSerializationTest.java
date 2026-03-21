package com.example.L17_SpringSecurity_demo.entity;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppUserSerializationTest {

    @Test
    void appUserCanBeSerializedForRedisBackedSession() {
        AppUser appUser = AppUser.builder()
                .id(1L)
                .email("user@example.com")
                .name("Test User")
                .password("secret")
                .role("USER")
                .build();

        assertDoesNotThrow(() -> serialize(appUser));
    }

    private void serialize(AppUser appUser) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(appUser);
        }
    }
}
