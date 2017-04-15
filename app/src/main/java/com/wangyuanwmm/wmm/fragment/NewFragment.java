package com.wangyuanwmm.wmm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangyuanwmm.wmm.Interface.NewsContract;
import com.wangyuanwmm.wmm.Interface.OnRecyclerViewOnClickListener;
import com.wangyuanwmm.wmm.Presenter.NewsPresenter;
import com.wangyuanwmm.wmm.R;
import com.wangyuanwmm.wmm.adapter.NewAdapter;
import com.wangyuanwmm.wmm.entity.NewData;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/15.
 */

public class NewFragment extends Fragment implements NewsContract.View {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private NewAdapter adapter;
    private NewsContract.Presenter presenter;

    public NewFragment() {}

    public static NewFragment newInstance() {
        return new NewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list,container,false);

        initViews(view);

        presenter.start();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });

        return view;
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {
        if (presenter != null){
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        //设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showResults(ArrayList<NewData.list> list) {
        if (adapter == null) {
            adapter = new NewAdapter(getContext(), list);
            adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void OnItemClick(View v, int position) {
                    presenter.startReading(position);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void stopLoading() {
        refreshLayout.setRefreshing(false);
    }
}
