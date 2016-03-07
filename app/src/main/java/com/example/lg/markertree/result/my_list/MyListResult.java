package com.example.lg.markertree.result.my_list;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LG on 2016-03-06.
 */
public class MyListResult implements Serializable {

    public List<MyItem> item;
    public int count;
    public MyListResult(List<MyItem> item,int count){
        this.item = item;
        this.count= count;
    }
}
