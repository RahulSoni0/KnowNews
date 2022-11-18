package com.rahulsoni0.knownews.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rahulsoni0.knownews.adapter.SavedNewsAdapter;
import com.rahulsoni0.knownews.cache.SavedListDatabase;
import com.rahulsoni0.knownews.cache.SavedListEntityModel;
import com.rahulsoni0.knownews.databinding.ActivitySavedListBinding;

import java.util.ArrayList;
import java.util.List;

public class SavedListActivity extends AppCompatActivity {

    private ActivitySavedListBinding binding;
    List<SavedListEntityModel> news = new ArrayList<>();
    SavedNewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SavedListActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        SavedListDatabase db = SavedListDatabase.getInstance(this.getApplicationContext());
        List<SavedListEntityModel> savednews = db.savedListDao().getAllNews();
        //Toast.makeText(this, ""+ savednews.toString(), Toast.LENGTH_SHORT).show();
        if (!savednews.isEmpty()) {
            news.addAll(savednews);
            binding.ivEmptyDataImage.setVisibility(View.GONE);
            binding.tvEmptyDataText.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "No saved news", Toast.LENGTH_SHORT).show();
            binding.ivEmptyDataImage.setVisibility(View.VISIBLE);
            binding.tvEmptyDataText.setVisibility(View.VISIBLE);

        }
        initRv();
    }

    private void initRv() {
        adapter = new SavedNewsAdapter(news);
        binding.rvSavedList.setAdapter(adapter);
        binding.rvSavedList.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false));
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}