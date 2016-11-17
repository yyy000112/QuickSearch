package android.ye.quicksearch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.concurrent.RecursiveAction;

/**
 * Created by ye on 2016/11/17.
 */
public class QuickBar extends View {

    private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z" };
    private Paint paint;
    private int width;
    private float unitHeight;


    public QuickBar(Context context) {
        super(context);
        init();
    }

    public QuickBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuickBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public QuickBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG); //设置抗锯齿
        paint.setColor(Color.WHITE);
        paint.setTextSize(48);
        paint.setTextAlign(Paint.Align.CENTER);//设置文本的起点是文字边框底边的中心

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredWidth();
        //得到一个格子的高度
        unitHeight = getMeasuredHeight()*1f/indexArr.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i<indexArr.length; i++){
            float x = width/2;
            float y = getTextHeight(indexArr[i])/2 + unitHeight/2 + i*unitHeight;
            paint.setColor(lastIndex==i?Color.BLACK:Color.WHITE);
            canvas.drawText(indexArr[i],x,y,paint);
        }
    }

    private int lastIndex=-1;//记录上次的触摸字母的索引

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                float y = event.getY();
                int index = (int) (y/unitHeight);//获得索引值
                if (lastIndex != index){
                    //说明当前触摸字母和上一个不是同一个字母
                    if (index>=0 && index<indexArr.length){
                        if (listener!=null){
                            listener.onTouchLetter(indexArr[index]);
                        }
                    }
                }
                lastIndex = index;
                break;
            case MotionEvent.ACTION_UP:
                //将索引置为-1
                lastIndex = -1;
                break;
        }
        invalidate();//重绘
        return true;
    }

    /**
     * 获取文本的高度
     * @param text
     * @return
     */
    private int getTextHeight(String text){
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    private OnTouchLetterListener listener;
    public void setOnTouchLetterListener(OnTouchLetterListener listener){
        this.listener = listener;
    }

    /**
     * 设置字母监听器
     */
    public interface OnTouchLetterListener{
        public void onTouchLetter(String letter);
    }
}
