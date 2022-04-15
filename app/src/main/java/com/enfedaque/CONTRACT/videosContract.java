package com.enfedaque.CONTRACT;

import com.enfedaque.API.PeliculasAPI;
import com.enfedaque.domain.video;

import java.util.List;

public interface videosContract {

    interface Model{
        PeliculasAPI getVideo();
    }

    interface Presenter{
        void getVideo(String idPelicula);
    }

    interface View{
        void mostrarVideo(List<video> clave);
        void avisarError(String msg);
    }
}

