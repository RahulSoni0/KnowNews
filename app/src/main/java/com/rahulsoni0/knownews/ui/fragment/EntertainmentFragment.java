package com.rahulsoni0.knownews.ui.fragment;

import static com.rahulsoni0.knownews.util.Constants.API_KEY;
import static com.rahulsoni0.knownews.util.Constants.BASE_URL;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rahulsoni0.knownews.adapter.NewsAdapter;
import com.rahulsoni0.knownews.api.NewsApiInterface;
import com.rahulsoni0.knownews.api.NewsRetrofitClient;
import com.rahulsoni0.knownews.cache.SavedListDatabase;
import com.rahulsoni0.knownews.cache.SavedListEntityModel;
import com.rahulsoni0.knownews.databinding.FragmentEntertainmentBinding;
import com.rahulsoni0.knownews.model.ArticleModel;
import com.rahulsoni0.knownews.model.NewsContentModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntertainmentFragment extends Fragment {

    private FragmentEntertainmentBinding binding;
    private int pageNo = 1;
    NewsAdapter adapter;
    List<ArticleModel> newsDataList = new ArrayList<>();
    List<SavedListEntityModel> savedNews = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEntertainmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRv();
        initData(1);
        setUpPagination(true);

        SavedListDatabase db = SavedListDatabase.getInstance(getContext());
        List<SavedListEntityModel> sn = db.savedListDao().getAllNews();
        savedNews.addAll(sn);

    }

    private void setUpPagination(boolean isAllowed) {

        if (isAllowed) {
            binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        if (pageNo < 5) {
                            initData(++pageNo);
                            Toast.makeText(getContext(), "On Page" + pageNo + " ", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getContext(), "Reached end ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }
    }


    private void initData(int page) {
        NewsApiInterface newsApiInterface = NewsRetrofitClient.getNewsClient(BASE_URL);
        Call<NewsContentModel> call = newsApiInterface.getGeneralNews(page, "entertainment OR music OR song OR Bollywood OR Hollywood OR podcast OR gaming", "en", "popularity", API_KEY);
        call.enqueue(new Callback<NewsContentModel>() {
            @Override
            public void onResponse(Call<NewsContentModel> call, Response<NewsContentModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NewsContentModel headlines = response.body();
                    List<ArticleModel> headlinesData = headlines.getArticles();
                    newsDataList.addAll(headlinesData);
                    adapter.notifyDataSetChanged();
                    Log.d("@@@@@", "onResponse: " + headlinesData.toString());

                } else {
                    Toast.makeText(getContext(), "ff", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsContentModel> call, Throwable t) {
                Log.d("@@@@@", "onResponse: " + t.getLocalizedMessage());
                Toast.makeText(getActivity().getBaseContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRv() {
        adapter = new NewsAdapter(newsDataList, savedNews);
        binding.rvHeadlines.setAdapter(adapter);
        binding.rvHeadlines.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter.notifyDataSetChanged();
    }
}