package com.peakmain.gankzhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.bean.pointplay.Channel;
import com.peakmain.gankzhihu.ui.activity.PointPlayActivity;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/26 0026 下午 2:16
 * mail : 2726449200@qq.com
 * describe ：
 */
public class ChannelAdapter extends BaseAdapter {

    private Context mContext;

    public ChannelAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return Channel.MAX_COUNT;
    }

    @Override
    public Channel getItem(int position) {
        return new Channel(position + 1, mContext);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Channel chanel = getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.pointplay_grid_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.tv_home_item_text);
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_home_item_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(chanel.getChannelName());
        int id = chanel.getChannelId();
        int imgResId = -1;
        switch (id) {
            case Channel.SHOW:
                imgResId = R.drawable.ic_show;
                break;
            case Channel.MOVIE:
                imgResId = R.drawable.ic_movie;
                break;
            case Channel.COMIC:
                imgResId = R.drawable.ic_comic;
                break;
            case Channel.DOCUMENTRY:
                imgResId = R.drawable.ic_doucument;
                break;
            case Channel.MUSIC:
                imgResId = R.drawable.ic_music;
                break;
            case Channel.VARIETY:
                imgResId = R.drawable.ic_variety;
                break;
            case Channel.LIVE:
                imgResId = R.drawable.ic_live;
                break;
            case Channel.FAVORITE:
                imgResId = R.drawable.ic_bookmark;
                break;
            case Channel.HISTORY:
                imgResId = R.drawable.ic_history;
                break;
        }

        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(imgResId));

        return convertView;
    }
}

class ViewHolder {
    TextView textView;
    ImageView imageView;
}
