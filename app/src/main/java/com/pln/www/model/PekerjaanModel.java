package com.pln.www.model;

/**
 * Created by ACHI on 13/02/2018.
 */

public class PekerjaanModel {

    String idPekerjaan, idKontrak, idKonsultan, namaPekerjaan, jenisPekerjaan, file;

    public PekerjaanModel(){}

    public PekerjaanModel(String idPekerjaan, String idKonsultan, String idKontrak, String namaPekerjaan, String jenisPekerjaan){
        this.idPekerjaan = idPekerjaan;
        this.idKonsultan = idKonsultan;
        this.idKontrak = idKontrak;
        this.namaPekerjaan = namaPekerjaan;
        this.jenisPekerjaan = jenisPekerjaan;
    }

    public String getIdPekerjaan() {
        return idPekerjaan;
    }

    public void setIdPekerjaan(String idPekerjaan) {
        this.idPekerjaan = idPekerjaan;
    }

    public String getNamaPekerjaan() {
        return namaPekerjaan;
    }

    public void setNamaPekerjaan(String namaPekerjaan) {
        this.namaPekerjaan = namaPekerjaan;
    }

    public String getJenisPekerjaan() {
        return jenisPekerjaan;
    }

    public void setJenisPekerjaan(String jenisPekerjaan) {
        this.jenisPekerjaan = jenisPekerjaan;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setIdKontrak (String idKontrak){
        this.idKontrak = idKontrak;
    }

    public String getIdKontrak (){
        return idKontrak;
    }

    public void setIdKonsultan (String idKonsultan){
        this.idKonsultan = idKonsultan;
    }

    public String getIdKonsultan (){
        return idKonsultan;
    }
}
