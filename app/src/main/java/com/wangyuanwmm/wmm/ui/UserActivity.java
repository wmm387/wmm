package com.wangyuanwmm.wmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wangyuanwmm.wmm.MainActivity;
import com.wangyuanwmm.wmm.R;
import com.wangyuanwmm.wmm.entity.MyUser;
import com.wangyuanwmm.wmm.utils.L;
import com.wangyuanwmm.wmm.utils.UtilTools;
import com.wangyuanwmm.wmm.utils.CustomDialog;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心
 */

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_edit,tv_exit,tv_change,tv_update_ok;

    private EditText et_username,et_age,et_sex,et_desc;
    //圆形图片
    private CircleImageView profile_image;

    private CustomDialog dialog;

    //private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        findView();
    }

    //初始化View
    private void findView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tv_edit = (TextView) findViewById(R.id.tv_edit);
        tv_exit = (TextView) findViewById(R.id.tv_exit);
        tv_change = (TextView) findViewById(R.id.tv_change_password);
        tv_update_ok = (TextView) findViewById(R.id.tv_update_ok);

        tv_edit.setOnClickListener(this);
        tv_exit.setOnClickListener(this);
        tv_change.setOnClickListener(this);
        tv_update_ok.setOnClickListener(this);

        et_username = (EditText) findViewById(R.id.et_username);
        et_sex = (EditText) findViewById(R.id.et_sex);
        et_age = (EditText) findViewById(R.id.et_age);
        et_desc = (EditText) findViewById(R.id.et_desc);

        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);

        //读取头像图片
        UtilTools.getImageToShare(this, profile_image);

        //自定义dialog
        dialog = new CustomDialog(this, 0, 0,
                R.layout.dialog_photo, R.style.pop_anim_style,
                Gravity.BOTTOM, 0);
        //屏幕外点击无效
        dialog.setCancelable(false);
        //在Dialog中设置按钮点击事件
        //btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        //btn_camera.setOnClickListener(this);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(this);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);


        //默认编辑框是不能点击的
        setEnable(false);

        //设置具体的值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);

        et_username.setText(userInfo.getUsername());
        et_sex.setText(userInfo.isSex() ? "男" : "女");
        et_age.setText(userInfo.getAge()+"");
        et_desc.setText(userInfo.getDesc());
    }

    //控制焦点
    private void setEnable(boolean is) {
        et_username.setEnabled(is);
        et_sex.setEnabled(is);
        et_age.setEnabled(is);
        et_desc.setEnabled(is);
    }

    //Toolbar中的返回键，点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit:
                //退出登录
                MyUser.logOut();//清除缓存对象
                BmobUser currentUser = MyUser.getCurrentUser();//现在的currUser时null了
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.tv_change_password:
                startActivity(new Intent(this,UpdatePasswordActivity.class));
                break;

            case R.id.tv_edit:
                setEnable(true);
                tv_update_ok.setVisibility(View.VISIBLE);
                tv_edit.setVisibility(View.GONE);
                break;

            case R.id.tv_update_ok:
                //拿到输入框的值
                String username = et_username.getText().toString();
                String age = et_age.getText().toString();
                String sex = et_sex.getText().toString();
                String desc = et_desc.getText().toString();

                //判断是否为空
                if (!TextUtils.isEmpty(username)
                        & !TextUtils.isEmpty(age)
                        & !TextUtils.isEmpty(sex)) {

                    //更新属性
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setAge(Integer.parseInt(age));

                    //性别判断
                    if (sex.equals(getString(R.string.male))) {
                        user.setSex(true);
                    } else {
                        user.setSex(false);
                    }

                    //简介判断
                    if (!TextUtils.isEmpty(desc)) {
                        user.setDesc(desc);
                    } else {
                        user.setDesc(getString(R.string.text_nothing));
                    }

                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //修改成功
                                setEnable(false);
                                tv_update_ok.setVisibility(View.GONE);
                                tv_edit.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), R.string.text_editor_success, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.text_editor_failure, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(this, R.string.text_tost_empty, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.profile_image:
//                dialog.show();
                toPicture();
                break;
//            case R.id.btn_cancel:
//                dialog.dismiss();
//                break;
//            case R.id.btn_camera:
//                toCamera();
//                break;
//            case R.id.btn_picture:
//                toPicture();
//                break;
        }
    }

    public static final String PHOTO_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 101;
    public static final int IMAGE_REQUEST_CODE = 102;
    public static final int RESULT_REQUEST_CODE = 103;
    private File tempFile = null;

    //跳转相机
//    private void toCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        //判断内存卡是否可用，可用则进行储存
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
//                        PHOTO_FILE_NAME)));
//        startActivityForResult(intent, CAMERA_REQUEST_CODE);
//        dialog.dismiss();
//    }

    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != this.RESULT_CANCELED) {
            switch (requestCode) {
                //相册数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //相机数据
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍弃
                    if (data != null) {
                        //拿到图片设置
                        setImageToView(data);
                        //设置图片后，之前的图片就删除
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }

    //裁剪
    private void startPhotoZoom(Uri uri) {

        if (uri == null) {
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);

        startActivityForResult(intent,RESULT_REQUEST_CODE);
    }

    private void setImageToView(Intent data) {

        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存
        UtilTools.putImageToShare(this,profile_image);
    }
}