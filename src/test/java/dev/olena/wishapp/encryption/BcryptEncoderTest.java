package dev.olena.wishapp.encryption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BcryptEncoderTest {
    
    @Autowired
    private BcryptEncoder bcryptEncoder;
    
    @Test
    public void testEncode() {
        String input = "password";
        String encoded = bcryptEncoder.encode(input);
        assertEquals(true, bcryptEncoder.matches(input, encoded));
    }
    @Test
    public void testMatches() {
        String input = "password";
        String encoded = bcryptEncoder.encode(input);
        assertTrue(bcryptEncoder.matches(input, encoded));
    }
}