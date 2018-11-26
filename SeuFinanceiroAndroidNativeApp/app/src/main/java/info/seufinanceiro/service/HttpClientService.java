package info.seufinanceiro.service;

import info.seufinanceiro.model.Auth;
import info.seufinanceiro.model.SignUp;
import info.seufinanceiro.model.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface HttpClientService {
    @Headers("Content-Type: application/json")

    @POST("auth")
    Call<Auth> authorize(@Body Auth auth);

    @POST("auth/valid")
    Call<Token> validate(@Body Token token);

    @POST("user/sign-up/")
    Call<SignUp> signup(@Body SignUp signUp);
}
