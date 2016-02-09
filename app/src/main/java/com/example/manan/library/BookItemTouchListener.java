package com.example.manan.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class BookItemTouchListener extends RecyclerView.SimpleOnItemTouchListener {

    GestureDetector mGestureDetector;
    private OnItemClickListener mListener;

    public BookItemTouchListener(Context ctx, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(ctx, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView);
        }
        return false;
    }


    public interface OnItemClickListener {
        void onItemClick(View view);
    }


}
