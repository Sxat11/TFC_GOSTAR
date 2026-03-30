package com.example.gostar3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragment extends Fragment implements PublicacionAdapter.OnItemClickListener {

    private ImageView avatarImage;
    private TextView textNombre, textUsername, textEmail, textBio, textRecetasCount, textLikesCount;
    private Button btnEditarPerfil;
    private RecyclerView recyclerView;
    private LinearLayout noPublicacionesLayout;
    private SwipeRefreshLayout swipeRefresh;
    private TabLayout tabLayout;

    private ApiService apiService;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private Usuario usuarioActual;
    private List<Publicacion> misPublicaciones = new ArrayList<>();
    private PublicacionAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        inicializarVistas(view);
        configurarBotones(view);

        apiService = ApiClient.getClient();
        sharedPreferences = getActivity().getSharedPreferences("GostarPrefs", getContext().MODE_PRIVATE);
        gson = new Gson();

        configurarRecyclerView(view);
        cargarUsuario();

        swipeRefresh.setOnRefreshListener(this::cargarMisPublicaciones);

        return view;
    }

    private void inicializarVistas(View view) {
        avatarImage = view.findViewById(R.id.avatarImage);
        textNombre = view.findViewById(R.id.textNombre);
        textUsername = view.findViewById(R.id.textUsername);
        textEmail = view.findViewById(R.id.textEmail);
        textBio = view.findViewById(R.id.textBio);
        textRecetasCount = view.findViewById(R.id.textRecetasCount);
        textLikesCount = view.findViewById(R.id.textLikesCount);
        btnEditarPerfil = view.findViewById(R.id.btnEditarPerfil);
        recyclerView = view.findViewById(R.id.recyclerView);
        noPublicacionesLayout = view.findViewById(R.id.noPublicacionesLayout);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        tabLayout = view.findViewById(R.id.tabLayout);

        // Configurar tabs
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    cargarMisPublicaciones();
                } else {
                    // TODO: Cargar favoritos
                    Toast.makeText(getContext(), "Favoritos - Próximamente", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void configurarBotones(View view) {
        btnEditarPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditarPerfilActivity.class);
            startActivity(intent);
        });

        view.findViewById(R.id.btnCrearReceta).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), CrearRecetaActivity.class));
        });
    }

    private void configurarRecyclerView(View view) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PublicacionAdapter(misPublicaciones, this);
        recyclerView.setAdapter(adapter);
    }

    private void cargarUsuario() {
        String token = sharedPreferences.getString("token", null);
        if (token == null) {
            Toast.makeText(getContext(), "Sesión no válida", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.getPerfil("Bearer " + token).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    usuarioActual = response.body();
                    mostrarPerfil();
                    cargarMisPublicaciones();
                } else {
                    Toast.makeText(getContext(), "Error al cargar perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarPerfil() {
        textNombre.setText(usuarioActual.getNombre() != null ? usuarioActual.getNombre() : usuarioActual.getUsername());
        textUsername.setText("@" + usuarioActual.getUsername());
        textEmail.setText(usuarioActual.getEmail());
        textBio.setText(usuarioActual.getBio() != null ? usuarioActual.getBio() : "Sin biografía aún");
    }

    private void cargarMisPublicaciones() {
        String token = sharedPreferences.getString("token", null);
        if (token == null || usuarioActual == null) return;

        apiService.getPublicacionesByUsuario("Bearer " + token, usuarioActual.getId(), 1)
                .enqueue(new Callback<List<Publicacion>>() {
                    @Override
                    public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                        swipeRefresh.setRefreshing(false);

                        if (response.isSuccessful() && response.body() != null) {
                            // FILTRAR POR NOMBRE DE USUARIO (SOLUCIÓN TEMPORAL)
                            List<Publicacion> todas = response.body();
                            List<Publicacion> filtradas = new ArrayList<>();

                            for (Publicacion pub : todas) {
                                // Si el usuarioId es 0 pero el nombre coincide, la consideramos nuestra
                                if (pub.getUsuarioNombre() != null &&
                                        pub.getUsuarioNombre().equals(usuarioActual.getUsername())) {
                                    filtradas.add(pub);
                                }
                            }

                            misPublicaciones.clear();
                            misPublicaciones.addAll(filtradas);
                            adapter.notifyDataSetChanged();

                            textRecetasCount.setText(String.valueOf(misPublicaciones.size()));

                            int totalLikes = 0;
                            for (Publicacion pub : misPublicaciones) {
                                totalLikes += pub.getLikes();
                            }
                            textLikesCount.setText(String.valueOf(totalLikes));

                            if (misPublicaciones.isEmpty()) {
                                recyclerView.setVisibility(View.GONE);
                                noPublicacionesLayout.setVisibility(View.VISIBLE);
                            } else {
                                recyclerView.setVisibility(View.VISIBLE);
                                noPublicacionesLayout.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Publicacion>> call, Throwable t) {
                        swipeRefresh.setRefreshing(false);
                    }
                });
    }




    @Override
    public void onItemClick(Publicacion publicacion) {
        Intent intent = new Intent(getActivity(), DetalleReceta.class);
        intent.putExtra("receta_id", publicacion.getId());
        startActivity(intent);
    }

    @Override
    public void onLikeClick(Publicacion publicacion, int position) {
        String token = sharedPreferences.getString("token", null);
        if (token == null) return;

        Call<LikeResponse> call;
        if (publicacion.isLikedByCurrentUser()) {
            call = apiService.quitarLike("Bearer " + token, publicacion.getId());
        } else {
            call = apiService.darLike("Bearer " + token, publicacion.getId());
        }

        call.enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    publicacion.setLikes(response.body().getLikes());
                    publicacion.setLikedByCurrentUser(response.body().isLiked());
                    adapter.notifyItemChanged(position);

                    // Actualizar total de likes
                    int totalLikes = 0;
                    for (Publicacion p : misPublicaciones) {
                        totalLikes += p.getLikes();
                    }
                    textLikesCount.setText(String.valueOf(totalLikes));
                }
            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error al dar like", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Recargar datos cuando volvemos
        cargarUsuario(); // Esto ya llama a cargarMisPublicaciones()
    }
}