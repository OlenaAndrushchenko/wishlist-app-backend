package dev.olena.wishapp.encryption;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Base64EncoderTest {

    @Autowired
    private Base64Encoder base64Encoder;

    @Test
    public void testEncode() {
        
        String input = "Hello, World!";
        String encoded = base64Encoder.encode(input);
        String expected = Base64.getEncoder().encodeToString(input.getBytes());
        assertEquals(expected, encoded);

    }

    @Test
    void testMatches() {

        String input = "Hello, World!";
        String encoded = base64Encoder.encode(input);
        boolean matches = base64Encoder.matches(input, encoded);
        assertEquals(true, matches);

    }
}
