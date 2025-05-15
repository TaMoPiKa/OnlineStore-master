package com.app.onlinestorebackend.repos;

import com.app.onlinestorebackend.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,String> {
}
