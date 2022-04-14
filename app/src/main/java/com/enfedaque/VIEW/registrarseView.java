package com.enfedaque.VIEW;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.enfedaque.BBDD.baseDeDatos;
import com.enfedaque.CONTRACT.registrarseContract;
import com.enfedaque.PRESENTER.registrarsePresenter;
import com.enfedaque.R;
import com.enfedaque.domain.usuario;

public class registrarseView extends AppCompatActivity implements registrarseContract.View{

    private EditText nuevoUsuario;
    private EditText nuevoEmail;
    private EditText nuevaPassword;
    private CheckBox cbTerminos;
    private Button btnAdd;
    private Button btnLimpiar;

    private usuario usuario;

    private registrarsePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrarse);
        inicializarComponentes();

        presenter=new registrarsePresenter(this);

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                limpiarFormulario();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recogerDatos();
            }
        });

    }

    private void inicializarComponentes(){
        nuevoUsuario=findViewById(R.id.nuevoUsuario);
        nuevoEmail=findViewById(R.id.nuevoEmail);
        nuevaPassword=findViewById(R.id.nuevaPassword);
        cbTerminos=findViewById(R.id.cbTerminos);
        btnAdd=findViewById(R.id.btnAddUsuario);
        btnLimpiar=findViewById(R.id.btnLimpiarUsuario);
    }

    //Recoger datos
    public void recogerDatos(){
        String nombreUsuario=nuevoUsuario.getText().toString();
        String email=nuevoEmail.getText().toString();
        String pass=nuevaPassword.getText().toString();
        if (nombreUsuario.equalsIgnoreCase("") || email.equalsIgnoreCase("") ||
                pass.equalsIgnoreCase("") || !cbTerminos.isChecked()){
            Toast.makeText(this, "Debe rellenar todos los campos", Toast.LENGTH_LONG).show();
        }else{
            usuario=new usuario(nombreUsuario, email, pass);

            if (presenter.registrar(usuario)){
                registrar();
            }else{
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }

        }
    }

    //Limpiar formulario
    public void limpiarFormulario(){
        nuevoUsuario.setText("");
        nuevoEmail.setText("");
        nuevaPassword.setText("");
        cbTerminos.setChecked(false);
    }


    @Override
    public void registrar() {
        Toast.makeText(getApplicationContext(), "Usuario registrado", Toast.LENGTH_LONG).show();
        Intent miIntent=new Intent(this, LoginView.class);
        startActivity(miIntent);
    }
}