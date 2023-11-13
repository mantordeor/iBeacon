package com.example.androidspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.beans.JavaBean;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping("/userphone")
    public ResponseBody SetPhoneNum(@RequestBody UserModel user){
        if(service.exists(user)){
            UserModel u = service.findByPhoneName(user);
            return new ResponseBody(u != null ? ResponseCode.SIGN_IN_SUCCESS : ResponseCode.SIGN_IN_FAILED, u != null ? u.getUserId() : "");
        }
        return new ResponseBody(service.insert(user) ? ResponseCode.SIGN_UP_SUCCESS : ResponseCode.SIGN_UP_FAILED, "");
    }
    @PutMapping("update")
    public ResponseBody update(@RequestBody UserModel user){
        return new ResponseBody(service.update(user) ? ResponseCode.UPDATE_SUCCESS : ResponseCode.UPDATE_FAILED, "");
    }
    @DeleteMapping("delete")
    public ResponseBody deleteByPhoneNum(@RequestBody int id){
        return new ResponseBody(service.deleteById(id) ? ResponseCode.DELETE_SUCCESS : ResponseCode.DELETE_FAILED, "");
    }
    @GetMapping("TestCase")
    public String test(){
        return "Test";
    }
}
