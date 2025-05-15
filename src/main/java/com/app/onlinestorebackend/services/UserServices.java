package com.app.onlinestorebackend.services;



import com.app.onlinestorebackend.DTOs.GeneralResponse;
import com.app.onlinestorebackend.DTOs.GeneralResponseBody;
import com.app.onlinestorebackend.DTOs.Requests.AddNewItemToCartRequest;
import com.app.onlinestorebackend.entities.CartItem;
import com.app.onlinestorebackend.entities.Item;
import com.app.onlinestorebackend.entities.Order;
import com.app.onlinestorebackend.entities.User;
import com.app.onlinestorebackend.repos.CartItemRepo;
import com.app.onlinestorebackend.repos.ItemRepo;
import com.app.onlinestorebackend.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class UserServices {
    private final CartItemRepo cartItemRepo;
    private final UserRepo userRepo;
    private final ItemRepo itemRepo;

    public GeneralResponse login(String username, String password){

        User userFound = userRepo.findUserByUsernameIsAndPasswordIs(username, password);

        if(userFound != null){
            return new GeneralResponse(
                    HttpStatus.FOUND,
                    "User logged in",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(userFound)
            );
        }else{
            return new GeneralResponse(
                    HttpStatus.NOT_FOUND,
                    "Wrong username or password",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(userFound)
            );
        }

    }

    public boolean usernameAndPasswordChecker(String username, String password){

        return userRepo.findUserByUsernameIsAndPasswordIs(username, password) != null;

    }



    public boolean userExists(String username){

        return userRepo.findUserByUsernameIs(username) != null;
    }


    public User getUserByUserName(String username){
        return userRepo.findUserByUsernameIs(username);
    }


//    public User updateUser(User user){
//        return userRepo.save(user);
//    }

    public GeneralResponse addNewUser(User user) {
        if(userExists(user.getUsername())){
            return new GeneralResponse(
                    HttpStatus.BAD_REQUEST,
                    "Username taken!",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(null)
            );
        }else{
            return new GeneralResponse(
                    HttpStatus.ACCEPTED,
                    "User Added!",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(userRepo.save(user))
            );
        }
    }


    public GeneralResponse getOrdersHistoryOfAUser(String username){

        return new GeneralResponse(
                HttpStatus.FOUND,
                "User Orders History found!",
                LocalDate.now(),
                LocalTime.now(),
                new GeneralResponseBody<>(userRepo.getUserOrdersHistoryByUsernameIs(username))
        );

    }


    public GeneralResponse getCartByUsername(String username){
        return new GeneralResponse(
                HttpStatus.FOUND,
                "User Cart Found!",
                LocalDate.now(),
                LocalTime.now(),
                new GeneralResponseBody<>(userRepo.findUserByUsernameIs(username).getCart())
        );
    }



    public GeneralResponse removeItemFromCart(String username, String itemId){

        try {
            User user = userRepo.findUserByUsernameIs(username);
            List<CartItem> cart = user.getCart();

            for(int i = 0; i < cart.size(); i++){
                if(cart.get(i).getId().equals(itemId)){
                    cart.remove(i);
                    break;
                }
            }

            user.setCart(cart);

            return new GeneralResponse(
                    HttpStatus.ACCEPTED,
                    "Item Removed If Existed",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(userRepo.save(user))
            );
        }catch (Exception e){
            return new GeneralResponse(
                    HttpStatus.NOT_ACCEPTABLE,
                    "user not found or cart is empty",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(userRepo.save(null))
            );
        }
    }


    public GeneralResponse addItemToCart(AddNewItemToCartRequest itemToCartRequest){

        User user = userRepo.findUserByUsernameIs(itemToCartRequest.getUsername());
        List<CartItem> cart = user.getCart();


        try{
            for(int i = 0; i < cart.size(); i++){
                if(cart.get(i).getId().equals(itemToCartRequest.getItemId())){
                    cart.get(i).setQuantity(cart.get(i).getQuantity() + itemToCartRequest.getQuantity());
                    user.setCart(cart);
                    return new GeneralResponse(
                            HttpStatus.ACCEPTED,
                            "Item added",
                            LocalDate.now(),
                            LocalTime.now(),
                            new GeneralResponseBody<>(userRepo.save(user))
                    );

                }
            }

            Item item = itemRepo.findItemByIdIs(itemToCartRequest.getItemId());

            cart.add(cartItemRepo.save(new CartItem(item.getId(), item.getName(), itemToCartRequest.getQuantity(), item.getUnitPrice(), item.getImage())));
            user.setCart(cart);

            return new GeneralResponse(
                    HttpStatus.ACCEPTED,
                    "Item added",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(userRepo.save(user))
            );

        }catch (Exception e){
            System.out.println(e);
            return new GeneralResponse(
                    HttpStatus.NOT_ACCEPTABLE,
                    "wrong user id or item id",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(null)
            );
        }

    }


//    public GeneralResponse addItemToUserCart(AddNewItemToCartRequest itemToCartRequest){
//        User user = getUserByUserName(itemToCartRequest.getUsername());
//        Item item = itemRepo.findItemByIdIs(itemToCartRequest.getItemId());
//        if((user == null) || (item == null)){
//            return new GeneralResponse(
//                    HttpStatus.BAD_REQUEST,
//                    "Username or item id not found!",
//                    LocalDate.now(),
//                    LocalTime.now(),
//                    new GeneralResponseBody<>(null)
//            );
//        }else{
//            List<CartItem> cartItems = user.getCart();
//            cartItems.add(new CartItem(item.getId(),
//                                        item.getName(),
//                                        itemToCartRequest.getQuantity(),
//                                        item.getUnitPrice()*))
//        }
//    }


}
