package com.gachon.morningroutin_layout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

public class TextOutline extends AppCompatTextView {
    private boolean stroke = false;
    private float strokeWidth = 0.0f;
    private int strokeColor;

    // constructor for custom textview
    public TextOutline(Context context) {
        super(context);
    }



    public TextOutline(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }



    public TextOutline(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }


    // initialize custom attributes
    private void initView(Context context, AttributeSet attrs) {
        TypedArray type = context.obtainStyledAttributes(attrs, R.styleable.TextOutline);

        stroke = type.getBoolean(R.styleable.TextOutline_textStroke, false);
        strokeWidth = type.getFloat(R.styleable.TextOutline_textStrokeWidth, 0.0f);
        strokeColor = type.getColor(R.styleable.TextOutline_textStrokeColor, 0xffffffff);
    }

    // set text borderline with designated color and width if borderline is set to true
    @Override
    protected void onDraw(Canvas canvas) {
        if (stroke) {
            ColorStateList states = getTextColors();
            getPaint().setStyle(Paint.Style.STROKE);
            getPaint().setStrokeWidth(strokeWidth);
            setTextColor(strokeColor);
            super.onDraw(canvas);
            getPaint().setStyle(Paint.Style.FILL);
            setTextColor(states);

        }
        super.onDraw(canvas);
    }
}
