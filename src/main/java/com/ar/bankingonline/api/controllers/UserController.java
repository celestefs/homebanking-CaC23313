package com.ar.bankingonline.api.controllers;

import com.ar.bankingonline.api.controllers.dtos.UserDto;
import com.ar.bankingonline.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

        return ResponseEntity.status(200).body(usuarios);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.getUserById(id));
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto){
        return ResponseEntity.status(201).body(service.createUser(dto));
    }

    @PutMapping(value = "/users")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user){
        return ResponseEntity.status(200).body(service.update(user));
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.status(200).body("Se ha eliminado el usuario exitosamente");
    }

}
