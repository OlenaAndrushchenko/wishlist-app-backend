package dev.olena.wishapp.WishlistItem;

import dev.olena.wishapp.wishlist.Wishlist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class WishlistItemTests {

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @Test
    public void testSaveAndRetrieveWishlistItem() {

        Wishlist wishlist = new Wishlist();
        wishlist.setTitle("Sample Wishlist");
        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setName("Sample Item");
        wishlistItem.setDescription("Sample Description");
        wishlistItem.setItemUrl("http://example.com/item");
        wishlistItem.setImageUrl("http://example.com/image.jpg");
        wishlistItem.setWishlist(wishlist);

        WishlistItem savedItem = wishlistItemRepository.save(wishlistItem);
        WishlistItem retrievedItem = wishlistItemRepository.findById(savedItem.getId()).orElse(null);


        assertNotNull(retrievedItem);
        assertEquals("Sample Item", retrievedItem.getName());
        assertEquals("Sample Description", retrievedItem.getDescription());
        assertEquals("http://example.com/item", retrievedItem.getItemUrl());
        assertEquals("http://example.com/image.jpg", retrievedItem.getImageUrl());

        assertNotNull(retrievedItem.getWishlist());
        assertEquals("Sample Wishlist", retrievedItem.getWishlist().getTitle());
    }
}