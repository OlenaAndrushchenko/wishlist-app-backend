package dev.olena.wishapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    
   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;

   @Autowired
   public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
       this.userRepository = userRepository;
       this.passwordEncoder = passwordEncoder;
   }

    public String save(UserDTO userDto) {

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email " + userDto.getEmail() + " already exists");
        }

        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
        return "User " + user.getUsername() + " has been registered";
    }
}
