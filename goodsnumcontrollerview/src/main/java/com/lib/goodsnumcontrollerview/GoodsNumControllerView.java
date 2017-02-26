package com.lib.goodsnumcontrollerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * author: ChenWei
 * create date: 2017/2/26
 * description:
 */

public class GoodsNumControllerView extends View{
    private int leftCircleBackgroundColor; //左边圆的背景
    private int rightCircleBackgroundColor; // 右边圆的背景
    private int leftCircleStrokeColor; //左边圆的边缘颜色
    private int rightCircleStrokeColor; //右边圆的边缘颜色
    private int leftCircleTextColor; //左边圆的内容颜色
    private int rightCircleTextColor; //右边圆的内容颜色
    private boolean subtractionIsLeft;        //减法在左边

    private int textSize;        //文字大小
    private int textColor;       //文字颜色
    private int maxNum;           // 最大值
    private int minNum;     //最小值
    private String leftText;
    private String rightText;

    private Paint leftCirclePaint;
    private Paint rightCirclePaint;
    private Paint leftTextPaint;
    private Paint rightTextPaint;
    private Paint centerTextPaint;
    private Paint leftCircleStrokePaint;
    private Paint rightCircleStrokePaint;

    private OnGoodsNumChangedListener onClickListener;

    private int circleRadius; //圆半径
    private int numValue; //当前数值
    private int centerWidth; //两圆中间宽度

    public static final String TAG = GoodsNumControllerView.class.getSimpleName();
    public GoodsNumControllerView(Context context) {
        this(context, null);
    }

    public GoodsNumControllerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        getAttrs(context, attrs);
    }

    public GoodsNumControllerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context, attrs);
        initPaint();
    }

    /**
     * 获取布局属性
     * @param context
     * @param attrs
     */
    private void getAttrs(Context context, AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GoodsNumControllerView);
        maxNum = ta.getInt(R.styleable.GoodsNumControllerView_maxNum, Integer.MAX_VALUE);
        minNum = ta.getInt(R.styleable.GoodsNumControllerView_minNum, 0);
        textSize = ta.getDimensionPixelOffset(R.styleable.GoodsNumControllerView_textSize, 32);
        textColor = ta.getColor(R.styleable.GoodsNumControllerView_textColor,
                ContextCompat.getColor(getContext(), R.color.gray));

        leftCircleBackgroundColor = ta.getColor(
                R.styleable.GoodsNumControllerView_leftCircleBackgroundColor,
                ContextCompat.getColor(getContext(), R.color.white));
        rightCircleBackgroundColor = ta.getColor(
                R.styleable.GoodsNumControllerView_rightCircleBackgroundColor,
                ContextCompat.getColor(getContext(), R.color.colorPrimary));
        leftCircleStrokeColor = ta.getColor(
                R.styleable.GoodsNumControllerView_leftCircleStrokeColor,
                ContextCompat.getColor(getContext(), R.color.gray));
        rightCircleStrokeColor = ta.getColor(
                R.styleable.GoodsNumControllerView_rightCircleStrokeColor,
                ContextCompat.getColor(getContext(), R.color.colorPrimary));
        leftCircleTextColor = ta.getColor(
                R.styleable.GoodsNumControllerView_leftCircleTextColor,
                ContextCompat.getColor(getContext(), R.color.gray));
        rightCircleTextColor = ta.getColor(
                R.styleable.GoodsNumControllerView_rightCircleTextColor,
                ContextCompat.getColor(getContext(), R.color.white));

        numValue = ta.getInt(R.styleable.GoodsNumControllerView_initialValue, 0);

        subtractionIsLeft = ta.getBoolean(R.styleable.GoodsNumControllerView_subtractionIsLeft,
                true);

        if(subtractionIsLeft){
            leftText = "－";
            rightText = "＋";
        }else{
            leftText = "＋";
            rightText = "－";
        }
        ta.recycle();

        if(numValue < minNum){
            numValue = minNum;
        }
    }


    private void initPaint(){
        leftCirclePaint = new Paint();
        leftCirclePaint.setColor(leftCircleBackgroundColor);
        leftCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        leftCirclePaint.setAntiAlias(true);

        rightCirclePaint = new Paint();
        rightCirclePaint.setColor(rightCircleBackgroundColor);
        rightCirclePaint.setStyle(Paint.Style.FILL);
        rightCirclePaint.setAntiAlias(true);

        leftCircleStrokePaint = new Paint();
        leftCircleStrokePaint.setColor(leftCircleStrokeColor);
        leftCircleStrokePaint.setStyle(Paint.Style.STROKE);
        leftCircleStrokePaint.setAntiAlias(true);

        rightCircleStrokePaint = new Paint();
        rightCircleStrokePaint.setColor(rightCircleStrokeColor);
        rightCircleStrokePaint.setStyle(Paint.Style.STROKE);
        rightCircleStrokePaint.setAntiAlias(true);

        leftTextPaint = new Paint();
        leftTextPaint.setTextAlign(Paint.Align.CENTER);
        leftTextPaint.setColor(leftCircleTextColor);
        leftTextPaint.setAntiAlias(true);

        rightTextPaint = new Paint();
        rightTextPaint.setTextAlign(Paint.Align.CENTER);
        rightTextPaint.setColor(rightCircleTextColor);
        rightTextPaint.setAntiAlias(true);

        centerTextPaint = new Paint();
        centerTextPaint.setColor(textColor);
        centerTextPaint.setTextSize(textSize);
        centerTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if(widthMode == MeasureSpec.AT_MOST){
            width = 260;
        }
        if(heightMode == MeasureSpec.AT_MOST){
            height = 80;
        }
        Log.d(TAG, "width:" + width);
        Log.d(TAG, "height: " + height);
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int canvasWidth = canvas.getWidth() - getPaddingLeft() - getPaddingRight();
        int canvasHeight = canvas.getHeight() - getPaddingTop() - getPaddingBottom();
        circleRadius = canvasHeight / 2;
//
//        Paint paint = new Paint();
//        paint.setColor(ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark));
//        canvas.drawRect(0,0,canvasWidth, canvasHeight, paint);


        //左边圆
        int leftCircleX = circleRadius;
        int leftCircleY = circleRadius;
        canvas.drawCircle(leftCircleX, leftCircleY, circleRadius, leftCircleStrokePaint);
        canvas.drawCircle(leftCircleX, leftCircleY, circleRadius - 1, leftCirclePaint);

        leftTextPaint.setTextSize(canvasHeight - 20);
        float leftTextHeight = getTextHeight(leftTextPaint);
        canvas.drawText(leftText,  circleRadius, leftTextHeight + circleRadius, leftTextPaint);


        centerWidth = canvasWidth - 2 *  2 * circleRadius;

        //右边圆
        int rightCircleX = circleRadius * 2 + centerWidth + circleRadius;
        int rightCircleY = circleRadius;
        canvas.drawCircle(rightCircleX, rightCircleY, circleRadius, rightCircleStrokePaint);
        canvas.drawCircle(rightCircleX, rightCircleY, circleRadius - 1 , rightCirclePaint);

        rightTextPaint.setTextSize(canvasHeight - 20);
        float rightTextHeight = getTextHeight(rightTextPaint);
        canvas.drawText(rightText, circleRadius * 2 + centerWidth + circleRadius,
                rightTextHeight + circleRadius,
                rightTextPaint);

        float centerTextHeight = getTextHeight(centerTextPaint);
        String centerText = String.valueOf(numValue);
        float centerTextWidth = centerTextPaint.measureText(centerText);
        float paddingLeft = (centerWidth - centerTextWidth) / 2;
        canvas.drawText(centerText, circleRadius * 2 + paddingLeft,
                centerTextHeight + circleRadius, centerTextPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isConsumed = false;
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            int x = (int)event.getX();
            //判断点击位置
            if(x <= circleRadius  * 2){
                if(subtractionIsLeft){
                    if(!subtraction()){
                        return true;
                    }
                }else{
                    if(!addition()){
                        return true;
                    }
                }
                onClickListener.onClickLeftChanged(numValue);
                invalidate();
                isConsumed = true;
            }else if(x >= centerWidth + circleRadius ){
                if(subtractionIsLeft){
                    if(!addition()){
                        return true;
                    }
                }else{
                    if(!subtraction()){
                        return true;
                    }
                }
                onClickListener.onClickRightChanged(numValue);
                invalidate();
                isConsumed = true;
            }

        }

        return isConsumed;
    }


    private float getTextHeight(Paint paint){
        Paint.FontMetrics fontMetrics =  paint.getFontMetrics();
        return (Math.abs(fontMetrics.ascent)-fontMetrics.descent)/2;
    }

    /**
     * 增加
     * @return false:以达到最大值，不能增加
     */
    private boolean addition(){
        if(numValue == maxNum){
            return false;
        }
        numValue++;
        return true;
    }

    /**
     * 获取当前商品数量
     * @return
     */
    public int  getNumValue(){
        return numValue;
    }


    /**
     * 减少
     * @return false:已达到最小值，不能减小
     */
    private boolean subtraction(){
        if(numValue == minNum){
            return false;
        }
        numValue--;
        return true;
    }

    public void setOnGoodsNumChangedListener(@Nullable OnGoodsNumChangedListener onClickListener){
        this.onClickListener = onClickListener;
    }



    /**
     * 商品数量更改时的回调
     */
    public interface OnGoodsNumChangedListener{
        /**
         * 点击左边的按钮
         * @param numValue 当前的商品数量
         */
        void onClickLeftChanged(int numValue);

        /**
         * 点击右边的按钮
         * @param numValue 当前的商品数量
         */
        void onClickRightChanged(int numValue);
    }

}
