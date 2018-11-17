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
public class JokesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<JokeBean.CommentsBean> data;
    private int status = 1;
    public static final int LOAD_MORE = 0;
    public static final int LOAD_PULL_TO = 1;
    public static final int LOAD_NONE = 2;
    public static final int LOAD_END = 3;
    private static final int TYPE_FOOTER = -1;

    @Inject
    public JokesAdapter(@ContextLife Context context, List<JokeBean.CommentsBean> data) {
        mContext = context;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        }
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View view = View.inflate(parent.getContext(), R.layout.activity_view_footer, null);
            return new FooterViewHolder(view);
        } else {
            View rootView = View.inflate(parent.getContext(), R.layout.item_joke, null);
            return new JokeViewHolder(rootView);
        }
    }

    /**
     * footer view
     */
    class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_load_prompt)
        TextView tv_load_prompt;
        @BindView(R.id.progress)
        ProgressBar progress;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.instance(mContext).dip2px(40));
            itemView.setLayoutParams(params);
        }

        private void bindItem() {
            switch (status) {
                case LOAD_MORE:
                    progress.setVisibility(View.VISIBLE);
                    tv_load_prompt.setText("正在加载...");
                    itemView.setVisibility(View.VISIBLE);
                    break;
                case LOAD_PULL_TO:
                    progress.setVisibility(View.GONE);
                    tv_load_prompt.setText("上拉加载更多");
                    itemView.setVisibility(View.VISIBLE);
                    break;
                case LOAD_NONE:
                    progress.setVisibility(View.GONE);
                    tv_load_prompt.setText("已无更多加载");
                    break;
                case LOAD_END:
                    itemView.setVisibility(View.GONE);
                default:
                    break;
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.bindItem();
        } else {
            JokeViewHolder jokeViewHolder = (JokeViewHolder) holder;
            jokeViewHolder.bindItem(data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class JokeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_author)
        TextView tv_author;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_like)
        TextView tv_like;
        @BindView(R.id.tv_unlike)
        TextView tv_unlike;
        @BindView(R.id.tv_comment_count)
        TextView tv_comment_count;
        @BindView(R.id.card_joke)
        CardView card_joke;

        public JokeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ScreenUtil screenUtil = ScreenUtil.instance(mContext);
            int screenWidth = screenUtil.getScreenWidth();
            card_joke.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        private void bindItem(JokeBean.CommentsBean commentsBean) {
            tv_author.setText(commentsBean.getComment_author());
            tv_time.setText(DateUtils.getTimestampString(DateUtils.string2Date(commentsBean.getComment_date(), "yyyy-MM-dd HH:mm:ss")));
            tv_content.setText(commentsBean.getText_content());
            tv_like.setText(commentsBean.getVote_negative());
            tv_unlike.setText(commentsBean.getVote_positive());
            tv_comment_count.setText(commentsBean.getSub_comment_count());
        }
    }

    //改变recylerview的状态
    public void updateLoadStatus(int status) {
        this.status = status;
        notifyDataSetChanged();
    }
}
