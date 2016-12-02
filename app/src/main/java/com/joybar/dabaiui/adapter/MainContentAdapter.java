package com.joybar.dabaiui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joybar.dabaiui.R;
import com.joybar.dabaiui.data.ChatMessageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joybar on 02/12/16.
 */
public class MainContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatMessageBean> mListData = new ArrayList<>();
    private final long TIME_INTERVAL = 10 * 60 * 1000;
    private final int ITEM_LEFT_TEXT = 0;
    private final int ITEM_RIGHT_TEXT = 1;


    public MainContentAdapter(List<ChatMessageBean> mListData) {
        this.mListData = mListData;
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mListData.get(position).getType() == ChatMessageBean.Type.SEND) {
            return ITEM_RIGHT_TEXT;
        } else {
            return ITEM_LEFT_TEXT;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessageBean chatMessageBean = mListData.get(position);
        if (holder instanceof ItemLeftViewHolder) {
            ((ItemLeftViewHolder) holder).textView.setText(chatMessageBean.getMsg());
        }else {
            ((ItemRightViewHolder) holder).textView.setText(chatMessageBean.getMsg());
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_RIGHT_TEXT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_send, null);
            return new ItemRightViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_receive, null);
            return new ItemLeftViewHolder(view);
        }

    }

    public static class ItemLeftViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ItemLeftViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.chat_from_content);
        }

    }

    public static class ItemRightViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ItemRightViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.chat_send_content);
        }

    }
}
