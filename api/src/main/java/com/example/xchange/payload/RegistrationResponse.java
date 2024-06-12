package com.example.xchange.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
}
