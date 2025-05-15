package com.app.onlinestorebackend.repos;


import com.app.onlinestorebackend.entities.Item;
import com.app.onlinestorebackend.entities.Order;
import com.app.onlinestorebackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<User, String> {

    User findUserByUsernameIsAndPasswordIs(String username, String password);

    User findUserByUsernameIs(String userName);

    List<Item> getUserCartByUsernameIs(String username);

    List<Order> getUserOrdersHistoryByUsernameIs(String username);

}
