
package com.alten.productbackend.controller;

import com.alten.productbackend.model.WishlistItem;
import com.alten.productbackend.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping
    public List<WishlistItem> getWishlistItems(Authentication authentication) {
        return wishlistService.getWishlistItemsByUser(authentication.getName());
    }

    @PostMapping
    public ResponseEntity<WishlistItem> addProductToWishlist(Authentication authentication, @RequestParam Long productId) {
        try {
            WishlistItem wishlistItem = wishlistService.addProductToWishlist(authentication.getName(), productId);
            return ResponseEntity.ok(wishlistItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{wishlistItemId}")
    public ResponseEntity<Void> removeProductFromWishlist(@PathVariable Long wishlistItemId) {
        wishlistService.removeProductFromWishlist(wishlistItemId);
        return ResponseEntity.noContent().build();
    }
}


