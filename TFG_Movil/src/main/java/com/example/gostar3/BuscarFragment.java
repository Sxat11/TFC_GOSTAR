package com.example.gostar3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarFragment extends Fragment implements PublicacionAdapter.OnItemClickListener {

    private EditText searchInput;
    private ImageView searchButton;
    private TextView resultadosLabel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout emptyLayout;

    private PublicacionAdapter adapter;
    private List<Publicacion> resultados = new ArrayList<>();

    private ApiService apiService;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        inicializarVistas(view);
        configurarRecyclerView();

        apiService = ApiClient.getClient();
        sharedPreferences = getActivity().getSharedPreferences("GostarPrefs", getContext().MODE_PRIVATE);

        configurarListeners();

        return view;
    }

    private void inicializarVistas(View view) {
        searchInput = view.findViewById(R.id.searchInput);
        searchButton = view.findViewById(R.id.searchButton);
        resultadosLabel = view.findViewById(R.id.resultadosLabel);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        emptyLayout = view.findViewById(R.id.emptyLayout);
    }

    private void configurarRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PublicacionAdapter(resultados, this);
        recyclerView.setAdapter(adapter);
    }

    private void configurarListeners() {
        // Búsqueda al hacer clic en el botón
        searchButton.setOnClickListener(v -> realizarBusqueda());

        // Búsqueda al presionar "Enter" en el teclado
        searchInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                realizarBusqueda();
                return true;
            }
            return false;
        });

        // Búsqueda en tiempo real (con debounce de 500ms)
        searchInput.addTextChangedListener(new TextWatcher() {
            private android.os.Handler handler = new android.os.Handler();
            private Runnable runnable;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                runnable = () -> {
                    String query = s.toString().trim();
                    if (query.length() >= 3) {
                        realizarBusqueda();
                    } else if (query.length() == 0) {
                        limpiarResultados();
                    }
                };
                handler.postDelayed(runnable, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void realizarBusqueda() {
        String query = searchInput.getText().toString().trim();
        if (query.isEmpty()) {
            Toast.makeText(getContext(), "Escribe algo para buscar", Toast.LENGTH_SHORT).show();
            return;
        }

        mostrarCargando(true);

        String token = sharedPreferences.getString("token", null);
        if (token == null) {
            Toast.makeText(getContext(), "Sesión no válida", Toast.LENGTH_SHORT).show();
            mostrarCargando(false);
            return;
        }

        apiService.buscarPublicaciones("Bearer " + token, query, 1, 50)
                .enqueue(new Callback<List<Publicacion>>() {
                    @Override
                    public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                        mostrarCargando(false);

                        if (response.isSuccessful() && response.body() != null) {
                            resultados.clear();
                            resultados.addAll(response.body());
                            adapter.notifyDataSetChanged();

                            resultadosLabel.setVisibility(View.VISIBLE);
                            resultadosLabel.setText("Resultados para: \"" + query + "\" (" + resultados.size() + ")");

                            if (resultados.isEmpty()) {
                                emptyLayout.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            } else {
                                emptyLayout.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            mostrarError("Error en la búsqueda");
                            resultadosLabel.setVisibility(View.GONE);
                            emptyLayout.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Publicacion>> call, Throwable t) {
                        mostrarCargando(false);
                        mostrarError("Error de conexión: " + t.getMessage());
                        resultadosLabel.setVisibility(View.GONE);
                        emptyLayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                });
    }

    private void limpiarResultados() {
        resultados.clear();
        adapter.notifyDataSetChanged();
        resultadosLabel.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void mostrarCargando(boolean mostrar) {
        progressBar.setVisibility(mostrar ? View.VISIBLE : View.GONE);
        if (!mostrar) {
            if (resultados.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyLayout.setVisibility(View.GONE);
            }
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);
        }
    }

    private void mostrarError(String mensaje) {
        Snackbar.make(getView(), mensaje, Snackbar.LENGTH_LONG).show();
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
                }
            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error al dar like", Toast.LENGTH_SHORT).show();
            }
        });
    }
}