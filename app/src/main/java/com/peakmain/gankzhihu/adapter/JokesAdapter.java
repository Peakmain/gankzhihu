package com.peakmain.gankzhihu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.peakmain.baselibrary.recylerview.adapter.CommonRecyclerAdapter;
import com.peakmain.baselibrary.recylerview.adapter.ViewHolder;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.bean.jandan.JokeBean;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.utils.DateUtils;
import com.peakmain.gankzhihu.utils.ScreenUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/17 0017 上午 8:48
 * mail : 2726449200@qq.com
 * describe ：
 */
public class JokesAdapter extends CommonRecyclerAdapter<JokeBean.CommentsBean> {
    private Context mContext;

    @Inject
    public JokesAdapter(@ContextLife Context context, List<JokeBean.CommentsBean> data) {
        super(context,data,R.layout.item_joke);
        mContext = context;

    }


    @Override
    public void convert(ViewHolder holder, JokeBean.CommentsBean commentsBean) {
        ScreenUtil screenUtil = ScreenUtil.instance(mContext);
        int screenWidth = screenUtil.getScreenWidth();
        CardView card_joke=holder.getView(R.id.card_joke);
        card_joke.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT));
        holder.setText(R.id.tv_author,commentsBean.getComment_author());
        holder.setText(R.id.tv_time,DateUtils.getTimestampString(DateUtils.string2Date(commentsBean.getComment_date(), "yyyy-MM-dd HH:mm:ss")));
        holder.setText(R.id.tv_content,commentsBean.getText_content());
        holder.setText(R.id.tv_like,commentsBean.getVote_negative());
        holder.setText(R.id.tv_unlike,commentsBean.getVote_positive());
        holder.setText(R.id.tv_comment_count,commentsBean.getSub_comment_count());
    }

}
