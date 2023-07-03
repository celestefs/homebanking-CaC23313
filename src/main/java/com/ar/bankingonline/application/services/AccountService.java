package com.ar.bankingonline.application.services;

import com.ar.bankingonline.api.dtos.AccountDto;
import com.ar.bankingonline.api.mappers.AccountMapper;
import com.ar.bankingonline.domain.models.Account;
import com.ar.bankingonline.infrastructure.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;
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

    public AccountDto getAccountById(Long id) {
        AccountDto account = AccountMapper.AccountToDto(repository.findById(id).get());
        return account;
    }

    public AccountDto updateAccount(Long id, AccountDto account) throws AccountNotFoundException {
        Optional<Account> accountCreated = repository.findById(id);

        if(accountCreated.isPresent()){
            Account entity = accountCreated.get();
            Account accountUpdated = AccountMapper.dtoToAccount(account);
            accountUpdated.setId(entity.getId());
            Account saved = repository.save(accountUpdated);
            return AccountMapper.AccountToDto(saved);
        } else {
            throw new AccountNotFoundException("Client not found with id: " + id);
        }
    }

    public String deleteAccount(Long id){

        if(repository.existsById(id)){
            repository.deleteById(id);
            return "Se ha eliminado la cuenta";
        } else {
            return "No se ha eliminado la cuenta";
        }
    }
}
