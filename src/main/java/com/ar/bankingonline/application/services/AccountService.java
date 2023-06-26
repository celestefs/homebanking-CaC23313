package com.ar.bankingonline.application.services;

import com.ar.bankingonline.api.controllers.dtos.AccountDto;
import com.ar.bankingonline.api.controllers.mappers.AccountMapper;
import com.ar.bankingonline.domain.models.Account;
import com.ar.bankingonline.infrastructure.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;

    public AccountService(AccountRepository repository){
        this.repository = repository;
    }

    public List<AccountDto> getAccounts(){
        List<Account> accounts = repository.findAll();
        return accounts.stream()
                .map(AccountMapper::AccountToDto)
                .collect(Collectors.toList());
    }

    public AccountDto createAccount(AccountDto account){
        return AccountMapper.AccountToDto(repository.save(AccountMapper.dtoToAccount(account)));
    }

}
