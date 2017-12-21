package com.lhy.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lhy.R;


/**
 * Created by zwy on 2017/12/18.
 * package_name is com.lhy.view
 * 描述:LhyImageView
 */

public class LhySeekProgress extends View {
    private Paint progress_paint;
    private Paint progress_normal_paint;

    private static final int default_normal_color = 0xfefefe;
    private static final int default_progress_color = 0xff0000;
    private static final int default_progress_size = 3;

    private int normal_color = default_normal_color;
    private int progress_color = default_progress_color;
    private float progress_size = default_progress_size;

    private int progress = 0;
    private int index_progress = 0;

    public LhySeekProgress(Context context) {
        this(context, null);
    }

    public LhySeekProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LhySeekProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LhySeekProgress);
        normal_color = typedArray.getColor(R.styleable.LhySeekProgress_seek_normal_color, default_normal_color);
        progress_color = typedArray.getColor(R.styleable.LhySeekProgress_seek_progress_color, default_progress_color);
        progress_size = typedArray.getDimension(R.styleable.LhySeekProgress_seek_progress_size, default_progress_size);
    }

    private void initPaint() {
        progress_normal_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progress_normal_paint.setStyle(Paint.Style.STROKE);
        progress_normal_paint.setStrokeWidth(progress_size);
        progress_normal_paint.setColor(normal_color);
        progress_normal_paint.setStrokeCap(Paint.Cap.ROUND);
        progress_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progress_paint.setStrokeCap(Paint.Cap.ROUND);
        progress_paint.setStyle(Paint.Style.STROKE);
        progress_paint.setStrokeWidth(progress_size);
        progress_paint.setColor(progress_color);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawLine(Canvas canvas) {
        canvas.drawRoundRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), 40, 40, progress_normal_paint);
        drawProgress(canvas);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawProgress(final Canvas canvas) {
        canvas.drawRoundRect(0, 0, index_progress * (getMeasuredWidth() / 100), getMeasuredHeight(), 40, 40, progress_paint);
        if (index_progress < getProgress() && index_progress <= 100) {
            index_progress++;
        }
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}