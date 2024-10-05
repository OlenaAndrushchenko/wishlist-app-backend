package dev.olena.wishapp.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import dev.olena.wishapp.user.User;
import dev.olena.wishapp.user.UserRepository;

@DataJpaTest
@Sql(scripts = "classpath:data-h2.sql")
public class UserRepositoryTests {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenFindByUsername_thenReturnUser() {

        User user = new User(null, "testuser", "password", "test@email.com", null);
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();

        User found = userRepository.findByEmail("test@email.com").orElse(null);
        assertNotNull(found);
        assertEquals("test@email.com", found.getUsername());
        assertEquals("testuser", found.getRealUsername());
    }
}
