package dev.olena.wishapp.wishlist;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import dev.olena.wishapp.user.User;

@DataJpaTest
public class WishlistRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
    void whenFindAllByUser_thenReturnsWishlists() {
        User user = new User();
        user.setUserIdentifier("user123");
        user.setEmail("user123@example.com");
        user.setUsername("user123");
        user.setPassword("password123");
        entityManager.persist(user);

        Wishlist wishlist1 = new Wishlist(null, "Pokemons", "Our friends", false, null, user, null, LocalDateTime.now(), LocalDateTime.now());
        Wishlist wishlist2 = new Wishlist(null, "Digimons", "Pocket friends", false, null, user, null, LocalDateTime.now(), LocalDateTime.now());
        entityManager.persist(wishlist1);
        entityManager.persist(wishlist2);
        entityManager.flush();

        List<Wishlist> foundWishlists = wishlistRepository.findAllByUser(user);

        assertThat(foundWishlists).hasSize(2).extracting(Wishlist::getTitle).containsOnly("Pokemons", "Digimons");
    }

    @Test
    void whenFindByUser_thenReturnsWishlist() {
        User user = new User();
        user.setUserIdentifier("user456");
        user.setEmail("user456@example.com");
        user.setUsername("user456");
        user.setPassword("password456");
        entityManager.persist(user);
    
        Wishlist wishlist = new Wishlist(null, "Travel", "Travel destinations", true, "/travel", user, null, LocalDateTime.now(), LocalDateTime.now());
        entityManager.persist(wishlist);
        entityManager.flush();
    
        List<Wishlist> wishlists = wishlistRepository.findAllByUser(user);
        assertThat(wishlists).isNotEmpty();
    }
}