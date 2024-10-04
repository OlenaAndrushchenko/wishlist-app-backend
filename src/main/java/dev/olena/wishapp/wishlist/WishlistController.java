package dev.olena.wishapp.wishlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.olena.wishapp.user.User;
import dev.olena.wishapp.user.UserService;

@RestController
@RequestMapping("/api/v1/wishlists")
public class WishlistController {
    
    private final WishlistService wishlistService;
    private final UserService userService;

    @Autowired
    public WishlistController(WishlistService wishlistService, UserService userService) {
        this.wishlistService = wishlistService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<WishlistDTO> createWishlist(@RequestBody WishlistDTO wishlistDTO) {
        User user = userService.getCurrentUser();
        WishlistDTO result = wishlistService.createWishlist(wishlistDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishlistDTO> readWishlist(@PathVariable Long id) {
        User user = userService.getCurrentUser();
        WishlistDTO result = wishlistService.readWishlist(id, user);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WishlistDTO> updateWishlist(@PathVariable Long id, @RequestBody WishlistDTO wishlistDTO) {
        User user = userService.getCurrentUser();
        WishlistDTO result = wishlistService.updateWishlist(id, wishlistDTO, user);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long id) {
        User user = userService.getCurrentUser();
        wishlistService.deleteWishlist(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<WishlistDTO>> readWishlists() {
        User user = userService.getCurrentUser();
        List<WishlistDTO> result = wishlistService.readWishlists(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/shared/{userIdentifier}/{shareableUrl}")
    public ResponseEntity<WishlistDTO> getPublicWishlist(@PathVariable String userIdentifier, @PathVariable String shareableUrl) {
        WishlistDTO result = wishlistService.readSharedWishlist(userIdentifier, shareableUrl);
        if (result != null && result.isShared()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

}
