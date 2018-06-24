package com.example.mg.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mg.newsapp.model.HeadLinesModel;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {


    private Context mContext;
    private List<HeadLinesModel.Articles> mArticles;
    private IItemClickListener clickListener;

    public NewsListAdapter(Context context, List<HeadLinesModel.Articles> articles) {
        mContext = context;
        mArticles = articles;
        clickListener = (IItemClickListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.author.setText(String.format("by: %s", mArticles.get(position).getAuthor()));
        holder.description.setText(mArticles.get(position).getDescription());
        holder.publishedAt.setText(mArticles.get(position).getPublishedAt());

        Glide.with(mContext)
                .load(mArticles.get(position).getUrlToImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.DATA))
                .into(holder.articleImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(mArticles.get(holder.getAdapterPosition()).getUrl());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView articleImage;
        TextView author, description, publishedAt;

        ViewHolder(View itemView) {
            super(itemView);
            articleImage = itemView.findViewById(R.id.image_article);
            author = itemView.findViewById(R.id.text_auther);
            description = itemView.findViewById(R.id.text_description);
            publishedAt = itemView.findViewById(R.id.text_published_at);
        }
    }

    public interface IItemClickListener {
        void onItemClick(String position);
    }
}
