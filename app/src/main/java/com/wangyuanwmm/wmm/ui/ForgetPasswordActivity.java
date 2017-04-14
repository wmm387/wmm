package com.wangyuanwmm.wmm.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.wangyuanwmm.wmm.R;
import com.wangyuanwmm.wmm.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by Administrator on 2017/2/12.
 * 忘记、重置密码
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_forget_password,btn_update_password;
    private EditText et_email,et_now,et_new,et_new_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();
    }

    //初始化View
    private void initView() {
        btn_forget_password = (Button) findViewById(R.id.btn_forget_password);
        btn_forget_password.setOnClickListener(this);

        btn_update_password = (Button) findViewById(R.id.btn_update_password);
        btn_update_password.setOnClickListener(this);

        et_email = (EditText) findViewById(R.id.et_email);
        et_now = (EditText) findViewById(R.id.et_now);
        et_new = (EditText) findViewById(R.id.et_new);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_forget_password:
                //获取输入框的邮箱
                final String email = et_email.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(email)) {
                    //发送重置邮件
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ForgetPasswordActivity.this,
                                        "邮箱已经发送至：" + email, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ForgetPasswordActivity.this,
                                        "邮箱发送失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, R.string.text_tost_empty, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update_password:

                //获取输入框的值
                String now = et_now.getText().toString().trim();
                String newpass = et_new.getText().toString().trim();
                String new_password = et_new_password.getText().toString().trim();

                //是否为空
                if (!TextUtils.isEmpty(now)
                        & !TextUtils.isEmpty(newpass)
                        & !TextUtils.isEmpty(new_password)) {

                    //判断两次密码是否一致
                    if (newpass.equals(new_password)) {

                        //重置密码
                        MyUser.updateCurrentUserPassword(now, new_password, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(ForgetPasswordActivity.this, "重置密码成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ForgetPasswordActivity.this, "重置密码失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, R.string.text_tost_empty, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
