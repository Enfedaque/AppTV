package com.enfedaque.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.enfedaque.BBDD.baseDeDatos;
import com.enfedaque.R;
import com.enfedaque.UTILS.GlobalVars;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class verFavoritos extends AppCompatActivity {

    private ArrayAdapter<String> LV_Adapter;
    private List<String> titulos;
    ListView lvPeliculasFav;

    View vista;
    ImageView foto;

    private ConstraintLayout favLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_favoritos);
        favLayout=findViewById(R.id.favLayout);

        titulos=new ArrayList<>();
        foto=findViewById(R.id.fotoFav);


        LV_Adapter =new ArrayAdapter<>(this, R.layout.adaptador_listview_fav,
                titulos);

        lvPeliculasFav=findViewById(R.id.lvFavs);
        lvPeliculasFav.setAdapter(LV_Adapter);

        registerForContextMenu(lvPeliculasFav);

        lvPeliculasFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String titulo=(String) lvPeliculasFav.getItemAtPosition(i);
                cargarFoto(titulo);
            }
        });

        detectarPreferencias();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cargarBBDDenLista();
        detectarPreferencias();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarBBDDenLista();
        detectarPreferencias();
    }

    private void cargarBBDDenLista(){
        titulos.clear();
        baseDeDatos database= Room.databaseBuilder(getApplicationContext(), baseDeDatos.class,
                "Peliculas").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        titulos.addAll(database.peliculaDAO().findmiPeliculaById(GlobalVars.getIdUsuario()));

        LV_Adapter.notifyDataSetChanged();
    }

    //MEnu superior àra volver al index o recargarlo
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    //Manu actionBar opciones
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Si toca la casa lo envio al inicio
        if(item.getItemId() == R.id.house){
            Intent miIntent=new Intent(this, index.class);
            startActivity(miIntent);
            return true;
        }else if(item.getItemId() == R.id.pref){
            Intent miIntent=new Intent(this, preferencias.class);
            startActivity(miIntent);
            return true;
        }else if(item.getItemId() == R.id.verFav){
            Intent miIntent=new Intent(this, verFavoritos.class);
            startActivity(miIntent);
            return true;
        }
        return true;
    }

    //MENU CONTEXTUAL
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextual_favoritos, menu);
    }
    //MENU CONTEXTUAL
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //Opcion de mostrar la informacion
        if (item.getItemId() == R.id.deleteFav) {
            String titulo=(String) lvPeliculasFav.getItemAtPosition(info.position);
            baseDeDatos database= Room.databaseBuilder(getApplicationContext(), baseDeDatos.class,
                    "Peliculas").allowMainThreadQueries().fallbackToDestructiveMigration().build();

            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setMessage("¿Seguro quieres eliminar ' " + titulo + " ' de favoritos?");
            alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    database.peliculaDAO().deleteAllByMiPeliculaAndIdUsuario(titulo, GlobalVars.getIdUsuario());
                    cargarBBDDenLista();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    cargarBBDDenLista();
                }
            });
            alert.show();

            return true;
        }

        return false;
    }

    private void cargarFoto(String miPelicula){

        LayoutInflater video_alert=LayoutInflater.from(verFavoritos.this);
        vista=video_alert.inflate(R.layout.foto_favoritos, null);

        baseDeDatos database= Room.databaseBuilder(getApplicationContext(), baseDeDatos.class,
                "Peliculas").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        String poster=database.peliculaDAO().findfotoBymiPelicula(miPelicula);

        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + poster).into((ImageView) vista.findViewById(R.id.fotoFav));

        AlertDialog.Builder dialogo=new AlertDialog.Builder(this);
        dialogo.setView(vista);
        dialogo.show();
    }

    private void detectarPreferencias(){

        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        boolean cbTemas = preferencias.getBoolean("cbTemas", false);
        if (cbTemas){
            favLayout.setBackgroundColor(Color.LTGRAY);
        }else{
            favLayout.setBackgroundColor(Color.BLACK);
        }
    }

}