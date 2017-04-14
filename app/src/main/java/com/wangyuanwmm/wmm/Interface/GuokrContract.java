package com.wangyuanwmm.wmm.Interface;

import com.wangyuanwmm.wmm.BasePresenter;
import com.wangyuanwmm.wmm.BaseView;
import com.wangyuanwmm.wmm.entity.GuokrHandpickNews;

import java.util.ArrayList;

public interface GuokrContract {

    interface View extends BaseView<Presenter> {

        void showError();

        void showResults(ArrayList<GuokrHandpickNews.result> list);

        void showLoading();

        void stopLoading();

    }

    interface Presenter extends BasePresenter {

        void loadPosts();

        void refresh();

        void startReading(int position);

        void feelLucky();

    }

}
