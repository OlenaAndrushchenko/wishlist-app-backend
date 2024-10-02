package dev.olena.wishapp.UserAccount;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import dev.olena.wishapp.user.RegisterService;
import dev.olena.wishapp.user.User;
import dev.olena.wishapp.user.UserDTO;
import dev.olena.wishapp.user.UserAccountRepository;

@SpringBootTest
public class RegisterServiceTest {
    
    @Mock
    private UserAccountRepository userAccountRepository;

    @InjectMocks
    private RegisterService registerService;

    @Test
    void testSaveUser() {
        UserDTO dto = new UserDTO("userTest", "passTest");
        User userAccount = new User(null, "userTest", "passTest");
        
        when(userAccountRepository.save(any(User.class))).thenReturn(userAccount);

        String result = registerService.save(dto);
        assert(result.equals("User userTest has been registered"));
    }
}
