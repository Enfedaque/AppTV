package com.enfedaque.MVP.MODEL;

import android.content.Context;

import androidx.room.Room;

import com.enfedaque.BBDD.baseDeDatos;
import com.enfedaque.MVP.CONTRACT.registrarseContract;
import com.enfedaque.domain.usuario;

public class registrarseModel implements registrarseContract.Model{

    @Override
    public boolean registrar(Context context, usuario miUsuario) {

        baseDeDatos database=conexionBBDD(context);

        try{
            database.usuarioDAO().insert(miUsuario);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    private baseDeDatos conexionBBDD(Context context){
        baseDeDatos database= Room.databaseBuilder(context, baseDeDatos.class,
                "Peliculas").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        return database;
    }
}
