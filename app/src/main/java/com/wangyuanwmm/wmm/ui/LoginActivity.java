package com.wangyuanwmm.wmm.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wangyuanwmm.wmm.MainActivity;
import com.wangyuanwmm.wmm.R;
import com.wangyuanwmm.wmm.entity.MyUser;
import com.wangyuanwmm.wmm.utils.ShareUtils;
import com.wangyuanwmm.wmm.utils.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 登录页面
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //注册按钮
    private Button btn_registered,btnLogin;
    private EditText et_name,et_password;
    private CheckBox keep_password;
    private TextView tv_forget;

    //自定义Dialog
    private CustomDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        btn_registered = (Button) findViewById(R.id.btn_registered);
        btn_registered.setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        keep_password = (CheckBox) findViewById(R.id.keep_password);

        tv_forget = (TextView) findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);

        //自定义dialog
        dialog = new CustomDialog(this, 300, 300, R.layout.dialog_loading, R.style.Theme_dialog,
                Gravity.CENTER, R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);

        //设置选中的状态
        boolean isCheck = ShareUtils.getBoolean(this, "keeppass", false);
        keep_password.setChecked(isCheck);
        if (isCheck) {
            //设置密码
            et_name.setText(ShareUtils.getString(this,"name",""));
            et_password.setText(ShareUtils.getString(this,"password",""));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forget:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.btn_registered:
                startActivity(new Intent(this,RegisteredActivity.class));
                break;
            case R.id.btnLogin:
                //1.获取输入框的值
                String name = et_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)) {
                    dialog.show();
                       //登录
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            //判断结果
                            if (e == null) {
                                //判断邮箱是否认证
                                if (user.getEmailVerified()) {
                                    //跳转
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "请前往邮箱认证", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败,账号或密码错误，请从新输入", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(this, R.string.text_tost_empty, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //保存状态
        ShareUtils.putBoolean(this,"keeppass",keep_password.isChecked());

        //是否记住密码
        if (keep_password.isChecked()) {
            //记住用户名和密码
            ShareUtils.putString(this, "name", et_name.getText().toString().trim());
            ShareUtils.putString(this, "password", et_password.getText().toString().trim());
        } else {
            ShareUtils.deleShare(this, "password");
        }
    }
}
