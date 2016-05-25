package com.example.steven.fzxyactivity.module.main.View.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.example.steven.fzxyactivity.Constant.Constants;
import com.example.steven.fzxyactivity.Entity.MessageEntity;
import com.example.steven.fzxyactivity.Entity.MyActivityEntity;
import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.common.util.LoadingState;
import com.example.steven.fzxyactivity.common.util.NetWorkUtil;
import com.example.steven.fzxyactivity.common.util.OkUtils;
import com.example.steven.fzxyactivity.common.util.SnackbarUtil;
import com.example.steven.fzxyactivity.common.util.SpUtils;
import com.example.steven.fzxyactivity.common.util.ToastUtil;
import com.example.steven.fzxyactivity.common.util.customview.QQListview;
import com.example.steven.fzxyactivity.common.util.customview.XHLoadingView;
import com.example.steven.fzxyactivity.materialdesign.utils.DialogAction;
import com.example.steven.fzxyactivity.materialdesign.views.MaterialDialog;
import com.example.steven.fzxyactivity.module.activitydetail.ActivityDeatailActivity;
import com.example.steven.fzxyactivity.module.main.View.Fragment.adapter.MessageAdapter;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MessageFrament#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFrament extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.rl_message)
    RelativeLayout rlMessage;
    @Bind(R.id.lv_loading)
    XHLoadingView lvLoading;
    @Bind(R.id.listview_qq)
    QQListview listviewQq;

    private static final String LOADING_STATE="state";
    private static final String LOADING="loading";
    private static final String LOADING_EMPTY="empty";
    private static final String LOADING_NONETWORK="nonet";
    private static final String LOADING_ERROR="error";
    private String mLoadState;
    List<MessageEntity> mDatas;
    MessageAdapter adapter;
    String content="";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LinearLayoutManager mLinearLayoutManager;

    public MessageFrament() {
        // Required empty public constructor
    }

    public static MessageFrament newInstance(String param1, String param2) {
        MessageFrament fragment = new MessageFrament();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_frament, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvLoading.withLoadEmptyText("≥﹏≤ , 啥也木有 !").withEmptyIcon(R.drawable.disk_file_no_data).withBtnEmptyEnnable(false)
                .withErrorIco(R.drawable.ic_chat_empty).withLoadErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !").withBtnErrorText("臭狗屎!!!")
                .withLoadNoNetworkText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走").withNoNetIcon(R.drawable.ic_chat_empty).withBtnNoNetText("网弄好了，重试")
                .withLoadingIcon(R.drawable.loading_animation).withLoadingText("加载中...").withOnRetryListener(new XHLoadingView.OnRetryListener() {
            @Override
            public void onRetry() {
                SnackbarUtil.show(lvLoading,"已经在努力重试了",0);
            }
        }).build();

        if (!NetWorkUtil.isNetWorkConnected(getActivity())){
            lvLoading.setVisibility(View.VISIBLE);
            lvLoading.setState(LoadingState.STATE_ERROR);
        }else {
            //加载中
            lvLoading.setVisibility(View.VISIBLE);
            lvLoading.setState(LoadingState.STATE_LOADING);
            String url = Constants.ServerUrl + "ses/searchActivitySes/" + SpUtils.getString(getActivity(), "userId");
            //String url = Constants.ServerUrl + "ses/searchActivitySes/sf";
            OkUtils.get(url, new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lvLoading.setVisibility(View.VISIBLE);
                            lvLoading.setState(LoadingState.STATE_ERROR);
                        }
                    });
                }

                @Override
                public void onResponse(String response) {
                    try {

                        if (response.equals("null")){
                            //null  没有活动
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    lvLoading.setVisibility(View.VISIBLE);
                                    lvLoading.setState(LoadingState.STATE_EMPTY);
                                }
                            });
                        }else {
                            final JSONObject jsonObject = new JSONObject(response);
                            final JSONArray array = jsonObject.getJSONArray("activitysessionmessage");
                            if (array.getJSONObject(0).getString("code").equals("1")) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        lvLoading.setVisibility(View.GONE);
                                        mDatas= new ArrayList<MessageEntity>();
                                        for (int i=0;i<array.length();i++){
                                            try {
                                                MessageEntity entity=new MessageEntity();
                                                entity.setMessage(array.getJSONObject(i).getString("message"));
                                                entity.setMessageId(array.getJSONObject(i).getString("messageId"));
                                                entity.setMessageTime("消息时间:"+array.getJSONObject(i).getString("messageTime"));
                                                entity.setUserId(array.getJSONObject(i).getString("userId"));
                                                entity.setUserName("用户名:"+array.getJSONObject(i).getString("userName"));
                                                mDatas.add(entity);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        adapter=new MessageAdapter(getActivity(),mDatas);
                                        listviewQq.setAdapter(adapter);
                                        listviewQq.setDelButtonClickListener(new QQListview.DelButtonClickListener() {
                                            @Override
                                            public void clickHappend(int position) {
                                                //网络请求操作删除
                                                try {
                                                    deleteMsg(array.getJSONObject(position).getString("messageId"),position);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        listviewQq.setEditButtonClickListener(new QQListview.EditButtonClickListener() {
                                            @Override
                                            public void clickEditHappend(final int position) {
                                                //编辑操作
                                                new MaterialDialog.Builder(getActivity())
                                                        .title("修改消息")
                                                        .content("修改您的活动消息")
                                                        .inputType(InputType.TYPE_CLASS_TEXT |
                                                                InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                                                                InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                                                        .positiveText("提交")
                                                        .onPositive(new MaterialDialog
                                                                .SingleButtonCallback() {
                                                            @Override
                                                            public void onClick(@NonNull
                                                                                MaterialDialog
                                                                                        dialog,
                                                                                @NonNull
                                                                                DialogAction
                                                                                        which) {
                                                                try {
                                                                    modifyMsg(content,array.getJSONObject(position).getString("messageId"));
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        })
                                                        .alwaysCallInputCallback()
                                                        .input(R.string.input, 0, false, new
                                                                MaterialDialog.InputCallback() {
                                                                    @Override
                                                                    public void onInput(MaterialDialog
                                                                                                dialog,
                                                                                        CharSequence
                                                                                                input) {
                                                                        if (!input.toString().equals
                                                                                ("")) {
                                                                            content = input.toString();
                                                                        }
                                                                    }
                                                                }).show();
                                            }
                                        });
                                        listviewQq.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                        {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                            {
                                                //点击操作,跳进消息详情

                                            }
                                        });
                                    }
                                });
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });


        }
    }

    private void modifyMsg(String content,String id) {
        String url = Constants.ServerUrl + "ses/updateActivitySes";
        Map<String, String> map = new HashMap<>();
        map.put("MessageId", id);
        map.put("UserId", SpUtils.getString(getActivity(),"userId"));
        map.put("UserName", SpUtils.getString(getActivity(),"userName"));
        map.put("Message", content);
        OkUtils.post(url, map, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    ToastUtil.toast(jsonObject.getString("msg"));
                    if (jsonObject.getString("code").equals("1")){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void deleteMsg(String messageId, final int position) {
        String url = Constants.ServerUrl+"ses/deleteActivitySes/"+messageId;
        OkUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    ToastUtil.toast(jsonObject.getString("msg"));
                    if (jsonObject.getString("code").equals("1")){
                        //界面删除
                        adapter.delete(position);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
