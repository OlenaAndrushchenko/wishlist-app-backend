package dev.olena.wishapp.wishlist;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.olena.wishapp.user.User;

@Service
public class WishlistService {
    
    @Autowired
    private WishlistRepository wishlistRepository;

    public WishlistDTO createWishlist(WishlistDTO wishlistDTO, User user) {
        
        Wishlist wishlist = new Wishlist();
        
        wishlist.setTitle(wishlistDTO.getTitle());
        wishlist.setDescription(wishlistDTO.getDescription());
        wishlist.setShared(wishlistDTO.isShared());
        wishlist.setUser(user);
        
        String wishlistUuid = UUID.randomUUID().toString();
        wishlist.setShareableUrl(user.getUserIdentifier() + "/" + wishlistUuid);

        wishlist = wishlistRepository.save(wishlist);
        return new WishlistDTO(wishlist);
    }

    public WishlistDTO readWishlist(Long id, User user) {

        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("Wishlist not found"));

        if (!wishlist.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("User is not authorized to read this wishlist");
        }

        return new WishlistDTO(wishlist);
    }

    public WishlistDTO updateWishlist(Long id, WishlistDTO wishlistDTO, User user) {
        
        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("Wishlist not found"));

        if (!wishlist.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("User is not authorized to update this wishlist");
        }

        wishlist.setTitle(wishlistDTO.getTitle());
        wishlist.setDescription(wishlistDTO.getDescription());
        wishlist.setShared(wishlistDTO.isShared());

        wishlist = wishlistRepository.save(wishlist);
        return new WishlistDTO(wishlist);
    }

    public void deleteWishlist(Long id, User user) {

        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("Wishlist not found"));
        if (!wishlist.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("User is not authorized to delete this wishlist");
        }

        wishlistRepository.delete(wishlist);
    }

    public List<WishlistDTO> readWishlists(User user) {
        List<Wishlist> wishlists = wishlistRepository.findAllByUser(user);
        return wishlists.stream().map(WishlistDTO::new).collect(Collectors.toList());
    }

    public WishlistDTO readSharedWishlist(String userIdentifier, String shareableUrl) {
        String path = userIdentifier + "/" + shareableUrl;
        Wishlist wishlist = wishlistRepository.findByUser_userIdentifierAndShareableUrl(userIdentifier, path).orElseThrow( () -> new IllegalArgumentException("Wishlist not found"));
        return new WishlistDTO(wishlist);
    }
}
