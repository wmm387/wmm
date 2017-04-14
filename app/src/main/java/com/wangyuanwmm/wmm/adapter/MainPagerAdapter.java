package com.wangyuanwmm.wmm.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wangyuanwmm.wmm.R;
import com.wangyuanwmm.wmm.fragment.DoubanMomentFragment;
import com.wangyuanwmm.wmm.fragment.GuokrFragment;
import com.wangyuanwmm.wmm.fragment.ZhihuDailyFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private String[] titles;
    private final Context context;

    private GuokrFragment guokrFragment;
    private ZhihuDailyFragment zhihuFragment;
    private DoubanMomentFragment doubanFragment;

    public GuokrFragment getGuokrFragment() {
        return guokrFragment;
    }

    public ZhihuDailyFragment getZhihuFragment() {
        return zhihuFragment;
    }

    public DoubanMomentFragment getDoubanFragment() {
        return doubanFragment;
    }

    public MainPagerAdapter(FragmentManager fm,
                            Context context,
                            ZhihuDailyFragment zhihuDailyFragment,
                            GuokrFragment guokrFragment,
                            DoubanMomentFragment doubanMomentFragment) {
        super(fm);
        this.context = context;
        titles = new String[] {
                context.getResources().getString(R.string.zhihu_daily),
                context.getResources().getString(R.string.guokr_handpick),
                context.getResources().getString(R.string.douban_moment)
        };

        this.zhihuFragment = zhihuDailyFragment;
        this.guokrFragment = guokrFragment;
        this.doubanFragment = doubanMomentFragment;

    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1){
            return guokrFragment;
        } else if (position == 2){
            return doubanFragment;
        }

        return zhihuFragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
