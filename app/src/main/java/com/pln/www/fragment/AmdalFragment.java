package com.pln.www.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.pln.www.R;
import com.pln.www.activity.DetailProsesActivity;
import com.pln.www.model.ItemModel;
import com.pln.www.model.KonsultanModel;
import com.pln.www.model.KontrakModel;
import com.pln.www.model.PekerjaanModel;
import com.pln.www.viewholder.PekerjaanModelViewHolder;

import java.util.ArrayList;
import java.util.List;


public class AmdalFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60; // menampilkan data sebanyak value
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private DatabaseReference dbPekerjaan;


    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PekerjaanModel, PekerjaanModelViewHolder>(
                PekerjaanModel.class,
                R.layout.list_view,
                PekerjaanModelViewHolder.class,
                dbPekerjaan.child("Pekerjaan")
        ) {
            @Override
            protected void populateViewHolder(final PekerjaanModelViewHolder viewHolder, final PekerjaanModel model, int position) {
                final String id_Pekerjaan = this.getRef(position).getKey();
                final String id_Konsultan = model.getIdKonsultan();
                final String id_Kontrak = model.getIdKontrak();
                viewHolder.setNamaPekerjaan(model.getNamaPekerjaan());
                viewHolder.setTegangan(model.getTegangan());
                viewHolder.setKms(model.getKms());
                viewHolder.setProvinsi(model.getProvinsi());

                dbPekerjaan.child("Kontrak").child(id_Kontrak).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        KontrakModel kontrakModel = dataSnapshot.getValue(KontrakModel.class);
                        String noKontrak = kontrakModel.getNoKontrak();
                        viewHolder.setNoKontrak(noKontrak);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getActivity(), "Failed to Get Contract ID", Toast.LENGTH_LONG).show();
                        return;
                    }
                });

                viewHolder.setOnClickListener(new PekerjaanModelViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), DetailProsesActivity.class);
                        intent.putExtra("id_pekerjaan", id_Pekerjaan);
                        intent.putExtra("id_konsultan", id_Konsultan);
                        intent.putExtra("id_kontrak", id_Kontrak);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }


                });

            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_amdal, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        dbPekerjaan = FirebaseDatabase.getInstance().getReference();

        return rootView;
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }
}

