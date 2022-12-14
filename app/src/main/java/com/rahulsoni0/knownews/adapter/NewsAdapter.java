package com.rahulsoni0.knownews.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rahulsoni0.knownews.R;
import com.rahulsoni0.knownews.cache.SavedListDatabase;
import com.rahulsoni0.knownews.cache.SavedListEntityModel;
import com.rahulsoni0.knownews.databinding.NewsItemBinding;
import com.rahulsoni0.knownews.model.ArticleModel;
import com.rahulsoni0.knownews.ui.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {
    List<ArticleModel> newsList;
    List<SavedListEntityModel> savedNews = new ArrayList<>();


    public NewsAdapter(List<ArticleModel> newsList, List<SavedListEntityModel> savedNews) {
        this.newsList = newsList;
        this.savedNews = savedNews;
    }

    @NonNull
    @Override
    public NewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
//        return new NewsAdapterViewHolder(view);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapterViewHolder holder, int position) {
        ArticleModel news = newsList.get(position);
        holder.setData(news.getUrlToImage(), news.getTitle(), news.getSource().getName(), news);

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder {

        private ImageView newsImage , heart , sharebtn;
        private TextView titleTv, sourceTv;

        public NewsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.ivItemNewsImage);
            heart = itemView.findViewById(R.id.btnItemLiked);
            titleTv = itemView.findViewById(R.id.tvItemNewsTitle);
            sourceTv = itemView.findViewById(R.id.tvItemNewsSource);
            sharebtn = itemView.findViewById(R.id.btnItemShare);


            newsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), WebViewActivity.class);
                    intent.putExtra("url", newsList.get(getAdapterPosition()).getUrl());
                    itemView.getContext().startActivity(intent);
                    Toast.makeText(itemView.getContext(), "Redirecting.....", Toast.LENGTH_SHORT).show();
                }
            });

            heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    heart.setImageResource(R.drawable.red_heart);
                    addSavedList(newsList.get(getAdapterPosition()));
                }
            });
            sharebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    /*This will be the actual content you wish you share.*/
                    String shareBody = " ???????? hey , I found this news on KnowNews App . \n check this out : "
                            + newsList.get(getAdapterPosition()).getUrl();
                    /*The type of the content is text, obviously.*/
                    intent.setType("text/plain");
                    /*Applying information Subject and Body.*/
                    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Shared from KnowNews App");
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    /*Fire!*/
                    itemView.getContext().startActivity(Intent.createChooser(intent, shareBody));
                }
            });
        }

        public void setData(String urlToImage, String title, String source, ArticleModel articleModel) {
            SavedListEntityModel s = new SavedListEntityModel(articleModel.getAuthor(), articleModel.getContent(),
                    articleModel.getDescription(), articleModel.getPublishedAt(), articleModel.getSource().getName()
                    , articleModel.getTitle(), articleModel.getUrl(), articleModel.getUrlToImage());
            Set<String> titles = new HashSet<>();
            for (SavedListEntityModel sss : savedNews
            ) {
                titles.add(sss.getTitle());
            }
            if (titles.contains(s.getTitle())) {
                Glide.with(itemView.getContext()).load(R.drawable.red_heart).into(heart);
            }

            titleTv.setText(title);
            sourceTv.setText(source);
            Glide.with(itemView.getContext()).load(urlToImage).into(newsImage);
        }

        public void addSavedList(ArticleModel articleModel) {
//            binding.btnItemLiked.setImageResource(R.drawable.red_heart);
            Glide.with(itemView.getContext()).load(R.drawable.red_heart).into(heart);
            SavedListDatabase db = SavedListDatabase.getInstance(itemView.getContext());
            SavedListEntityModel s = new SavedListEntityModel(articleModel.getAuthor(), articleModel.getContent(),
                    articleModel.getDescription(), articleModel.getPublishedAt(), articleModel.getSource().getName()
                    , articleModel.getTitle(), articleModel.getUrl(), articleModel.getUrlToImage());
            Set<String> titles = new HashSet<>();
            for (SavedListEntityModel sss : savedNews
            ) {
                titles.add(sss.getTitle());
            }
            if (titles.contains(s.getTitle())) {
                Toast.makeText(itemView.getContext(), "Already present", Toast.LENGTH_SHORT).show();
            } else {
                db.savedListDao().insertSavedNews(s);
                savedNews.add(s);
                Toast.makeText(itemView.getContext(), "Saved this news", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
