package com.enfedaque.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.enfedaque.domain.favoritos;
import com.enfedaque.domain.peliculas;
import com.enfedaque.domain.usuario;

import java.util.List;

@Dao
public interface peliculaDAO {

    //REgsitrar nueva peli FAV
    @Insert
    void insert(favoritos miFavorito);

    //Obtener titulo de una pelicula guardada
    @Query("SELECT distinct(miPelicula) FROM favoritos where idUsuario= :idUsuario")
    List<String> findmiPeliculaById(long idUsuario);

    //Obtener foto de una pelicula guardada
    @Query("SELECT foto FROM favoritos where idUsuario= :idUsuario")
    String findfotoById(long idUsuario);

    //Obtener id de una pelicula guardada
    @Query("SELECT idPelicula FROM favoritos where idUsuario= :idUsuario")
    List<String> findidPeliculaById(long idUsuario);

    @Query("SELECT foto FROM favoritos where miPelicula= :miPelicula")
    String findfotoBymiPelicula(String miPelicula);

    //Eliminar una pelicula de favoritos
    @Query("DELETE FROM favoritos where miPelicula= :miPelicula and idUsuario= :idUsuario")
    void deleteAllByMiPeliculaAndIdUsuario(String miPelicula, long idUsuario);

}
