package com.example.xchange.payload;

import com.example.xchange.entity.ApplicationUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserResponse {

    private String username;
    private String roles;
    private String firstName;
    private String lastName;
    private String email;
    private String userImg;

    public CurrentUserResponse(ApplicationUser user) {
        this.username = user.getUserName();
        this.roles = user.getRoles();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.userImg = user.getUserImg();
    }
}
