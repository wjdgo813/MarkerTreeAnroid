package com.example.lg.markertree.result.login;

import com.example.lg.markertree.result.signUp.Result;

import java.io.Serializable;

/**
 * Created by LG on 2016-02-24.
 */
public class LoginResult implements Serializable{
    public int success;
    public Result result;

    public LoginResult(int success,Result result){
        this.success=success;
        this.result = result;
    }
}
