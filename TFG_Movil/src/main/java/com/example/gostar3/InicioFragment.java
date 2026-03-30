package com.example.gostar3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioFragment extends Fragment implements PublicacionAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private PublicacionAdapter adapter;
    private List<Publicacion> publicaciones = new ArrayList<>();
    private ApiService apiService;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PublicacionAdapter(publicaciones, this);
        recyclerView.setAdapter(adapter);

        apiService = ApiClient.getClient();
        sharedPreferences = getActivity().getSharedPreferences("GostarPrefs", getContext().MODE_PRIVATE);
        gson = new Gson();

        cargarPublicaciones();

        return view;
    }

    private void cargarPublicaciones() {
        String token = sharedPreferences.getString("token", null);

        if (token == null) {
            Toast.makeText(getContext(), "No hay sesión activa", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.getPublicaciones("Bearer " + token, 1, 20).enqueue(new Callback<List<Publicacion>>() {
            @Override
            public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    publicaciones.clear();
                    publicaciones.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Error al cargar publicaciones", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Publicacion>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                }
            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error al dar like", Toast.LENGTH_SHORT).show();
            }
        });
    }
}