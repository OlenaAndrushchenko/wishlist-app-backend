package dev.olena.wishapp.encryption;

public interface IEncoder {
    String encode(String input);
    boolean matches(String input, String encodedInput);
}
