package com.ar.bankingonline.application.services;

import com.ar.bankingonline.api.dtos.AccountDto;
import com.ar.bankingonline.api.mappers.AccountMapper;
import com.ar.bankingonline.domain.exceptions.AccountNotFoundException;
import com.ar.bankingonline.domain.models.Account;
import com.ar.bankingonline.domain.models.User;
import com.ar.bankingonline.infrastructure.repositories.AccountRepository;
import com.ar.bankingonline.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;
    @Autowired
    private UserRepository userRepository;

    public AccountService(AccountRepository repository, UserRepository userRepository){
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<AccountDto> getAccounts(){
        List<Account> accounts = repository.findAll();
        return accounts.stream()
                .map(AccountMapper::AccountToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AccountDto createAccount(AccountDto account){
        Optional<User> user = userRepository.findById(account.getOwner().getId());
        Account accountModel = AccountMapper.dtoToAccount(account);
        accountModel.setOwner(user.get());
        accountModel = repository.save(accountModel);
        AccountDto dto = AccountMapper.AccountToDto(accountModel);
        return dto;
    }

    @Transactional
    public AccountDto getAccountById(Long id) {
        AccountDto account = AccountMapper.AccountToDto(repository.findById(id).get());
        return account;
    }

    @Transactional
    public AccountDto updateAccount(Long id, AccountDto account)  {
        Optional<Account> accountCreated = repository.findById(id);

        if(accountCreated.isPresent()){
            Account entity = accountCreated.get();

            if (account.getAmount()!=null){
                entity.setBalance(account.getAmount());
            }
            if(account.getOwner()!=null){
                User user = userRepository.getReferenceById(account.getOwner().getId());
                if (user!=null){
                    entity.setOwner(user);
                }
            }

            Account saved = repository.save(entity);

            return AccountMapper.AccountToDto(saved);
        } else {
            throw new AccountNotFoundException("Account not found with id: " + id);
        }
    }

    @Transactional
    public String deleteAccount(Long id){

        if(repository.existsById(id)){
            repository.deleteById(id);
            return "Se ha eliminado la cuenta";
        } else {
            return "No se ha eliminado la cuenta";
        }
    }


    public BigDecimal withdraw(BigDecimal amount, Long idOrigin) {
        Account account = repository.findById(idOrigin).orElse(null);
        if (account.getBalance().subtract(amount).intValue() > 0) {
            account.setBalance(account.getBalance().subtract(amount));
            repository.save(account);
        }
        return account.getBalance().subtract(amount);
    }

    public BigDecimal addAmountToAccount(BigDecimal amount, Long idOrigin) {
        Account account = repository.findById(idOrigin).orElse(null);
        account.setBalance(account.getBalance().add(amount));
        repository.save(account);
        return amount;
    }

}
