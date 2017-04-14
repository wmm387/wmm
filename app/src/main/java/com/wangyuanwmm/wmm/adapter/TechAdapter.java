package com.wangyuanwmm.wmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangyuanwmm.wmm.R;
import com.wangyuanwmm.wmm.entity.TechData;
import com.wangyuanwmm.wmm.utils.PicassoUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/4/7.
 */

public class TechAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<TechData> mList;
    private TechData data;

    private int width;
    private WindowManager windowManager;

    public TechAdapter(Context mContext, List<TechData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item, null);
            viewHolder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_source = (TextView) convertView.findViewById(R.id.tv_source);
            convertView.setTag(viewHolder);//设置缓存
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        data = mList.get(position);
        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_source.setText(data.getSource());

        //加载图片
        if (data.getImgUrl().isEmpty()) {
            viewHolder.iv_img.setImageResource(R.drawable.placeholder);
        } else{
            PicassoUtils.loadImageViewSize(mContext,data.getImgUrl(),width/3,250,viewHolder.iv_img);
        }

        return convertView;
    }

    class ViewHolder {
        private ImageView iv_img;
        private TextView tv_title;
        private TextView tv_source;
    }

}
