package dev.olena.wishapp.UserAccount;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegisterServiceTest {
    
    @Mock
    private UserAccountRepository userAccountRepository;

    @InjectMocks
    private RegisterService registerService;

    @Test
    void testSaveUser() {
        UserAccountDto dto = new UserAccountDto("userTest", "passTest");
        UserAccount userAccount = new UserAccount(null, "userTest", "passTest");
        
        when(userAccountRepository.save(any(UserAccount.class))).thenReturn(userAccount);

        String result = registerService.save(dto);
        assert(result.equals("User userTest has been registered"));
    }
}
