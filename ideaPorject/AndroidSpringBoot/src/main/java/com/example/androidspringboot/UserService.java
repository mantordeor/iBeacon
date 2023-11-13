package com.example.androidspringboot;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean exists(UserModel user){
        return repository.existsByName(user.getPhoneNum());
    }
    public UserModel findByPhoneName(UserModel user){
        return repository.findByPhoneNum(user.getPhoneNum());
    }
    public boolean insert(UserModel user){
        repository.save(user);
        return true;
    }
    public boolean update(UserModel user){
        if(repository.findById(user.getUserId()).isEmpty()) return false;
        repository.save(user);
        return true;
    }
    public boolean deleteById(Integer id){
        if(!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
