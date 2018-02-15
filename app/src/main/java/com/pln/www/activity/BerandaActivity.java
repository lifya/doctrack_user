package com.pln.www.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pln.www.R;
import com.pln.www.adapter.ViewPageAdapter;
import com.pln.www.fragment.AboutFragment;
import com.pln.www.fragment.SettingFragment;

import java.util.Timer;
import java.util.TimerTask;

public class BerandaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private ViewPager viewPager;
    private ImageView imagev1;
    private ImageView imagev2;
    private TextView tvName, tvEmail;
    private long backPressedTime = 0;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        imagev1 = (ImageView) findViewById(R.id.imagev1);
        imagev1.setOnClickListener(this);

        imagev2 = (ImageView) findViewById(R.id.materi);
        imagev2.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        viewPager = (ViewPager) findViewById(R.id.contain);

        ViewPageAdapter viewPagerAdapter = new ViewPageAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 4000, 6000);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvEmail);
        tvName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvName);
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        String email = currentUser.getEmail();
        tvEmail.setText(email);
//        Query query = databaseReference.orderByChild("email").equalTo(email);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
//                    UserModel userModel = singleSnapshot.getValue(UserModel.class);
//                    String name = userModel.getNama();
//                    tvName.setText(name);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        long t = System.currentTimeMillis();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "Press back again to exit",
                    Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_beranda) {
            BerandaActivity home = new BerandaActivity();
            sentoStartBeranda();

            // Handle the camera action
        }   else if (id == R.id.nav_about) {
            AboutFragment aFrag = new AboutFragment();
            setFragment(aFrag);

        }else if (id ==  R.id.nav_setting) {
            SettingFragment sFrag = new SettingFragment();
            setFragment(sFrag);

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

    public void sendtoStart(){
        Intent startIntent = new Intent(BerandaActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }
    public void sentoStartBeranda() {
        Intent startIntent = new Intent(BerandaActivity.this, BerandaActivity.class);
        startActivity(startIntent);
        finish();
    }

    public void sentoStartDoc() {
        Intent startIntent = new Intent(BerandaActivity.this, DocumentTrackingActivity.class);
        startActivity(startIntent);
        finish();
    }

    public void sentoStartMateri() {
        Intent startIntent = new Intent(BerandaActivity.this, MateriActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v == imagev1){
            DocumentTrackingActivity home = new DocumentTrackingActivity();
            sentoStartDoc();
        }
        else if (v == imagev2) {
            MateriActivity mFrag = new MateriActivity();
            sentoStartMateri();
        }

    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            BerandaActivity.this.runOnUiThread(new Runnable() {
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
