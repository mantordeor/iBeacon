package com.example.weeeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weeeapp.databinding.ActivityMainBinding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding; //全抓
    private final ObjectMapper mapper = new ObjectMapper();
    private static final MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    private final Message message = new Message();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int SignInId = 0;
    private String oldName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    public void signInUp(View view){
        try{
            String name = binding.Loginname.getText().toString();
            Request request = new Request.Builder().url(NetworkSettings.SIGN_IN_UP).post(
                    RequestBody.create(mapper.writeValueAsString(new com.example.weeeapp.User(SignInId,name)),mediaType)
            ).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    message.what = ResponseCode.REQUEST_FAILED;
                    handler.post(()->Utils.showMessage(getApplicationContext(),message));
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if(response.isSuccessful()){
                        ResponseBody body = response.body();
                        if(body != null) {
                            RestResponse restResponse = mapper.readValue(body.string(), RestResponse.class);
                            message.what = restResponse.getCode();
                            if (message.what == ResponseCode.SIGN_IN_SUCCESS) {
                                handler.post(() -> {
                                    oldName = binding.Loginname.getText().toString();
                                    binding.signInUp.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(MainActivity.this,ScanActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                });
                            }
                        }else {
                            message.what = ResponseCode.EMPTY_RESPONSE;
                            Log.e("RESPONSE_BODY_EMPTY", response.message());
                        }
                    }else{
                        message.what = ResponseCode.SERVER_ERROR;
                        Log.e("SERVER_ERROR",response.message());
                    }
                    handler.post(()->Utils.showMessage(getApplicationContext(),message));
                }
            });
        } catch (JsonProcessingException e) {
            message.what = ResponseCode.JSON_SERIALIZATION;
            Utils.showMessage(getApplicationContext(),message);
            throw new RuntimeException(e);
        }
    }
    public void update(View view){
        try{
            String name = binding.Loginname.getText().toString();
            if(name.equals(oldName)){
                message.what = ResponseCode.UNCHANGED_INFORMATION;
                handler.post(()->Utils.showMessage(getApplicationContext(),message));
                return;
            }
            Request request = new Request.Builder().url(NetworkSettings.UPDATE).put(
                    RequestBody.create(
                            mapper.writeValueAsString(new User(SignInId,name)),
                            mediaType
                    )
            ).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    message.what = ResponseCode.REQUEST_FAILED;
                    handler.post(()->Utils.showMessage(getApplicationContext(),message));
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if(response.isSuccessful()){
                        ResponseBody body = response.body();
                        if(body != null){
                            RestResponse restResponse = mapper.readValue(body.string(),RestResponse.class);
                            message.what = restResponse.getCode();
                            if(message.what == ResponseCode.UPDATE_SUCCESS){
                                oldName = binding.Loginname.getText().toString();
                            }
                        }else{
                            message.what = ResponseCode.EMPTY_RESPONSE;
                            Log.e("RESPONSE_BODY_EMPTY", response.message());
                        }
                    }else {
                        message.what = ResponseCode.SERVER_ERROR;
                        Log.e("SERVER_ERROR", response.message());
                    }
                    handler.post(()->Utils.showMessage(getApplicationContext(),message));
                }
            });
        } catch (Exception e) {
            message.what = ResponseCode.JSON_SERIALIZATION;
            Utils.showMessage(getApplicationContext(),message);
            e.printStackTrace();
        }
    }
    public void delete(View view) {
        try {
            Request request = new Request.Builder().url(NetworkSettings.DELETE+"?id="+SignInId).delete().build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    message.what = ResponseCode.REQUEST_FAILED;
                    handler.post(()->Utils.showMessage(getApplicationContext(),message));
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        ResponseBody body = response.body();
                        if (body != null) {
                            RestResponse restResponse = mapper.readValue(body.string(), RestResponse.class);
                            message.what = restResponse.getCode();
                            if(message.what == ResponseCode.DELETE_SUCCESS){
                                handler.post(()->signOut(true));
                            }
                        } else {
                            message.what = ResponseCode.EMPTY_RESPONSE;
                            Log.e("RESPONSE_BODY_EMPTY", response.message());
                        }
                    } else {
                        message.what = ResponseCode.SERVER_ERROR;
                        Log.e("SERVER_ERROR", response.message());
                    }
                    handler.post(()->Utils.showMessage(getApplicationContext(),message));
                }
            });
        } catch (Exception e) {
            message.what = ResponseCode.JSON_SERIALIZATION;
            Utils.showMessage(getApplicationContext(), message);
            e.printStackTrace();
        }

    }
    public void signOut(boolean isDeleted){
        SignInId = 0;
        binding.signInUp.setText("注册/登录");
        binding.signInUp.setOnClickListener(this::signInUp);
        binding.Loginname.setText("");
        if(!isDeleted){
            message.what = ResponseCode.EXIT_SUCCESS;
        }
        Utils.showMessage(getApplicationContext(),message);
    }
}