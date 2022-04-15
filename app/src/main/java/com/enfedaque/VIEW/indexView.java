package com.enfedaque.VIEW;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enfedaque.CONTRACT.indexContract;
import com.enfedaque.PRESENTER.indexPresenter;
import com.enfedaque.R;
import com.enfedaque.adaptadores.recyclerViewAdapterPOPULAR;
import com.enfedaque.domain.peliculas;
import com.enfedaque.API.PeliculasAPI;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;

public class indexView extends AppCompatActivity implements indexContract.View {

    private RecyclerView rvPopular;
    private recyclerViewAdapterPOPULAR rvAdapter;
    private RecyclerView rvTop;
    private RecyclerView rvOther;

    private PeliculasAPI peliculasAPI;
    private ConstraintLayout index;
    private TextView etPopular;
    private TextView etTopRated;
    private TextView etMasOpciones;

    private indexPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rv);

        inicializarComponentes();
        presenter=new indexPresenter(this);
        detectarPreferencias();

        TaskVideos taskVideos=new TaskVideos();
        taskVideos.execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        TaskVideos taskVideos=new TaskVideos();
        taskVideos.execute();

        detectarPreferencias();
    }

    //Hilo que me va a lanzar la busqueda en la API
    class TaskVideos extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... strings) {
            try{
                presenter.getPopular();
                presenter.getTopRated();
                presenter.getOthers();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        }
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
        }else if(item.getItemId() == R.id.verFav){
            Intent miIntent=new Intent(this, verFavoritosView.class);
            startActivity(miIntent);
            return true;
        }else if(item.getItemId() == R.id.pref){
            Intent miIntent=new Intent(this, preferencias.class);
            startActivity(miIntent);
            return true;
        }
        return true;
    }

    //Mostrar populares
    @Override
    public void mostrarAllPopular(List<peliculas> lista){
        rvAdapter=new recyclerViewAdapterPOPULAR(lista);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext(),
                RecyclerView.HORIZONTAL, false);
        rvPopular.setLayoutManager(mLayoutManager);
        rvPopular.setItemAnimator(new DefaultItemAnimator());
        rvPopular.setAdapter(rvAdapter);
        rvAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent miIntent=new Intent(getApplicationContext(), videosView.class);
                miIntent.putExtra("ID", String.valueOf(lista.get(rvPopular.getChildAdapterPosition(view)).getId()));
                miIntent.putExtra("Titulo", lista.get(rvPopular.getChildAdapterPosition(view)).getOriginal_title());
                miIntent.putExtra("Fecha", lista.get(rvPopular.getChildAdapterPosition(view)).getRelease_date());
                miIntent.putExtra("Popularity", String.valueOf(lista.get(rvPopular.getChildAdapterPosition(view)).getVote_count()));
                miIntent.putExtra("Overview", lista.get(rvPopular.getChildAdapterPosition(view)).getOverview());
                startActivity(miIntent);

            }
        });
    }

    @Override
    public void errorPopular(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarUpcoming(List<peliculas> listado){
        List<peliculas> lista=listado;
        rvAdapter=new recyclerViewAdapterPOPULAR(lista);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext(),
                RecyclerView.HORIZONTAL, false);
        rvOther.setLayoutManager(mLayoutManager);
        rvOther.setItemAnimator(new DefaultItemAnimator());
        rvOther.setAdapter(rvAdapter);

        rvAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent miIntent=new Intent(getApplicationContext(), videosView.class);
                miIntent.putExtra("ID", String.valueOf(lista.get(rvPopular.getChildAdapterPosition(view)).getId()));
                miIntent.putExtra("Titulo", lista.get(rvPopular.getChildAdapterPosition(view)).getOriginal_title());
                miIntent.putExtra("Fecha", lista.get(rvPopular.getChildAdapterPosition(view)).getRelease_date());
                miIntent.putExtra("Popularity", String.valueOf(lista.get(rvPopular.getChildAdapterPosition(view)).getVote_count()));
                miIntent.putExtra("Overview", lista.get(rvPopular.getChildAdapterPosition(view)).getOverview());
                startActivity(miIntent);

            }
        });
    }

    @Override
    public void errorUpcoming(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarTopRated(List<peliculas> lista){
        rvAdapter=new recyclerViewAdapterPOPULAR(lista);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext(),
                RecyclerView.HORIZONTAL, false);
        rvTop.setLayoutManager(mLayoutManager);
        rvTop.setItemAnimator(new DefaultItemAnimator());
        rvTop.setAdapter(rvAdapter);

        rvAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent miIntent=new Intent(getApplicationContext(), videosView.class);
                miIntent.putExtra("ID", String.valueOf(lista.get(rvPopular.getChildAdapterPosition(view)).getId()));
                miIntent.putExtra("Titulo", lista.get(rvPopular.getChildAdapterPosition(view)).getOriginal_title());
                miIntent.putExtra("Fecha", lista.get(rvPopular.getChildAdapterPosition(view)).getRelease_date());
                miIntent.putExtra("Popularity", String.valueOf(lista.get(rvPopular.getChildAdapterPosition(view)).getVote_count()));
                miIntent.putExtra("Overview", lista.get(rvPopular.getChildAdapterPosition(view)).getOverview());
                startActivity(miIntent);

            }
        });
    }

    @Override
    public void errorTopRated(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void inicializarComponentes(){
        rvPopular =findViewById(R.id.rvRecyclerViewPopular);
        rvTop =findViewById(R.id.rv2);
        rvOther =findViewById(R.id.rv3);
        index=findViewById(R.id.layoutIndex);
        etPopular=findViewById(R.id.etPopulares);
        etTopRated=findViewById(R.id.etTopRated);;
        etMasOpciones=findViewById(R.id.etMasOpciones);;
    }

    //Añadir una pelicula a FAVORITOS
    public void marcarFavorito(View view){
        Toast.makeText(getApplicationContext(), "Pelicula añadida a FAVORITOS" , Toast.LENGTH_LONG).show();
    }

    //Detectar cambio de las preferencias
    private void detectarPreferencias(){

        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        boolean cbTemas = preferencias.getBoolean("cbTemas", false);
        boolean cbLetras = preferencias.getBoolean("cbLetras", false);
        if (cbTemas){
            index.setBackgroundColor(Color.LTGRAY);
            etPopular.setTextColor(Color.BLACK);
            etTopRated.setTextColor(Color.BLACK);
            etMasOpciones.setTextColor(Color.BLACK);
        }else{
            index.setBackgroundColor(Color.BLACK);
            etPopular.setTextColor(Color.WHITE);
            etTopRated.setTextColor(Color.WHITE);
            etMasOpciones.setTextColor(Color.WHITE);
        }

        if (cbLetras) {
            etPopular.setText(etPopular.getText().toString().toUpperCase());
            etTopRated.setText(etTopRated.getText().toString().toUpperCase());
            etMasOpciones.setText(etMasOpciones.getText().toString().toUpperCase());
        }else{
            etPopular.setText(etPopular.getText().toString().toLowerCase());
            etTopRated.setText(etTopRated.getText().toString().toLowerCase());
            etMasOpciones.setText(etMasOpciones.getText().toString().toLowerCase());
        }
    }

}