package com.lhy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.lhy.R;
import com.lhy.util.DensityUtil;

/**
 * Created by zwy on 2017/12/8.
 * package_name is com.lhy.view
 * 描述:LhyImageView
 */

public class LhyRoundImageView extends android.support.v7.widget.AppCompatImageView {
    private static final PorterDuffXfermode xferMode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

    public static final int default_radius = 4;//默认圆角
    public static final int default_border_width = 2;//默认边框宽度
    public static final int default_border_color = 0x000000;//默认边框颜色
    public static final int default_crop_type = 0;
    public static final boolean default_border_show = false;//默认边框

    private Paint bitmapPaint;
    private Paint borderPaint;

    private Path roundPath;
    private Path borderPath;

    private enum Crop_Type {
        circle, round_all, round_top_left,
        round_top_right, round_bottom_left, round_bottom_right,
        round_top_both, round_left_both, round_bottom_both,
        round_right_both, round_top_left_other, round_top_right_other,
        round_bottom_left_other, round_bottom_right_other
    }

    private Crop_Type[] crop_types = new Crop_Type[]{Crop_Type.circle, Crop_Type.round_all, Crop_Type.round_top_left,
            Crop_Type.round_top_right, Crop_Type.round_bottom_left, Crop_Type.round_bottom_right,
            Crop_Type.round_top_both, Crop_Type.round_left_both, Crop_Type.round_bottom_both,
            Crop_Type.round_right_both, Crop_Type.round_top_left_other, Crop_Type.round_top_right_other,
            Crop_Type.round_bottom_left_other, Crop_Type.round_bottom_right_other
    };


    private Crop_Type crop_type = crop_types[default_crop_type];

    private int index_crop_type = default_crop_type;
    private float index_radius = default_radius;
    private float index_border_width = default_border_width;
    private int index_border_color = default_border_color;
    private boolean index_border_is_show = default_border_show;

    private float index_top_left_radius = 0;
    private float index_top_right_radius = 0;
    private float index_bottom_left_radius = 0;
    private float index_bottom_right_radius = 0;

    public LhyRoundImageView(Context context) {
        this(context, null);
    }

    public LhyRoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LhyRoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LhyRoundImageView);
            index_radius = typedArray.getDimension(R.styleable.LhyRoundImageView_crop_radius, DensityUtil.dp2px(getContext(), default_radius));
            index_border_width = typedArray.getDimension(R.styleable.LhyRoundImageView_crop_border_width, DensityUtil.dp2px(getContext(), default_border_width));
            index_border_color = typedArray.getColor(R.styleable.LhyRoundImageView_crop_border_color, default_border_color);
            index_crop_type = typedArray.getInteger(R.styleable.LhyRoundImageView_crop_type, default_crop_type);
            index_border_is_show = typedArray.getBoolean(R.styleable.LhyRoundImageView_crop_border_is_show, false);
            crop_type = crop_types[index_crop_type];
            typedArray.recycle();
        }
    }

    private void initPaint() {
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        borderPaint.setStrokeWidth(index_border_width);

        roundPath = new Path();
        borderPath = new Path();

        setScaleType(ScaleType.CENTER_CROP);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            initBorderPath();
            initBitmapPath(crop_type);
        }
    }

    public Bitmap getRoundBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        canvas.drawPath(roundPath, paint);
        return bitmap;
    }

    private void drawBorder(Canvas canvas) {
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(index_border_color);
        canvas.drawPath(borderPath, borderPaint);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawBitmap(canvas);
        if (index_border_is_show) {
            drawBorder(canvas);
        }
    }

    private void drawBitmap(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (!isInEditMode() && drawable != null) {
            Bitmap bitmap;
            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }
            //先画一个矩形
            Canvas drawCanvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
            drawable.draw(drawCanvas);

            Bitmap roundBmp = getRoundBitmap();
            bitmapPaint.reset();//重置paint属性
            bitmapPaint.setFilterBitmap(false);//线性过滤
            bitmapPaint.setXfermode(xferMode);
            drawCanvas.drawBitmap(roundBmp, 0, 0, bitmapPaint);
            bitmapPaint.setXfermode(null);
            canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        }
    }


    private void initBitmapPath(Crop_Type crop_type) {
        roundPath.reset();
        int width = getWidth();
        int height = getHeight();
        if (crop_type == Crop_Type.circle) {
            int center = Math.min(width, height) / 2;
            roundPath.addCircle(center, center, center, Path.Direction.CW);
        } else {
            RectF rectF = new RectF(0, 0, width, height);
            switch (crop_type) {
                case round_all:
                    index_top_left_radius = index_top_right_radius = index_bottom_left_radius = index_bottom_right_radius = index_radius;
                    break;
                case round_top_left:
                    index_top_left_radius = index_radius;
                    break;
                case round_top_right:
                    index_top_right_radius = index_radius;
                    break;
                case round_bottom_left:
                    index_bottom_left_radius = index_radius;
                    break;
                case round_bottom_right:
                    index_bottom_right_radius = index_radius;
                    break;
                case round_top_both:
                    index_top_left_radius = index_top_right_radius = index_radius;
                    break;
                case round_bottom_both:
                    index_bottom_left_radius = index_bottom_right_radius = index_radius;
                    break;
                case round_top_left_other:
                    index_top_right_radius = index_bottom_left_radius = index_bottom_right_radius = index_radius;
                    break;
                case round_top_right_other:
                    index_top_left_radius = index_bottom_left_radius = index_bottom_right_radius = index_radius;
                    break;
                case round_bottom_left_other:
                    index_top_left_radius = index_top_right_radius = index_bottom_right_radius = index_radius;
                    break;
                case round_bottom_right_other:
                    index_top_left_radius = index_top_right_radius = index_bottom_left_radius = index_radius;
                    break;
            }
            roundPath.addRoundRect(rectF, new float[]{index_top_left_radius, index_top_left_radius, index_top_right_radius, index_top_right_radius, index_bottom_left_radius, index_bottom_left_radius, index_bottom_right_radius, index_bottom_right_radius}, Path.Direction.CW);
        }
    }

    private void initBorderPath() {
        borderPath.reset();
        int width = getWidth();
        int height = getHeight();
        index_border_width = index_border_width * 0.35f;
        if (crop_type == Crop_Type.circle) {
            int center = Math.min(width, height) / 2;
            borderPath.addCircle(center, center, center - index_border_width, Path.Direction.CW);
        } else {

            RectF rectF = new RectF(index_border_width, index_border_width, width - index_border_width, height - index_border_width);
            switch (crop_type) {
                case round_all:
                    index_top_left_radius = index_top_right_radius = index_bottom_left_radius = index_bottom_right_radius = index_radius;
                    break;
                case round_top_left:
                    index_top_left_radius = index_radius;
                    break;
                case round_top_right:
                    index_top_right_radius = index_radius;
                    break;
                case round_bottom_left:
                    index_bottom_left_radius = index_radius;
                    break;
                case round_bottom_right:
                    index_bottom_right_radius = index_radius;
                    break;
                case round_top_both:
                    index_top_left_radius = index_top_right_radius = index_radius;
                    break;
                case round_bottom_both:
                    index_bottom_left_radius = index_bottom_right_radius = index_radius;
                    break;
                case round_top_left_other:
                    index_top_right_radius = index_bottom_left_radius = index_bottom_right_radius = index_radius;
                    break;
                case round_top_right_other:
                    index_top_left_radius = index_bottom_left_radius = index_bottom_right_radius = index_radius;
                    break;
                case round_bottom_left_other:
                    index_top_left_radius = index_top_right_radius = index_bottom_right_radius = index_radius;
                    break;
                case round_bottom_right_other:
                    index_top_left_radius = index_top_right_radius = index_bottom_left_radius = index_radius;
                    break;
            }
            borderPath.addRoundRect(rectF, new float[]{index_top_left_radius, index_top_left_radius, index_top_right_radius, index_top_right_radius, index_bottom_left_radius, index_bottom_left_radius, index_bottom_right_radius, index_bottom_right_radius}, Path.Direction.CW);
        }
    }

}
