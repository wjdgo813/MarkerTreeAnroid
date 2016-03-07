package com.example.lg.markertree.result.hot_list;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LG on 2016-03-01.
 */
public class HotListResult implements Serializable {
    public List<HotItem> item;
    public int count;
    public HotListResult(List<HotItem> item,int count){
        this.item = item;
        this.count= count;
    }
}
