package dev.olena.wishapp.WishlistItem;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.olena.wishapp.firebase.FirebaseStorageService;

@RestController
@RequestMapping("/api/v1/wishlist-items")
@Profile("!test")
public class WishlistItemController {
    
    private final WishlistItemService wishlistItemService;
    private final FirebaseStorageService firebaseStorageService;
    private final ObjectMapper objectMapper;

    @Autowired
    public WishlistItemController(WishlistItemService wishlistItemService, FirebaseStorageService firebaseStorageService, ObjectMapper objectMapper) {
        this.wishlistItemService = wishlistItemService;
        this.firebaseStorageService = firebaseStorageService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createWishlistItem(@RequestParam(value = "file", required = false) MultipartFile file,  @RequestParam("data") String wishlistItemDTOJson) {
        try {
            WishlistItemDTO wishlistItemDTO = objectMapper.readValue(wishlistItemDTOJson, WishlistItemDTO.class);

            if (file != null && !file.isEmpty()) {
                String imageUrl = firebaseStorageService.uploadFile(file);
                wishlistItemDTO.setImageUrl(imageUrl);
            } else {
                wishlistItemDTO.setImageUrl(null);
            }

            WishlistItemDTO result = wishlistItemService.createWishlistItem(wishlistItemDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload image or parse data");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishlistItemDTO> readWishlistItem(@PathVariable Long id) {
        WishlistItemDTO result = wishlistItemService.readWishlistItem(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWishlistItem(@PathVariable Long id, @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam("data") String wishlistItemDTOJson) {
        try {
            WishlistItemDTO wishlistItemDTO = objectMapper.readValue(wishlistItemDTOJson, WishlistItemDTO.class);

            if (file != null && !file.isEmpty()) {
                String imageUrl = firebaseStorageService.uploadFile(file);
                wishlistItemDTO.setImageUrl(imageUrl);
            }

            WishlistItemDTO result = wishlistItemService.updateWishlistItem(id, wishlistItemDTO);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload image or parse data");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlistItem(@PathVariable Long id) {
        wishlistItemService.deleteWishlistItem(id);
        return ResponseEntity.noContent().build();
    }
}
