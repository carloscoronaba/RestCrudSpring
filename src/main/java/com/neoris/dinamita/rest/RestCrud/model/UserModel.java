package com.neoris.dinamita.rest.RestCrud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer user_id; 

    String name; 

    String password; 

    @Column(name = "EMAIL", unique = true)
    String email;
}
