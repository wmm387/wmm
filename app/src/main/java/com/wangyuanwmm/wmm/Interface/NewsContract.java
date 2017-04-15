package com.wangyuanwmm.wmm.Interface;

import com.wangyuanwmm.wmm.BasePresenter;
import com.wangyuanwmm.wmm.BaseView;
import com.wangyuanwmm.wmm.entity.NewData;

import java.util.ArrayList;

public class NewsContract {

    public interface View extends BaseView<Presenter> {

        void showError();

        void showResults(ArrayList<NewData.list> list);

        void showLoading();

        void stopLoading();


    }

    public interface Presenter extends BasePresenter {

        void loadPosts();

        void refresh();

        void startReading(int position);

        void feelLucky();

    }
}
