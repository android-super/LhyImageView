package com.lhy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lhy.R;

/**
 * Created by zwy on 2017/12/18.
 * package_name is com.lhy.view
 * 描述:LhyImageView
 */

public class LhyProgressBar extends View {
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

    public LhyProgressBar(Context context) {
        this(context, null);
    }

    public LhyProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LhyProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LhyProgressBar);
        normal_color = typedArray.getColor(R.styleable.LhyProgressBar_progress_normal_color, default_normal_color);
        progress_color = typedArray.getColor(R.styleable.LhyProgressBar_progress_color, default_progress_color);
        progress_size = typedArray.getDimension(R.styleable.LhyProgressBar_progress_size, default_progress_size);
    }

    private void initPaint() {
        progress_normal_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progress_normal_paint.setStyle(Paint.Style.STROKE);
        progress_normal_paint.setStrokeWidth(progress_size);
        progress_normal_paint.setColor(normal_color);
        progress_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progress_paint.setStyle(Paint.Style.STROKE);
        progress_paint.setStrokeCap(Paint.Cap.ROUND);
        progress_paint.setStrokeWidth(progress_size);
        progress_paint.setColor(progress_color);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int circle_width = Math.min(width, height);
        canvas.drawCircle(circle_width / 2, circle_width / 2, circle_width / 2 - progress_size, progress_normal_paint);
        RectF oval = new RectF(getPaddingLeft() + progress_size, getPaddingTop() + progress_size,
                circle_width - getPaddingRight() - progress_size, circle_width - getPaddingBottom() - progress_size);
        drawProgress(canvas, oval);

    }

    private void drawProgress(final Canvas canvas, final RectF oval) {
        canvas.drawArc(oval, -90, index_progress * 3.6f, false, progress_paint);
        if (index_progress < getProgress()) {
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