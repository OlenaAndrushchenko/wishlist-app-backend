package dev.olena.wishapp.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.olena.wishapp.user.User;

public class UserTests {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testUserIdentifierGeneration() {
        user.generateUserIdentifier();
        assertNotNull(user.getUserIdentifier());
        assertEquals(8, user.getUserIdentifier().length());
    }

    @Test
    void testUserDetailsImplementation() {
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }
}
