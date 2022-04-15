package com.enfedaque.MVP.CONTRACT;

import com.enfedaque.OTROS_PAQUETES.API.PeliculasAPI;
import com.enfedaque.domain.peliculas;

import java.util.List;

public interface indexContract {

    interface Model{
        PeliculasAPI getInstance();
    }

    interface Presenter{
        void getPopular();
        void getTopRated();
        void getOthers();
    }

    interface View{
        void mostrarAllPopular(List<peliculas> lista);
        void errorPopular(String msg);
        void mostrarUpcoming(List<peliculas> listado);
        void errorUpcoming(String msg);
        void mostrarTopRated(List<peliculas> lista);
        void errorTopRated(String msg);
    }
}
