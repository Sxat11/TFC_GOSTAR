package com.example.gostar3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritosFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout emptyLayout; // Asegúrate de tenerlo en tu XML
    private ImageView btnExplorar;

    private PublicacionAdapter adapter;
    private List<Publicacion> favoritos = new ArrayList<>();

    private ApiService apiService;
    private String token;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favoritas, container, false);

        // Inicializar vistas
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        emptyLayout = view.findViewById(R.id.emptyLayout); // DEBE EXISTIR EN EL XML
        btnExplorar = view.findViewById(R.id.btnBack); // BOTÓN PARA IR AL MURO

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PublicacionAdapter(favoritos, new PublicacionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Publicacion publicacion) {
                Intent intent = new Intent(getActivity(), DetalleReceta.class);
                intent.putExtra("receta_id", publicacion.getId());
                startActivity(intent);
            }

            @Override
            public void onLikeClick(Publicacion publicacion, int position) {
                toggleLike(publicacion, position);
            }
        });
        recyclerView.setAdapter(adapter);

        // API y Token
        apiService = ApiClient.getClient();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("GostarPrefs", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        // Listeners
        swipeRefresh.setOnRefreshListener(this::cargarFavoritos);
        if (btnExplorar != null) {
            btnExplorar.setOnClickListener(v -> irAlMuro());
        }

        cargarFavoritos();
        return view;
    }

    private void cargarFavoritos() {
        if (token == null) return;

        swipeRefresh.setRefreshing(true);

        // Llamamos al mismo método que en el muro, pero filtramos
        apiService.getPublicaciones("Bearer " + token, 1, 100)
                .enqueue(new Callback<List<Publicacion>>() {
                    @Override
                    public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                        swipeRefresh.setRefreshing(false);
                        if (response.isSuccessful() && response.body() != null) {
                            favoritos.clear();
                            for (Publicacion pub : response.body()) {
                                // FILTRO CLAVE: Solo añadimos si tiene LIKE
                                if (pub.isLikedByCurrentUser()) {
                                    favoritos.add(pub);
                                }
                            }
                            actualizarInterfaz();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Publicacion>> call, Throwable t) {
                        swipeRefresh.setRefreshing(false);
                        Toast.makeText(getContext(), "Error de red", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void toggleLike(Publicacion publicacion, int position) {
        // Al quitar el like desde aquí, eliminamos la tarjeta directamente
        apiService.quitarLike("Bearer " + token, publicacion.getId())
                .enqueue(new Callback<LikeResponse>() {
                    @Override
                    public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                        if (response.isSuccessful()) {
                            favoritos.remove(position);
                            adapter.notifyItemRemoved(position);
                            actualizarInterfaz();
                            Snackbar.make(getView(), "Eliminado de favoritos", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LikeResponse> call, Throwable t) {}
                });
    }

    private void actualizarInterfaz() {
        if (favoritos.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    private void irAlMuro() {
        if (getActivity() instanceof Muro) {
            ((Muro) getActivity()).seleccionarFragment(0); // Ajusta el índice según tu BottomNavigationView
        }
    }
}