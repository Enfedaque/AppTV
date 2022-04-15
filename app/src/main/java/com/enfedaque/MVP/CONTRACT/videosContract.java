package com.enfedaque.MVP.CONTRACT;

import com.enfedaque.OTROS_PAQUETES.API.PeliculasAPI;
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

