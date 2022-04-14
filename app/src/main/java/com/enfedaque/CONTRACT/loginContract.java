package com.enfedaque.CONTRACT;

import android.content.Context;

public interface loginContract {

    interface Model {
        String getPassword(Context context, String email);
        long getIdUsuario(Context context, String email);
    }

    interface Presenter{
        void getPassword(String email, String passComprobar);
        long getIdUsuario(String email);
    }

    interface View{
        String paswordOk(String email);
        String paswordCancel();
        void guardarVariables(String email, String pass);
    }
}
