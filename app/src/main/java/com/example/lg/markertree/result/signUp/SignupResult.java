package com.example.lg.markertree.result.signUp;

import java.io.Serializable;

/**
 * Created by LG on 2016-02-23.
 */
public class SignupResult implements Serializable{
    public int success;
    public Result result;
    public SignupResult(int success,Result result){
        this.success = success;
        this.result = result;
    }
}
