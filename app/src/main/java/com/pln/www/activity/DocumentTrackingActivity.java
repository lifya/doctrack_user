package com.pln.www.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pln.www.R;
import com.pln.www.fragment.AmdalFragment;
import com.pln.www.fragment.UklUplFragment;
import com.pln.www.model.ItemModel;
import com.pln.www.viewholder.PekerjaanModelViewHolder;

public class DocumentTrackingActivity extends AppCompatActivity  implements View.OnClickListener {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    ImageView image1;
    ImageView searchdoc;
    private DatabaseReference mItemDatabase;
    private RecyclerView mResultList;
    private EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_tracking);

        image1 = (ImageView) findViewById(R.id.back);
        image1.setOnClickListener(this);
        mItemDatabase = FirebaseDatabase.getInstance().getReference("Pekerjaan");
        searchField = (EditText) findViewById(R.id.searchEdit);
        searchdoc = (ImageView) findViewById(R.id.searchdoc);
        searchdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String editSearch = searchField.getText().toString();
                firebaseUserSearch(editSearch);
            }
        });
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));



    }

    private void firebaseUserSearch(String editSearch) {
        Toast.makeText(DocumentTrackingActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mItemDatabase.orderByChild("judul").startAt(editSearch).endAt(editSearch + "\ufBff");

        FirebaseRecyclerAdapter<ItemModel, PekerjaanModelViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ItemModel, PekerjaanModelViewHolder>(
                ItemModel.class,
                R.layout.list_view,
                PekerjaanModelViewHolder.class,
                mItemDatabase
        ) {


            @Override
            protected void populateViewHolder(PekerjaanModelViewHolder viewHolder, ItemModel model, int position) {

                //viewHolder.(model.getmJudul());

            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onBackPressed() {
        DocumentTrackingActivity doc = new DocumentTrackingActivity();
        sentoHome();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public void sentoHome () {
        Intent startIntent = new Intent(DocumentTrackingActivity.this, BerandaActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public void onClick(View view) {
       if (image1 == view) {
           BerandaActivity home = new BerandaActivity();
           sentoHome();
       }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0 :
                    AmdalFragment amdalFragment = new AmdalFragment();
                    return amdalFragment;
                case 1 :
                    UklUplFragment uklFragment = new UklUplFragment();
                    return uklFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }
}
