package com.dailylocally.utilities.fonts.league;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class FooterTextView extends AppCompatTextView {


    public FooterTextView(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "LeagueSpartan-Bold.otf");
        this.setTypeface(face,  Typeface.BOLD);
    }

    public FooterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "LeagueSpartan-Bold.otf");
        this.setTypeface(face,  Typeface.BOLD);
    }

    public FooterTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "LeagueSpartan-Bold.otf");
        this.setTypeface(face,  Typeface.BOLD);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

}