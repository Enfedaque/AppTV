package com.enfedaque.MVP.PRESENTER;

import com.enfedaque.MVP.CONTRACT.registrarseContract;
import com.enfedaque.MVP.MODEL.registrarseModel;
import com.enfedaque.MVP.VIEW.registrarseView;
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
