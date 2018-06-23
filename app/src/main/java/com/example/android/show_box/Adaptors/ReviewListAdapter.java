package com.example.android.show_box.Adaptors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.show_box.Models.Reviews_POJO;
import com.example.android.show_box.R;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder> {

    Context context;
    private List<Reviews_POJO> mReviewList;
    private LayoutInflater mInflater;

    public ReviewListAdapter(Context context, List<Reviews_POJO> mReviewList) {
        this.context = context;
        this.mReviewList = mReviewList;
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.review_list_item,parent,false);
        return new ReviewViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewViewHolder holder, int position) {

        holder.mAuthor.setText(mReviewList.get(position).getAuthor());
        holder.mContent.setText(mReviewList.get(position).getContent());
        holder.mContent.setEllipsize(TextUtils.TruncateAt.END);
        holder.mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mContent.getMaxLines() == 3) {
                    holder.mContent.setEllipsize(null);
                    holder.mContent.setMaxLines(100);
                } else {
                    if(holder.mContent.getMaxLines() == 100){
                        holder.mContent.setEllipsize(TextUtils.TruncateAt.END);
                        holder.mContent.setMaxLines(3);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.author_tv)
        TextView mAuthor;

        @BindView(R.id.content_tv)
        TextView mContent;
    public ReviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
}
