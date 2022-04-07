package com.enfedaque.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.enfedaque.domain.favoritos;
import com.enfedaque.domain.peliculas;
import com.enfedaque.domain.usuario;

import java.util.List;

@Dao
public interface peliculaDAO {

    @Query("SELECT * FROM favoritos")
    List<favoritos> getAllFav();

    //REgsitrar nueva peli FAV
    @Insert
    void insert(favoritos miFavorito);
}
