package com.example.mg.newsapp.screens.NewsActivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mg.newsapp.R;
import com.example.mg.newsapp.data.model.HeadLinesModel;
import com.example.mg.newsapp.screens.NewsActivity.DI.IActivityScope;

import java.util.List;

import javax.inject.Inject;

@IActivityScope
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {


    private List<HeadLinesModel.Articles> mArticles;
    private final RequestManager mGlide;
    private IItemClickListener mClickListener;

    @Inject
    public NewsListAdapter(NewsActivity context, RequestManager glide) {
        mClickListener = context;
        mGlide = glide;
    }

    public void setData(List<HeadLinesModel.Articles> articles) {
        this.mArticles = articles;
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

        mGlide.load(mArticles.get(position).getUrlToImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.DATA))
                .into(holder.articleImage);

        holder.itemView.setOnClickListener(v -> mClickListener.onItemClick(mArticles.get(holder.getAdapterPosition()).getUrl()));

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
