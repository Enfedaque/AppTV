package com.enfedaque.MVP.CONTRACT;

import android.content.Context;

import java.util.List;

public interface verFavoritosContract {

    interface Model{
        List<String> cargarTitulos(Context context);
        String cargarFoto(Context context, String pelicula);
        boolean eliminarPeliculaFav(Context context,String titulo, long idUsuario);
    }

    interface Presenter{
        void cargarTitulos();
        void cargarFoto(String pelicula);
        boolean eliminarPelicula(String titulo, long idUsuario);
    }

    interface View{
        void cargarFavoritos(List<String> listaFavoritos);
        void cargarFotoPelicula(String foto);
        void eliminarPelicula();
    }
}
