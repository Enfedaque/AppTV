package com.enfedaque.VIEW;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

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
import android.widget.Toast;

import com.enfedaque.CONTRACT.verFavoritosContract;
import com.enfedaque.PRESENTER.verFavoritosPresenter;
import com.enfedaque.R;
import com.enfedaque.UTILS.GlobalVars;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class verFavoritosView extends AppCompatActivity implements verFavoritosContract.View {

    private verFavoritosPresenter presenter;

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
                presenter.cargarFoto(titulo);
            }
        });

        presenter=new verFavoritosPresenter(this);
        presenter.cargarTitulos();

        detectarPreferencias();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.cargarTitulos();
        detectarPreferencias();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.cargarTitulos();
        detectarPreferencias();
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
            Intent miIntent=new Intent(this, indexView.class);
            startActivity(miIntent);
            return true;
        }else if(item.getItemId() == R.id.pref){
            Intent miIntent=new Intent(this, preferencias.class);
            startActivity(miIntent);
            return true;
        }else if(item.getItemId() == R.id.verFav){
            Intent miIntent=new Intent(this, verFavoritosView.class);
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

            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setMessage("¿Seguro quieres eliminar ' " + titulo + " ' de favoritos?");
            alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (presenter.eliminarPelicula(titulo, GlobalVars.idUsuario)){
                        eliminarPelicula();
                    }else{
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   presenter.cargarTitulos();
                }
            });
            alert.show();

            return true;
        }

        return false;
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

    @Override
    public void cargarFavoritos(List<String> listaFavoritos) {
        titulos.clear();
        titulos.addAll(listaFavoritos);
        LV_Adapter.notifyDataSetChanged();
    }

    @Override
    public void cargarFotoPelicula(String foto) {
        LayoutInflater video_alert=LayoutInflater.from(verFavoritosView.this);
        vista=video_alert.inflate(R.layout.foto_favoritos, null);

        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + foto).into((ImageView) vista.findViewById(R.id.fotoFav));

        AlertDialog.Builder dialogo=new AlertDialog.Builder(this);
        dialogo.setView(vista);
        dialogo.show();
    }

    @Override
    public void eliminarPelicula() {
        presenter.cargarTitulos();
        Toast.makeText(getApplicationContext(), "Pelicula eliminada", Toast.LENGTH_SHORT).show();
    }
}