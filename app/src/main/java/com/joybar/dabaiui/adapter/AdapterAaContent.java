package com.joybar.dabaiui.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.joybar.dabaiui.R;
import com.joybar.dabaiui.data.AaIdAllContentBean;

import java.util.List;

/**
 * Created by joybar on 02/12/16.
 */
public class AdapterAaContent extends BaseAdapter implements SectionIndexer {
    private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局
    private  List<AaIdAllContentBean> list;
    public AdapterAaContent(Context context, List<AaIdAllContentBean> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    public void onDateChange(List<AaIdAllContentBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);

    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.item_aa_content, parent,
                    false);
            // 得到各个控件的对象
            holder.tv_content = (TextView) convertView
                    .findViewById(R.id.tv_content);
            convertView.setTag(holder);// 绑定ViewHolder对象

        } else {
            holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象

        }

        holder.tv_content.setText(list.get(position).getContent());

        ObjectAnimator rotate = ObjectAnimator.ofFloat(convertView, "translationY",convertView.getHeight(),0);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(convertView, "alpha",  0.0f, 0.5f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate).with(fadeIn);
        animSet.setDuration(180);
        animSet.start();


        return convertView;
    }

    /* 存放控件 */
    public final class ViewHolder {

        public TextView tv_content;

    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @SuppressLint("DefaultLocale")
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public Object[] getSections() {
        // TODO Auto-generated method stub
        return null;
    }
}
