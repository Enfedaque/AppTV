package com.enfedaque.Activities;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.enfedaque.R;
import com.enfedaque.adaptadores.recyclerViewAdapterPOPULAR;
import com.enfedaque.domain.peliculas;
import com.enfedaque.domain.respuestaPeliculas;
import com.enfedaque.API.PeliculasAPI;
import com.enfedaque.domain.respuestaUpcoming;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

public class index extends AppCompatActivity {

    private RecyclerView rvPopular;
    private recyclerViewAdapterPOPULAR rvAdapter;
    private RecyclerView rvTop;
    private RecyclerView rvOther;

    private PeliculasAPI peliculasAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rv);

        inicializarComponentes();

        TaskVideos taskVideos=new TaskVideos();
        taskVideos.execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        TaskVideos taskVideos=new TaskVideos();
        taskVideos.execute();
    }

    //Hilo que me va a lanzar la busqueda en la API
    class TaskVideos extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... strings) {
            try{
                findAll();
                findAllOther();
                findAllTopRated();
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
            Intent miIntent=new Intent(this, index.class);
            startActivity(miIntent);
            return true;
        }else if(item.getItemId() == R.id.verFav){
            Intent miIntent=new Intent(this, verFavoritos.class);
            startActivity(miIntent);
            return true;
        }
        return true;
    }

    //POPULARES
    private void findAll(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        PeliculasAPI peliculasAPI =retrofit.create(PeliculasAPI.class);
        peliculasAPI.findAllPopular()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respuestaPeliculas -> mostrarAllPopular(respuestaPeliculas.getResults()));
    }

    //TOP RATED
    private void findAllTopRated(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        PeliculasAPI peliculasAPI =retrofit.create(PeliculasAPI.class);
        peliculasAPI.findAllTopRated()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respuestaPeliculas -> mostrarTopRated(respuestaPeliculas.getResults()));
    }

    //OTHERS
    private void findAllOther(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        PeliculasAPI peliculasAPI =retrofit.create(PeliculasAPI.class);
        peliculasAPI.findOthers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respuestaUpcoming -> mostrarUpcoming(respuestaUpcoming.getResults()));
    }

    //Mostrar populares
    private void mostrarAllPopular(List<peliculas> lista){
        rvAdapter=new recyclerViewAdapterPOPULAR(lista);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext(),
                RecyclerView.HORIZONTAL, false);
        rvPopular.setLayoutManager(mLayoutManager);
        rvPopular.setItemAnimator(new DefaultItemAnimator());
        rvPopular.setAdapter(rvAdapter);
        rvAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent miIntent=new Intent(getApplicationContext(), videos.class);
                miIntent.putExtra("ID", String.valueOf(lista.get(rvPopular.getChildAdapterPosition(view)).getId()));
                miIntent.putExtra("Titulo", lista.get(rvPopular.getChildAdapterPosition(view)).getOriginal_title());
                miIntent.putExtra("Fecha", lista.get(rvPopular.getChildAdapterPosition(view)).getRelease_date());
                miIntent.putExtra("Popularity", String.valueOf(lista.get(rvPopular.getChildAdapterPosition(view)).getVote_count()));
                miIntent.putExtra("Overview", lista.get(rvPopular.getChildAdapterPosition(view)).getOverview());
                startActivity(miIntent);

            }
        });
    }

    //Mostrar upComing
    private void mostrarUpcoming(List<peliculas> listado){
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
                Intent miIntent=new Intent(getApplicationContext(), videos.class);
                miIntent.putExtra("ID", String.valueOf(lista.get(rvPopular.getChildAdapterPosition(view)).getId()));
                miIntent.putExtra("Titulo", lista.get(rvPopular.getChildAdapterPosition(view)).getOriginal_title());
                miIntent.putExtra("Fecha", lista.get(rvPopular.getChildAdapterPosition(view)).getRelease_date());
                miIntent.putExtra("Popularity", String.valueOf(lista.get(rvPopular.getChildAdapterPosition(view)).getVote_count()));
                miIntent.putExtra("Overview", lista.get(rvPopular.getChildAdapterPosition(view)).getOverview());
                startActivity(miIntent);

            }
        });
    }

    //Mostrar topRated
    private void mostrarTopRated(List<peliculas> lista){
        rvAdapter=new recyclerViewAdapterPOPULAR(lista);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext(),
                RecyclerView.HORIZONTAL, false);
        rvTop.setLayoutManager(mLayoutManager);
        rvTop.setItemAnimator(new DefaultItemAnimator());
        rvTop.setAdapter(rvAdapter);

        rvAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent miIntent=new Intent(getApplicationContext(), videos.class);
                miIntent.putExtra("ID", String.valueOf(lista.get(rvPopular.getChildAdapterPosition(view)).getId()));
                miIntent.putExtra("Titulo", lista.get(rvPopular.getChildAdapterPosition(view)).getOriginal_title());
                miIntent.putExtra("Fecha", lista.get(rvPopular.getChildAdapterPosition(view)).getRelease_date());
                miIntent.putExtra("Popularity", String.valueOf(lista.get(rvPopular.getChildAdapterPosition(view)).getVote_count()));
                miIntent.putExtra("Overview", lista.get(rvPopular.getChildAdapterPosition(view)).getOverview());
                startActivity(miIntent);

            }
        });
    }

    private void inicializarComponentes(){
        rvPopular =findViewById(R.id.rvRecyclerViewPopular);
        rvTop =findViewById(R.id.rv2);
        rvOther =findViewById(R.id.rv3);
    }

    //Añadir una pelicula a FAVORITOS
    public void marcarFavorito(View view){
        Toast.makeText(getApplicationContext(), "Pelicula añadida a FAVORITOS" , Toast.LENGTH_LONG).show();
    }


}