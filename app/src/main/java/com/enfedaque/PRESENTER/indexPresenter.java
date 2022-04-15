package com.enfedaque.PRESENTER;

import com.enfedaque.API.PeliculasAPI;
import com.enfedaque.CONTRACT.indexContract;
import com.enfedaque.MODEL.indexModel;
import com.enfedaque.VIEW.indexView;
import com.enfedaque.domain.peliculas;
import com.enfedaque.domain.respuestaPeliculas;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class indexPresenter implements indexContract.Presenter{

    private indexModel model;
    private indexView view;

    public indexPresenter(indexView view){
        this.view=view;
        model=new indexModel();
    }

    @Override
    public void getPopular() {
        List<peliculas> lista=new ArrayList<>();
        PeliculasAPI peliculasAPI=model.getInstance();
        peliculasAPI.findAllPopular()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<respuestaPeliculas>() {
                    @Override
                    public void onCompleted() {
                        view.mostrarAllPopular(lista);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.errorPopular("Error al cargar peliculas POPULARES");
                    }

                    @Override
                    public void onNext(respuestaPeliculas respuestaPeliculas) {
                        lista.addAll(respuestaPeliculas.getResults());
                    }
                });
    }

    @Override
    public void getTopRated() {
        List<peliculas> lista=new ArrayList<>();
        PeliculasAPI peliculasAPI=model.getInstance();
        peliculasAPI.findAllTopRated()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<respuestaPeliculas>() {
                    @Override
                    public void onCompleted() {
                        view.mostrarTopRated(lista);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.errorTopRated("Error al cargar peliculas TOP RATED");
                    }

                    @Override
                    public void onNext(respuestaPeliculas respuestaPeliculas) {
                        lista.addAll(respuestaPeliculas.getResults());
                    }
                });
    }

    @Override
    public void getOthers() {
        List<peliculas> lista=new ArrayList<>();
        PeliculasAPI peliculasAPI=model.getInstance();
        peliculasAPI.findOthers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<respuestaPeliculas>() {
                    @Override
                    public void onCompleted() {
                        view.mostrarUpcoming(lista);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.errorUpcoming("Error al cargar OTRAS peliculas");
                    }

                    @Override
                    public void onNext(respuestaPeliculas respuestaPeliculas) {
                        lista.addAll(respuestaPeliculas.getResults());
                    }
                });
    }
}
