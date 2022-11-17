package com.rahulsoni0.knownews.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.rahulsoni0.knownews.R;
import com.rahulsoni0.knownews.adapter.CategoryViewPagerAdapter;
import com.rahulsoni0.knownews.databinding.ActivityMainBinding;
import com.rahulsoni0.knownews.util.InternetManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;
    CategoryViewPagerAdapter categoryViewPagerAdapter;
    ActionBarDrawerToggle toggle;
    private HandlerThread mHandlerThread;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //internet check
        mHandlerThread = new HandlerThread("HandlerThread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (InternetManager.isConnected(MainActivity.this)) {
                    //gone no-internet layout
                    //show frame layout
                } else {
                    //gone frame layout
                    //show no-internet layout
                }
            }
        }, 1000);


        setSupportActionBar(binding.contentMain.toolbar);
        toggle = new ActionBarDrawerToggle(MainActivity.this, binding.drawerLayout, binding.contentMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);
        categoryViewPagerAdapter = new CategoryViewPagerAdapter(this, getSupportFragmentManager(), binding.contentMain.TabLayoutCategory.getTabCount());


        binding.contentMain.vewPagerCategory.setAdapter(categoryViewPagerAdapter);
        binding.contentMain.TabLayoutCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.contentMain.vewPagerCategory.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        binding.contentMain.vewPagerCategory.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.contentMain.TabLayoutCategory));


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.Home) {
            Toast.makeText(this, "Clicked Home", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.Liked) {
            Intent i = new Intent(MainActivity.this, SavedListActivity.class);
            startActivity(i);
//            Toast.makeText(this, "Clicked Liked", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.About) {
            Toast.makeText(this, "Clicked About", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.share) {
            Toast.makeText(this, "Clicked share", Toast.LENGTH_SHORT).show();
        }

        return false;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}