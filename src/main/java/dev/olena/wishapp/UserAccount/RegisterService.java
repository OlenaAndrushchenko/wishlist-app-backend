package dev.olena.wishapp.UserAccount;

import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    
   private final UserAccountRepository userAccountRepository;

    public RegisterService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public String save(UserAccountDto userAccountDto) {

        UserAccount userAccount = new UserAccount();

        userAccount.setUsername(userAccountDto.getUsername());
        userAccount.setPassword(userAccountDto.getPassword());
        userAccountRepository.save(userAccount);
        return "User " + userAccount.getUsername() + " has been registered";
    }
}
