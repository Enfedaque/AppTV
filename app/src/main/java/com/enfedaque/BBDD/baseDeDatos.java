package com.enfedaque.BBDD;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.enfedaque.DAO.peliculaDAO;
import com.enfedaque.domain.favoritos;

@Database(entities = {favoritos.class}, version = 1)
public abstract class baseDeDatos extends RoomDatabase {
    //Aqui hace de intermediario entre la clase y el DAO

    public abstract peliculaDAO peliculaDAO();
}

