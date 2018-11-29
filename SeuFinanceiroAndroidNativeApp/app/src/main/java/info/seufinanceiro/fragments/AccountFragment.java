package info.seufinanceiro.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import info.seufinanceiro.R;
import info.seufinanceiro.model.Token;
import info.seufinanceiro.model.User;
import info.seufinanceiro.service.HttpClientService;
import info.seufinanceiro.service.HttpClientServiceCreator;
import info.seufinanceiro.service.SharedPreferencesService;
import info.seufinanceiro.utils.ResponseDataSimple;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    private LayoutInflater layoutInflater;

    private EditText txtEditName;
    private EditText txtEditEmail;
    private EditText txtEditPassword;
    private EditText textEditRePassword;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.account_layout, null);

        layoutInflater = inflater;

        SharedPreferencesService sharedPreferencesService
                = new SharedPreferencesService(layoutInflater.getContext());

        final Token token = new Token( sharedPreferencesService.getToken());

        final HttpClientService service = HttpClientServiceCreator
                                                            .createService(HttpClientService.class);

        populateUser(view, service, token);


        final Button buttonUpdateUser = (Button) view.findViewById(R.id.btn_signup);

        buttonUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser(container.getRootView(),service, token);
            }
        });


        return view;
    }


    private void updateUser(final View view, HttpClientService service, Token token){

        User user = new User();

        txtEditName = view.findViewById(R.id.input_name);
        txtEditEmail = view.findViewById(R.id.input_email);
        txtEditPassword = view.findViewById(R.id.input_password);
        textEditRePassword = view.findViewById(R.id.input_reEnterPassword);

        String name = txtEditName.getText().toString();
        String email = txtEditEmail.getText().toString();
        String password = txtEditPassword.getText().toString();
        String repassword = textEditRePassword.getText().toString();

        user.setNome(name);
        user.setEmail(email);

            if (password.equals(repassword)){
                user.setSenha(password);

                Call<ResponseDataSimple<String>> call =
                        service.updateUser("Bearer " + token.getToken(),user);

                call.enqueue(new Callback<ResponseDataSimple<String>>() {

                    @Override
                    public void onResponse(Call<ResponseDataSimple<String>> call,
                                           Response<ResponseDataSimple<String>> response) {
                        if (response.isSuccessful()){

                            TextView txtNameView = view.findViewById(R.id.user_name);
                            txtNameView.setText(txtEditName.getText());
                            txtEditPassword.getText().clear();
                            textEditRePassword.getText().clear();

                            Toast.makeText(view.getContext(),
                                    "Usuário atualizado com sucesso!!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseDataSimple<String>> call, Throwable t) {

                    }
                });

            }else {
                Toast.makeText(view.getContext(),
                        "As senhas não são iguais!!!",
                        Toast.LENGTH_SHORT).show();
            }
        }

    private void populateUser(final View view, HttpClientService service, Token token){

        Call<ResponseDataSimple<User>> call = service.getUser("Bearer " + token.getToken());

        call.enqueue(new Callback<ResponseDataSimple<User>>() {

            @Override
            public void onResponse(@NonNull Call<ResponseDataSimple<User>> call,
                                   @NonNull Response<ResponseDataSimple<User>> response) {

                if (response.isSuccessful()) {
                    ResponseDataSimple<User> userResponse = response.body();

                    TextView txtViewName = view.findViewById(R.id.input_name);

                    txtViewName.setText(userResponse.getData().getNome());

                    TextView txtViewEmail = view.findViewById(R.id  .input_email);
                    txtViewEmail.setText(userResponse.getData().getEmail());

                    txtViewEmail.setEnabled(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseDataSimple<User>> call, @NonNull Throwable t) {
            }
        });

    }


}
