package com.joybar.dabaiui.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.joybar.dabaiui.R;
import com.joybar.dabaiui.adapter.AdapterAaContent;
import com.joybar.dabaiui.adapter.MainContentAdapter;
import com.joybar.dabaiui.data.AaIdAllContentBean;
import com.joybar.dabaiui.data.ChatMessageBean;
import com.joybar.dabaiui.helper.SpaceItemDecoration;
import com.joybar.dabaiui.utis.ChatListViewAniUtils;
import com.joybar.dabaiui.utis.ScreenUtils;
import com.joybar.dabaiui.view.ElasticListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joybar on 02/12/16.
 */
public class FragmentMain extends Fragment implements View.OnClickListener {

    private List<ChatMessageBean> mListData = new ArrayList<>();
    private List<AaIdAllContentBean> listAaContent =new ArrayList<>();
    private MainContentAdapter mAdapter;
    private  AdapterAaContent adapterAaContent;
    private RecyclerView recyclerView;
    private ElasticListView listview_aa_content;
    private ImageView imv_chat_send;
    private ImageView imv_chat_AA;
    private EditText et_chat_msg;

    private InputMethodManager inputManager;// 输入法

    public FragmentMain() {
        // Requires empty public constructor
    }

    public static FragmentMain newInstance() {
        FragmentMain fragmentMain = new FragmentMain();
        return fragmentMain;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_content, container, false);
        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        intData();
        initView(view);
        setListener();
        return view;

    }

    private void initView(View view) {

        imv_chat_send = (ImageView) view.findViewById(R.id.imv_chat_send);
        imv_chat_AA = (ImageView) view.findViewById(R.id.imv_chat);
        et_chat_msg = (EditText) view.findViewById(R.id.et_chat_msg);
        listview_aa_content = (ElasticListView) view.findViewById(R.id.listview_aa_content);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_chat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new MainContentAdapter(mListData);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(ScreenUtils.convertDipOrPx(15)));

        adapterAaContent = new AdapterAaContent(getActivity(),listAaContent);
        listview_aa_content.setAdapter(adapterAaContent);

    }

    private void intData() {
        String msg1 = "大白是你24小时的私人助理，随时等候你的差遣。把你的需求告诉大白，剩下的事就交给我们去处理吧";
        ChatMessageBean chatMessageBean1 = new ChatMessageBean(ChatMessageBean.Type.RECEIVE,msg1);
        String msg2 = "亲，我是大白，有什么可以帮助你的吗";
        ChatMessageBean chatMessageBean2 = new ChatMessageBean(ChatMessageBean.Type.RECEIVE,msg2);

        String msg3 = "可以帮我叫辆车吗，我想去火车东站";
        ChatMessageBean chatMessageBean3 = new ChatMessageBean(ChatMessageBean.Type.SEND,msg3);

        String msg4 = "好的，这就给你叫车，请稍等";
        ChatMessageBean chatMessageBean4 = new ChatMessageBean(ChatMessageBean.Type.RECEIVE,msg4);

        mListData.add(chatMessageBean1);
        mListData.add(chatMessageBean2);
        mListData.add(chatMessageBean3);
        mListData.add(chatMessageBean4);


        AaIdAllContentBean aaIdAllContentBean1 = new AaIdAllContentBean("我要打滴滴","出行");
        AaIdAllContentBean aaIdAllContentBean2 = new AaIdAllContentBean("我要打出租车","出行");

        listAaContent.add(aaIdAllContentBean1);
        listAaContent.add(aaIdAllContentBean2);


    }
   boolean isStartAni = false;
    private void setListener() {
        imv_chat_send.setOnClickListener(this);
        imv_chat_AA.setOnClickListener(this);
        listview_aa_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                imv_chat_AA.setBackgroundResource(R.drawable.icon_hide);
                ChatListViewAniUtils.hideAAContent(listAaContent.size(),(RelativeLayout) getActivity().findViewById(R.id.rel_lv), (RelativeLayout)getActivity().findViewById(R.id.rel_aa));
                ChatMessageBean chatMessageBean = new ChatMessageBean(ChatMessageBean.Type.SEND,listAaContent.get(i).getContent());
                mListData.add(chatMessageBean);
                mAdapter.notifyItemRangeInserted(mListData.size()-1, 1);
                recyclerView.smoothScrollToPosition(mListData.size()-1);
            }
        });
        et_chat_msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (getActivity().findViewById(R.id.rel_aa).getVisibility() == View.VISIBLE) {
                    ChatListViewAniUtils.hideAAContent(listAaContent.size(),(RelativeLayout) getActivity().findViewById(R.id.rel_lv), (RelativeLayout)getActivity().findViewById(R.id.rel_aa));
                    imv_chat_AA.setBackgroundResource(R.drawable.icon_hide);

                }
            }
        });
        et_chat_msg.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_chat_msg.getText().toString().isEmpty()) {
                    imv_chat_send.setBackgroundResource(R.drawable.icon_send_empty);
                    isStartAni = false;
                } else {
                    if (isStartAni == false) {
                        isStartAni = true;
                        imv_chat_send.setBackgroundResource(R.drawable.icon_send_not_empty);
                        startSenImgAni();

                    }
                }

            }
        });

    }
    private void startSenImgAni(){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(
                imv_chat_send, "scaleX", 0.3f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(
                imv_chat_send, "scaleY", 0.3f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animatorX).with(animatorY);
        animSet.setDuration(300);
        animSet.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imv_chat_send:
                if(et_chat_msg.getText().toString().isEmpty()){
                    Snackbar.make(et_chat_msg, "亲，请说出你的需求哦", Snackbar.LENGTH_LONG).show();
                }else{
                    ChatMessageBean chatMessageBean = new ChatMessageBean(ChatMessageBean.Type.SEND,et_chat_msg.getText().toString());
                    mListData.add(chatMessageBean);
                    mAdapter.notifyItemRangeInserted(mListData.size()-1, 1);
                    recyclerView.smoothScrollToPosition(mListData.size()-1);
                    et_chat_msg.setText(null);
                    startSenImgAni();
                }
                break;
            case R.id.imv_chat:
                inputManager.hideSoftInputFromWindow(et_chat_msg.getWindowToken(), 0); // 强制隐藏键盘
                if (getActivity().findViewById(R.id.rel_aa).getVisibility() != View.VISIBLE) {
                    ChatListViewAniUtils.showAAContent(listAaContent.size(),(RelativeLayout) getActivity().findViewById(R.id.rel_lv), (RelativeLayout)getActivity().findViewById(R.id.rel_aa));
                    imv_chat_AA.setBackgroundResource(R.drawable.icon_show);
                }else{
                    ChatListViewAniUtils.hideAAContent(listAaContent.size(),(RelativeLayout) getActivity().findViewById(R.id.rel_lv), (RelativeLayout)getActivity().findViewById(R.id.rel_aa));
                }
                break;


            default:
                break;
        }
    }


}
