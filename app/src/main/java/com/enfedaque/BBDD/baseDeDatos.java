package com.enfedaque.BBDD;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.enfedaque.DAO.peliculaDAO;
import com.enfedaque.DAO.usuarioDAO;
import com.enfedaque.domain.favoritos;
import com.enfedaque.domain.usuario;

@Database(entities = {favoritos.class, usuario.class}, version = 10)
public abstract class baseDeDatos extends RoomDatabase {
    //Aqui hace de intermediario entre la clase y el DAO

    public abstract peliculaDAO peliculaDAO();
    public abstract usuarioDAO usuarioDAO();
}

