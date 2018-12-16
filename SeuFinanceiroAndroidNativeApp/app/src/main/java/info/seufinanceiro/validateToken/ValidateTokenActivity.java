package info.seufinanceiro.validateToken;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import info.seufinanceiro.login.Login;
import info.seufinanceiro.model.Token;
import info.seufinanceiro.service.HttpClientService;
import info.seufinanceiro.service.HttpClientServiceCreator;
import info.seufinanceiro.service.SharedPreferencesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidateTokenActivity extends Activity {

    public void validateToken(final @Nullable Class next) {
        SharedPreferencesService preferences = new SharedPreferencesService(this);
        Token token = new Token(preferences.getToken());
        HttpClientService service = HttpClientServiceCreator.createService(HttpClientService.class);
        Call<Token> call = service.validate(token);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                if (response.isSuccessful()) {
                    final Token tokenResponse = response.body();
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    if (!tokenResponse.getData()) {
                                        headNext(Login.class);
                                    } else if (next != null) {
                                        headNext(next);
                                    }
                                }
                            }, 3000);
                } else {
                    headNext(Login.class);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
                headNext(Login.class);
            }
        });
    }

    public void headNext(Class next) {
        Intent i = new Intent(this, next);
        startActivity(i);
        finish();
    }

}
