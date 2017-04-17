package com.wangyuanwmm.wmm.utils;

/**
 * 数据、常量、api接口
 */

public class StaticClass {

    // 消息内容获取与离线下载
    // 在最新消息中获取到的id，拼接到这个NEWS之后，可以获得对应的JSON格式的内容
    public static final String ZHIHU_NEWS = "http://news-at.zhihu.com/api/4/news/";

    // 过往消息
    // 若要查询的11月18日的消息，before后面的数字应该为20161118
    // 知乎日报的生日为2013 年 5 月 19 日，如果before后面的数字小于20130520，那么只能获取到空消息
    public static final String ZHIHU_HISTORY = "http://news.at.zhihu.com/api/4/news/before/";


    // 获取果壳精选的文章列表,通过组合相应的参数成为完整的url
    public static final String GUOKR_ARTICLES = "http://apis.guokr.com/handpick/article.json?retrieve_type=by_since&category=all&limit=25&ad=1";

    // 获取果壳文章的具体信息 V1
    public static final String GUOKR_ARTICLE_LINK_V1 = "http://jingxuan.guokr.com/pick/";

    // 豆瓣一刻
    // 根据日期查询消息列表
    // eg:https://moment.douban.com/api/stream/date/2016-08-11
    public static final String DOUBAN_MOMENT = "https://moment.douban.com/api/stream/date/";

    // 获取文章具体内容
    // eg:https://moment.douban.com/api/post/100484
    public static final String DOUBAN_ARTICLE_DETAIL = "https://moment.douban.com/api/post/";

    //极速新闻API
    //头条
    public static final String TOP_API = "http://api.jisuapi.com/news/get?channel=%E5%A4%B4%E6%9D%A1&start=0&num=30&appkey=e6a45186161863e8";
    //新闻
    public static final String NEWS_API = "http://api.jisuapi.com/news/get?channel=%E6%96%B0%E9%97%BB&start=0&num=30&appkey=e6a45186161863e8";
    //科技
    public static final String TECh_API = "http://api.jisuapi.com/news/get?channel=%E7%A7%91%E6%8A%80&start=0&num=30&appkey=e6a45186161863e8";
    //财经
    public static final String MONEY_API = "http://api.jisuapi.com/news/get?channel=%E8%B4%A2%E7%BB%8F&start=0&num=30&appkey=e6a45186161863e8";
    //体育
    public static final String PT_API = "http://api.jisuapi.com/news/get?channel=%E4%BD%93%E8%82%B2&start=0&num=30&appkey=e6a45186161863e8";


    //Bmob ApplicationID
    public static final String BMOB_APP_ID = "a7d3fafe72a63a21deaee0dc0206dd9b";
}