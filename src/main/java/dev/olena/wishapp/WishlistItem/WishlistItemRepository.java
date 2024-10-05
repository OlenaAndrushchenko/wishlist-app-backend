package dev.olena.wishapp.WishlistItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
   List<WishlistItem> findAllByWishlistId(Long wishlistId);
}
