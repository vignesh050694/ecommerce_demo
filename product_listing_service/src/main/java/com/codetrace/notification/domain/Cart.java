package com.codetrace.notification.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String username;
    private List<CartItem> items = new ArrayList<>();

    public Cart() {}

    public Cart(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void addProduct(Product product) {
        items.add(new CartItem(product, LocalDateTime.now()));
    }

    public static class CartItem {
        private Product product;
        private LocalDateTime addedAt;

        public CartItem(Product product, LocalDateTime addedAt) {
            this.product = product;
            this.addedAt = addedAt;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public LocalDateTime getAddedAt() {
            return addedAt;
        }

        public void setAddedAt(LocalDateTime addedAt) {
            this.addedAt = addedAt;
        }
    }
}
