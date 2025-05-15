package com.app.onlinestorebackend.controllers;


import com.app.onlinestorebackend.DTOs.GeneralResponse;
import com.app.onlinestorebackend.DTOs.GeneralResponseBody;
import com.app.onlinestorebackend.entities.Item;
import com.app.onlinestorebackend.services.ItemsServices;
import com.app.onlinestorebackend.services.OrdersServices;
import com.app.onlinestorebackend.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemsServices itemsServices;
    @Autowired
    private UserServices userServices;
    @Autowired
    private OrdersServices ordersServices;


    @PostMapping("/addnew/{username}/{password}")
    @ResponseBody
    private GeneralResponse addNewItemToInventory(@RequestBody Item item,
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

        return itemsServices.addItemToInventory(item);
    }


    @GetMapping("/itemdetails/{itemId}/{username}/{password}")
    @ResponseBody
    private GeneralResponse viewItemDetails(@PathVariable String itemId,
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

        return itemsServices.viewItemDetails(itemId);
    }

    @GetMapping("/AllItems")
    @ResponseBody
    private GeneralResponse viewAllItems(){

        return itemsServices.viewAllItems();
    }


    @DeleteMapping("/delete/{itemId}/{username}/{password}")
    @ResponseBody
    private GeneralResponse deleteItem(@PathVariable String itemId,
                                       @PathVariable String username,
                                       @PathVariable String password){

        if(!userServices.usernameAndPasswordChecker(username, password)){
            if (userServices.getUserByUserName(username).getIsAdmin() != 'Y') {
                return new GeneralResponse(
                        HttpStatus.UNAUTHORIZED,
                        "Wrong Security Credentials",
                        LocalDate.now(),
                        LocalTime.now(),
                        new GeneralResponseBody(null)
                );
            }
        }
        return itemsServices.deleteItemById(itemId);
    }


    @GetMapping("/AllOrders/{username}/{password}")
    @ResponseBody
    private GeneralResponse viewOrders(
                                       @PathVariable String username,
                                       @PathVariable String password){
        if(!userServices.usernameAndPasswordChecker(username, password)){
            if (userServices.getUserByUserName(username).getIsAdmin() != 'Y') {
                return new GeneralResponse(
                        HttpStatus.UNAUTHORIZED,
                        "Wrong Security Credentials",
                        LocalDate.now(),
                        LocalTime.now(),
                        new GeneralResponseBody(null)
                );
            }
        }

        return ordersServices.getAllOrders();
    }

}
