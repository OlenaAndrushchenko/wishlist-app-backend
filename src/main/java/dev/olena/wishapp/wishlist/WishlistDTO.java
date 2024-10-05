package dev.olena.wishapp.wishlist;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import dev.olena.wishapp.WishlistItem.WishlistItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WishlistDTO {
    
    private Long id;
    private String title;
    private String description;
    private boolean shared;
    private String shareableUrl;
    private String userIdentifier;
    private List<WishlistItemDTO> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WishlistDTO(Wishlist wishlist) {
        this.id = wishlist.getId();
        this.title = wishlist.getTitle();
        this.description = wishlist.getDescription();
        this.shared = wishlist.isShared();
        this.shareableUrl = wishlist.getShareableUrl();
        this.userIdentifier = wishlist.getUser().getUserIdentifier();
        this.createdAt = wishlist.getCreatedAt();
        this.updatedAt = wishlist.getUpdatedAt();
        this.items = wishlist.getItems().isEmpty() ? null : 
                    wishlist.getItems().stream()
                            .map(WishlistItemDTO::new)
                            .collect(Collectors.toList());
    }
}
