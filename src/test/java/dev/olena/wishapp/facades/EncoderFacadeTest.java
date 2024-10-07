package dev.olena.wishapp.facades;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import dev.olena.wishapp.encryption.IEncoder;

@SpringBootTest
public class EncoderFacadeTest {

    @Mock
    private IEncoder bcryptEncoder;

    @Mock
    private IEncoder base64Encoder;

    @InjectMocks
    private EncoderFacade encoderFacade;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        encoderFacade = new EncoderFacade(bcryptEncoder, base64Encoder);
    }

    @Test
    public void testEncodeBase64() {
        String input = "Hello";
        when(base64Encoder.encode(anyString())).thenReturn("encodedBase64");
        assertEquals("encodedBase64", encoderFacade.encode(input, "base64"), "Base64 encoding should return 'encodedBase64'");
    }

    @Test
    public void testEncodeBcrypt() {
        String input = "Hello";
        when(bcryptEncoder.encode(anyString())).thenReturn("encodedBcrypt");
        assertEquals("encodedBcrypt", encoderFacade.encode(input, "bcrypt"), "Bcrypt encoding should return 'encodedBcrypt'");
    }
    
    @Test
    public void testMatchesBase64() {
        String rawInput = "test";
        String encodedInput = "encodedBase64";
        when(base64Encoder.matches(rawInput, encodedInput)).thenReturn(true);
        assertTrue(encoderFacade.matches(rawInput, encodedInput, "base64"), "Should return true for matching Base64 values.");
    }
    
    @Test
    public void testMatchesBcrypt() {
        String rawInput = "test";
        String encodedInput = "encodedBcrypt";
        when(bcryptEncoder.matches(rawInput, encodedInput)).thenReturn(true);
        assertTrue(encoderFacade.matches(rawInput, encodedInput, "bcrypt"), "Should return true for matching BCrypt values.");
    }
}
