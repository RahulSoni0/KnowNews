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
import com.rahulsoni0.knownews.databinding.FragmentHeadlinesBinding;
import com.rahulsoni0.knownews.model.ArticleModel;
import com.rahulsoni0.knownews.model.NewsContentModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HeadlinesFragment extends Fragment {


    private FragmentHeadlinesBinding binding;
    private int pageNo = 1;
    NewsAdapter adapter;
    List<ArticleModel> headlinesList = new ArrayList<>();
    List<SavedListEntityModel> savedNews = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHeadlinesBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SavedListDatabase db = SavedListDatabase.getInstance(getContext());
        List<SavedListEntityModel> sn = db.savedListDao().getAllNews();
        savedNews.addAll(sn);
        initData(1);
        setUpPagination(true);
        initRv();
    }

    public void initData(int page) {
        NewsApiInterface newsApiInterface = NewsRetrofitClient.getNewsClient(BASE_URL);
        Call<NewsContentModel> call = newsApiInterface.getBreakingNews(page, "in", API_KEY);
        call.enqueue(new Callback<NewsContentModel>() {
            @Override
            public void onResponse(Call<NewsContentModel> call, Response<NewsContentModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NewsContentModel headlines = response.body();
                    List<ArticleModel> headlinesData = headlines.getArticles();
                    headlinesList.addAll(headlinesData);
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
        adapter = new NewsAdapter(headlinesList, savedNews);
        binding.rvHeadlines.setAdapter(adapter);
        binding.rvHeadlines.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter.notifyDataSetChanged();
    }

    private void setUpPagination(boolean isAllowed) {
        if (isAllowed) {
            binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        if (pageNo < 4) {
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
}