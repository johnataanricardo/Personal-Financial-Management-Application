package info.seufinanceiro.service;

import info.seufinanceiro.model.Auth;
import info.seufinanceiro.model.Category;
import info.seufinanceiro.model.Movement;
import info.seufinanceiro.model.MovementData;
import info.seufinanceiro.model.SignUp;
import info.seufinanceiro.model.Token;
import info.seufinanceiro.utils.ResponseData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface HttpClientService {

    @Headers("Content-Type: application/json")

    @POST("auth")
    Call<Auth> authorize(@Body Auth auth);

    @POST("auth/valid")
    Call<Token> validate(@Body Token token);

    @POST("user/sign-up/")
    Call<SignUp> signup(@Body SignUp signUp);

    @GET("movimentacoes")
    Call <ResponseData<Movement>> getMovements(@Header("Authorization") String authorization);

    // Crud Category
    @GET("categoria/")
    Call<ResponseData<Category>> getAllCategories(@Header("Authorization") String token);

    @POST("categoria/")
    Call<Category> saveCategory(@Header("Authorization") String token, @Body Category category);

    @DELETE("categoria/{id}")
    Call<Category> deleteCategory(@Header("Authorization") String token, @Path("id") Long id);

    @PUT("categoria/{id}")
    Call<Category> updateCategory(@Header("Authorization") String token, @Body Category category,
                                  @Path("id") Long id);
}
