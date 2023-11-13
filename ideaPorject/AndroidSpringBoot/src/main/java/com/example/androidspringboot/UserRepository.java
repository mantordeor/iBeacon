package com.example.androidspringboot;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Repository
public interface UserRepository extends CrudRepository<UserModel, Integer> {
    boolean existsByName(String name);
    UserModel findByPhoneNum(String PhoneNum);
}
