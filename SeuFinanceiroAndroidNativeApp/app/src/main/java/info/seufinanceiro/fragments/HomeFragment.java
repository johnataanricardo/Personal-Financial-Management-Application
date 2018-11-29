package info.seufinanceiro.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.seufinanceiro.R;
import info.seufinanceiro.model.Category;
import info.seufinanceiro.model.Movement;
import info.seufinanceiro.service.HttpClientService;
import info.seufinanceiro.service.HttpClientServiceCreator;
import info.seufinanceiro.service.SharedPreferencesService;
import info.seufinanceiro.utils.ResponseData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private ContentTab listener;
    private Context mContext;
    private ProgressDialog progressDialog;
    private String tipoDespesa;
    private String descricao;
    private String valor;
    private Integer month;
    private String monthString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home_layout, null);
        FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = onCreateDialogSingleChoice();
                dialog.show();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Integer month = bundle.getInt("tab");
            listener.setContentTab(month);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof ContentTab) {
            listener = (ContentTab) context;
        } else {
            throw new ClassCastException();
        }
    }

    public Dialog onCreateDialogSingleChoice() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final CharSequence[] array = {"Entrada", "Saída"};
        final EditText input = new EditText(mContext);
        tipoDespesa = "ENTRADA";

        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        builder.setTitle("Escolha o tipo de transação e insira o valor")
                .setView(input)
                .setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tipoDespesa = array[which].toString().toUpperCase()
                                .replace("Í","I");
                    }
                })

                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        valor = Double.valueOf(input.getText().toString()).toString();
                        dialog.dismiss();
                        progressDialog = new ProgressDialog(getContext(),
                                R.style.AppCompatAlertDialogStyle);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Carregando categorias...");
                        progressDialog.show();
                        fillDialogPick();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    public void fillDialogPick() {
        SharedPreferencesService preferences = new SharedPreferencesService(getContext());
        String token = preferences.getToken();
        HttpClientService service = HttpClientServiceCreator.createService(HttpClientService.class);
        Call<ResponseData<Category>> call = service.getAllCategories("Bearer " + token);

        call.enqueue(new Callback<ResponseData<Category>>() {
            @Override
            public void onResponse(@NonNull Call<ResponseData<Category>> call,
                                   @NonNull Response<ResponseData<Category>> response) {
                if (response.isSuccessful()) {
                    final List<Category> categories = response.body().getData();

                    if (categories.isEmpty()) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Não há categorias cadastradas",
                                Toast.LENGTH_LONG).show();
                    } else {
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        Dialog pickerDialog = onCreateDialogPick(categories);
                                        progressDialog.dismiss();
                                        pickerDialog.show();

                                    }
                                }, 3000);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseData<Category>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Ops! Algo deu errado...", Toast.LENGTH_LONG)
                        .show();
            }

        });

    }

    public Dialog onCreateDialogPick(List<Category> categories) {
        ArrayList<String> list = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        for(Category category : categories) {
            list.add(category.getDescricao());
        }

        final CharSequence[] array = list.toArray(new CharSequence[list.size()]);

        builder.setTitle("Escolha a categoria")
                .setItems(array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        descricao = array[which].toString();
                        Dialog monthDialog = onCreateDialogMonthChoice();
                        monthDialog.show();
                    }
                })

                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    public Dialog onCreateDialogMonthChoice() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final CharSequence[] array = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        month = new Date().getMonth();

        builder.setTitle("Escolha o mês")
                .setSingleChoiceItems(array, month, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        month = which;
                    }
                })

                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        progressDialog = new ProgressDialog(getContext(),
                                R.style.AppCompatAlertDialogStyle);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Salvando...");
                        progressDialog.show();
                        saveMovement();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }


    public void saveMovement(){
        HttpClientService service = HttpClientServiceCreator.createService(HttpClientService.class);
        SharedPreferencesService preferences = new SharedPreferencesService(getContext());
        String token = preferences.getToken();
        Movement movement = new Movement();
        movement.setAno("2018");
        movement.setDescricao(descricao);
        movement.setMes(month.toString());
        movement.setTipoDespesa(tipoDespesa);
        movement.setValor(valor);

        Call<Movement> call = service.saveMovement("Bearer " + token, movement);

        call.enqueue(new Callback<Movement>() {
            @Override
            public void onResponse(@NonNull Call<Movement> call, @NonNull Response<Movement> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Bundle bundle = getArguments();

                    if (bundle != null) {
                        Integer month = bundle.getInt("tab");
                        listener.setContentTab(month);
                    }

                    Toast.makeText(getContext(), "Lançamento salvo com sucesso",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movement> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Ops! Algo deu errado...",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public interface ContentTab {
        void setContentTab(Integer tab);
    }

}