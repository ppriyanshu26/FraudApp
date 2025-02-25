package com.priyanshu.projectexhibition;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

class RequestData {
    String text;
    RequestData(String text) { this.text = text; }
}

class ResponseData {
    String input;
    String prediction;
}

public interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/predict")
    Call<ResponseData> sendText(@Body RequestData requestData);
}
