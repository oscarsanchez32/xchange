package com.example.xchange.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.xchange.dto.UserUpdateDTO;
import com.example.xchange.entity.ApplicationUser;
import com.example.xchange.payload.RegistrationRequest;

public interface UserService extends UserDetailsService {
    ApplicationUser processRegistrationRequest(RegistrationRequest registrationRequest);

    boolean isUsernameTaken(String username);

    boolean isEmailTaken(String email);

    ApplicationUser getCurrentUser();

    ApplicationUser getUserByUserName(String username);

    ApplicationUser getUserById(int id);

    void addGameToCart(int gameId);

    void removeItemFromCart(int gameId);

    boolean isBalanceIsSufficient(ApplicationUser user, double amount);

    void debit(int id, double amount);

    void credit(int id, double amount);

    boolean itemExistsInUserInventory(int gameId, int userId);

    void clearCart(int id);

    void updateUser(String username, UserUpdateDTO userUpdateDTO);

    void removeFromInventory(int userId, int gameId);

    void addToInventory(int userId, int gameId);
}
