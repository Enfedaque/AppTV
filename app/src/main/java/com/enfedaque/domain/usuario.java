package com.enfedaque.domain;

public class usuario {

    private String nombreUsuario;
    private String email;
    private String pass;

    public usuario(String nombreUsuario, String email, String pass) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.pass = pass;
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
