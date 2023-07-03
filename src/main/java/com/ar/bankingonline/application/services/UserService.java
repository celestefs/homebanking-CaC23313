package com.ar.bankingonline.application.services;

import com.ar.bankingonline.api.mappers.UserMapper;
import com.ar.bankingonline.domain.models.User;
import com.ar.bankingonline.api.dtos.UserDto;
import com.ar.bankingonline.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public List<UserDto> getUsers(){
        List<User> users = repository.findAll();
        return users.stream()
                .map(UserMapper::userMapToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id){
        return UserMapper.userMapToDto(repository.findById(id).get());
    }

    public UserDto createUser(UserDto user){
        return UserMapper.userMapToDto(repository.save(UserMapper.dtoToUser(user)));
    }

    public UserDto update(Long id, UserDto user) throws AccountNotFoundException {
        Optional<User> userCreated = repository.findById(id);

        if(userCreated.isPresent()){
            User entity = userCreated.get();
            User userUpdated = UserMapper.dtoToUser(user);
            userUpdated.setId(entity.getId());
            User saved = repository.save(userUpdated);
            return UserMapper.userMapToDto(saved);
        } else {
            throw new AccountNotFoundException("User not found with id: " + id);
        }
    }

    public String delete(Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return "Se ha eliminado la cuenta";
        } else {
            return "No se ha eliminado la cuenta";
        }
    }

}
