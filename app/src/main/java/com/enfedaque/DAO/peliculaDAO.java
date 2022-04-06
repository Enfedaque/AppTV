package com.enfedaque.DAO;

import androidx.room.Dao;
import androidx.room.Query;

import com.enfedaque.domain.favoritos;

import java.util.List;

@Dao
public interface peliculaDAO {

    @Query("SELECT * FROM favoritos")
    List<favoritos> getAllFav();
}
