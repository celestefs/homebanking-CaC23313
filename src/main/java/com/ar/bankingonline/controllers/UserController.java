package com.ar.bankingonline.controllers;

import com.ar.bankingonline.models.User;
import com.ar.bankingonline.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers(){

        List<User> usuarios = service.getUsers();

        return ResponseEntity.status(200).body(usuarios);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.getUserById(id));
    }

    @PostMapping(value = "/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.status(201).body(user);
    }

    public void updateUser(User user){}

    public void deleteUser(Integer id){}

}
