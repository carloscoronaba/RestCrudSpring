package com.neoris.dinamita.rest.RestCrud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/holamundo")
public class HolaMundo {

    @GetMapping("/saludo")
    public String saludar(@RequestParam String saludo) {
        System.out.println("Hola " + saludo + ", desde Consola");
        return "Hola " + saludo;
    }
}
