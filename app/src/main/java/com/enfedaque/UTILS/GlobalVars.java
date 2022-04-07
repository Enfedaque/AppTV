package com.enfedaque.UTILS;

public class GlobalVars {

    public static long idUsuario;
    public static String email;
    public static String pass;

    public static long getIdUsuario() {
        return idUsuario;
    }

    public static void setIdUsuario(long idUsuario) {
        GlobalVars.idUsuario = idUsuario;
    }

    public static String getEmail() {
        return email;
    }

    public static String getPass() {
        return pass;
    }

    public static void setEmail(String email) {
        GlobalVars.email = email;
    }

    public static void setPass(String pass) {
        GlobalVars.pass = pass;
    }
}
