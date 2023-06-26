package com.ar.bankingonline.api.controllers;

import com.ar.bankingonline.api.controllers.dtos.AccountDto;
import com.ar.bankingonline.application.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    private AccountService service;

    @Autowired
    public AccountController(AccountService service){
        this.service = service;
    }

    @GetMapping(value = "/accounts")
    public ResponseEntity<List<AccountDto>> getAccounts(){

        List<AccountDto> accounts = service.getAccounts();

        return ResponseEntity.status(200).body(accounts);
    }

    @PostMapping(value = "/accounts")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto dto){
        return ResponseEntity.status(201).body(service.createAccount(dto));
    }

}