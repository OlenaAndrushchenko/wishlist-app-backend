package dev.olena.wishapp.encryption;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptEncoder implements IEncoder {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encode(String input) {
        return bCryptPasswordEncoder.encode(input);
    }

    @Override
    public boolean matches(String rawInput, String encodedInput) {
        return bCryptPasswordEncoder.matches(rawInput, encodedInput);
    }
}
