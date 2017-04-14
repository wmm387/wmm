package com.wangyuanwmm.wmm.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
 * 忘记密码
 */

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_forget_password;
    private EditText et_email;

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

        et_email = (EditText) findViewById(R.id.et_email);
    }

    @Override
    public void onClick(View v) {
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
                                "邮箱发送失败,请确认邮箱无误后重新发送", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, R.string.text_tost_empty, Toast.LENGTH_SHORT).show();
        }
    }
}
