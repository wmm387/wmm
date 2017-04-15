package com.wangyuanwmm.wmm.utils;

/**
 * 数据、常量、api接口
 */

public class StaticClass {

    // 所有的知乎日报API的HTTP METHOD 均为GET

    // 知乎日报base url,将文章id拼接值base url之后即可
    public static final String ZHIHU_DAILY_BASE_URL = "http://news-at.zhihu.com/story/";

    // 获取界面启动图像
    // start_image后面为图像分辨率
    public static final String START_IMAGE = "http://news-at.zhihu.com/api/4/start-image/1080*1776";

    // 最新消息，ZHIHU_NEWS API替代，拼接当日日期后可以获取
    public static final String LATEST = "http://news-at.zhihu.com/api/4/news/latest";

    // 消息内容获取与离线下载
    // 在最新消息中获取到的id，拼接到这个NEWS之后，可以获得对应的JSON格式的内容
    public static final String ZHIHU_NEWS = "http://news-at.zhihu.com/api/4/news/";

    // 过往消息
    // 若要查询的11月18日的消息，before后面的数字应该为20161118
    // 知乎日报的生日为2013 年 5 月 19 日，如果before后面的数字小于20130520，那么只能获取到空消息
    public static final String ZHIHU_HISTORY = "http://news.at.zhihu.com/api/4/news/before/";

    // 新闻对应长评论查看
    // 使用在 最新消息 中获得的 id
    // 在 http://news-at.zhihu.com/api/4/story/#{id}/long-comments 中将 id 替换为对应的 id
    // 得到长评论 JSON 格式的内容
    // 新闻对应短评论查看
    // http://news-at.zhihu.com/api/4/story/4232852/short-comments
    // 使用在 最新消息 中获得的 id
    // 在 http://news-at.zhihu.com/api/4/story/#{id}/short-comments 中将 id 替换为对应的 id
    // 得到短评论 JSON 格式的内容
    public static final String COMMENTS = "http://news-at.zhihu.com/api/4/story/";


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

    //快递key
    public static final String COURIER_KEY = "ab88f4c39e4309aefde9e374fb2a9c22";

    //归属地key
    public static final String PHONE_KEY = "49bcb0f66e16dab8f31bc63b2650ce31";

    //问答机器人key
    public static final String CHAT_LIST_KEY = "1692ea66247c1203a4a1330c84d7921b";

    //微信精选key
    public static final String WECHAT_KEY = "9ad4dbd21f550c79fbc3cc2db906eb54";

    //gank.io接口
    public static final String GANK_URL = "http://gank.io/api/search/query/listview/category/.../count/20/page/1";

    //语音key
    public static final String VOICE_KEY = "58a3b041";

    //短信Action
    public static final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    //版本更新
    public static final String CHECK_UPDATE_URL = "http://192.168.1.105:8080/wangyuan/config.json";
}