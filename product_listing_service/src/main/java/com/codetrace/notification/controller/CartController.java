package com.codetrace.notification.controller;

import com.codetrace.notification.domain.Cart;
import com.codetrace.notification.domain.Product;
import com.codetrace.notification.service.CartService;
import com.codetrace.notification.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @GetMapping("/{username}")
    public Cart getCart(@PathVariable String username) {
        return cartService.getCart(username);
    }

    @PostMapping("/add/{username}/{productId}")
    public String addProductToCart(@PathVariable String username, @PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return "Product not found";
        }
        cartService.addProductToCart(username, product);
        return "Product added to cart";
    }

    @GetMapping("/user/{usernameOrEmail}")
    public Object getUserDetails(@PathVariable String usernameOrEmail) {
        return cartService.fetchUserDetails(usernameOrEmail);
    }
}
