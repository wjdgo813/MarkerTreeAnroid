package com.example.lg.markertree.result.create_book_mark;

import com.example.lg.markertree.result.signUp.Result;

import java.io.Serializable;

/**
 * Created by LG on 2016-02-26.
 */
public class CreateResult implements Serializable {
    public int success;
    public Result result;

    public CreateResult(int success,Result result){
        this.success=success;
        this.result = result;
    }
}