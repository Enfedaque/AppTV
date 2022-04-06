package com.enfedaque.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfedaque.R;
import com.enfedaque.domain.peliculas;
import com.squareup.picasso.Picasso;

import java.util.List;

public class recyclerViewAdapterPOPULAR
        extends RecyclerView.Adapter<recyclerViewAdapterPOPULAR.MyViewHolder>
        implements View.OnClickListener {

    private List<peliculas> listadoPeliculas;
    private View.OnClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView portada;

        public MyViewHolder(View view){
            super(view);
            portada=view.findViewById(R.id.portada);
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        peliculas miPelicula= listadoPeliculas.get(position);
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + miPelicula.getPoster_path()).into(holder.portada);
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
