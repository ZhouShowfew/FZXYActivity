package com.example.steven.fzxyactivity.materialdesign.views;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;


import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.materialdesign.utils.DisplayUtil;

import java.math.BigDecimal;
import java.util.ArrayList;


/**
 * Created by Administrator on 2015/8/5.
 */
public class SesameCreditView extends View {
    private Point mCenterPoint = new Point();
    private int mRadius;
    private Paint mDefaultPaint = new Paint();
    private Paint mLineDefaultPaint = new Paint();
    private Paint mVisivlePaint = new Paint();
    private Paint mTextPaint = new Paint();
    private Paint mBitmapPaint = new Paint();
    private double mIdentityValue;
    private double mCreditValue;
    private double mCreditHistoryValue;
    private double mPepoleRelativeValue;
    private double mActionFavoriteValue;
    private String mDefaultText = "未知";
    private Rect mTextRect = new Rect();
    private float mDefaultRatio = 0.5f;
    private int mPicAndViewSpacing;
    private Activity activity = null;
    private PopupWindow popWin;
    private ArrayList<Region> mPicAreas = new ArrayList<Region>();
    private int[] mPicResIds = new int[]{R.mipmap.wealth_icon, R.mipmap.ability_icon, R.mipmap
            .charm_icon,
                                         R.mipmap.credit_icon, R.mipmap.prestige_icon};
    private ArrayList<Bitmap> mPicBitmap;

    private final ValueAnimator mIdentityAnimator = new ValueAnimator();//财富
    private final ValueAnimator mCreditAnimator = new ValueAnimator();//能力
    private final ValueAnimator mCreditHistoryAnimator = new ValueAnimator();//魅力值
    private final ValueAnimator mPepoleRelativeAnimator = new ValueAnimator();//历史信用
    private final ValueAnimator mActionFavoriteAnimator = new ValueAnimator();//声望值
    private float f1 = 0f, f2 = 0f, f3 = 0f, f4 = 0f, f5 = 0f;
    private String exp = null;

    public SesameCreditView(Context context) {
        this(context, null);
    }

    public SesameCreditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SesameCreditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initAnimator(f1, f2, f3, f4, f5, exp);
        loadPicBitmap();
        mPicAndViewSpacing = DisplayUtil.dip2px(context, 20);
    }

    private void loadPicBitmap() {
        mPicBitmap = new ArrayList<Bitmap>();
        for (int i = 0; i < mPicResIds.length; i++) {
            mPicBitmap.add(BitmapFactory.decodeResource(getResources(), mPicResIds[i]));
        }
    }

    public void initAnimator(float f1, float f2, float f3, float f4, float f5, String exp) {
        mIdentityAnimator.setFloatValues(mDefaultRatio, f1);
        mCreditAnimator.setFloatValues(mDefaultRatio, f2);
        mCreditHistoryAnimator.setFloatValues(mDefaultRatio, f3);
        mPepoleRelativeAnimator.setFloatValues(mDefaultRatio, f4);
        mActionFavoriteAnimator.setFloatValues(mDefaultRatio, f5);
        mDefaultText = exp;

        mIdentityAnimator.setDuration(1000L);
        mCreditAnimator.setDuration(1000L);
        mCreditHistoryAnimator.setDuration(1000L);
        mPepoleRelativeAnimator.setDuration(1000L);
        mActionFavoriteAnimator.setDuration(1000L);

        mIdentityAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mIdentityValue = (float) animation.getAnimatedValue() * mRadius;
                InvalidateView();
            }
        });
        mCreditAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCreditValue = (float) animation.getAnimatedValue() * mRadius;
                InvalidateView();
            }
        });
        mCreditHistoryAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCreditHistoryValue = (float) animation.getAnimatedValue() * mRadius;
                InvalidateView();
            }
        });
        mPepoleRelativeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPepoleRelativeValue = (float) animation.getAnimatedValue() * mRadius;
                InvalidateView();
            }
        });
        mActionFavoriteAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mActionFavoriteValue = (float) animation.getAnimatedValue() * mRadius;
                InvalidateView();
            }
        });
    }

    private void initPaint() {
        mDefaultPaint.setDither(true);
        mDefaultPaint.setAntiAlias(true);
        mDefaultPaint.setColor(Color.rgb(239, 245, 243));
        mDefaultPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mLineDefaultPaint.setDither(true);
        mLineDefaultPaint.setAntiAlias(true);
        mLineDefaultPaint.setStrokeWidth(1f);
        mLineDefaultPaint.setColor(Color.rgb(212, 218, 216));
        mLineDefaultPaint.setStyle(Paint.Style.STROKE);

        mVisivlePaint.setDither(true);
        mVisivlePaint.setAntiAlias(true);
        mVisivlePaint.setStrokeWidth(3f);
        mVisivlePaint.setColor(Color.rgb(78, 216, 180));
        mVisivlePaint.setAlpha(155);
        mVisivlePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mTextPaint.setDither(true);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setFakeBoldText(true);
        /**
         * 设置中间白字大小
         */
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 32,
                getResources().getDisplayMetrics()));

        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);
        mBitmapPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (mPicAreas.size() == 0) {
                    return true;
                }
                for (int i = 0; i < mPicAreas.size(); i++) {
                    Region mPicArea = mPicAreas.get(i);
                    if (mPicArea.contains((int) event.getX(), (int) event.getY())) {
                        /*这里展示各个字段的定义*/
                        //参数对应雷达图值顺序 财富,能力,魅力,信用,声望
//                        showExpression(i);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_CANCEL:

                break;
        }
        return true;
    }

//    private void showExpression(int i) {
//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service
// .LAYOUT_INFLATER_SERVICE);
//        View linear = inflater.inflate(R.layout.fragment_me,
//                (ViewGroup) findViewById(R.id.fragment_me_liner));
//        View layout = inflater.inflate(R.layout.info_expression_win,
//                (ViewGroup) findViewById(R.id.expression_blank));
//        ImageView expressIcon = (ImageView) layout.findViewById(R.id.express_icon);
//        TextView expressText = (TextView) layout.findViewById(R.id.express_text);
//        ImageView iKnow = (ImageView) layout.findViewById(R.id.i_know);
//        //参数对应雷达图值顺序 财富,能力,魅力,信用,声望
//        switch (i) {
//            case 0:
//                expressIcon.setBackgroundResource(R.drawable.wealth_icon);
//                expressText.setText(R.string.wealth_expression);
//                break;
//            case 1:
//                expressIcon.setBackgroundResource(R.drawable.ability_icon);
//                expressText.setText(R.string.ability_expression);
//                break;
//            case 2:
//                expressIcon.setBackgroundResource(R.drawable.charm_icon);
//                expressText.setText(R.string.charm_expression);
//                break;
//            case 3:
//                expressIcon.setBackgroundResource(R.drawable.prestige_icon);
//                expressText.setText(R.string.prestige_expression);
//                break;
//            case 4:
//                expressIcon.setBackgroundResource(R.drawable.charm_icon);
//                expressText.setText(R.string.wealth_expression);
//                break;
//            default:
//                break;
//        }
//
//        popWin = new PopupWindow(layout, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        //设置背景才可以消除popwin
//        popWin.setBackgroundDrawable(new BitmapDrawable());
////        popWin.setOutsideTouchable(true);
////        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service
// .LAYOUT_INFLATER_SERVICE);
////        WindowManager win= (WindowManager) getContext().getSystemService(Context
// .WINDOW_SERVICE);
//        activity = (Activity) getContext();
//        WindowManager windowManager = activity.getWindowManager();
//        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
//        params.alpha = 0.6f;
//        activity.getWindow().setAttributes(params);
//        //显示在指定位置
//        popWin.showAtLocation(linear, Gravity.CENTER, 0, 0);
//        iKnow.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                popWin.dismiss();
//                popWin = null;
//                WindowManager.LayoutParams params = activity.getWindow().getAttributes();
//                params.alpha = 1f;
//                activity.getWindow().setAttributes(params);
//                activity = null;
//            }
//        });
//
//    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Point mPoint = getPICMaxHeightAndWidth();
        int mViewWidth = w - (mPoint == null ? 0 : 2 * mPoint.x) - 2 * mPicAndViewSpacing;
        int mViewHeight = h - (mPoint == null ? 0 : 2 * mPoint.y) - 2 * mPicAndViewSpacing;
        int mCenterX = w / 2;
        int mCenterY = h / 2;
        mCenterPoint.set(mCenterX, mCenterY);
        mRadius = mViewWidth > mViewHeight ? mViewHeight / 2 : mViewWidth / 2;
        //默认为半径一半
        mIdentityValue = mRadius * mDefaultRatio;
        mCreditValue = mRadius * mDefaultRatio;
        mCreditHistoryValue = mRadius * mDefaultRatio;
        mPepoleRelativeValue = mRadius * mDefaultRatio;
        mActionFavoriteValue = mRadius * mDefaultRatio;

        postDelayed(new Runnable() {
            @Override
            public void run() {
                mIdentityAnimator.start();
                mCreditAnimator.start();
                mCreditHistoryAnimator.start();
                mPepoleRelativeAnimator.start();
                mActionFavoriteAnimator.start();
            }
        }, 900);
    }

    private Point getPICMaxHeightAndWidth() {
        if (mPicBitmap == null || mPicBitmap.size() == 0) {
            return null;
        }
        Point point = new Point();
        int maxHeight = 0, maxWidth = 0;
        for (int i = 0; i < mPicBitmap.size(); i++) {
            maxHeight = Math.max(maxHeight, mPicBitmap.get(i).getHeight());
            maxWidth = Math.max(maxWidth, mPicBitmap.get(i).getWidth());
        }
        point.set(maxWidth, maxHeight);
        return point;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制图片
        int mPicValue = mRadius + mPicAndViewSpacing;
        ArrayList<PointF> mPICDefaultPointF = getPoints(mCenterPoint, mPicValue, mPicValue,
                mPicValue, mPicValue, mPicValue);
        drawBitmap(canvas, mPICDefaultPointF);

        //绘制默认灰色背景
        ArrayList<PointF> mDefaultPointF = getPoints(mCenterPoint, mRadius, mRadius, mRadius,
                mRadius, mRadius);
        ArrayList<Path> mDefaultPath = getPaths(mCenterPoint, mDefaultPointF);
        drawView(canvas, mDefaultPath, mDefaultPaint);

        //为灰色背景添加明显线条,以便区分块儿
        ArrayList<PointF> mLineDefaultPointF = getPoints(mCenterPoint, mRadius, mRadius, mRadius,
                mRadius, mRadius);
        ArrayList<Path> mLineDefaultPath = getPaths(mCenterPoint, mLineDefaultPointF);
        drawView(canvas, mLineDefaultPath, mLineDefaultPaint);

        //绘制显示色块，初始半径的五分之一
        ArrayList<PointF> mVisivlePointF = getPoints(mCenterPoint, mIdentityValue, mCreditValue,
                mCreditHistoryValue, mPepoleRelativeValue, mActionFavoriteValue);
        ArrayList<Path> mVisivlePath = getPaths(mCenterPoint, mVisivlePointF);
        drawView(canvas, mVisivlePath, mVisivlePaint);
    }

    private void drawBitmap(Canvas canvas, ArrayList<PointF> mPICDefaultPointF) {
        if (mPicBitmap == null || mPicBitmap.size() == 0) {
            return;
        }
        if (mPICDefaultPointF == null || mPICDefaultPointF.size() == 0) {
            return;
        }
        mPicAreas.clear();
        for (int i = 0; i < mPicBitmap.size(); i++) {
            PointF point = mPICDefaultPointF.get(i);
            Bitmap bitmap = mPicBitmap.get(i);
            Region area = new Region();
            area.set((int) (point.x - bitmap.getWidth() / 2), (int) (point.y -
                    bitmap.getHeight() / 2), (int) (point.x + bitmap.getWidth() / 2), (int) (
                    point.y + bitmap.getHeight() / 2));
            mPicAreas.add(area);
            canvas.drawBitmap(bitmap,
                    point.x - bitmap.getWidth() / 2,
                    point.y - bitmap.getHeight() / 2, mBitmapPaint);
        }

    }

    private void drawView(Canvas mCanvas, ArrayList<Path> paths, Paint mPaint) {
        if (paths == null || paths.size() == 0) {
            return;
        }
        for (int i = 0; i < paths.size(); i++) {
            mCanvas.drawPath(paths.get(i), mPaint);
        }

        if (TextUtils.isEmpty(mDefaultText)) {
            mDefaultText = "未知";
        }
        mTextPaint.getTextBounds(mDefaultText, 0, mDefaultText.length(), mTextRect);
        mCanvas.drawText(mDefaultText,
                mCenterPoint.x - (mTextRect.width() / 2),
                mCenterPoint.y + (mTextRect.height() / 2), mTextPaint);
    }

    private ArrayList<Path> getPaths(Point center, ArrayList<PointF> points) {
        if (points == null || points.size() == 0) {
            return null;
        }
        ArrayList<Path> paths = new ArrayList<Path>();
        for (int i = 0; i < points.size(); i++) {
            Path path = new Path();
            path.reset();
            path.moveTo(points.get(i).x, points.get(i).y);
            path.lineTo(center.x, center.y);
            path.lineTo(points.get(i == points.size() - 1 ? 0 : i + 1).x, points.get(
                    i == points.size() - 1 ? 0 : i + 1).y);
            path.close();
            paths.add(path);
        }
        return paths;
    }

    /**
     * 获取各个点
     *
     * @param center               中心点
     * @param mIdentityValue       身份特质值
     * @param mCreditValue         履约能力值
     * @param mCreditHistoryValue  信用历史值
     * @param mPepoleRelativeValue 人脉关系值
     * @param mActionFavorite      行为偏好值
     */
    private ArrayList<PointF> getPoints(Point center, double mIdentityValue, double mCreditValue,
                                        double mCreditHistoryValue, double mPepoleRelativeValue,
                                        double mActionFavorite) {
        ArrayList<PointF> points = new ArrayList<PointF>();
        points.add(new PointF(center.x, toFloat(center.y - mIdentityValue)));
        points.add(new PointF(toFloat(
                center.x + Math.sin(Math.toRadians(72D)) * mCreditValue), toFloat(
                center.y - Math.cos(Math.toRadians(72d)) * mCreditValue)));
        points.add(new PointF(toFloat(
                center.x + Math.cos(Math.toRadians(54D)) * mCreditHistoryValue), toFloat(
                center.y + Math.sin(Math.toRadians(54d)) * mCreditHistoryValue)));
        points.add(new PointF(toFloat(
                center.x - Math.cos(Math.toRadians(54D)) * mPepoleRelativeValue), toFloat(
                center.y + Math.sin(Math.toRadians(54d)) * mPepoleRelativeValue)));
        points.add(new PointF(toFloat(
                center.x - Math.sin(Math.toRadians(72D)) * mActionFavorite), toFloat(
                center.y - Math.cos(Math.toRadians(72d)) * mActionFavorite)));
        return points;
    }

    public float toFloat(double d) {
        BigDecimal bigDecimal = new BigDecimal(d);
        return bigDecimal.floatValue();
    }

    public void InvalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
}
