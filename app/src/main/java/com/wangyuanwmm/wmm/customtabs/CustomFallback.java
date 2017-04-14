package com.wangyuanwmm.wmm.customtabs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.wangyuanwmm.wmm.ui.InnerBrowserActivity;

public class CustomFallback implements CustomTabActivityHelper.CustomTabFallback {

    @Override
    public void openUri(Activity activity, Uri uri) {
        activity.startActivity(new Intent(activity, InnerBrowserActivity.class).putExtra("url", uri.toString()));
    }

}
