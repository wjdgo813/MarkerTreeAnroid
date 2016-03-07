package com.example.lg.markertree.result.category_list;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LG on 2016-03-06.
 */
public class CategoryResult implements Serializable{
    public List<CategoryItem> item;
    public int count;
    public CategoryResult(List<CategoryItem> item,int count){
        this.item= item;
        this.count = count;
    }
}
