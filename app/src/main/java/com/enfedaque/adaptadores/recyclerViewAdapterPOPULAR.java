package com.enfedaque.adaptadores;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.enfedaque.BBDD.baseDeDatos;
import com.enfedaque.R;
import com.enfedaque.UTILS.GlobalVars;
import com.enfedaque.domain.favoritos;
import com.enfedaque.domain.peliculas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class recyclerViewAdapterPOPULAR
        extends RecyclerView.Adapter<recyclerViewAdapterPOPULAR.MyViewHolder>
        implements View.OnClickListener {

    private List<peliculas> listadoPeliculas;
    private View.OnClickListener listener;
    public FloatingActionButton fab;

    private LinearLayout popularLayout;
    private MediaPlayer mp;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView portada;
        public FloatingActionButton fab;

        public MyViewHolder(View view){
            super(view);
            portada=view.findViewById(R.id.portada);
            fab=view.findViewById(R.id.FAV);
            popularLayout=view.findViewById(R.id.popularLayout);
        }
    }

    public recyclerViewAdapterPOPULAR(List<peliculas> listadoPeliculas){
        this.listadoPeliculas =listadoPeliculas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listado_peliculas, parent, false);

        itemView.setOnClickListener(this);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        peliculas miPelicula= listadoPeliculas.get(position);
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + miPelicula.getPoster_path()).into(holder.portada);

        holder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(holder.fab.getContext());
                boolean cbSonidos = preferencias.getBoolean("cbSonido", false);
                if (cbSonidos){
                    mp=MediaPlayer.create(holder.fab.getContext(), R.raw.boton1);
                }else{
                    mp=MediaPlayer.create(holder.fab.getContext(), R.raw.boton2);
                }mp.start();

                peliculas miPeli=listadoPeliculas.get(position);
                baseDeDatos database= Room.databaseBuilder(view.getContext(), baseDeDatos.class,
                        "Peliculas").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                database.peliculaDAO().insert(new favoritos(miPeli.getOriginal_title(), miPeli.getPoster_path(),
                        String.valueOf(miPeli.getId()), GlobalVars.getIdUsuario()));
                Toast.makeText(view.getContext(), "Añadida a FAVORITOS", Toast.LENGTH_LONG).show();
            }
        });

        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(holder.fab.getContext());
        boolean cbTemas = preferencias.getBoolean("cbTemas", false);
        if (cbTemas){
            popularLayout.setBackgroundColor(Color.LTGRAY);
        }else{
            popularLayout.setBackgroundColor(Color.BLACK);
        }

    }

    @Override
    public int getItemCount() {
        return listadoPeliculas.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }

    }

}
