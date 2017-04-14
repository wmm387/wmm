package com.wangyuanwmm.wmm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangyuanwmm.wmm.R;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    //TabLayout
    private TabLayout mTabLayout;
    //ViewPager
    private ViewPager mViewPager;
    //Title
    private List<String> mTilte;
    //Fragment
    private List<Fragment> mFragment;

    private Context context;

    public MainFragment() {}

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main2, container, false);

        initData();
        initView(view);

        return view;
    }

    //初始化数据
    private void initData() {
        mTilte = new ArrayList<>();
        mTilte.add("今日头条");
        mTilte.add("当前实事");
        mTilte.add("前沿科技");

        mFragment = new ArrayList<>();
        mFragment.add(new TopFragment());
        mFragment.add(new NewsFragment());
        mFragment.add(new TechFragment());
    }

    //初始化View
    private void initView(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.mViewPager);

        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            //设置标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTilte.get(position);
        } {

            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }
}
