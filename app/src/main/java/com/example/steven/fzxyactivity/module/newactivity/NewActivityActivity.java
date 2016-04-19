package com.example.steven.fzxyactivity.module.newactivity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.bumptech.glide.Glide;
import com.example.steven.fzxyactivity.Constant.Constants;
import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.common.util.LogUtil;
import com.example.steven.fzxyactivity.common.util.OkUtils;
import com.example.steven.fzxyactivity.common.util.ToastUtil;
import com.example.steven.fzxyactivity.common.util.glide.GlideCircleTransform;
import com.example.steven.fzxyactivity.materialdesign.views.ButtonRectangle;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import okhttp3.Call;

public class NewActivityActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_pic)
    ImageView ivPic;
    @Bind(R.id.et_title)
    AppCompatEditText etTitle;
    @Bind(R.id.et_desc)
    AppCompatEditText etDesc;
    @Bind(R.id.et_tag)
    AppCompatEditText etTag;
    @Bind(R.id.sp_school)
    Spinner spSchool;
    @Bind(R.id.btn_register)
    ButtonRectangle btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ButterKnife.bind(this);
        toolbar.setTitle("发布活动");
        toolbar.setSubtitle("填写您的活动信息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_register)
    public void setBtnRegister(){

        ToastUtil.toast("发布中，请稍等");
        finish();
    }

    private void newTaskByServer() {
        String url = Constants.ServerUrl + "user/reg";
        Map<String, String> map = new HashMap<>();
        map.put("activitytitle", etTitle.getText().toString());
        map.put("createdtime", String.valueOf(System.currentTimeMillis()));
        map.put("activitytag", etTag.getText().toString());
        map.put("collegename", spSchool.getSelectedItem().toString());
       // map.put("activitydesc", );
        OkUtils.post(url, map, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                LogUtil.log(response);
            }
        });
    }

    MaterialDialog alert;
    @OnClick(R.id.iv_pic)
    public void setIvPic(){
        View view=getLayoutInflater().inflate(R.layout.dialog_headpic_choice,null);
        ButtonRectangle btnCamera= (ButtonRectangle) view.findViewById(R.id.btn_Camera);
        ButtonRectangle btnPic= (ButtonRectangle) view.findViewById(R.id.btn_Pic);
        alert = new MaterialDialog(this).setTitle(
                "选择封面").setContentView(view).setCanceledOnTouchOutside(true);
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
        Glide.with(this).load(uri).asBitmap().into(ivPic);
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
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 1280);
        intent.putExtra("outputY", 640);
        //返回的数据true Bitmap false Uri
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, Constants.PHOTO_CLIP);
    }
}
