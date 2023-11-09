package com.example.weeeapp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class RestResponse {
    private int code;
    private Object data;

    public int getCode() {
        return code;
    }
    public Object getData(){
        return data;
    }
}