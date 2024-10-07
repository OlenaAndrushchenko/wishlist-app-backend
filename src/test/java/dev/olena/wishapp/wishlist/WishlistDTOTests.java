package dev.olena.wishapp.wishlist;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dev.olena.wishapp.WishlistItem.WishlistItem;
import dev.olena.wishapp.user.User;

public class WishlistDTOTests {

    @Test
    void whenConstructedWithWishlist_thenCorrectlyInitializesFields() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User();
        user.setUserIdentifier("1234abcd");

        WishlistItem item1 = new WishlistItem();
        item1.setId(1L);
        item1.setName("Samsung Galaxy S24");
        
        WishlistItem item2 = new WishlistItem();
        item2.setId(2L);
        item2.setName("Macbook Pro");
        
        Wishlist wishlist = new Wishlist();
        wishlist.setId(1L);
        wishlist.setTitle("Cool things");
        wishlist.setDescription("My favorite phone and computer");
        wishlist.setShared(true);
        wishlist.setShareableUrl("http://test.test/wishlist");
        wishlist.setUser(user);
        wishlist.setCreatedAt(now);
        wishlist.setUpdatedAt(now);
        
        item1.setWishlist(wishlist);
        item2.setWishlist(wishlist);
        wishlist.setItems(Arrays.asList(item1, item2));

        WishlistDTO dto = new WishlistDTO(wishlist);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getTitle()).isEqualTo("Cool things");
        assertThat(dto.getDescription()).isEqualTo("My favorite phone and computer");
        assertThat(dto.isShared()).isTrue();
        assertThat(dto.getShareableUrl()).isEqualTo("http://test.test/wishlist");
        assertThat(dto.getUserIdentifier()).isEqualTo("1234abcd");
        assertThat(dto.getCreatedAt()).isEqualTo(now);
        assertThat(dto.getUpdatedAt()).isEqualTo(now);
        assertThat(dto.getItems()).hasSize(2);
        assertThat(dto.getItems().get(0).getId()).isEqualTo(1L);
        assertThat(dto.getItems().get(1).getId()).isEqualTo(2L);
    }
}