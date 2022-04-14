package com.enfedaque.CONTRACT;

import android.content.Context;

import com.enfedaque.domain.usuario;

public interface registrarseContract {

    interface Model{
        boolean registrar(Context context, usuario miUsuario);
    }

    interface Presenter{
        boolean registrar(usuario miUsuario);
    }

    interface View{
        void registrar();
    }
}
