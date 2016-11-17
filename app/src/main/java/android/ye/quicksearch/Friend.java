package android.ye.quicksearch;

import android.ye.quicksearch.utils.PinYinUtil;

/**
 * Created by ye on 2016/11/17.
 */
public class Friend implements Comparable<Friend> {
    public String name;
    public String pinyin;

    public Friend(String name){
        super();
        this.name = name;
        //一开始转化好拼音
        setPinyin(PinYinUtil.getPinyin(name));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }


    @Override
    public int compareTo(Friend another) {
        return getPinyin().compareTo(another.getPinyin());
    }
}
