package dev.olena.wishapp.wishlist;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.olena.wishapp.user.User;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findAllByUser(User user);
    Optional<Wishlist> findByUser_userIdentifierAndShareableUrl(String userIdentifier, String shareableUrl);
}
