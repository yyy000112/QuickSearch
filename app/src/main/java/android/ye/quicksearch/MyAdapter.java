package android.ye.quicksearch;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

/**
 * Created by ye on 2016/11/17.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Friend> list;

    public MyAdapter(Context context,ArrayList<Friend> list){
        super();
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = View.inflate(context,R.layout.list_friend,null);
        }
        ViewHolder holder = ViewHolder.getHolder(convertView);
        //设置数据
        Friend friend = list.get(position);
        holder.name.setText(friend.getName());
        String currentWord = friend.getPinyin().charAt(0)+"";

        if (position>0){
            //获取上一个的首个拼音字母
            String preWord = list.get(position-1).getPinyin().charAt(0)+"";
            if (currentWord.equals(preWord)){
                //如跟前一个首字母相同则隐藏当前item的first_word
                holder.firstWord.setVisibility(View.GONE);
            }else {
                holder.firstWord.setVisibility(View.VISIBLE);
                holder.firstWord.setText(currentWord);
            }
        }else {
            holder.firstWord.setVisibility(View.VISIBLE);
            holder.firstWord.setText(currentWord);
        }
        return convertView;
    }

   static class ViewHolder{
        TextView name;
        TextView firstWord;
        public ViewHolder(View convertView){
            name = (TextView) convertView.findViewById(R.id.name);
            firstWord = (TextView) convertView.findViewById(R.id.first_word);
        }
        public static ViewHolder getHolder(View convertView){
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null){
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}
