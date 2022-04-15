package com.enfedaque.MVP.PRESENTER;

import com.enfedaque.MVP.CONTRACT.loginContract;
import com.enfedaque.MVP.MODEL.loginModel;
import com.enfedaque.MVP.VIEW.LoginView;

public class loginPresenter implements loginContract.Presenter{

    private loginModel model;
    private LoginView view;

    public loginPresenter(LoginView view){
        this.view=view;
        model=new loginModel();
    }

    @Override
    public void getPassword(String email, String passComprobar) {
        String pass=model.getPassword(view.getApplicationContext(), email);
        if (pass!=null && pass.equals(passComprobar)){
            view.paswordOk(email);
            view.guardarVariables(email, pass);
        }else{
            view.paswordCancel();
        }
    }

    @Override
    public long getIdUsuario(String email) {
        return model.getIdUsuario(view.getApplicationContext(), email);

    }
}
