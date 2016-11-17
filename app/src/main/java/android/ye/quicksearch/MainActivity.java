package android.ye.quicksearch;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends Activity {

    private QuickBar quickBar;
    private ListView listView;
   private ArrayList<Friend> friends = new ArrayList<Friend>();
    private TextView currentWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quickBar = (QuickBar) findViewById(R.id.quick_index_bar);
        listView = (ListView) findViewById(R.id.ll_listview);
        currentWord = (TextView) findViewById(R.id.currentWord);

        //准备数据
        fillList();
        //对数据进行排序
        Collections.sort(friends);
        //设置Adapter
        listView.setAdapter(new MyAdapter(this, friends));

        //设置监听
        quickBar.setOnTouchLetterListener(new QuickBar.OnTouchLetterListener() {
            @Override
            public void onTouchLetter(String letter) {

                for (int i = 0; i < friends.size(); i++) {
                    //根据当前触摸的字母，去集合中找那个item的首字母和letter一样，然后将对应的item放到屏幕顶端
                    String firstWord = friends.get(i).getPinyin().charAt(0) + "";
                    if (letter.equals(firstWord)) {
                        //说明找到了，那么应该讲当前的item放到屏幕顶端
                        listView.setSelection(i);
                        //只需要找到第一个就行
                        break;
                    }
                }
                //显示当前触摸的字母
                showCurrentWord(letter);
            }
        });

        //通过缩小currentWord来隐藏
        ViewHelper.setScaleX(currentWord, 0);
        ViewHelper.setScaleY(currentWord, 0);
    }

    public boolean isScal = false;
    private Handler handler = new Handler();
    private void showCurrentWord(String letter) {
        currentWord.setText(letter);
        if (!isScal){
            isScal = true;
            ViewPropertyAnimator.animate(currentWord).scaleX(1f).setInterpolator(new OvershootInterpolator()).setDuration(500).start();
            ViewPropertyAnimator.animate(currentWord).scaleY(1f).setInterpolator(new OvershootInterpolator()).setDuration(500).start();
        }

        //先移除之前的任务
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewPropertyAnimator.animate(currentWord).scaleX(0f).setDuration(450).start();
                ViewPropertyAnimator.animate(currentWord).scaleY(0f).setDuration(450).start();
                isScal = false;
            }
        },500);
    }

    private void fillList() {
        // 虚拟数据
        friends.add(new Friend("李伟"));
        friends.add(new Friend("张三"));
        friends.add(new Friend("阿三"));
        friends.add(new Friend("阿四"));
        friends.add(new Friend("段誉"));
        friends.add(new Friend("段正淳"));
        friends.add(new Friend("张三丰"));
        friends.add(new Friend("陈坤"));
        friends.add(new Friend("林俊杰1"));
        friends.add(new Friend("陈坤2"));
        friends.add(new Friend("王二a"));
        friends.add(new Friend("林俊杰a"));
        friends.add(new Friend("张四"));
        friends.add(new Friend("林俊杰"));
        friends.add(new Friend("王二"));
        friends.add(new Friend("王二b"));
        friends.add(new Friend("赵四"));
        friends.add(new Friend("杨坤"));
        friends.add(new Friend("赵子龙"));
        friends.add(new Friend("杨坤1"));
        friends.add(new Friend("李伟1"));
        friends.add(new Friend("宋江"));
        friends.add(new Friend("宋江1"));
        friends.add(new Friend("李伟3"));
    }
}
