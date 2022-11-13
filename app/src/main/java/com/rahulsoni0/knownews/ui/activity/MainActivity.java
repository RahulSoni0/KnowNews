package com.rahulsoni0.knownews.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.rahulsoni0.knownews.R;
import com.rahulsoni0.knownews.adapter.CategoryViewPagerAdapter;
import com.rahulsoni0.knownews.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;
    CategoryViewPagerAdapter categoryViewPagerAdapter;
    ActionBarDrawerToggle toggle;
    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem headlines, sports, technology, science, politics, entertainment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = findViewById(R.id.TabLayoutCategory);
        viewPager = findViewById(R.id.vewPagerCategory);
        //tabItems
        headlines = findViewById(R.id.tabHeadlines);
        sports = findViewById(R.id.tabSports);
        technology = findViewById(R.id.tabTechnology);
        science = findViewById(R.id.tabScience);
        politics = findViewById(R.id.tabPolitics);
        entertainment = findViewById(R.id.tabEntertainment);

        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);

        categoryViewPagerAdapter = new CategoryViewPagerAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(categoryViewPagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.Home) {
            Toast.makeText(this, "Clicked Home", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.Liked) {
            Toast.makeText(this, "Clicked Liked", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.About) {
            Toast.makeText(this, "Clicked About", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.share) {
            Toast.makeText(this, "Clicked share", Toast.LENGTH_SHORT).show();
        }

        return false;

    }
}