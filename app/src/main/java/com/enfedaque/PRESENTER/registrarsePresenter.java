package com.enfedaque.PRESENTER;

import android.content.Context;

import com.enfedaque.CONTRACT.registrarseContract;
import com.enfedaque.MODEL.registrarseModel;
import com.enfedaque.VIEW.registrarseView;
import com.enfedaque.domain.usuario;

public class registrarsePresenter implements registrarseContract.Presenter{

    private registrarseModel model;
    private registrarseView view;

    public registrarsePresenter(registrarseView view){
        this.view=view;
        model=new registrarseModel();
    }

    @Override
    public boolean registrar(usuario miUsuario) {

        if (model.registrar(view.getApplicationContext(), miUsuario)){
            return true;
        }else{
            return false;
        }
    }
}
