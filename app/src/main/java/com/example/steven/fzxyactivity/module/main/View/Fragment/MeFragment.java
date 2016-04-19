package com.example.steven.fzxyactivity.module.main.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.steven.fzxyactivity.App;
import com.example.steven.fzxyactivity.Constant.Constants;
import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.common.util.SpUtils;
import com.example.steven.fzxyactivity.common.util.ToastUtil;
import com.example.steven.fzxyactivity.common.util.glide.GlideCircleTransform;
import com.example.steven.fzxyactivity.materialdesign.views.Button;
import com.example.steven.fzxyactivity.materialdesign.views.ButtonRectangle;
import com.example.steven.fzxyactivity.module.user.LoginActivity;
import com.example.steven.fzxyactivity.module.user.ModifyActivity;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.iv_headerPic)
    ImageView ivHeaderPic;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_ID)
    TextView tvID;
    @Bind(R.id.tv_my_activity)
    TextView tvMyActivity;
    @Bind(R.id.tv_my_interest_activity)
    TextView tvMyInterestActivity;
    @Bind(R.id.tv_shareToFriend)
    TextView tvShareToFriend;
    @Bind(R.id.tv_about)
    TextView tvAbout;
    @Bind(R.id.tv_set)
    TextView tvSet;
    @Bind(R.id.tv_exit)
    TextView tvExit;
    @Bind(R.id.tv_modify_user)
    TextView tvModifyUser;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Glide.with(this).load(R.mipmap.circleheader).asBitmap().transform(new GlideCircleTransform(getActivity())).into(ivHeaderPic);//圆形头像
    }

    @OnClick(R.id.tv_exit)
    public void setTvExit() {
        final MaterialDialog materialDialog=new MaterialDialog(getActivity());
        materialDialog.setTitle("确定退出吗?")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SpUtils.clear(App.getApp());
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        materialDialog.dismiss();
                    }
                })
                .setCanceledOnTouchOutside(true)
                .show();



    }
    @OnClick(R.id.tv_modify_user)
    public void setTvModifyUser() {
        startActivity(new Intent(getActivity(), ModifyActivity.class));
    }
    MaterialDialog alert;
    @OnClick(R.id.iv_headerPic)
    public void setIvHeaderPic() {
        View view=getActivity().getLayoutInflater().inflate(R.layout.dialog_headpic_choice,null);
        ButtonRectangle btnCamera= (ButtonRectangle) view.findViewById(R.id.btn_Camera);
        ButtonRectangle btnPic= (ButtonRectangle) view.findViewById(R.id.btn_Pic);
        alert = new MaterialDialog(getActivity()).setTitle(
                "更换头像").setContentView(view).setCanceledOnTouchOutside(true);
        alert.show();
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //拍摄静态照片
                chooseCameraPic();
            }
        });
        btnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //从照片库选择
                chooseLocalPic();
            }
        });

    }

    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";//temp file
    Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);
    private Uri uri;
    private void chooseCameraPic() {
        uri = null;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, Constants.CAMERA_WITH_DATA);
    }

    private void chooseLocalPic() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, Constants.PHOTO_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.PHOTO_REQUEST:
                if (data != null) {
                    photoClip(data.getData());
                }
                break;
            case Constants.PHOTO_CLIP:
                setHeaderPic();
                break;
            case Constants.CAMERA_WITH_DATA:
                photoClip(imageUri);
                break;
        }
    }
    public void setHeaderPic() {
        Glide.with(this).load(uri).asBitmap().transform(new GlideCircleTransform(getActivity())).into(ivHeaderPic);//圆形头像
        alert.dismiss();
    }

    /**
     * 图片裁剪
     *
     * @param uri 根据图片uri进行裁剪
     */
    private void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        this.uri=uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        //缩放
        intent.putExtra("scale", true);
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //返回的数据true Bitmap false Uri
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, Constants.PHOTO_CLIP);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
