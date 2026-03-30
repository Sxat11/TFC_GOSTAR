package com.example.gostar3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PublicacionAdapter extends RecyclerView.Adapter<PublicacionAdapter.ViewHolder> {

    private List<Publicacion> publicaciones;
    private OnItemClickListener listener;
    private int layoutResId; // NUEVO: para usar diferentes layouts

    public interface OnItemClickListener {
        void onItemClick(Publicacion publicacion);
        void onLikeClick(Publicacion publicacion, int position);
    }

    // Constructor original (para item_publicacion)
    public PublicacionAdapter(List<Publicacion> publicaciones, OnItemClickListener listener) {
        this.publicaciones = publicaciones;
        this.listener = listener;
        this.layoutResId = R.layout.item_publicacion;
    }

    // NUEVO CONSTRUCTOR: para usar layout personalizado
    public PublicacionAdapter(List<Publicacion> publicaciones, OnItemClickListener listener, int layoutResId) {
        this.publicaciones = publicaciones;
        this.listener = listener;
        this.layoutResId = layoutResId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutResId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Publicacion pub = publicaciones.get(position);

        // Verificar que los elementos no sean null antes de usarlos
        if (holder.titulo != null) holder.titulo.setText(pub.getTitulo());
        if (holder.duracion != null) holder.duracion.setText(pub.getDuracion());
        if (holder.usuarioNombre != null) holder.usuarioNombre.setText(pub.getUsuarioNombre());
        if (holder.likesCount != null) holder.likesCount.setText(String.valueOf(pub.getLikes()));

        // Cargar imagen con Picasso
        if (pub.getImagenPrincipal() != null && !pub.getImagenPrincipal().isEmpty() && holder.imagen != null) {
            Picasso.get().load(pub.getImagenPrincipal()).into(holder.imagen);
        }

        // Click en la tarjeta para ver detalle
        holder.itemView.setOnClickListener(v -> listener.onItemClick(pub));

        // Click en el corazón para like
        if (holder.likeButton != null) {
            holder.likeButton.setOnClickListener(v -> listener.onLikeClick(pub, position));

            // Color del corazón (rojo si liked, gris si no)
            if (pub.isLikedByCurrentUser()) {
                holder.likeButton.setColorFilter(holder.itemView.getContext().getColor(android.R.color.holo_red_dark));
            } else {
                holder.likeButton.setColorFilter(holder.itemView.getContext().getColor(android.R.color.darker_gray));
            }
        }
    }

    public void updateData(List<Publicacion> nuevasPublicaciones) {
        this.publicaciones = nuevasPublicaciones;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return publicaciones.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen, likeButton;
        TextView titulo, duracion, usuarioNombre, likesCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Buscar elementos de forma segura (pueden ser null si no existen en el layout)
            imagen = itemView.findViewById(R.id.imagenPublicacion);
            titulo = itemView.findViewById(R.id.tituloPublicacion);
            duracion = itemView.findViewById(R.id.duracionPublicacion);
            usuarioNombre = itemView.findViewById(R.id.usuarioNombre);
            likesCount = itemView.findViewById(R.id.likesCount);
            likeButton = itemView.findViewById(R.id.likeButton);
        }
    }
}