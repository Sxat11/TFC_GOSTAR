package com.example.gostar3;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://10.0.2.2:8080/mi-primera-api/rest/";
    // Para dispositivo real: "http://192.168.1.X:8080/mi-primera-api/rest/"
    //private static final String BASE_URL = "http://192.168.1.X:8080/mi-primera-api/rest/";

    private static Retrofit retrofit = null;
    private static ApiService apiService = null;

    public static ApiService getClient() {
        if (apiService == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
}