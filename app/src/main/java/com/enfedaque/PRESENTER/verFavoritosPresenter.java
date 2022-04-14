package com.enfedaque.PRESENTER;

import android.content.Context;

import com.enfedaque.CONTRACT.verFavoritosContract;
import com.enfedaque.MODEL.verFavoritosModel;
import com.enfedaque.VIEW.verFavoritosView;

import java.util.List;

public class verFavoritosPresenter implements verFavoritosContract.Presenter{

    private verFavoritosModel model;
    private verFavoritosView view;

    public verFavoritosPresenter(verFavoritosView view){
        this.view=view;
        model=new verFavoritosModel();
    }

    //OBTENGO EL LISTADO DE PELICULAS FAV Y SE LO DOY A LA VIEW
    @Override
    public void cargarTitulos() {
        List<String> peliculasFav = model.cargarTitulos(view.getApplicationContext());
        view.cargarFavoritos(peliculasFav);
    }

    //OBTENGO LA PORTADA DE UNA PELICULA FAV Y SE LA DOY AL VIEW
    @Override
    public void cargarFoto(String pelicula) {
        String foto=model.cargarFoto(view.getApplicationContext(), pelicula);
        view.cargarFotoPelicula(foto);
    }

    //ELIMINAR UNA PELICULA DE FAVORITOS
    @Override
    public boolean eliminarPelicula(String titulo, long idUsuario) {
        boolean eliminada=model.eliminarPeliculaFav(view.getApplicationContext(), titulo, idUsuario);
        if (eliminada) {
            return true;
        }
        return false;
    }
}
