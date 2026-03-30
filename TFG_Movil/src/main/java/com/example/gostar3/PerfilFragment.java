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

import androidx.annotation.NonNull;
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

    private static final String TAG = "PERFIL";

    private ImageView avatarImage;
    private TextView textNombre, textUsername, textEmail, textBio, textRecetasCount, textLikesCount;
    private Button btnEditarPerfil, btnCrearReceta;
    private RecyclerView recyclerView;
    private LinearLayout noPublicacionesLayout;
    private SwipeRefreshLayout swipeRefresh;
    private TabLayout tabLayout;

    private ApiService apiService;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private Usuario usuarioActual;
    private List<Publicacion> misPublicaciones = new ArrayList<>();
    private List<Publicacion> favoritos = new ArrayList<>();
    private PublicacionAdapter adapter;
    private String tabActual = "mis_recetas";

    // Cache de todas las publicaciones para evitar múltiples peticiones
    private List<Publicacion> todasPublicacionesCache = new ArrayList<>();
    private boolean isLoading = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        inicializarVistas(view);
        configurarBotones(view);
        configurarRecyclerView(view);
        configurarTabs();

        apiService = ApiClient.getClient();
        sharedPreferences = getActivity().getSharedPreferences("GostarPrefs", getContext().MODE_PRIVATE);
        gson = new Gson();

        swipeRefresh.setOnRefreshListener(() -> {
            if (tabActual.equals("mis_recetas")) {
                cargarMisPublicaciones();
            } else {
                cargarFavoritos();
            }
        });

        cargarUsuario();

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
        btnCrearReceta = view.findViewById(R.id.btnCrearReceta);
        recyclerView = view.findViewById(R.id.recyclerView);
        noPublicacionesLayout = view.findViewById(R.id.noPublicacionesLayout);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        tabLayout = view.findViewById(R.id.tabLayout);
    }

    private void configurarBotones(View view) {
        btnEditarPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditarPerfilActivity.class);
            startActivity(intent);
        });

        btnCrearReceta.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), CrearRecetaActivity.class));
        });

        Button btnCrearDesdeEmpty = view.findViewById(R.id.btnCrearRecetaDesdeEmpty);
        if (btnCrearDesdeEmpty != null) {
            btnCrearDesdeEmpty.setOnClickListener(v -> {
                startActivity(new Intent(getActivity(), CrearRecetaActivity.class));
            });
        }
    }

    private void configurarRecyclerView(View view) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PublicacionAdapter(misPublicaciones, this);
        recyclerView.setAdapter(adapter);
    }

    private void configurarTabs() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tabActual = "mis_recetas";
                    mostrarMisRecetas();
                } else {
                    tabActual = "favoritos";
                    cargarFavoritos();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void cargarUsuario() {
        String token = sharedPreferences.getString("token", null);
        if (token == null) {
            Toast.makeText(getContext(), "Sesión no válida", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "========== CARGANDO USUARIO ==========");

        apiService.getPerfil("Bearer " + token).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    usuarioActual = response.body();
                    Log.d(TAG, "✅ Usuario cargado: ID=" + usuarioActual.getId() +
                            ", Nombre=" + usuarioActual.getNombre() +
                            ", Username=" + usuarioActual.getUsername());
                    mostrarPerfil();
                    cargarTodasPublicaciones();
                } else {
                    Log.e(TAG, "❌ Error al cargar perfil: " + response.code());
                    Toast.makeText(getContext(), "Error al cargar perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e(TAG, "❌ Error de conexión: " + t.getMessage());
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarPerfil() {
        if (usuarioActual != null) {
            textNombre.setText(usuarioActual.getNombre() != null ? usuarioActual.getNombre() : usuarioActual.getUsername());
            textUsername.setText("@" + usuarioActual.getUsername());
            textEmail.setText(usuarioActual.getEmail());
            textBio.setText(usuarioActual.getBio() != null ? usuarioActual.getBio() : "Sin biografía aún");
        }
    }

    private void cargarTodasPublicaciones() {
        if (isLoading) {
            Log.d(TAG, "⚠️ Ya está cargando, ignorando...");
            return;
        }

        isLoading = true;
        swipeRefresh.setRefreshing(true);

        String token = sharedPreferences.getString("token", null);
        if (token == null || usuarioActual == null) {
            Log.e(TAG, "❌ Token o usuario null");
            swipeRefresh.setRefreshing(false);
            isLoading = false;
            return;
        }

        Log.d(TAG, "========== CARGANDO TODAS LAS PUBLICACIONES ==========");
        Log.d(TAG, "Usuario ID: " + usuarioActual.getId());
        Log.d(TAG, "Usuario Nombre: " + usuarioActual.getUsername());

        apiService.getPublicaciones("Bearer " + token, 1, 100)
                .enqueue(new Callback<List<Publicacion>>() {
                    @Override
                    public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                        swipeRefresh.setRefreshing(false);
                        isLoading = false;

                        Log.d(TAG, "📡 Código respuesta: " + response.code());

                        if (response.isSuccessful() && response.body() != null) {
                            todasPublicacionesCache = response.body();
                            Log.d(TAG, "📦 Total publicaciones recibidas: " + todasPublicacionesCache.size());

                            // Imprimir todas las publicaciones para debug
                            for (int i = 0; i < todasPublicacionesCache.size(); i++) {
                                Publicacion pub = todasPublicacionesCache.get(i);
                                Log.d(TAG, "  [" + i + "] ID: " + pub.getId() +
                                        ", Título: " + pub.getTitulo() +
                                        ", usuarioId: " + pub.getUsuarioId() +
                                        ", usuarioNombre: " + pub.getUsuarioNombre() +
                                        ", likedByCurrentUser: " + pub.isLikedByCurrentUser());
                            }

                            // Filtrar mis publicaciones
                            filtrarMisPublicaciones();

                            // Filtrar favoritos
                            filtrarFavoritos();

                            // Mostrar mis recetas por defecto
                            mostrarMisRecetas();

                        } else {
                            Log.e(TAG, "❌ Respuesta no exitosa: " + response.code());
                            Toast.makeText(getContext(), "Error al cargar recetas", Toast.LENGTH_SHORT).show();
                            mostrarError();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Publicacion>> call, Throwable t) {
                        swipeRefresh.setRefreshing(false);
                        isLoading = false;
                        Log.e(TAG, "❌ Error de conexión: " + t.getMessage());
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        mostrarError();
                    }
                });
    }

    private void filtrarMisPublicaciones() {
        misPublicaciones.clear();
        for (Publicacion pub : todasPublicacionesCache) {
            // Comparar por ID o por nombre de usuario
            boolean esMia = (pub.getUsuarioId() == usuarioActual.getId()) ||
                    (pub.getUsuarioNombre() != null &&
                            pub.getUsuarioNombre().equals(usuarioActual.getUsername()));

            if (esMia) {
                misPublicaciones.add(pub);
                Log.d(TAG, "✅ AÑADIDA a Mis Recetas: " + pub.getTitulo() + " (ID: " + pub.getId() + ")");
            }
        }

        Log.d(TAG, "🎯 Mis publicaciones filtradas: " + misPublicaciones.size());

        // Actualizar contador de recetas
        textRecetasCount.setText(String.valueOf(misPublicaciones.size()));

        // Calcular total de likes
        int totalLikes = 0;
        for (Publicacion pub : misPublicaciones) {
            totalLikes += pub.getLikes();
        }
        textLikesCount.setText(String.valueOf(totalLikes));
    }

    private void filtrarFavoritos() {
        favoritos.clear();
        for (Publicacion pub : todasPublicacionesCache) {
            if (pub.isLikedByCurrentUser()) {
                favoritos.add(pub);
                Log.d(TAG, "⭐ AÑADIDA a Favoritos: " + pub.getTitulo());
            }
        }

        Log.d(TAG, "🎯 Favoritos filtrados: " + favoritos.size());
    }

    private void cargarMisPublicaciones() {
        Log.d(TAG, "🔄 Recargando Mis Publicaciones");

        if (todasPublicacionesCache.isEmpty()) {
            cargarTodasPublicaciones();
        } else {
            filtrarMisPublicaciones();
            mostrarMisRecetas();
        }
    }

    private void cargarFavoritos() {
        Log.d(TAG, "🔄 Recargando Favoritos");

        if (todasPublicacionesCache.isEmpty()) {
            cargarTodasPublicaciones();
        } else {
            filtrarFavoritos();
            mostrarFavoritos();
        }
    }

    private void mostrarMisRecetas() {
        Log.d(TAG, "========== MOSTRANDO MIS RECETAS ==========");
        Log.d(TAG, "misPublicaciones.size() = " + misPublicaciones.size());

        if (misPublicaciones.isEmpty()) {
            Log.d(TAG, "📭 No hay publicaciones, mostrando layout vacío");
            recyclerView.setVisibility(View.GONE);
            noPublicacionesLayout.setVisibility(View.VISIBLE);
        } else {
            Log.d(TAG, "📱 Mostrando " + misPublicaciones.size() + " publicaciones en RecyclerView");
            recyclerView.setVisibility(View.VISIBLE);
            noPublicacionesLayout.setVisibility(View.GONE);

            // Actualizar el adapter
            if (adapter == null) {
                adapter = new PublicacionAdapter(misPublicaciones, this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.updateData(misPublicaciones);
            }
        }
    }

    private void mostrarFavoritos() {
        Log.d(TAG, "========== MOSTRANDO FAVORITOS ==========");
        Log.d(TAG, "favoritos.size() = " + favoritos.size());

        if (favoritos.isEmpty()) {
            Log.d(TAG, "📭 No hay favoritos, mostrando layout vacío");
            recyclerView.setVisibility(View.GONE);
            noPublicacionesLayout.setVisibility(View.VISIBLE);
            // Cambiar texto para favoritos
            TextView emptyText = noPublicacionesLayout.findViewById(android.R.id.text1);
            if (emptyText != null) {
                emptyText.setText("No tienes recetas favoritas");
            }
        } else {
            Log.d(TAG, "⭐ Mostrando " + favoritos.size() + " favoritos en RecyclerView");
            recyclerView.setVisibility(View.VISIBLE);
            noPublicacionesLayout.setVisibility(View.GONE);

            // Actualizar el adapter
            if (adapter == null) {
                adapter = new PublicacionAdapter(favoritos, this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.updateData(favoritos);
            }
        }
    }

    private void mostrarError() {
        recyclerView.setVisibility(View.GONE);
        noPublicacionesLayout.setVisibility(View.VISIBLE);
        TextView emptyText = noPublicacionesLayout.findViewById(android.R.id.text1);
        if (emptyText != null) {
            emptyText.setText("Error al cargar las recetas");
        }
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
                    // Actualizar el estado del like en la publicación
                    publicacion.setLikes(response.body().getLikes());
                    publicacion.setLikedByCurrentUser(response.body().isLiked());

                    // Actualizar en la caché
                    for (int i = 0; i < todasPublicacionesCache.size(); i++) {
                        if (todasPublicacionesCache.get(i).getId() == publicacion.getId()) {
                            todasPublicacionesCache.get(i).setLikes(response.body().getLikes());
                            todasPublicacionesCache.get(i).setLikedByCurrentUser(response.body().isLiked());
                            break;
                        }
                    }

                    if (tabActual.equals("mis_recetas")) {
                        // Actualizar la lista de mis publicaciones
                        for (int i = 0; i < misPublicaciones.size(); i++) {
                            if (misPublicaciones.get(i).getId() == publicacion.getId()) {
                                misPublicaciones.set(i, publicacion);
                                adapter.notifyItemChanged(position);
                                break;
                            }
                        }
                        // Actualizar total de likes en estadísticas
                        int totalLikes = 0;
                        for (Publicacion pub : misPublicaciones) {
                            totalLikes += pub.getLikes();
                        }
                        textLikesCount.setText(String.valueOf(totalLikes));
                    } else {
                        // En favoritos, si quitamos like, la receta debe desaparecer
                        if (!publicacion.isLikedByCurrentUser()) {
                            favoritos.removeIf(p -> p.getId() == publicacion.getId());
                            adapter.notifyItemRemoved(position);
                            mostrarFavoritos();
                        } else {
                            adapter.notifyItemChanged(position);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Log.e(TAG, "Error al dar like: " + t.getMessage());
                Toast.makeText(getContext(), "Error al dar like", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume - Recargando datos");
        cargarTodasPublicaciones();
    }
}