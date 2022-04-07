package com.enfedaque.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class usuario {

    @PrimaryKey(autoGenerate = true)
    private long idUsuario;
    private String nombreUsuario;
    private String email;
    private String pass;

    public usuario(String nombreUsuario, String email, String pass) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.pass = pass;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
