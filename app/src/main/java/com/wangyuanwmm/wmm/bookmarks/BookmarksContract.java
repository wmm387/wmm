package com.wangyuanwmm.wmm.bookmarks;

import com.wangyuanwmm.wmm.BasePresenter;
import com.wangyuanwmm.wmm.BaseView;
import com.wangyuanwmm.wmm.entity.BeanType;
import com.wangyuanwmm.wmm.entity.DoubanMomentNews;
import com.wangyuanwmm.wmm.entity.GuokrHandpickNews;
import com.wangyuanwmm.wmm.entity.ZhihuDailyNews;

import java.util.ArrayList;

public interface BookmarksContract {

    interface View extends BaseView<Presenter> {

        void showResults(ArrayList<ZhihuDailyNews.Question> zhihuList,
                         ArrayList<GuokrHandpickNews.result> guokrList,
                         ArrayList<DoubanMomentNews.posts> doubanList,
                         ArrayList<Integer> types);

        void notifyDataChanged();

        void showLoading();

        void stopLoading();

    }

    interface Presenter extends BasePresenter {

        void loadResults(boolean refresh);

        void startReading(BeanType type, int position);

        void checkForFreshData();

        void feelLucky();

    }

}
