
package com.alten.productbackend.controller;

import com.alten.productbackend.model.CartItem;
import com.alten.productbackend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public List<CartItem> getCartItems(Authentication authentication) {
        return cartService.getCartItemsByUser(authentication.getName());
    }

    @PostMapping
    public ResponseEntity<CartItem> addProductToCart(Authentication authentication, @RequestParam Long productId, @RequestParam Integer quantity) {
        try {
            CartItem cartItem = cartService.addProductToCart(authentication.getName(), productId, quantity);
            return ResponseEntity.ok(cartItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long cartItemId) {
        cartService.removeProductFromCart(cartItemId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemQuantity(@PathVariable Long cartItemId, @RequestParam Integer quantity) {
        try {
            CartItem updatedCartItem = cartService.updateCartItemQuantity(cartItemId, quantity);
            return ResponseEntity.ok(updatedCartItem);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


