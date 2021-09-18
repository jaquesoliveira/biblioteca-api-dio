package com.dio.api.biblioteca.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/autor")
public class AutorController {

    @GetMapping("/check")
    public String apiCheck(){
        return new String("Hello World!!!");
    }
}
