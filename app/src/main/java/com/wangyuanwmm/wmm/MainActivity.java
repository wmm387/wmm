package com.wangyuanwmm.wmm;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wangyuanwmm.wmm.bookmarks.BookmarksPresenter;
import com.wangyuanwmm.wmm.bookmarks.BookmarksFragment;
import com.wangyuanwmm.wmm.entity.MyUser;
import com.wangyuanwmm.wmm.fragment.MainFragment;
import com.wangyuanwmm.wmm.fragment.PPFragment;
import com.wangyuanwmm.wmm.service.CacheService;
import com.wangyuanwmm.wmm.ui.LoginActivity;
import com.wangyuanwmm.wmm.ui.UserActivity;
import com.wangyuanwmm.wmm.utils.FastBlurUtil;

import cn.bmob.v3.BmobUser;

/**
 * 软件主界面
 * 是一个带有侧滑栏的fragment容器
 * 并设置一高斯图片做背景
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private PPFragment PPFragment;
    private MainFragment mainFragment;
    private BookmarksFragment bookmarksFragment;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    private MyUser userInfo;

    private ImageView blur_image;

    public static final String ACTION_BOOKMARKS = "com.wangyuanwmm.wmm.bookmarks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        if (savedInstanceState != null) {
            PPFragment = (PPFragment) getSupportFragmentManager().getFragment(savedInstanceState, "PPFragment");
            bookmarksFragment = (BookmarksFragment) getSupportFragmentManager().getFragment(savedInstanceState, "BookmarksFragment");
            mainFragment = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState,"MainFragment");
        } else {
            PPFragment = PPFragment.newInstance();
            bookmarksFragment = BookmarksFragment.newInstance();
            mainFragment = MainFragment.newInstance();
        }

        if (!PPFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment, PPFragment, "PPFragment")
                    .commit();
        }

        if (!mainFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment, mainFragment, "MainFragment")
                    .commit();
        }

        if (!bookmarksFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment, bookmarksFragment, "BookmarksFragment")
                    .commit();
        }

        new BookmarksPresenter(MainActivity.this, bookmarksFragment);

        String action = getIntent().getAction();

        if (action.equals(ACTION_BOOKMARKS)) {
            showBookmarksFragment();
            navigationView.setCheckedItem(R.id.nav_bookmarks);
        } else {
            showMainFragment();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        startService(new Intent(this, CacheService.class));
    }


    //初始化View
    private void initView() {

        //初始化toolbar，并设置点击事件
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //单独添加头部布局，进行头部布局控件的设置
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView textView = (TextView) headerView.findViewById(R.id.nav_username);

        //登录设置，获取用户信息，并判断做出点击事件
        userInfo = BmobUser.getCurrentUser(MyUser.class);
        if (userInfo != null) {
            textView.setText(userInfo.getUsername());
        }else{
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }

        //设置高斯图片
        blur_image = (ImageView) findViewById(R.id.blur_image);
        Resources res = getResources();
        Bitmap scaledBitmap = BitmapFactory.decodeResource(res, R.drawable.main_bg);

        Bitmap blurBitmap = FastBlurUtil.toBlur(scaledBitmap, 10);
        blur_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        blur_image.setImageBitmap(blurBitmap);
    }

    @Override
    protected void onDestroy() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (CacheService.class.getName().equals(service.service.getClassName())) {
                stopService(new Intent(this, CacheService.class));
            }
        }
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (PPFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "PPFragment", PPFragment);
        }

        if (mainFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "MainFragment", mainFragment);
        }

        if (bookmarksFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "BookmarksFragment", bookmarksFragment);
        }
    }

    //侧滑栏菜单点击事件
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_home) {
            drawer.closeDrawer(GravityCompat.START);
            showMainFragment();
        } else if (id == R.id.nav_bookmarks) {
            if (userInfo != null) {
                drawer.closeDrawer(GravityCompat.START);
                showBookmarksFragment();
            } else {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_news){
            if (userInfo != null) {
                drawer.closeDrawer(GravityCompat.START);
                showNewsFragment();
            } else {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_user){
            if (userInfo != null) {
                drawer.closeDrawer(GravityCompat.START);
                showUserCenter();
            } else {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_change_theme) {
            drawer.closeDrawer(GravityCompat.START);

            // 更换夜间主题
            drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {

                }

                @Override
                public void onDrawerOpened(View drawerView) {

                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    SharedPreferences sp =  getSharedPreferences("user_settings",MODE_PRIVATE);
                    if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                            == Configuration.UI_MODE_NIGHT_YES) {
                        sp.edit().putInt("theme", 0).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    } else {
                        sp.edit().putInt("theme", 1).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                    getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                    recreate();
                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });

        }
        else if (id == R.id.nav_exit) {
            drawer.closeDrawer(GravityCompat.START);
            finish();
        }

        return true;
    }

    //加载首页
    private void showMainFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(PPFragment);
        fragmentTransaction.hide(bookmarksFragment);
        fragmentTransaction.hide(mainFragment);
        fragmentTransaction.commit();

        toolbar.setTitle(getResources().getString(R.string.app_name));
    }

    //加载新闻页
    private void showNewsFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(mainFragment);
        fragmentTransaction.hide(bookmarksFragment);
        fragmentTransaction.hide(PPFragment);
        fragmentTransaction.commit();

        toolbar.setTitle("新闻头条");
    }

    //加载收藏页
    private void showBookmarksFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(bookmarksFragment);
        fragmentTransaction.hide(PPFragment);
        fragmentTransaction.hide(mainFragment);
        fragmentTransaction.commit();

        toolbar.setTitle(getResources().getString(R.string.nav_bookmarks));

        if (bookmarksFragment.isAdded()) {
            bookmarksFragment.notifyDataChanged();
        }
    }

    //跳转个人中心
    private void showUserCenter() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }
}
