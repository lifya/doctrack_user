package com.pln.www;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.pln.www.fragment.AboutFragment;
import com.pln.www.fragment.ChatFragment;
import com.pln.www.fragment.SettingFragment;

import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager;
    ImageView imagev1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imagev1 = (ImageView) findViewById(R.id.imagev1);
        imagev1.setOnClickListener((View.OnClickListener) this);

        viewPager = (ViewPager) findViewById(R.id.contain);

        ViewPageAdapter viewPagerAdapter = new ViewPageAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public void onClick(View v) {
        if(v == imagev1){
            ChatFragment sFrag = new ChatFragment();
            setChatFragment(sFrag);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_beranda) {
            HomeActivity home = new HomeActivity();
            sentoStartBeranda();

            // Handle the camera action
        }   else if (id == R.id.nav_about) {
            AboutFragment aFrag = new AboutFragment();
            setFragment(aFrag);

        }else if (id ==  R.id.nav_setting) {
            SettingFragment sFrag = new SettingFragment();
            setFragmentSetting(sFrag);

        }
        else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            sendtoStart();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setFragment(android.support.v4.app.Fragment frag) {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.main_content, frag);
        ft.addToBackStack(null);
        ft.commit();
    }
    private void setFragmentSetting(android.support.v4.app.Fragment frag) {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.main_content, frag);
        ft.addToBackStack(null);
        ft.commit();
    }
    private void setChatFragment(android.support.v4.app.Fragment cfrag) {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.main_content, cfrag);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void sendtoStart(){
        Intent startIntent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }
    public void sentoStartBeranda() {
        Intent startIntent = new Intent(HomeActivity.this, HomeActivity.class);
        startActivity(startIntent);
        finish();
    }


    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    }
                    else if (viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    }
                    else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }


}
