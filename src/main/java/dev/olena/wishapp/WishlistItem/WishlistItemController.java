package dev.olena.wishapp.WishlistItem;

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

@RestController
@RequestMapping("/api/v1/wishlist-items")
public class WishlistItemController {
    
    private final WishlistItemService wishlistItemService;

    @Autowired
    public WishlistItemController(WishlistItemService wishlistItemService) {
        this.wishlistItemService = wishlistItemService;
    }

    @PostMapping("/create")
    public ResponseEntity<WishlistItemDTO> createWishlistItem(@RequestBody WishlistItemDTO wishlistItemDTO) {
        WishlistItemDTO result = wishlistItemService.createWishlistItem(wishlistItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishlistItemDTO> readWishlistItem(@PathVariable Long id) {
        WishlistItemDTO result = wishlistItemService.readWishlistItem(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WishlistItemDTO> updateWishlistItem(@PathVariable Long id, @RequestBody WishlistItemDTO wishlistItemDTO) {
        WishlistItemDTO result = wishlistItemService.updateWishlistItem(id, wishlistItemDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlistItem(@PathVariable Long id) {
        wishlistItemService.deleteWishlistItem(id);
        return ResponseEntity.noContent().build();
    }
}
