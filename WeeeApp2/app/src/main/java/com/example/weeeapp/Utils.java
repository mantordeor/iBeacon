package com.example.weeeapp;

import android.content.Context;
import android.os.Message;
import android.widget.Toast;

import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;

public class Utils {
    private static final Keccak.Digest512 digest512 = new Keccak.Digest512();
    public static String encrypt(String origin){
        return new String(Hex.encode(digest512.digest(origin.getBytes(StandardCharsets.UTF_8))));
    }
    public static String getResponseMessge(int code){
        String message = "";
        switch (code){
            case ResponseCode.SIGN_IN_SUCCESS:
                message = "登陸成功";
                break;
            case ResponseCode.SIGN_UP_SUCCESS:
                message = "註冊成功";
                break;
            case ResponseCode.SIGN_IN_FAILED:
                message = "手機號碼錯誤";
                break;
            case ResponseCode.SIGN_UP_FAILED:
                message = "註冊失敗";
                break;
            case ResponseCode.DELETE_SUCCESS:
                message = "刪除成功";
                break;
            case ResponseCode.UPDATE_SUCCESS:
                message = "更新成功";
                break;
            case ResponseCode.UPDATE_FAILED:
                message = "更新失敗";
                break;
            case ResponseCode.EMPTY_RESPONSE:
                message = "響應體為空";
                break;
            case ResponseCode.SERVER_ERROR:
                message = "伺服器錯誤";
                break;
            case ResponseCode.JSON_SERIALIZATION:
                message = "JSON序列化錯誤";
                break;
            case ResponseCode.EXIT_SUCCESS:
                message = "退出成功";
                break;
            case ResponseCode.REQUEST_FAILED:
                message = "請求發送失敗";
                break;
            case ResponseCode.UNCHANGED_INFORMATION:
                message = "未修改訊息";
                break;
        }
        return message;
    }
    public static void showMessage(Context context, Message message){
        Toast.makeText(context, getResponseMessge(message.what), Toast.LENGTH_SHORT).show();
    }
}
