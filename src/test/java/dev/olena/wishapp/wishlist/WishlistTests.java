package dev.olena.wishapp.wishlist;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import dev.olena.wishapp.WishlistItem.WishlistItem;

class WishlistTests {

    @Test
    void testGettersAndSetters() {
        Wishlist wishlist = new Wishlist();
        wishlist.setId(1L);
        wishlist.setTitle("Birthday");
        wishlist.setDescription("Birthday gift ideas");
        wishlist.setShared(true);
        wishlist.setShareableUrl("http://example.com/mywishlist");
        
        LocalDateTime now = LocalDateTime.now();
        wishlist.setCreatedAt(now);
        wishlist.setUpdatedAt(now);

        WishlistItem item = new WishlistItem();
        item.setId(1L);
        wishlist.setItems(Collections.singletonList(item));

        assertThat(wishlist.getId()).isEqualTo(1L);
        assertThat(wishlist.getTitle()).isEqualTo("Birthday");
        assertThat(wishlist.getDescription()).isEqualTo("Birthday gift ideas");
        assertThat(wishlist.isShared()).isTrue();
        assertThat(wishlist.getShareableUrl()).isEqualTo("http://example.com/mywishlist");
        assertThat(wishlist.getCreatedAt()).isEqualTo(now);
        assertThat(wishlist.getUpdatedAt()).isEqualTo(now);
        assertThat(wishlist.getItems()).containsOnly(item);
    }
}