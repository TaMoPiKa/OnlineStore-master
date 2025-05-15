package com.app.onlinestorebackend.repos;

import com.app.onlinestorebackend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface OrderRepo extends JpaRepository<Order, String> {


    public Order findOrderByOrderNumberIs(Long orderNumber);


}
