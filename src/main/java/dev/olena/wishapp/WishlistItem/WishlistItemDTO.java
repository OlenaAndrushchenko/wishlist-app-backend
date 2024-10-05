package dev.olena.wishapp.WishlistItem;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WishlistItemDTO {
    
    private Long id;
    private String name;
    private String description;
    private String itemUrl;
    private String imageUrl;
    private Long wishlistId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WishlistItemDTO(WishlistItem wishlistItem) {
        this.id = wishlistItem.getId();
        this.name = wishlistItem.getName();
        this.description = wishlistItem.getDescription();
        this.itemUrl = wishlistItem.getItemUrl();
        this.imageUrl = wishlistItem.getImageUrl();
        this.wishlistId = wishlistItem.getWishlist().getId();
        this.createdAt = wishlistItem.getCreatedAt();
        this.updatedAt = wishlistItem.getUpdatedAt();
    }
}
