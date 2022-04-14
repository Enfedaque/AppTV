package com.enfedaque.MODEL;

import android.content.Context;

import androidx.room.Room;

import com.enfedaque.BBDD.baseDeDatos;
import com.enfedaque.CONTRACT.loginContract;

public class loginModel implements loginContract.Model {
    @Override
    public String getPassword(Context context, String email) {

        baseDeDatos database=conexionBBDD(context);

        return database.usuarioDAO().findByEmail(email);
    }

    @Override
    public long getIdUsuario(Context context, String email) {

        baseDeDatos database=conexionBBDD(context);

        return database.usuarioDAO().findById(email);
    }

    private baseDeDatos conexionBBDD(Context context){
        baseDeDatos database= Room.databaseBuilder(context, baseDeDatos.class,
                "Peliculas").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        return database;
    }
}
