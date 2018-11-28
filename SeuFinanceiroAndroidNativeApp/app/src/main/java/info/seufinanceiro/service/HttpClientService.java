package info.seufinanceiro.service;

import java.util.ArrayList;
import java.util.List;

import info.seufinanceiro.model.Auth;
import info.seufinanceiro.model.Movement;
import info.seufinanceiro.model.MovementData;
import info.seufinanceiro.model.MovementResponse;
import info.seufinanceiro.model.SignUp;
import info.seufinanceiro.model.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @GET("movimentacoes")
    Call <MovementData> getMovements(@Header("Authorization") String authorization);
}
