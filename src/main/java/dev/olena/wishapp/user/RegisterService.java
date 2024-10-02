package dev.olena.wishapp.user;

import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    
   private final UserRepository userAccountRepository;

    public RegisterService(UserRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public String save(UserDTO userAccountDto) {

        User userAccount = new User();

        userAccount.setUsername(userAccountDto.getUsername());
        userAccount.setPassword(userAccountDto.getPassword());
        userAccountRepository.save(userAccount);
        return "User " + userAccount.getUsername() + " has been registered";
    }
}
