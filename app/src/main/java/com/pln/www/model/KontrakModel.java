package com.pln.www.model;

/**
 * Created by ACHI on 13/02/2018.
 */

public class KontrakModel {
    String idKontrak, noKontrak, tglMulai, tglAkhir;

    public KontrakModel(){}

    public KontrakModel(String idKontrak, String noKontrak, String tglMulai, String tglAkhir) {
        this.idKontrak = idKontrak;
        this.noKontrak = noKontrak;
        this.tglMulai = tglMulai;
        this.tglAkhir = tglAkhir;
    }

    public String getIdKontrak() {
        return idKontrak;
    }

    public void setIdKontrak(String idKontrak) {
        this.idKontrak = idKontrak;
    }

    public String getNoKontrak() {
        return noKontrak;
    }

    public void setNoKontrak(String noKontrak) {
        this.noKontrak = noKontrak;
    }

    public String getTglMulai() {
        return tglMulai;
    }

    public void setTglMulai(String tglMulai) {
        this.tglMulai = tglMulai;
    }

    public String getTglAkhir() {
        return tglAkhir;
    }

    public void setTglAkhir(String tglAkhir) {
        this.tglAkhir = tglAkhir;
    }
}
