package com.wangyuanwmm.wmm.Presenter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.wangyuanwmm.wmm.Interface.NewsContract;
import com.wangyuanwmm.wmm.Interface.OnStringListener;
import com.wangyuanwmm.wmm.detail.DetailActivity;
import com.wangyuanwmm.wmm.entity.BeanType;
import com.wangyuanwmm.wmm.entity.NewData;
import com.wangyuanwmm.wmm.entity.StringModelImpl;
import com.wangyuanwmm.wmm.service.CacheService;
import com.wangyuanwmm.wmm.utils.DatabaseHelper;
import com.wangyuanwmm.wmm.utils.StaticClass;
import com.wangyuanwmm.wmm.utils.UtilTools;

import java.util.ArrayList;
import java.util.Random;

public class NewsPresenter implements NewsContract.Presenter{

    private NewsContract.View view;
    private Context context;
    private StringModelImpl model;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Gson gson = new Gson();

    private ArrayList<NewData.list> list = new ArrayList<>();

    public NewsPresenter(Context context, NewsContract.View view) {
        this.context = context;
        this.view = view;
        model = new StringModelImpl(context);
        dbHelper = new DatabaseHelper(context, "History.db", null, 5);
        db = dbHelper.getWritableDatabase();
    }

    //开始阅读
    @Override
    public void startReading(int position) {
        NewData.list item = list.get(position);
        //跳转到阅读界面
        context.startActivity(new Intent(context, DetailActivity.class)
                .putExtra("type", BeanType.TYPE_NEWS)
                .putExtra("coverUrl", item.getImgUrl())
                .putExtra("title", item.getTitle())
        );
    }

    //随机阅读
    @Override
    public void feelLucky() {
        if (list.isEmpty()) {
            view.showError();
            return;
        }
        startReading(new Random().nextInt(list.size()));
    }

    @Override
    public void start() {
        loadPosts();
    }

    //加载信息
    @Override
    public void loadPosts() {

        view.showLoading();

        if (UtilTools.networkConnected(context)) {

            model.load(StaticClass.NEWS_API, new OnStringListener() {
                @Override
                public void onSuccess(String result) {

                    //list.clear();

                    try {
                        NewData data = gson.fromJson(result, NewData.class);

                        for (NewData.list da : data.getList()) {

                            list.add(da);

                            if (!queryIfIDExists(da.getTitle())) {
                                try {
                                    db.beginTransaction();
                                    ContentValues values = new ContentValues();
                                    values.put("news_title", da.getTitle());
                                    values.put("news_news", gson.toJson(da));
                                    values.put("news_content", "");
                                    values.put("news_time", (long) da.getTiem());
                                    db.insert("News", null, values);
                                    values.clear();
                                    db.setTransactionSuccessful();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.endTransaction();
                                }

                            }

                            Intent intent = new Intent("com.marktony.zhihudaily.LOCAL_BROADCAST");
                            intent.putExtra("type", CacheService.TYPE_NEWS);
                            intent.putExtra("title", da.getTitle());
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        }
                        view.showResults(list);

                    } catch (JsonSyntaxException e) {
                        view.showError();
                    }

                    view.stopLoading();
                }

                @Override
                public void onError(VolleyError error) {
                    view.stopLoading();
                    view.showError();
                }
            });

        } else {
            Cursor cursor = db.query("Guokr", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    NewData.list result = gson.fromJson(cursor.getString(cursor.getColumnIndex("news_news")), NewData.list.class);
                    list.add(result);
                } while (cursor.moveToNext());
            }
            cursor.close();
            view.stopLoading();
            view.showResults(list);
            //当第一次安装应用，并且没有打开网络时
            //此时既无法网络加载，也无法本地加载
            if (list.isEmpty()) {
                view.showError();
            }
        }

    }

    @Override
    public void refresh() {
        loadPosts();
    }

    private boolean queryIfIDExists(String title){
        Cursor cursor = db.query("News",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                if (title == cursor.getString(cursor.getColumnIndex("news_id"))){
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        return false;
    }

}
