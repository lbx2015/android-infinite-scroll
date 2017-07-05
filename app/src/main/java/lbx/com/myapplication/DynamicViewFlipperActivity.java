package lbx.com.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ViewFlipper;

/**
 * Created by zw.zhang on 2017/7/5.
 */

public class DynamicViewFlipperActivity extends AppCompatActivity {
    private ViewFlipper flipper;
    private GestureDetector detector;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_viewflipper);
        init();
    }
    private void init(){
        flipper = (ViewFlipper) findViewById(R.id.vf);
        flipper.addView(getImageView(R.drawable.sample_0));//在一个根视图中动态添加子视图，可以根据new对象来进行子视图的增加和删减
        flipper.addView(getImageView(R.drawable.sample_1));
        flipper.addView(getImageView(R.drawable.sample_3));
        flipper.setInAnimation(inFromRightAnimation());//设置View进入屏幕时候使用的动画
        flipper.setOutAnimation(outToLeftAnimation());  //设置View退出屏幕时候使用的动画
        flipper.setFlipInterval(2000);//设置自动切换的间隔时间
        flipper.startFlipping();//开启切换效果
        flipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return DynamicViewFlipperActivity.this.detector.onTouchEvent(event);//当有触屏事件作用于ViewFlipper时，把这个触屏事件返回给 GestureDetector手势识别器处理
                //如果想要把整个屏幕上的触屏事件都返回给 GestureDetector手势识别器处理，
                //而不单单是在ViewFlipper上发生触屏事件时就给 GestureDetector处理，
                //那么我们就不必为flipper单独设置onTouchListener（）触摸监听器了，就只需要重写Activity中的onTouchEvent()方法，
                //在该方法中讲整个屏幕的触摸事件返回给手势识别器处理就行
            }
        });

        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {//创建手势识别器并监听手势事件

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                    float distanceY) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                // TODO Auto-generated method stub
            }
            /*
                 * 用户按下触摸屏、快速移动后松开即触发这个事件
                 * e1：第1个ACTION_DOWN MotionEvent
                 * e2：最后一个ACTION_MOVE MotionEvent
                 * velocityX：X轴上的移动速度，像素/秒
                 * velocityY：Y轴上的移动速度，像素/秒
                 * 触发条件 ：
                 * X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
                 */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {//滑动事件回调函数

                final float FLING_MIN_DISTANCE = 100;//最小滑动像素
                final float FLING_MIN_VELOCITY = 150;//最小滑动速度
                if(e1.getX() - e2.getX() > FLING_MIN_DISTANCE &&
                        Math.abs(velocityX) > FLING_MIN_VELOCITY){//X轴上的移动速度去绝对值进行比较
                    //判断x轴坐标如果第一次按下时的坐标减去第二次离开屏幕时的坐标大于我们设置的位移，因为一个控件的原点是在左上角，就说明此时是向左滑动的
                    //设置View进入屏幕时候使用的动画
                    flipper.setInAnimation(inFromRightAnimation());
                    //设置View退出屏幕时候使用的动画
                    flipper.setOutAnimation(outToLeftAnimation());
                    flipper.showNext();//显示下一个视图

                }else if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE &&
                        Math.abs(velocityX) > FLING_MIN_VELOCITY){
                    //判断x轴坐标如果第二次离开屏幕时的坐标减去第一次按下时的坐标大于我们设置的位移，因为一个控件的原点是在左上角，就说明此时是向右滑动的
                    flipper.setInAnimation(inFromLeftAnimation());
                    flipper.setOutAnimation(outToRightAnimation());
                    flipper.showPrevious();//显示上一个视图
                }
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {//手指按下屏幕时触发
                flipper.stopFlipping();//当触摸到ViewFlipper时，就让它停止自动滑动，如果要触摸到整个屏幕的任意一处就让它停止滑动，就按上面那个方法，不用设置ViewFlipper的触摸事件监听了
                new Handler().postDelayed(new Runnable() {//在当前线程（也即主线程中）开启一个消息处理器，并在3秒后发送flipper.startFlipping();这个消息给主线程，再让它自动滑动，从而来更新UI
                    @Override
                    public void run() {
                        flipper.startFlipping();//3秒后执行，让它又开始滑动
                    }
                }, 3000);
                return true;
            }
        });
    }
    //创建一个ImageView对象
    private ImageView getImageView(int id){
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(id);
        return imageView;
    }
    /**
     * 定义从右侧进入的动画效果
     * @return
     */
    protected Animation inFromRightAnimation() {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(200);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    /**
     * 定义从左侧退出的动画效果
     * @return
     */
    protected Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(200);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    /**
     * 定义从左侧进入的动画效果
     * @return
     */
    protected Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(200);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    /**
     * 定义从右侧退出时的动画效果
     * @return
     */
    protected Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(200);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }

}
