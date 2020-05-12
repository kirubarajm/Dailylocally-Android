package com.dailylocally.utilities.fonts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class SacremantoTextView extends AppCompatTextView {


    public SacremantoTextView(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Sacramento-Regular.ttf");
        this.setTypeface(face,  Typeface.BOLD);


    }

    public SacremantoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Sacramento-Regular.ttf");
        this.setTypeface(face,  Typeface.BOLD);

    }

    public SacremantoTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Sacramento-Regular.ttf");
        this.setTypeface(face,  Typeface.BOLD);

    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}