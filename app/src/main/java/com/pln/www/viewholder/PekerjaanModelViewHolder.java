package com.pln.www.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pln.www.R;

/**
 * Created by ACHI on 13/02/2018.
 */

public class PekerjaanModelViewHolder extends RecyclerView.ViewHolder {

    private PekerjaanModelViewHolder.ClickListener mClickListener;
    public TextView tvNamaPekerjaan, tvNamaKonsultan, tvTanggal, tvWaktu, tvLastUpdate;
    public ImageView ivStatus, ivHapus;
    public View mView;

    public PekerjaanModelViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        tvNamaPekerjaan = (TextView) mView.findViewById(R.id.tvJudul);
        tvNamaKonsultan = (TextView) mView.findViewById(R.id.tvKonsultan);
        tvTanggal = (TextView) mView.findViewById(R.id.tanggal);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });

        mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });

    }

    public interface ClickListener{
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(PekerjaanModelViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

    public void setNamaPekerjaan(String namaPekerjaan){
        tvNamaPekerjaan.setText(namaPekerjaan);
    }

    public void setNamaKonsultan (String namaKonsultan){
        tvNamaKonsultan.setText(namaKonsultan);
    }

    public void setTanggal(String tanggal){
        tvTanggal.setText(tanggal);
    }


}
