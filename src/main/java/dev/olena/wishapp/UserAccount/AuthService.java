package dev.olena.wishapp.UserAccount;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private final UserAccountRepository userAccountRepository;

    public AuthService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public String login(UserAccountDto userAccountDto) {
        return userAccountRepository.findByUsername(userAccountDto.getUsername())
                .filter(user -> user.getPassword().equals(userAccountDto.getPassword()))
                .map(user -> "User " + user.getUsername() + " has logged in")
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
    }
}
