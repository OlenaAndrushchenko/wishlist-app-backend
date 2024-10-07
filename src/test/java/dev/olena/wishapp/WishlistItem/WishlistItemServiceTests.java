package dev.olena.wishapp.WishlistItem;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import dev.olena.wishapp.wishlist.Wishlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WishlistItemServiceTests {

    @Mock
    private WishlistItemRepository wishlistItemRepository;

    @InjectMocks
    private WishlistItemService wishlistItemService;

    private WishlistItem wishlistItem;
    private WishlistItemDTO wishlistItemDTO;
    private Wishlist wishlist;

    @BeforeEach
    void setUp() {

        wishlist = new Wishlist(1L);

        wishlistItem = new WishlistItem();
        wishlistItem.setId(1L);
        wishlistItem.setName("Sample Item");
        wishlistItem.setDescription("Sample Description");
        wishlistItem.setItemUrl("http://example.com/item");
        wishlistItem.setImageUrl("http://example.com/image.jpg");
        wishlistItem.setWishlist(wishlist);

        wishlistItemDTO = new WishlistItemDTO(wishlistItem);
    }

    @Test
    void testCreateWishlistItem() {

        when(wishlistItemRepository.save(any(WishlistItem.class))).thenReturn(wishlistItem);


        WishlistItemDTO result = wishlistItemService.createWishlistItem(wishlistItemDTO);

        assertEquals(wishlistItemDTO.getName(), result.getName());
        assertEquals(wishlistItemDTO.getWishlistId(), result.getWishlistId());

        verify(wishlistItemRepository, times(1)).save(any(WishlistItem.class));
    }

    @Test
    void testReadWishlistItem() {

        when(wishlistItemRepository.findById(1L)).thenReturn(Optional.of(wishlistItem));

        WishlistItemDTO result = wishlistItemService.readWishlistItem(1L);

        assertEquals(wishlistItemDTO.getName(), result.getName());
        assertEquals(wishlistItemDTO.getId(), result.getId());

        verify(wishlistItemRepository, times(1)).findById(1L);
    }

    @Test
    void testReadWishlistItem_NotFound() {

        when(wishlistItemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            wishlistItemService.readWishlistItem(1L);
        });

        verify(wishlistItemRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateWishlistItem() {

        when(wishlistItemRepository.findById(1L)).thenReturn(Optional.of(wishlistItem));
        when(wishlistItemRepository.save(any(WishlistItem.class))).thenReturn(wishlistItem);

        wishlistItemDTO.setName("Updated Item");
        wishlistItemDTO.setDescription("Updated Description");

        WishlistItemDTO result = wishlistItemService.updateWishlistItem(1L, wishlistItemDTO);

        assertNotNull(result);
        assertEquals("Updated Item", result.getName());
        assertEquals("Updated Description", result.getDescription());

        verify(wishlistItemRepository, times(1)).findById(1L);
        verify(wishlistItemRepository, times(1)).save(any(WishlistItem.class));
    }

    @Test
    void testUpdateWishlistItem_NotFound() {
        when(wishlistItemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            wishlistItemService.updateWishlistItem(1L, wishlistItemDTO);
        });

        verify(wishlistItemRepository, times(1)).findById(1L);
        verify(wishlistItemRepository, never()).save(any(WishlistItem.class));
    }

    @Test
    void testDeleteWishlistItem() {

        when(wishlistItemRepository.findById(1L)).thenReturn(Optional.of(wishlistItem));
        doNothing().when(wishlistItemRepository).delete(wishlistItem);

        wishlistItemService.deleteWishlistItem(1L);

        verify(wishlistItemRepository, times(1)).findById(1L);
        verify(wishlistItemRepository, times(1)).delete(wishlistItem);
    }

    @Test
    void testDeleteWishlistItem_NotFound() {

        when(wishlistItemRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> {
            wishlistItemService.deleteWishlistItem(1L);
        });

        verify(wishlistItemRepository, times(1)).findById(1L);
        verify(wishlistItemRepository, never()).delete(any(WishlistItem.class));
    }

    @Test
    void testGetItemsByWishlist() {

        when(wishlistItemRepository.findAllByWishlistId(1L)).thenReturn(Arrays.asList(wishlistItem));
        List<WishlistItemDTO> result = wishlistItemService.getItemsByWishlist(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(wishlistItemDTO.getName(), result.get(0).getName());

        verify(wishlistItemRepository, times(1)).findAllByWishlistId(1L);
    }

    @Test
    void testGetItemsByWishlist_EmptyList() {

        when(wishlistItemRepository.findAllByWishlistId(1L)).thenReturn(Arrays.asList());
        List<WishlistItemDTO> result = wishlistItemService.getItemsByWishlist(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(wishlistItemRepository, times(1)).findAllByWishlistId(1L);
    }
}
