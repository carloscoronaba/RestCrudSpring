package com.neoris.dinamita.rest.RestCrud.controller;

import com.neoris.dinamita.rest.RestCrud.model.UserModel;
import com.neoris.dinamita.rest.RestCrud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UserModelController {

    private final IUserService iUserService;

    @Autowired
    public UserModelController(IUserService iUserService) {
        this.iUserService = iUserService;
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

    @GetMapping("/listar")
    public ResponseEntity<Object>listarUsuarios(){

        try{
            List<UserModel> listaUsers = iUserService.listaUsuarios();
            if(listaUsers.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La lista esta vacia");
            }else{
                return ResponseEntity.ok().body(listaUsers);
            }
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarUserModel(@RequestParam String email) {

        boolean eliminado = iUserService.eliminarUsuario(email);
        if (eliminado) {
            return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al eliminar usuario");
        }
    }

    @PutMapping("/modificar")
    public ResponseEntity<String> modificarUserModel(@RequestParam String email, @RequestBody UserModel userModel){

        boolean editado = iUserService.editarUsuario(email, userModel);
        if(editado){
            return ResponseEntity.status(HttpStatus.OK).body("Usuario modificada con exito");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al editar usuario");
        }
    }

}
