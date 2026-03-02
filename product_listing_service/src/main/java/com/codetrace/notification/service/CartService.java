package com.codetrace.notification.service;

import com.codetrace.notification.domain.Cart;
import com.codetrace.notification.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartService {
    private final Map<String, Cart> userCarts = new ConcurrentHashMap<>();
    private final RestTemplate restTemplate;
    private final String authServiceBaseUrl;

    @Autowired
    public CartService(RestTemplate restTemplate, @Value("${auth.service.base-url}") String authServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.authServiceBaseUrl = authServiceBaseUrl;
    }

    public Cart getCart(String username) {
        return userCarts.computeIfAbsent(username, Cart::new);
    }

    public void addProductToCart(String username, Product product) {
        Cart cart = getCart(username);
        cart.addProduct(product);
    }

    public Object fetchUserDetails(String usernameOrEmail) {
        String url = authServiceBaseUrl + "/api/auth/user?usernameOrEmail=" + usernameOrEmail;
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
        return response.getBody();
    }
}
