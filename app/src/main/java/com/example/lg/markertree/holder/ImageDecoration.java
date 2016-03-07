package com.example.lg.markertree.holder;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by LG on 2016-02-07.
 */
public class ImageDecoration extends RecyclerView.ItemDecoration{
    Drawable mDivider;
    int[] ATTR = {android.R.attr.listDivider};

    public ImageDecoration(Context context){
        TypedArray ta = context.obtainStyledAttributes(ATTR);
        mDivider = ta.getDrawable(0);
        ta.recycle();
    }

    @Override
    public void onDraw(Canvas c,RecyclerView parent,RecyclerView.State state){
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int top =0;
        for(int i=0;i<parent.getChildCount();i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }
}
