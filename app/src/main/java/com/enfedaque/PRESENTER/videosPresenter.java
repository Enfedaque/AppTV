package com.enfedaque.PRESENTER;

import android.util.Log;

import com.enfedaque.API.PeliculasAPI;
import com.enfedaque.CONTRACT.videosContract;
import com.enfedaque.MODEL.videosModel;
import com.enfedaque.VIEW.videosView;
import com.enfedaque.domain.respuestaVideos;
import com.enfedaque.domain.video;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

public class videosPresenter implements videosContract.Presenter{

    private videosModel model;
    private videosView view;

    public videosPresenter(videosView view){
        model=new videosModel();
        this.view=view;
    }


    @Override
    public void getVideo(String idPelicula) {
        List<video> clave=new ArrayList<>();
        PeliculasAPI peliculasAPI= model.getVideo();
        peliculasAPI.findVideos(idPelicula)
                .subscribe(new Observer<respuestaVideos>() {
                    @Override
                    public void onCompleted() {
                        view.mostrarVideo(clave);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.avisarError("Error al cargar video");
                    }

                    @Override
                    public void onNext(respuestaVideos respuestaVideos) {
                        clave.addAll(respuestaVideos.getResults());
                    }
                });
    }
}
