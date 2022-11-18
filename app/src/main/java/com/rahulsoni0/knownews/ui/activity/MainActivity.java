package com.rahulsoni0.knownews.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.rahulsoni0.knownews.R;
import com.rahulsoni0.knownews.adapter.CategoryViewPagerAdapter;
import com.rahulsoni0.knownews.broadcast.NetworkStateReceiver;
import com.rahulsoni0.knownews.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NetworkStateReceiver.NetworkStateReceiverListener {
    private ActivityMainBinding binding;
    CategoryViewPagerAdapter categoryViewPagerAdapter;
    ActionBarDrawerToggle toggle;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private NetworkStateReceiver networkStateReceiver;
    Dialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //internet check


        d = new Dialog(MainActivity.this);
        d.setContentView(R.layout.no_internet_dialog);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.setCancelable(false);

        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(MainActivity.this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));


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
//            Toast.makeText(this, "Clicked Home", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.Liked) {
            Intent i = new Intent(MainActivity.this, SavedListActivity.class);
            startActivity(i);
//            Toast.makeText(this, "Clicked Liked", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.About) {
            Toast.makeText(this, "Developed with ♥️ by Rahul Soni", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.share) {
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            /*This will be the actual content you wish you share.*/
            String shareBody = " 👋🏻 Hey , I found this Amazing KnowNews App . \n check this out : "
                    + " https://github.com/RahulSoni0/KnowNews ";
            /*The type of the content is text, obviously.*/
            intent.setType("text/plain");
            /*Applying information Subject and Body.*/
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Shared from KnowNews App");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            /*Fire!*/
            startActivity(Intent.createChooser(intent, shareBody));
        }

        return false;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        networkStateReceiver.removeListener(this);
        this.unregisterReceiver(networkStateReceiver);
    }

    @Override
    public void networkAvailable() {

        d.dismiss();
        Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void networkUnavailable() {
        d.show();
        Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();


    }
}