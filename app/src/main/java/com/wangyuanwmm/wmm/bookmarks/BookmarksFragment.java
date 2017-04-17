package com.wangyuanwmm.wmm.bookmarks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.wangyuanwmm.wmm.Interface.OnRecyclerViewOnClickListener;
import com.wangyuanwmm.wmm.R;
import com.wangyuanwmm.wmm.adapter.BookmarksAdapter;
import com.wangyuanwmm.wmm.entity.BeanType;
import com.wangyuanwmm.wmm.entity.DoubanMomentNews;
import com.wangyuanwmm.wmm.entity.GuokrHandpickNews;
import com.wangyuanwmm.wmm.entity.ZhihuDailyNews;

import java.util.ArrayList;

//收藏界面

public class BookmarksFragment extends Fragment
        implements BookmarksContract.View {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private BookmarksAdapter adapter;
    private BookmarksContract.Presenter presenter;

    public BookmarksFragment() {}

    public static BookmarksFragment newInstance() {
        return new BookmarksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        initViews(view);

        setHasOptionsMenu(true);

        presenter.loadResults(false);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadResults(true);
            }
        });

        return view;
    }

    @Override
    public void setPresenter(BookmarksContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bookmarks, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_feel_lucky) {
                presenter.feelLucky();
            }
        return true;
    }

    @Override
    public void showResults(ArrayList<ZhihuDailyNews.Question> zhihuList,
                            ArrayList<GuokrHandpickNews.result> guokrList,
                            ArrayList<DoubanMomentNews.posts> doubanList,
                            ArrayList<Integer> types) {

        if (adapter == null) {

            adapter = new BookmarksAdapter(getActivity(), zhihuList, guokrList, doubanList, types);
            adapter.setItemListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void OnItemClick(View v, int position) {
                    int type = recyclerView.findViewHolderForLayoutPosition(position).getItemViewType();
                    if (type == BookmarksAdapter.TYPE_ZHIHU_NORMAL) {
                        presenter.startReading(BeanType.TYPE_ZHIHU, position);
                    } else if (type == BookmarksAdapter.TYPE_GUOKR_NORMAL) {
                        presenter.startReading(BeanType.TYPE_GUOKR, position);
                    } else if (type == BookmarksAdapter.TYPE_DOUBAN_NORMAL) {
                        presenter.startReading(BeanType.TYPE_DOUBAN, position);
                    }
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void notifyDataChanged() {
        presenter.loadResults(true);
        adapter.notifyDataSetChanged();
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
