package com.app.onlinestorebackend.services;


import com.app.onlinestorebackend.DTOs.GeneralResponse;
import com.app.onlinestorebackend.DTOs.GeneralResponseBody;
import com.app.onlinestorebackend.DTOs.Requests.PlaceNewOrderRequest;
import com.app.onlinestorebackend.entities.CartItem;
import com.app.onlinestorebackend.entities.Item;
import com.app.onlinestorebackend.entities.Order;
import com.app.onlinestorebackend.entities.User;
import com.app.onlinestorebackend.repos.ItemRepo;
import com.app.onlinestorebackend.repos.OrderRepo;
import com.app.onlinestorebackend.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersServices {

    private final OrderRepo orderRepo;
    private final ItemRepo itemRepo;
    private final UserRepo userRepo;


    public GeneralResponse getOrderByOrderNumber(Long orderNumber, String userId) {

        Order order = orderRepo.findOrderByOrderNumberIs(orderNumber);

        try {
            if (order.getBuyer().getUsername().equals(userId)) {
                return new GeneralResponse(
                        HttpStatus.FOUND,
                        "Order Found!",
                        LocalDate.now(),
                        LocalTime.now(),
                        new GeneralResponseBody<>(order)
                );
            } else {
                return new GeneralResponse(
                        HttpStatus.NOT_ACCEPTABLE,
                        "This Order Doesn't Belong to This User!",
                        LocalDate.now(),
                        LocalTime.now(),
                        new GeneralResponseBody<>(null)
                );
            }
        } catch (NullPointerException e) {
            return new GeneralResponse(
                    HttpStatus.NOT_FOUND,
                    "Order Not Found!",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(null)
            );
        }

    }


    public GeneralResponse placeAnOrder(PlaceNewOrderRequest newOrderRequest) {

        User user = userRepo.findUserByUsernameIs(newOrderRequest.getBuyerId());
        List<CartItem> cart = user.getCart();
        Item item;
        double total = 0;

        for (CartItem cartItem : cart) {
            if (cartItem.getQuantity() > itemRepo.findItemByIdIs(cartItem.getId()).getQuantity()) {
                return new GeneralResponse(
                        HttpStatus.BAD_REQUEST,
                        "One or More Items inn the cart exceeded the limit we have in the inventory",
                        LocalDate.now(),
                        LocalTime.now(),
                        new GeneralResponseBody<>(null)
                );
            }
            total = total + (cartItem.getUnitPrice() * cartItem.getQuantity());


            item = itemRepo.findItemByIdIs(cartItem.getId());
            item.setQuantity(item.getQuantity() - cartItem.getQuantity());
            itemRepo.save(item);
        }
        user.setCart(new ArrayList<>());
        userRepo.save(user);

        return new GeneralResponse(
                HttpStatus.ACCEPTED,
                "Order Placed Successfully",
                LocalDate.now(),
                LocalTime.now(),
                new GeneralResponseBody(orderRepo.save(new Order(
                        user,
                        cart,
                        LocalDate.now(),
                        LocalTime.now(),
                        total,
                        "Accepted",
                        newOrderRequest.getPaymentMethod()
                )))
        );

    }


    public GeneralResponse getAllOrders(){
        return new GeneralResponse(
                HttpStatus.FOUND,
                "Order Found!",
                LocalDate.now(),
                LocalTime.now(),
                new GeneralResponseBody<>(orderRepo.findAll())
        );
    }


}























