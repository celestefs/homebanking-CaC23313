package com.ar.bankingonline.api.controllers;

import com.ar.bankingonline.api.dtos.UserDto;
import com.ar.bankingonline.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getUsers(){

        List<UserDto> usuarios = service.getUsers();

        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getUserById(id));
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(dto));
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto user) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, user));
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }

}
