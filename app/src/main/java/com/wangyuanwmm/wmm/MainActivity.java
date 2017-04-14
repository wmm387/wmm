package com.wangyuanwmm.wmm;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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

import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private PPFragment PPFragment;
    private MainFragment mainFragment;
    private BookmarksFragment bookmarksFragment;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    private MyUser userInfo;

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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();
        if (id == R.id.nav_home) {
            showMainFragment();
        } else if (id == R.id.nav_bookmarks) {
            if (userInfo != null) {
                showBookmarksFragment();
            } else {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_news){
            if (userInfo != null) {
                showNewsFragment();
            } else {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_user){
            if (userInfo != null) {
                showUserCenter();
            } else {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_change_theme) {

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
            finish();
        }

        return true;
    }

    private void showMainFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(PPFragment);
        fragmentTransaction.hide(bookmarksFragment);
        fragmentTransaction.hide(mainFragment);
        fragmentTransaction.commit();

        toolbar.setTitle(getResources().getString(R.string.app_name));
    }

    private void showNewsFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(mainFragment);
        fragmentTransaction.hide(bookmarksFragment);
        fragmentTransaction.hide(PPFragment);
        fragmentTransaction.commit();

        toolbar.setTitle("新闻头条");
    }

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

    private void showUserCenter() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }
}
