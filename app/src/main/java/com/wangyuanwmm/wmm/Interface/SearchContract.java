package com.wangyuanwmm.wmm.Interface;


import com.wangyuanwmm.wmm.BasePresenter;
import com.wangyuanwmm.wmm.BaseView;
import com.wangyuanwmm.wmm.entity.BeanType;
import com.wangyuanwmm.wmm.entity.DoubanMomentNews;
import com.wangyuanwmm.wmm.entity.GuokrHandpickNews;
import com.wangyuanwmm.wmm.entity.ZhihuDailyNews;

import java.util.ArrayList;

public interface SearchContract {

    interface View extends BaseView<Presenter> {

        void showResults(ArrayList<ZhihuDailyNews.Question> zhihuList,
                         ArrayList<GuokrHandpickNews.result> guokrList,
                         ArrayList<DoubanMomentNews.posts> doubanList,
                         ArrayList<Integer> types);

        void showLoading();

        void stopLoading();

    }

    interface Presenter extends BasePresenter {

        void loadResults(String queryWords);

        void startReading(BeanType type, int position);

    }

}
