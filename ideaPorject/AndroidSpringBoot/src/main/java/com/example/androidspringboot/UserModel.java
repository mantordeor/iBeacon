package com.example.androidspringboot;

import jakarta.persistence.*;

@Entity
@Table(name = "usertable")
public class UserModel {
    @Id
    @Column(name = "id", unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "PhoneNum")
    private String PhoneNum;
    public UserModel(){

    }

    public UserModel(Integer id, String PhoneNum) {
        this.id = id;
        this.PhoneNum = PhoneNum;
    }

    public int getUserId(){return id;}

    public void setUserId(){this.id = id;}

    public String getPhoneNum(){return PhoneNum;}

    public void setPhoneNum(){this.PhoneNum = PhoneNum;}

}