package com.pln.www.model;

/**
 * Created by ACHI on 13/02/2018.
 */

public class KonsultanModel {

    String namaKonsultan, idKonsultan;

    public KonsultanModel(){}


    public KonsultanModel(String idKonsultan, String namaKonsultan){
        this.idKonsultan = idKonsultan;
        this.namaKonsultan = namaKonsultan;
    }

    public void setNamaKonsultan(String namaKonsultan) {
        this.namaKonsultan = namaKonsultan;
    }

    public String getNamaKonsultan() {
        return namaKonsultan;
    }

    public String getIdKonsultan() {
        return idKonsultan;
    }

    public void setIdKonsultan(String idKonsultan) {
        this.idKonsultan = idKonsultan;
    }
}
