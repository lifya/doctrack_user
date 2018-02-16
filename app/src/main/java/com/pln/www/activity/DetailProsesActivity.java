package com.pln.www.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pln.www.R;
import com.pln.www.model.KonsultanModel;
import com.pln.www.model.KontrakModel;
import com.pln.www.model.PekerjaanModel;

public class DetailProsesActivity extends AppCompatActivity {
    private ImageView ivBack;
    private TextView tvJudul, tvKonsultan, tvTanggalMulai, tvTanggalAKhir, tvTegangan, tvKms, tvProvinsi, tvKontrak;
    private ProgressDialog progressDialog;
    private DatabaseReference dbKonsultan, dbKontrak, dbPekerjaan;
    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_proses_);
        intent = getIntent();
        bundle = intent.getExtras();

        dbKonsultan = FirebaseDatabase.getInstance().getReference("Konsultan");
        dbKontrak = FirebaseDatabase.getInstance().getReference("Kontrak");
        dbPekerjaan = FirebaseDatabase.getInstance().getReference("Pekerjaan");

        tvJudul = (TextView) findViewById(R.id.tvJudul);
        tvKonsultan = (TextView) findViewById(R.id.tvKonsultan);
        tvTegangan = (TextView) findViewById(R.id.tvTegangan);
        tvKms = (TextView) findViewById(R.id.tvKms);
        tvProvinsi = (TextView) findViewById(R.id.tvProvinsi);
        tvKontrak = (TextView) findViewById(R.id.tvKontrak);
        tvTanggalMulai = (TextView) findViewById(R.id.tvTanggalMulai);
        tvTanggalAKhir = (TextView) findViewById(R.id.tvTanggalAkhir);
        ivBack = (ImageView) findViewById(R.id.back);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        String get_idPekerjaan, get_idKonsultan, get_idKontrak;

        if(bundle != null){
            get_idPekerjaan = (String) bundle.get("id_pekerjaan");
            get_idKonsultan = (String) bundle.get("id_konsultan");
            get_idKontrak = (String) bundle.get("id_kontrak");

            dbPekerjaan.child(get_idPekerjaan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    PekerjaanModel pekerjaanModel = dataSnapshot.getValue(PekerjaanModel.class);
                    String namaPekerjaan = pekerjaanModel.getNamaPekerjaan();
                    String tegangan = pekerjaanModel.getTegangan();
                    String kms = pekerjaanModel.getKms();
                    String provinsi = pekerjaanModel.getProvinsi();
                    tvJudul.setText(namaPekerjaan);
                    tvTegangan.setText(tegangan);
                    tvKms.setText(kms);
                    tvProvinsi.setText(provinsi);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(DetailProsesActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    return;
                }
            });
            dbKonsultan.child(get_idKonsultan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    KonsultanModel konsultanModel = dataSnapshot.getValue(KonsultanModel.class);
                    String namaKonsultan = konsultanModel.getNamaKonsultan();
                    tvKonsultan.setText(namaKonsultan);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(DetailProsesActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    return;
                }
            });
            dbKontrak.child(get_idKontrak).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    KontrakModel kontrakModel = dataSnapshot.getValue(KontrakModel.class);
                    String noKontrak = kontrakModel.getNoKontrak();
                    String tglMulai = kontrakModel.getTglMulai();
                    String tglAkhir = kontrakModel.getTglAkhir();
                    tvKontrak.setText(noKontrak);
                    tvTanggalMulai.setText(tglMulai);
                    tvTanggalAKhir.setText(tglAkhir);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(DetailProsesActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    return;
                }
            });
        }
    }

}
