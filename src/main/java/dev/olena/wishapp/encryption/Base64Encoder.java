package dev.olena.wishapp.encryption;

import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class Base64Encoder implements IEncoder {
    
    @Override
    public String encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    @Override
    public boolean matches(String rawInput, String encodedInput) {
        return encode(rawInput).equals(encodedInput);
    }
}
