package com.priyanshu.projectexhibition;

import android.content.Context;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static ApiService getInstance(Context context) {
        if (retrofit == null) {
            String baseUrl = PreferenceManager.getApiBaseUrl(context); // Get the correct base URL
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }

    public static void resetRetrofit() {
        retrofit = null; // Reset Retrofit instance when settings change
    }
}
