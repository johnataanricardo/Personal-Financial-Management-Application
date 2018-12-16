package info.seufinanceiro.service;

import info.seufinanceiro.model.Auth;
import info.seufinanceiro.model.Category;
import info.seufinanceiro.model.Transaction;
import info.seufinanceiro.model.SignUp;
import info.seufinanceiro.model.Token;
import info.seufinanceiro.model.User;
import info.seufinanceiro.utils.ResponseData;
import info.seufinanceiro.utils.ResponseDataSimple;
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

    @GET("transaction/")
    Call<ResponseData<Transaction>> getMovements(@Header("Authorization") String authorization);

    @POST("transaction/")
    Call<Transaction> saveMovement(@Header("Authorization") String authorization, @Body Transaction transaction);

    @GET("category/")
    Call<ResponseData<Category>> getAllCategories(@Header("Authorization") String token);

    @POST("category/")
    Call<Category> saveCategory(@Header("Authorization") String token, @Body Category category);

    @DELETE("category/{id}")
    Call<Category> deleteCategory(@Header("Authorization") String token, @Path("id") Long id);

    @PUT("category/")
    Call<Category> updateCategory(@Header("Authorization") String token, @Body Category category);

    @GET("user")
    Call<ResponseDataSimple<User>> getUser(@Header("Authorization") String token);

    @PUT("user")
    Call<ResponseDataSimple<String>> updateUser(@Header("Authorization") String token, @Body User user);

}
