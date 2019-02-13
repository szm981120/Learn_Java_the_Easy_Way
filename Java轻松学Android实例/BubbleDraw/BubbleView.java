package com.example.shen.bubbledraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/*
BubbleView类中包含了所有气泡绘制代码
为了继承绘图功能，扩展ImageView类
为了处理触摸事件，应用View.OnTouchListener接口
 */
public class BubbleView extends ImageView implements View.OnTouchListener {
    private Random rand = new Random();
    private ArrayList<Bubble> bubbleList;	// 用可变长度的ArrayList来存储Bubble对象
    private int size = 50;	// 气泡默认直径
    private int delay = 33;	// 延迟33ms，即约30帧
    private Paint myPaint = new Paint();    // 创建实例化android.graphics.Paint对象，可以看作是在Android屏幕上绘制气泡的画笔
    /* 创建实例化android.os.Handler对象，用线程来实现动画，线程是同时运行多个应用的多任务环境中的进程，使用线程可减少CPU利用率 */
    /* 使用线程技术来实现动画的优点之一是不会在等待重绘帧期间占着处理器 */
    /* 在手机和平板电脑等处理能力有限的设备上，线程技术显得尤其重要 */
    private android.os.Handler h = new android.os.Handler();

    // 构造函数，导入android.content.Context和android.util.AttributeSet，Android用这俩你个各类来存储有关当前应用的信息
    public BubbleView(Context context, AttributeSet attributeSet){
        // 需要导入那两个类才能调用方法super()，方法super()调用父类ImageVi额外的构造函数来设置应用和绘画屏幕
        super(context, attributeSet);
        bubbleList = new ArrayList<Bubble>();	// 初始化，清空所有气泡
//        testBubbles();	// 测试语句
        setOnTouchListener(this);   // 添加触摸事件监听器
    }
    /* 在Java应用中添加线程，有两种方式：扩展Thread类或实现Runnable接口 */
    private Runnable runnable = new Runnable() {
        @Override
        /* 实现了接口Runnable()的类必须提供方法run()，它告诉线程运行时该做什么 */
        public void run() {
            // 遍历bubbleList中的所有Bubble对象，并绘制到画布上
            for(Bubble bubble : bubbleList)
                bubble.update();
            invalidate();   // 清屏并调用绘制方法onDraw()
        }
    };
    /*
    onDraw()方法告诉Java在屏幕刷新时绘制什么
    必须声明为protected，并将一个Canvas对象作为参数
    （其签名必须与要重写的默认方法View.onDraw()完全相同）
    之所以定义这个方法，是因为所有View子类都必须包含它。BubbleView是ImageView的子类，而ImageView又是View的子类
    每当需要刷新包含BubbleView的屏幕时，都将调用方法onDraw()
     */
    protected void onDraw(Canvas canvas){
        // 遍历bubbleList中的所有Bubble对象，并绘制到画布上
        for(Bubble bubble : bubbleList)
            bubble.draw(canvas);
        /* 添加基于线程的动画的最后一步，是将Handler对象与线程(Runnable对象)关联起来 */
        h.postDelayed(runnable, delay); // 该方法向线程发送一条消息，让它在delay（毫秒）时间后再次运行
    }
    /*
    测试代码
    生成100个气泡，每个气泡的坐标和直径都是随机的，颜色也是随机的
    public void testBubbles(){
        for(int n=0; n<100; n++){
            int x = rand.nextInt(1920);
            int y = rand.nextInt(1080);
            int s = rand.nextInt(size) + size;
            bubbleList.add(new Bubble(x, y, s));
        }
        invalidate();   // 类似repaint()，告诉Java需要更新或刷新屏幕，清屏并调用绘制方法onDraw()
    }
     */

    @Override
    /* 由于我们的BubbleView类应用了OnTouchListener，所以必须要添加onTouch方法，这是用来处理触摸事件的 */
    public boolean onTouch(View view, MotionEvent motionEvent) {
        // 这个循环可以实现多点触控，getPointerCount方法返回当前有多少个触点
        for(int n=0; n<motionEvent.getPointerCount(); n++){
            int x = (int)motionEvent.getX(n);   // 获取用户第n个触摸位置的横坐标
            int y = (int)motionEvent.getY(n);   // 获取用户第n个触摸位置的纵坐标
            int s = rand.nextInt(size) + size;  // 随机生成直径
            bubbleList.add(new Bubble(x, y, s));    // 生成气泡
        }
        /* 在Android中，如果对触摸事件做了全面处理，onTouch方法营返回true，
        如果要让Android去处理滚动或缩放，应让onTouch返回false */
        return true;
    }

    private class Bubble{
        private int x;	// x坐标
        private int y;	// y坐标
        private int size;	// 气泡直径
        /* Android图形中，颜色值存储为整数int，而不是Color对象 */
        private int color;  // 气泡颜色
        // xspeed用于存储每次更新屏幕时气泡沿水平方向移动的像素数，表示水平速度
        // yspeed用于存储每次更新屏幕时气泡沿垂直方向移动的像素数，表示垂直速度
        private int xspeed, yspeed;
        private final int MAX_SPEED = 20;	// 最大速度
        public Bubble(int newX, int newY, int newSize) {
            x = newX;	// 初始化x坐标
            y = newY;	// 初始化y坐标
            size = newSize;	// 初始化气泡直径
            /* 方法Color.argb()不创建新对象，而是将4个ARGB值转换为一个表示颜色的整数 */
            color = Color.argb(rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256));		// 随机初始化一个彩色透明气泡
            xspeed = rand.nextInt(MAX_SPEED*2 - 1) - MAX_SPEED;	// 速度随机，有正负
            if(xspeed == 0)	// 避免静止气泡
                xspeed = 1;
            yspeed = rand.nextInt(MAX_SPEED*2 - 1) - MAX_SPEED;	// 速度随机，由正负
            if(yspeed == 0)	// 避免静止气泡
                yspeed = 1;
        }
        // 方法draw()接受一个类型为android.graphics.Canvas的参数（表示画布）
        public void draw(Canvas canvas) {
            myPaint.setColor(color);	// 将画笔颜色设置为气泡颜色
            // 绘制椭圆，五个参数分别是：左边缘，上边缘，右边缘，下边缘，画笔
            canvas.drawOval(x - size/2,
                    y - size/2,
                    x + size/2,
                    y + size/2,
                    myPaint);
        }
        // 更新气泡状态
        public void update() {
            x += xspeed;	// 更新横坐标，加上水平速度
            y += yspeed;	// 更新纵坐标，加上水平速度
            // 边缘检测，速度方向，表示碰撞反弹
            if(x - size/2 <= 0 || x + size/2 >= getWidth()) {
                xspeed = -xspeed;
            }
            if(y - size/2 <= 0 || y + size/2 >= getHeight()) {
                yspeed = -yspeed;
            }
        }
    }
}
