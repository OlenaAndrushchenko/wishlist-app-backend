package dev.olena.wishapp.WishlistItem;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.olena.wishapp.wishlist.Wishlist;

@Service
public class WishlistItemService {
    
    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    public WishlistItemDTO createWishlistItem(WishlistItemDTO wishlistItemDTO) {
        
        WishlistItem wishlistItem = new WishlistItem();
        
        wishlistItem.setName(wishlistItemDTO.getName());
        wishlistItem.setDescription(wishlistItemDTO.getDescription());
        wishlistItem.setItemUrl(wishlistItemDTO.getItemUrl());
        wishlistItem.setImageUrl(wishlistItemDTO.getImageUrl());

        Wishlist wishlist = new Wishlist(wishlistItemDTO.getWishlistId());
        wishlistItem.setWishlist(wishlist);

        wishlistItem = wishlistItemRepository.save(wishlistItem);
        return new WishlistItemDTO(wishlistItem);

    }

    public WishlistItemDTO readWishlistItem(Long id) {

        WishlistItem item = wishlistItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Wishlist item not found"));

        return new WishlistItemDTO(item);
    }

    @Transactional
    public WishlistItemDTO updateWishlistItem(Long id, WishlistItemDTO wishlistItemDTO) {
        WishlistItem item = wishlistItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Wishlist item not found"));

        item.setName(wishlistItemDTO.getName());
        item.setDescription(wishlistItemDTO.getDescription());
        item.setItemUrl(wishlistItemDTO.getItemUrl());
        if (wishlistItemDTO.getImageUrl() != null && !wishlistItemDTO.getImageUrl().isEmpty()) {
            item.setImageUrl(wishlistItemDTO.getImageUrl());
        }

        return new WishlistItemDTO(wishlistItemRepository.save(item));
    }

    public void deleteWishlistItem(Long id) {
        WishlistItem item = wishlistItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Wishlist item not found"));
        wishlistItemRepository.delete(item);
    }

    public List<WishlistItemDTO> getItemsByWishlist(Long wishlistId) {

        List<WishlistItem> items = wishlistItemRepository.findAllByWishlistId(wishlistId);
        return items.stream().map(WishlistItemDTO::new).collect(Collectors.toList());
    }
}
