package com.enfedaque.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class favoritos {

    @PrimaryKey(autoGenerate = true)
    private long idFav;
    private String miPelicula;
    private String foto;
    private String idPelicula;
    private long idUsuario;

    public favoritos( String miPelicula, String foto, String idPelicula, long idUsuario) {
        this.miPelicula = miPelicula;
        this.foto = foto;
        this.idPelicula = idPelicula;
        this.idUsuario = idUsuario;
    }

    public String getFoto() {
        return foto;
    }

    public long getIdFav() {
        return idFav;
    }

    public void setIdFav(long idFav) {
        this.idFav = idFav;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(String idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getMiPelicula() {
        return miPelicula;
    }

    public void setMiPelicula(String miPelicula) {
        this.miPelicula = miPelicula;
    }
}
