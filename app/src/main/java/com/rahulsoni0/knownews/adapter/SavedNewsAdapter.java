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
import com.rahulsoni0.knownews.ui.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.ViewHolder> {
    List<SavedListEntityModel> newslist = new ArrayList<>();

    public SavedNewsAdapter(List<SavedListEntityModel> newslist) {
        this.newslist = newslist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.savednews_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SavedListEntityModel news = newslist.get(position);
        holder.setData(news.getUrlToImage(), news.getTitle(), news.getSource());
    }

    @Override
    public int getItemCount() {
        return newslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView deleteBtn, newsImage, sharebtn;
        private TextView titleTv, sourceTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteBtn = itemView.findViewById(R.id.btnItemDelete);
            newsImage = itemView.findViewById(R.id.ivItemNewsImage);
            titleTv = itemView.findViewById(R.id.tvItemNewsTitle);
            sourceTv = itemView.findViewById(R.id.tvItemNewsSource);
            sharebtn = itemView.findViewById(R.id.btnItemShare);


            newsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), WebViewActivity.class);
                    intent.putExtra("url", newslist.get(getAdapterPosition()).getUrl());
                    itemView.getContext().startActivity(intent);
                    Toast.makeText(itemView.getContext(), "Redirecting.....", Toast.LENGTH_SHORT).show();
                }
            });

            sharebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    /*This will be the actual content you wish you share.*/
                    String shareBody = " 👋🏻 hey , I found this news on KnowNews App . \n check this out : "
                            + newslist.get(getAdapterPosition()).getUrl();
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


        public void setData(String urlToImage, String title, String source) {
            titleTv.setText(title);
            sourceTv.setText(source);
            Glide.with(itemView.getContext()).load(urlToImage).into(newsImage);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SavedListDatabase db = SavedListDatabase.getInstance(itemView.getContext());
                    SavedListEntityModel s = newslist.get(getLayoutPosition());
                    newslist.remove(getLayoutPosition());
                    db.savedListDao().deleteNews(s);
                    Toast.makeText(itemView.getContext(), "delete succesfull", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            });
        }
    }

}
