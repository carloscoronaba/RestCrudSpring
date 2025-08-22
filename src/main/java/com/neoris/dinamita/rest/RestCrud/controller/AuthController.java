package com.neoris.dinamita.rest.RestCrud.controller;

import com.neoris.dinamita.rest.RestCrud.dao.AuthRequestDto;
import com.neoris.dinamita.rest.RestCrud.dao.AuthResponseDto;
import com.neoris.dinamita.rest.RestCrud.jwt.JwtUtilService;
import com.neoris.dinamita.rest.RestCrud.model.UserModel;
import com.neoris.dinamita.rest.RestCrud.repository.IUserRepository;
import com.neoris.dinamita.rest.RestCrud.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IUserService iUserService;


    @Autowired
    private AuthenticationManager authenticationManager; 

    @Autowired
    private UserDetailsService userDetailsService; 

    @Autowired
    private JwtUtilService jwtUtilService; 

 
   


    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequestDto authRequestDto){


        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequestDto.getUser(), authRequestDto.getPassword()
            ));

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequestDto.getUser());

            String jwt = this.jwtUtilService.generateToken(userDetails);

            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setToken(jwt);

            return new ResponseEntity<AuthResponseDto>(authResponseDto, HttpStatus.OK);

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error Authetication:::" + e.getMessage());
        }


    }

    @PostMapping("/registro")
    public ResponseEntity<String> insertarPersona(@RequestBody UserModel userModel){

        try {
            System.out.println(userModel);
            if (iUserService.agregarUsuario(userModel)) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Usuario Ingresado con exito: " + userModel);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Correo electronico o UserName ya existe: " + userModel.getEmail());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("No se ha podido insertar al usuario: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}



