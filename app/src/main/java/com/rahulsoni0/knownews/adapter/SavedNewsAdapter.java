package com.rahulsoni0.knownews.adapter;

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
        private ImageView deleteBtn, heart;
        private TextView titleTv, sourceTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteBtn = itemView.findViewById(R.id.btnItemDelete);
            heart = itemView.findViewById(R.id.ivItemNewsImage);
            titleTv = itemView.findViewById(R.id.tvItemNewsTitle);
            sourceTv = itemView.findViewById(R.id.tvItemNewsSource);

        }

        public void setData(String urlToImage, String title, String source) {
            titleTv.setText(title);
            sourceTv.setText(source);
            Glide.with(itemView.getContext()).load(urlToImage).into(heart);
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
