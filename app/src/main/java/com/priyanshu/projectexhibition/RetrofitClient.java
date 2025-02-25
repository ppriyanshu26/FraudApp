package com.priyanshu.projectexhibition;

import android.content.Context;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static ApiService getInstance(Context context) {
        if (retrofit == null) {
            String baseUrl = PreferenceManager.getFullBaseUrl(context); // Use latest saved IP
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }

    public static void resetRetrofit() {
        retrofit = null; // Reset Retrofit when settings change
    }
}
