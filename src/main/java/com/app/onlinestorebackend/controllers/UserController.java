package com.app.onlinestorebackend.controllers;


import com.app.onlinestorebackend.DTOs.GeneralResponse;
import com.app.onlinestorebackend.DTOs.GeneralResponseBody;
import com.app.onlinestorebackend.DTOs.Requests.AddNewItemToCartRequest;
import com.app.onlinestorebackend.DTOs.Requests.PlaceNewOrderRequest;
import com.app.onlinestorebackend.entities.User;
import com.app.onlinestorebackend.services.OrdersServices;
import com.app.onlinestorebackend.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {



    @Autowired
    private UserServices userServices;
    @Autowired
    private OrdersServices ordersServices;



    @GetMapping("/login/{username}/{password}")
    @ResponseBody
    private GeneralResponse userLoginTo(@PathVariable String username, @PathVariable String password){
        return userServices.login(username, password);
    }


    @PostMapping("/add")
    @ResponseBody
    private GeneralResponse addNewUser(@RequestBody User user){
        return userServices.addNewUser(user);
    }




    @GetMapping("/cart/{username}/{password}")
    @ResponseBody
    private GeneralResponse getUserCart(@PathVariable String username,
                                              @PathVariable String password){

        if(!userServices.usernameAndPasswordChecker(username, password)){
            return new GeneralResponse(
                    HttpStatus.UNAUTHORIZED,
                    "Wrong Security Credentials",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody(null)
            );
        }
        return userServices.getCartByUsername(username);
    }


    @GetMapping("/vieworders/{username}/{password}")
    @ResponseBody
    private GeneralResponse getUserOrders(@PathVariable String username,
                                        @PathVariable String password){

        if(!userServices.usernameAndPasswordChecker(username, password)){
            return new GeneralResponse(
                    HttpStatus.UNAUTHORIZED,
                    "Wrong Security Credentials",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody(null)
            );
        }
        return userServices.getOrdersHistoryOfAUser(username);
    }


    @GetMapping("/order/{orderId}/{username}/{password}")
    @ResponseBody
    private GeneralResponse getOrder(@PathVariable Long orderId,
                                          @PathVariable String username,
                                          @PathVariable String password){

        if(!userServices.usernameAndPasswordChecker(username, password)){
            return new GeneralResponse(
                    HttpStatus.UNAUTHORIZED,
                    "Wrong Security Credentials",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody(null)
            );
        }
        return ordersServices.getOrderByOrderNumber(orderId, username);
    }


    @GetMapping("/order/new/{username}/{password}")
    @ResponseBody
    private GeneralResponse placeNewOrder(@RequestBody PlaceNewOrderRequest newOrderRequest,
                                          @PathVariable String username,
                                          @PathVariable String password){

        if(!userServices.usernameAndPasswordChecker(username, password)){
            return new GeneralResponse(
                    HttpStatus.UNAUTHORIZED,
                    "Wrong Security Credentials",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody(null)
            );
        }
        return ordersServices.placeAnOrder(newOrderRequest);
    }

    @DeleteMapping("/cart/remove/{itemId}/{username}/{password}")
    @ResponseBody
    private GeneralResponse removeItemFromCart(@PathVariable String itemId,
                                          @PathVariable String username,
                                          @PathVariable String password){

        if(!userServices.usernameAndPasswordChecker(username, password)){
            return new GeneralResponse(
                    HttpStatus.UNAUTHORIZED,
                    "Wrong Security Credentials",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody(null)
            );
        }
        return userServices.removeItemFromCart(username, itemId);
    }



    @PostMapping("/cart/add/{username}/{password}")
    @ResponseBody
    private GeneralResponse addItemToCart(@RequestBody AddNewItemToCartRequest itemToCartRequest,
                                          @PathVariable String username,
                                          @PathVariable String password){

        if(!userServices.usernameAndPasswordChecker(username, password)){
            return new GeneralResponse(
                    HttpStatus.UNAUTHORIZED,
                    "Wrong Security Credentials",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody(null)
            );
        }
        return userServices.addItemToCart(itemToCartRequest);
    }



}
