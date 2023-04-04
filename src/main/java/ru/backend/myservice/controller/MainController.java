package ru.backend.myservice.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600) // TODO: do this
public class MainController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }
}
