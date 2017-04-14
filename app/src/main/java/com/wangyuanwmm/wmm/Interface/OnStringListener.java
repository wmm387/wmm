package com.wangyuanwmm.wmm.Interface;

import com.android.volley.VolleyError;

public interface OnStringListener {

    //请求成功时回调
    void onSuccess(String result);

    //请求失败时回调
    void onError(VolleyError error);

}
