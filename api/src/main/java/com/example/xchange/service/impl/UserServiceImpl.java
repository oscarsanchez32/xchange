package com.example.xchange.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.xchange.dto.UserUpdateDTO;
import com.example.xchange.entity.ApplicationUser;
import com.example.xchange.entity.Game;
import com.example.xchange.exception.InvalidInputException;
import com.example.xchange.exception.ResourceNotFoundException;
import com.example.xchange.payload.RegistrationRequest;
import com.example.xchange.repository.ApplicationUserRepository;
import com.example.xchange.security.ApplicationUserDetails;
import com.example.xchange.service.GameService;
import com.example.xchange.service.UserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final GameService gameService;

    public UserServiceImpl(ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder, @Lazy GameService gameService) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.gameService = gameService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> userOptional = applicationUserRepository.findByUserName(username);
        userOptional.orElseThrow(() -> new UsernameNotFoundException("User name [" + username + "] not found."));
        return new ApplicationUserDetails(userOptional.get());
    }

    @Override
    public ApplicationUser processRegistrationRequest(RegistrationRequest registrationRequest) {
        ApplicationUser user = new ApplicationUser();
        user.setUserName(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setUserImg("https://thumbs.dreamstime.com/b/default-avatar-profile-icon-social-media-user-vector-default-avatar-profile-icon-social-media-user-vector-portrait-176194876.jpg");
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setRoles("ROLE_USER");
        user.setCredits(2.0);

        return applicationUserRepository.save(user);
    }

    @Override
    public boolean isUsernameTaken(String username) {
        Optional<ApplicationUser> user = applicationUserRepository.findByUserName(username);
        return user.isPresent();
    }

    @Override
    public boolean isEmailTaken(String email) {
        Optional<ApplicationUser> user = applicationUserRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public ApplicationUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<ApplicationUser> userOptional = applicationUserRepository.findByUserName(username);
        return userOptional.orElseThrow(() -> new UsernameNotFoundException("No user found with username: "+ username));
    }

    @Override
    public ApplicationUser getUserByUserName(String username) {
        return applicationUserRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    @Override
    public ApplicationUser getUserById(int id) {
        return applicationUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public void addGameToCart(int gameId) {
        ApplicationUser user = getCurrentUser();
        Game game = gameService.getGameById(gameId);

        if(user.getCart().contains(game)){
            throw new InvalidInputException("El producto ya está en el carrito.");
        }

        user.getCart().add(game);
        applicationUserRepository.save(user);
    }

    @Override
    @Transactional
    public void removeItemFromCart(int gameId) {
        ApplicationUser user = getCurrentUser();
        user.getCart().removeIf(b -> b.getId() == gameId);
    }

    @Override
    public boolean isBalanceIsSufficient(ApplicationUser user, double amount) {
        return user.getCredits() >= amount;
    }

    @Override
    @Transactional
    public void debit(int id, double amount) {
        ApplicationUser user = getUserById(id);
        if(amount > user.getCredits())
            throw new InvalidInputException("Insufficient balance - cannot debit for user with id: " + id);
        double balance = user.getCredits() - amount;
        balance = Math.round(balance * 100);
        balance = balance/100;
        user.setCredits(balance);
    }

    @Override
    @Transactional
    public void credit(int id, double amount){
        ApplicationUser user = getUserById(id);
        double balance = user.getCredits() + amount;
        balance = Math.round(balance * 100);
        balance = balance/100;
        user.setCredits(balance);
    }

    @Override
    public boolean itemExistsInUserInventory(int gameId, int userId) {
        ApplicationUser currentUser = getUserById(userId);
        Game game = gameService.getGameById(gameId);
        return currentUser.getUserInventory().contains(game);
    }

    @Override
    public void clearCart(int id) {
        ApplicationUser user = getUserById(id);
        user.setCart(new ArrayList<>());
        applicationUserRepository.save(user);
    }

    @Override
    public void updateUser(String username, UserUpdateDTO userUpdateDTO) {
        ApplicationUser user = getUserByUserName(username);

        if(StringUtils.hasText(userUpdateDTO.getFirstName())) user.setFirstName(userUpdateDTO.getFirstName());
        if(StringUtils.hasText(userUpdateDTO.getLastName())) user.setLastName(userUpdateDTO.getLastName());
        if(StringUtils.hasText(userUpdateDTO.getEmail())) user.setEmail(userUpdateDTO.getEmail());
        if(StringUtils.hasText(userUpdateDTO.getPassword())) user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
        if(StringUtils.hasText(userUpdateDTO.getUserImg())) user.setUserImg(userUpdateDTO.getUserImg());
        if(StringUtils.hasText(userUpdateDTO.getUsername())){
            if(isUsernameTaken(userUpdateDTO.getUsername())){
                throw new InvalidInputException("This username is taken");
            }
            user.setUserName(userUpdateDTO.getUsername());
        }
        applicationUserRepository.save(user);
    }

    @Override
    @Transactional
    public void removeFromInventory(int userId, int gameId) {
        ApplicationUser user = getUserById(userId);
        Game game = gameService.getGameById(gameId);
        user.getUserInventory().remove(game);
    }

    @Override
    @Transactional
    public void addToInventory(int userId, int gameId) {
        ApplicationUser user = getUserById(userId);
        Game game = gameService.getGameById(gameId);
        user.getUserInventory().add(game);
    }


}
