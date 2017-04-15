package com.wangyuanwmm.wmm.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wangyuanwmm.wmm.R;
import com.wangyuanwmm.wmm.fragment.SearchFragment;
import com.wangyuanwmm.wmm.Presenter.SearchPresenter;

public class SearchActivity extends AppCompatActivity {

    private SearchFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        fragment = SearchFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();

        new SearchPresenter(this, fragment);

    }
}
