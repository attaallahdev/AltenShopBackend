
package com.alten.productbackend.service;

import com.alten.productbackend.model.Product;
import com.alten.productbackend.model.User;
import com.alten.productbackend.model.WishlistItem;
import com.alten.productbackend.repository.ProductRepository;
import com.alten.productbackend.repository.UserRepository;
import com.alten.productbackend.repository.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    @Autowired
    private WishlistItemRepository wishlistItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public List<WishlistItem> getWishlistItemsByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        return wishlistItemRepository.findByUser(user);
    }

    public WishlistItem addProductToWishlist(String userEmail, Long productId) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<WishlistItem> existingWishlistItem = wishlistItemRepository.findByUser(user).stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingWishlistItem.isPresent()) {
            return existingWishlistItem.get(); // Product already in wishlist
        } else {
            WishlistItem wishlistItem = new WishlistItem();
            wishlistItem.setProduct(product);
            wishlistItem.setUser(user);
            return wishlistItemRepository.save(wishlistItem);
        }
    }

    public void removeProductFromWishlist(Long wishlistItemId) {
        wishlistItemRepository.deleteById(wishlistItemId);
    }
}


