package com.pln.www.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pln.www.R;

import static android.content.ContentValues.TAG;


public class MateriUklUplFragment extends Fragment {
    public MateriUklUplFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_materi_uklupl, container, false);
        rootView.setTag(TAG);
        return  rootView;
    }
}
