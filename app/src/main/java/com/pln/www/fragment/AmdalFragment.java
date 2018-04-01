package com.pln.www.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.pln.www.R;
import com.pln.www.activity.DetailWorkDocumentActivity;
import com.pln.www.model.KontrakModel;
import com.pln.www.model.PekerjaanModel;
import com.pln.www.viewholder.PekerjaanViewHolder;

import java.util.ArrayList;


public class AmdalFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private DatabaseReference dbPekerjaan;
    private SearchView searchView;
    private ArrayList<PekerjaanModel> listPekerjaan;
    private RecycleAdapterPekerjaan adapterPekerjaan;



    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public class RecycleAdapterPekerjaan extends RecyclerView.Adapter<PekerjaanViewHolder> {
        ArrayList<PekerjaanModel> dataPekerjaan = new ArrayList<>();

        public RecycleAdapterPekerjaan(ArrayList<PekerjaanModel> list) {
            dataPekerjaan = list;
        }

        @Override
        public PekerjaanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_work, parent, false);

            return new PekerjaanViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final PekerjaanViewHolder holder, int position) {
            final String id_Pekerjaan  = dataPekerjaan.get(position).getIdPekerjaan();
            final String id_Kontrak = dataPekerjaan.get(position).getIdKontrak();
            holder.setNamaPekerjaan(dataPekerjaan.get(position).getNamaJalur());
            holder.setTegangan(dataPekerjaan.get(position).getTegangan());
            holder.setKms(dataPekerjaan.get(position).getKms());
            holder.setProvinsi(dataPekerjaan.get(position).getProvinsi());

            dbPekerjaan.child("Kontrak").child(id_Kontrak).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    KontrakModel kontrakModel = dataSnapshot.getValue(KontrakModel.class);
                    String noKontrak = kontrakModel.getNoKontrak();
                    holder.setNoKontrak(noKontrak);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Failed to Get Contract ID", Toast.LENGTH_LONG).show();
                }
            });

            holder.setOnClickListener(new PekerjaanViewHolder.ClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getActivity(), DetailWorkDocumentActivity.class);
                    intent.putExtra("id_pekerjaan", id_Pekerjaan);
                    intent.putExtra("id_kontrak", id_Kontrak);
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    final AlertDialog.Builder alertDelete = new AlertDialog.Builder(getActivity());

                }
            });

        }

        @Override
        public int getItemCount() {
            return dataPekerjaan.size();
        }

        public void setFilter(ArrayList<PekerjaanModel> list) {
            dataPekerjaan = new ArrayList<>();
            dataPekerjaan.addAll(list);
            notifyDataSetChanged();
        }
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
        searchView = (SearchView) rootView.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();

                ArrayList<PekerjaanModel> list = new ArrayList<>();
                for(PekerjaanModel pekerjaan : listPekerjaan){
                    String nama_pekerjaan = pekerjaan.getNamaJalur().toLowerCase();
                    if(nama_pekerjaan.contains(newText)){
                        list.add(pekerjaan);
                    }
                }
                adapterPekerjaan.setFilter(list);
                return  true;
            }
        });

        initialize();

        return rootView;
    }

    private void initialize() {
        listPekerjaan = new ArrayList<>();
        dbPekerjaan.child("Pekerjaan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listPekerjaan = new ArrayList<>();
                for (DataSnapshot pekerjaanSnapshot : dataSnapshot.getChildren()){
                    if (pekerjaanSnapshot.child("jenisPekerjaan").getValue().toString().equals("AMDAL")) {
                        PekerjaanModel pekerjaanModel = pekerjaanSnapshot.getValue(PekerjaanModel.class);
                        listPekerjaan.add(pekerjaanModel);

                    }
                }
                adapterPekerjaan = new RecycleAdapterPekerjaan(listPekerjaan);
                mRecyclerView.setAdapter(adapterPekerjaan);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

