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
import com.joybar.dabaiui.view.LetterSortView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joybar on 02/12/16.
 */
public class FragmentMain extends Fragment implements View.OnClickListener {

    private List<ChatMessageBean> mListData = new ArrayList<>();
    private List<AaIdAllContentBean> mListAaContent =new ArrayList<>();
    List<String> listSortLetter = new ArrayList<String>();
    private MainContentAdapter mAdapter;
    private AdapterAaContent mAdapterAaContent;
    private RecyclerView mRecyclerView;
    private ElasticListView mListViewAA;
    private LetterSortView mSortLetter;
    private ImageView imvChatSend;
    private ImageView imvChatAATAG;
    private EditText etChatMsg;

    private InputMethodManager inputManager;

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

        imvChatSend = (ImageView) view.findViewById(R.id.imv_chat_send);
        imvChatAATAG = (ImageView) view.findViewById(R.id.imv_chat);
        etChatMsg = (EditText) view.findViewById(R.id.et_chat_msg);
        mListViewAA = (ElasticListView) view.findViewById(R.id.listview_aa_content);
        mSortLetter = (LetterSortView) view.findViewById(R.id.right_letter);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_chat);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new MainContentAdapter(mListData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(ScreenUtils.convertDipOrPx(15)));

        mAdapterAaContent = new AdapterAaContent(getActivity(),mListAaContent);
        mListViewAA.setAdapter(mAdapterAaContent);

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


        AaIdAllContentBean aaIdAllContentBean1 = new AaIdAllContentBean("我要叫滴滴","出行");
        AaIdAllContentBean aaIdAllContentBean2 = new AaIdAllContentBean("我要叫出租车","出行");

        AaIdAllContentBean aaIdAllContentBean3 = new AaIdAllContentBean("我要吃麦当劳","外卖");
        AaIdAllContentBean aaIdAllContentBean4 = new AaIdAllContentBean("我要吃必胜客","外卖");
        AaIdAllContentBean aaIdAllContentBean5 = new AaIdAllContentBean("我要叫肯德基","外卖");

        AaIdAllContentBean aaIdAllContentBean6 = new AaIdAllContentBean("找个厨师上门","生活");
        AaIdAllContentBean aaIdAllContentBean7 = new AaIdAllContentBean("找个打扫卫生的阿姨","生活");
        AaIdAllContentBean aaIdAllContentBean8 = new AaIdAllContentBean("我要预约理发师","生活");
        AaIdAllContentBean aaIdAllContentBean9 = new AaIdAllContentBean("我要买飞机票","票务");
        AaIdAllContentBean aaIdAllContentBean10 = new AaIdAllContentBean("我要买电影票","票务");

        AaIdAllContentBean aaIdAllContentBean11 = new AaIdAllContentBean("我想去吃海底捞","美食");
        AaIdAllContentBean aaIdAllContentBean12 = new AaIdAllContentBean("我想吃广东菜","美食");
        AaIdAllContentBean aaIdAllContentBean13 = new AaIdAllContentBean("我要吃海鲜","美食");

        mListAaContent.add(aaIdAllContentBean1);
        mListAaContent.add(aaIdAllContentBean2);
        mListAaContent.add(aaIdAllContentBean3);
        mListAaContent.add(aaIdAllContentBean4);
        mListAaContent.add(aaIdAllContentBean5);
        mListAaContent.add(aaIdAllContentBean6);
        mListAaContent.add(aaIdAllContentBean7);
        mListAaContent.add(aaIdAllContentBean8);
        mListAaContent.add(aaIdAllContentBean9);
        mListAaContent.add(aaIdAllContentBean10);

        mListAaContent.add(aaIdAllContentBean11);
        mListAaContent.add(aaIdAllContentBean12);
        mListAaContent.add(aaIdAllContentBean13);

        String type1 = "出行";
        String type2 = "外卖";
        String type3 = "生活";
        String type4 = "票务";
        String type5 = "美食";
        listSortLetter.add(type1);
        listSortLetter.add(type2);
        listSortLetter.add(type3);
        listSortLetter.add(type4);
        listSortLetter.add(type5);

        mSortLetter.setListIndex(listSortLetter);


    }
   boolean isStartAni = false;
    private void setListener() {
        imvChatSend.setOnClickListener(this);
        imvChatAATAG.setOnClickListener(this);
        mListViewAA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                imvChatAATAG.setBackgroundResource(R.drawable.icon_hide);
                ChatListViewAniUtils.hideAAContent(mListAaContent.size(),(RelativeLayout) getActivity().findViewById(R.id.rel_lv), (RelativeLayout)getActivity().findViewById(R.id.rel_aa));
                ChatMessageBean chatMessageBean = new ChatMessageBean(ChatMessageBean.Type.SEND,mListAaContent.get(i).getContent());
                mListData.add(chatMessageBean);
                mAdapter.notifyItemRangeInserted(mListData.size()-1, 1);
                mRecyclerView.smoothScrollToPosition(mListData.size()-1);
            }
        });
        etChatMsg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (getActivity().findViewById(R.id.rel_aa).getVisibility() == View.VISIBLE) {
                    ChatListViewAniUtils.hideAAContent(mListAaContent.size(),(RelativeLayout) getActivity().findViewById(R.id.rel_lv), (RelativeLayout)getActivity().findViewById(R.id.rel_aa));
                    imvChatAATAG.setBackgroundResource(R.drawable.icon_hide);

                }
            }
        });

        // 设置右侧触摸监听
        mSortLetter
                .setOnTouchingLetterChangedListener(new LetterSortView.OnTouchingLetterChangedListener() {

                    @Override
                    public void onTouchingLetterChanged(String s) {
                        int position = mAdapterAaContent.getPositionForSection(s
                                .charAt(0));
                        if (position != -1) {
                            mListViewAA.setSelection(position);
                        }
                    }
                });
        etChatMsg.addTextChangedListener(new TextWatcher() {

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
                if (etChatMsg.getText().toString().isEmpty()) {
                    imvChatSend.setBackgroundResource(R.drawable.icon_send_empty);
                    isStartAni = false;
                } else {
                    if (isStartAni == false) {
                        isStartAni = true;
                        imvChatSend.setBackgroundResource(R.drawable.icon_send_not_empty);
                        startSenImgAni();

                    }
                }

            }
        });

    }
    private void startSenImgAni(){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(
                imvChatSend, "scaleX", 0.3f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(
                imvChatSend, "scaleY", 0.3f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animatorX).with(animatorY);
        animSet.setDuration(300);
        animSet.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imv_chat_send:
                if(etChatMsg.getText().toString().isEmpty()){
                    Snackbar.make(etChatMsg, "亲，请说出你的需求哦", Snackbar.LENGTH_LONG).show();
                }else{
                    ChatMessageBean chatMessageBean = new ChatMessageBean(ChatMessageBean.Type.SEND,etChatMsg.getText().toString());
                    mListData.add(chatMessageBean);
                    mAdapter.notifyItemRangeInserted(mListData.size()-1, 1);
                    mRecyclerView.smoothScrollToPosition(mListData.size()-1);
                    etChatMsg.setText(null);
                    startSenImgAni();
                }
                break;
            case R.id.imv_chat:
                inputManager.hideSoftInputFromWindow(etChatMsg.getWindowToken(), 0);
                if (getActivity().findViewById(R.id.rel_aa).getVisibility() != View.VISIBLE) {
                    ChatListViewAniUtils.showAAContent(mListAaContent.size(),(RelativeLayout) getActivity().findViewById(R.id.rel_lv), (RelativeLayout)getActivity().findViewById(R.id.rel_aa));
                    imvChatAATAG.setBackgroundResource(R.drawable.icon_show);
                }else{
                    ChatListViewAniUtils.hideAAContent(mListAaContent.size(),(RelativeLayout) getActivity().findViewById(R.id.rel_lv), (RelativeLayout)getActivity().findViewById(R.id.rel_aa));
                }
                break;


            default:
                break;
        }
    }


}
