package dev.olena.wishapp.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import dev.olena.wishapp.encryption.Base64Encoder;
import dev.olena.wishapp.encryption.BcryptEncoder;
import dev.olena.wishapp.encryption.IEncoder;

public class EncoderFacade {
    
    private final BcryptEncoder bcryptEncoder;
    private final Base64Encoder base64Encoder;

    @Autowired
    public EncoderFacade(@Qualifier("bcryptEncoder") IEncoder bcryptEncoder,
                        @Qualifier("base64Encoder") IEncoder base64Encoder) {
        this.bcryptEncoder = (BcryptEncoder) bcryptEncoder;
        this.base64Encoder = (Base64Encoder) base64Encoder;
    }

    public String encode(String input, String type) {
        switch (type) {
            case "bcrypt":
                return bcryptEncoder.encode(input);
            case "base64":
                return base64Encoder.encode(input);
            default:
                throw new IllegalArgumentException("Invalid encoder type: " + type);
        }
    }

    public boolean matches(String rawInput, String encodedInput, String type) {
        switch (type) {
            case "bcrypt":
                return bcryptEncoder.matches(rawInput, encodedInput);
            case "base64":
                return base64Encoder.matches(rawInput, encodedInput);
            default:
                throw new IllegalArgumentException("Invalid encoder type: " + type);
        }
    }
}
