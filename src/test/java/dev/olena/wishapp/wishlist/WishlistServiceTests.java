package dev.olena.wishapp.wishlist;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import dev.olena.wishapp.WishlistItem.WishlistItemService;
import dev.olena.wishapp.user.User;

class WishlistServiceTests {

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private WishlistItemService wishlistItemService;

    @InjectMocks
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenCreateWishlist_thenSaveWishlist() {
        User user = new User();
        user.setId(1L);
        user.setUserIdentifier("user123");

        WishlistDTO inputDTO = new WishlistDTO(null, "My Wishlist", "Description", true, null, null, null, null, null);
        Wishlist wishlist = new Wishlist(null, "My Wishlist", "Description", true, "user123/" + UUID.randomUUID().toString(), user, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);

        WishlistDTO resultDTO = wishlistService.createWishlist(inputDTO, user);

        assertThat(resultDTO.getTitle()).isEqualTo(inputDTO.getTitle());
        assertThat(resultDTO.getDescription()).isEqualTo(inputDTO.getDescription());
        assertThat(resultDTO.isShared()).isEqualTo(true);
        verify(wishlistRepository).save(any(Wishlist.class));
    }

    @Test
    void whenReadWishlist_thenReturnWishlistDTO() {
        User user = new User();
        user.setId(1L);
        user.setUserIdentifier("user123");

        Wishlist wishlist = new Wishlist(1L, "My Wishlist", "Description", true, "user123/uuid", user, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        when(wishlistRepository.findById(1L)).thenReturn(Optional.of(wishlist));

        WishlistDTO resultDTO = wishlistService.readWishlist(1L, user);

        assertThat(resultDTO.getId()).isEqualTo(wishlist.getId());
        assertThat(resultDTO.getUserIdentifier()).isEqualTo(user.getUserIdentifier());
    }

    @Test
    void whenUpdateWishlist_thenUpdateFields() {
        User user = new User();
        user.setId(1L);
        user.setUserIdentifier("user123");

        Wishlist existingWishlist = new Wishlist(1L, "Old Title", "Old Description", false, "user123/uuid", user, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        when(wishlistRepository.findById(1L)).thenReturn(Optional.of(existingWishlist));

        WishlistDTO updatedDTO = new WishlistDTO(1L, "New Title", "New Description", true, "user123/uuid", null, null, LocalDateTime.now(), LocalDateTime.now());
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(existingWishlist);

        WishlistDTO resultDTO = wishlistService.updateWishlist(1L, updatedDTO, user);

        assertThat(resultDTO.getTitle()).isEqualTo("New Title");
        assertThat(resultDTO.getDescription()).isEqualTo("New Description");
        assertThat(resultDTO.isShared()).isTrue();
        verify(wishlistRepository).save(existingWishlist);
    }

    @Test
    void whenDeleteWishlist_thenPerformDelete() {
        User user = new User();
        user.setId(1L);
        user.setUserIdentifier("user123");

        Wishlist wishlist = new Wishlist(1L, "My Wishlist", "Description", true, "url", user, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        when(wishlistRepository.findById(1L)).thenReturn(Optional.of(wishlist));

        wishlistService.deleteWishlist(1L, user);
        verify(wishlistRepository).delete(wishlist);
    }

    @Test
    void whenReadWishlists_thenReturnListOfWishlistDTOs() {
        User user = new User();
        user.setId(1L);
        user.setUserIdentifier("user123");

        List<Wishlist> wishlists = List.of(
            new Wishlist(1L, "Wishlist 1", "Description 1", true, "url1", user, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now()),
            new Wishlist(2L, "Wishlist 2", "Description 2", true, "url2", user, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now())
        );
        when(wishlistRepository.findAllByUser(user)).thenReturn(wishlists);

        List<WishlistDTO> results = wishlistService.readWishlists(user);

        assertThat(results).hasSize(2);
        assertThat(results.get(0).getTitle()).isEqualTo("Wishlist 1");
        assertThat(results.get(1).getTitle()).isEqualTo("Wishlist 2");
    }

    @Test
    void whenReadSharedWishlist_thenReturnWishlistDTO() {
        User user = new User();
        user.setId(1L);
        user.setUserIdentifier("user123");
        String shareableUrl = "uuid";

        Wishlist wishlist = new Wishlist(1L, "My Wishlist", "Description", true, "user123/uuid", user, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        when(wishlistRepository.findByUser_userIdentifierAndShareableUrl("user123", "user123/uuid")).thenReturn(Optional.of(wishlist));

        WishlistDTO result = wishlistService.readSharedWishlist("user123", shareableUrl);

        assertThat(result.getTitle()).isEqualTo("My Wishlist");
        assertThat(result.isShared()).isTrue();
    }
}