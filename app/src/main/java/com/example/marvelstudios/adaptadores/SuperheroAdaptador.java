package com.example.marvelstudios.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marvelstudios.HomeFragment;
import com.example.marvelstudios.clases.Superhero;
import com.example.marvelstudios.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SuperheroAdaptador extends RecyclerView.Adapter<SuperheroAdaptador.ViewHolder> {

    private List<Superhero> datos;
    public SuperheroAdaptador(List<Superhero> datos){
        this.datos = datos;
    }
    private OnItemClickListener listener;

    public void setOnItemClickListener(HomeFragment listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adaptador,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Superhero dato = datos.get(position);
        holder.bind(dato);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(dato);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtId, txtName;
        ImageView imgSuperhero;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtId);
            txtName = itemView.findViewById(R.id.txtName);
            imgSuperhero = itemView.findViewById(R.id.imgSuperhero);
        }
        public void bind (Superhero dato){
            txtName.setText(dato.getName());
            txtId.setText(dato.getId());
            Picasso.get().load(dato.getImage()).into(imgSuperhero);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Superhero superhero);
    }

}
