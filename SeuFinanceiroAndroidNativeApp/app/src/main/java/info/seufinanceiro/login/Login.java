package info.seufinanceiro.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import info.seufinanceiro.R;
import info.seufinanceiro.main.MainActivity;
import info.seufinanceiro.model.Auth;
import info.seufinanceiro.model.User;
import info.seufinanceiro.service.HttpClientService;
import info.seufinanceiro.service.HttpClientServiceCreator;
import info.seufinanceiro.service.SharedPreferencesService;
import info.seufinanceiro.utils.ResponseDataSimple;
import info.seufinanceiro.utils.SoftKeyboardUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;
    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;
    private ProgressDialog progressDialog;
    private SharedPreferencesService preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        TextView signupLink = findViewById(R.id.link_signup);


        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SoftKeyboardUtils.hideKeyboardFrom(getBaseContext(), v);
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        progressDialog = new ProgressDialog(Login.this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        if (!validate()) {
            onLoginFailed("E-mail ou senha inválida");
            return;
        }

        loginButton.setEnabled(false);
        makeCall();
    }

    private void makeCall() {
        HttpClientService service = HttpClientServiceCreator.createService(HttpClientService.class);

        Auth auth = new Auth();

        auth.setEmail(emailText.getText().toString());
        auth.setPassword(passwordText.getText().toString());
        Call<Auth> call = service.authorize(auth);

        call.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(@NonNull Call<Auth> call, @NonNull Response<Auth> response) {
                if (response.isSuccessful()) {

                    Auth authResponse = response.body();
                    String token;

                    try {
                        token = authResponse.getData().getToken();
                    } catch (NullPointerException e) {
                        token = "";
                    }

                    if (token != null) {
                        preferences = new SharedPreferencesService(getApplicationContext());
                        preferences.writeToken(token);

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        onLoginSuccess();

                                    }
                                }, 3000);
                    } else {
                        onLoginFailed("");
                    }

                } else {
                    String message = "";

                    if (response.code() == 401) {
                        message = "Usuário não encontrado ou senha incorreta";
                    }

                    onLoginFailed(message);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Auth> call, @NonNull Throwable t) {
                onLoginFailed("");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        progressDialog.dismiss();
        loginButton.setEnabled(true);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void onLoginFailed(String message) {
        progressDialog.dismiss();
        if (message.equals("")) message = "Ops! Algo deu errado...";
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Insira um e-mail válido");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 16) {
            passwordText.setError("Entre 4 e 16 caracteres");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}