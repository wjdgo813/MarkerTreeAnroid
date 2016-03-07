package com.example.lg.markertree.result.read_comm;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LG on 2016-03-04.
 */
public class ReadCommResult implements Serializable{
    public int count;
    public String favorite;
    public List<CommItem> item;

    public ReadCommResult(int count,String favorite,List<CommItem> item){
        this.count = count;
        this.favorite = favorite;
        this.item = item;
    }
}
