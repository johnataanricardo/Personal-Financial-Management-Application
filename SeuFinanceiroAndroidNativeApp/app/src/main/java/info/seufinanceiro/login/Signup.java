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
import info.seufinanceiro.model.SignUp;
import info.seufinanceiro.service.HttpClientService;
import info.seufinanceiro.service.HttpClientServiceCreator;
import info.seufinanceiro.service.SharedPreferencesService;
import info.seufinanceiro.utils.SoftKeyboardUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {

    private EditText nameText;
    private EditText emailText;
    private EditText passwordText;
    private EditText reEnterPasswordText;
    private Button signupButton;
    private ProgressDialog progressDialog;
    private SharedPreferencesService preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailText = findViewById(R.id.input_email);
        nameText = findViewById(R.id.input_name);
        passwordText = findViewById(R.id.input_password);
        reEnterPasswordText = findViewById(R.id.input_reEnterPassword);
        signupButton = findViewById(R.id.btn_signup);
        TextView loginLink = findViewById(R.id.link_login);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftKeyboardUtils.hideKeyboardFrom(getBaseContext(), v);
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        progressDialog = new ProgressDialog(Signup.this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Criando conta...");
        progressDialog.show();

        if (!validate()) {
            onSignupFailed("Dados inválidos");
            return;
        }

        signupButton.setEnabled(false);
        makeCall();
    }

    private void makeCall() {
        HttpClientService service = HttpClientServiceCreator.createService(HttpClientService.class);

        SignUp signUp = new SignUp();

        signUp.setEmail(emailText.getText().toString());
        signUp.setPassword(passwordText.getText().toString());
        signUp.setName(nameText.getText().toString());
        Call<SignUp> call = service.signup(signUp);

        call.enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(@NonNull Call<SignUp> call, @NonNull Response<SignUp> response) {
                if (response.isSuccessful()) {

                    SignUp signupResponse = response.body();
                    String token;

                    try {
                        token = signupResponse.getData().getToken();
                    } catch (NullPointerException e) {
                        token = "";
                    }

                    if (token != null) {
                        preferences = new SharedPreferencesService(getApplicationContext());
                        preferences.writeToken(token);

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        onSignupSuccess();
                                    }
                                }, 3000);
                    } else {
                        onSignupFailed("");
                    }

                } else {
                    String message = "";

                    if (response.code() == 400) {
                        message = "Usuário já cadastrado";
                    }

                    onSignupFailed(message);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUp> call, @NonNull Throwable t) {
                onSignupFailed("");
            }
        });
    }


    public void onSignupSuccess() {
        progressDialog.dismiss();
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed(String message) {
        progressDialog.dismiss();
        if (message.equals("")) message = "Ops! Algo deu errado...";
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("O nome deve ter no mínimo 3 caracteres");
            valid = false;
        } else {
            nameText.setError(null);
        }

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

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 ||
                reEnterPassword.length() > 16 || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError("Senhas não conferem");
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }

        return valid;
    }

}
