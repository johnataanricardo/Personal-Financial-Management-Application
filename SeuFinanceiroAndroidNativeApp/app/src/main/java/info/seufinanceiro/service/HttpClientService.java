package info.seufinanceiro.service;

import info.seufinanceiro.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface HttpClientService {
    @Headers("Content-Type: application/json")

    @POST("auth")
    Call<User> authorize(@Body User user);
}
