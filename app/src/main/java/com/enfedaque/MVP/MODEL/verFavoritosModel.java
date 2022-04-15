package com.enfedaque.MVP.MODEL;

import android.content.Context;

import androidx.room.Room;

import com.enfedaque.BBDD.baseDeDatos;
import com.enfedaque.MVP.CONTRACT.verFavoritosContract;
import com.enfedaque.OTROS_PAQUETES.UTILS.GlobalVars;

import java.util.List;

public class verFavoritosModel implements verFavoritosContract.Model{

    //CARGAR LISTADO DE FAVORITOS
    @Override
    public List<String> cargarTitulos(Context context) {

        baseDeDatos database=conexionBBDD(context);

        return database.peliculaDAO().findmiPeliculaById(GlobalVars.getIdUsuario());
    }

    //CARGAR UNA FOTO DE UN FAV DE LA BBDD
    @Override
    public String cargarFoto(Context context, String pelicula) {

        baseDeDatos database=conexionBBDD(context);

        return database.peliculaDAO().findfotoBymiPelicula(pelicula);
    }

    //ELIMINAR UNA PELICULA DE FAVORITOS
    @Override
    public boolean eliminarPeliculaFav(Context context, String titulo, long idUsuario) {

        baseDeDatos database=conexionBBDD(context);

        try{
            database.peliculaDAO().deleteAllByMiPeliculaAndIdUsuario(titulo, GlobalVars.getIdUsuario());
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    //CONEXION CON LA BBDD
    private baseDeDatos conexionBBDD(Context context){
        baseDeDatos database= Room.databaseBuilder(context, baseDeDatos.class,
                "Peliculas").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        return database;
    }
}
