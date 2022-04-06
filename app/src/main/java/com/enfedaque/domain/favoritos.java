package com.enfedaque.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class favoritos {

    @PrimaryKey(autoGenerate = true)
    private long idFav;
    private String idPelicula;
    private String tituloPelicula;

    public favoritos(String idPelicula, String tituloPelicula) {
        this.idPelicula = idPelicula;
        this.tituloPelicula = tituloPelicula;
    }

    public long getIdFav() {
        return idFav;
    }

    public void setIdFav(long idFav) {
        this.idFav = idFav;
    }

    public String getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(String idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTituloPelicula() {
        return tituloPelicula;
    }

    public void setTituloPelicula(String tituloPelicula) {
        this.tituloPelicula = tituloPelicula;
    }
}
