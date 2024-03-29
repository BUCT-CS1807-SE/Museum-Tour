package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.activity.BaseActivity;
import com.example.myapplication.activity.HomePageActivity;
import com.example.myapplication.api.Api;
import com.example.myapplication.api.ApiConfig;
import com.example.myapplication.api.TtitCallback;
import com.example.myapplication.entity.LoginResponse;
import com.example.myapplication.util.StringUtils;
import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends BaseActivity {

    private Button btnLogin;
    private EditText etAccount,etPassword;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        getSupportActionBar().setTitle("登录");
//
//        btnLogin = findViewById(R.id.btn_login);
//        etAccount = findViewById(R.id.et_account);
//        etPassword = findViewById(R.id.et_password);
//
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String account = etAccount.getText().toString();
//                String password = etPassword.getText().toString();
//                login(account, password);
////                if(TextUtils.equals(account,account)){
////                    if(TextUtils.equals(password,pwd)){
////                        Toast.makeText(LoginActivity.this,"恭喜你，登录成功",Toast.LENGTH_LONG).show();
////                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
////                    }else {
////                        Toast.makeText(LoginActivity.this,"密码错误！",Toast.LENGTH_LONG).show();
////                    }
////                }else {
////                    Toast.makeText(LoginActivity.this,"用户名错误！",Toast.LENGTH_LONG).show();
////                }
//            }
//        });
//
//    }
    private void login (String account, String pwd)
    {
        if (StringUtils.isEmpty(account)) {
            showToast("请输入账号");
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            showToast("请输入密码");
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", account);
        params.put("password", pwd);
        Api.config(ApiConfig.LOGIN, params).postRequest(this,new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("onSuccess", res);
                Gson gson = new Gson();
                LoginResponse loginResponse = gson.fromJson(res, LoginResponse.class);
                if (loginResponse.getCode() == 0) {
                    String token = loginResponse.getToken();
                    insertVal("token", token);
                    navigateToWithFlag(HomePageActivity.class,
                            Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    showToastSync("登录成功");
                } else {
                    showToastSync("登录失败");
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        btnLogin = findViewById(R.id.btn_login);
        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
    }

    @Override
    protected void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                login(account, password);
//                if(TextUtils.equals(account,account)){
//                    if(TextUtils.equals(password,pwd)){
//                        Toast.makeText(LoginActivity.this,"恭喜你，登录成功",Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                    }else {
//                        Toast.makeText(LoginActivity.this,"密码错误！",Toast.LENGTH_LONG).show();
//                    }
//                }else {
//                    Toast.makeText(LoginActivity.this,"用户名错误！",Toast.LENGTH_LONG).show();
//                }
            }
        });
    }
}