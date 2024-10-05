package dev.olena.wishapp.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class JwtUtilTest {
    
    private JwtUtil jwtUtil;

    private String jwtSecretKey = "b8vo0gK9R0Imotq0UbTxkIS36zpK3iHOwR82FwjgeQ0";
    private Long expirationTime = 3600000L;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil(jwtSecretKey);
        jwtUtil.setExpirationTime(expirationTime);
    }

    @Test
    void testTokenGenerationAndValidation() {
        
        String email = "test@test.com";
        String token = jwtUtil.generateToken(email);
    
        assertNotNull(token, "Token should not be null");
        assertEquals(email, jwtUtil.extractEmail(token), "Extracted email should match the given email");
        assertFalse(jwtUtil.isTokenExpired(token), "Token should not be expired right after generation");

        assertTrue(jwtUtil.validateToken(token, email), "Token should be valid for the correct email");
        assertFalse(jwtUtil.validateToken(token, "invalid@test.com"), "Token should not be valid for an incorrect email");
    }
}
