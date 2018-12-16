package info.seufinanceiro.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import info.seufinanceiro.R;
import info.seufinanceiro.model.Category;
import info.seufinanceiro.model.enums.converter.TypeTransactionConverter;
import info.seufinanceiro.model.Transaction;
import info.seufinanceiro.service.HttpClientService;
import info.seufinanceiro.service.HttpClientServiceCreator;
import info.seufinanceiro.service.SharedPreferencesService;
import info.seufinanceiro.utils.ResponseData;
import info.seufinanceiro.utils.ToastMessageUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {

    private ContentTab listener;
    private Context mContext;
    private ProgressDialog progressDialog;
    private String typeTransaction;
    private Category category;
    private String valor;
    private TabLayout tabLayout;
    private String token;
    private HttpClientService service;
    private LayoutInflater layoutInflater;

    public HomeFragment(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home_layout, null);
        layoutInflater = inflater;
        SharedPreferencesService preferences = new SharedPreferencesService(inflater.getContext());
        token = preferences.getToken();

        service = HttpClientServiceCreator.createService(HttpClientService.class);

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
        typeTransaction = "ENTRADA";

        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        builder.setTitle("Escolha o tipo de transação e insira o valor")
                .setView(input)
                .setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        typeTransaction = array[which].toString().toUpperCase().replace("Í", "I");
                    }
                })
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        valor = Double.valueOf(input.getText().toString()).toString();
                        dialog.dismiss();
                        progressDialog = new ProgressDialog(getContext(), R.style.AppCompatAlertDialogStyle);
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
        Call<ResponseData<Category>> call = service.getAllCategories("Bearer " + token);
        call.enqueue(new Callback<ResponseData<Category>>() {
            @Override
            public void onResponse(@NonNull Call<ResponseData<Category>> call, @NonNull Response<ResponseData<Category>> response) {
                if (response.isSuccessful()) {
                    final List<Category> categories = response.body().getData();

                    if (categories.isEmpty()) {
                        progressDialog.dismiss();
                        saveMovement();
                    } else {
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        Dialog pickerDialog = onCreateDialogPick(categories);
                                        progressDialog.dismiss();
                                        pickerDialog.show();

                                    }
                                }, 0);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseData<Category>> call, Throwable t) {
                progressDialog.dismiss();
                ToastMessageUtil.toastMessage(layoutInflater, "Ops! Algo deu errado...");
            }

        });

    }

    public Dialog onCreateDialogPick(final List<Category> categories) {
        ArrayList<String> list = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        for (Category category : categories) {
            list.add(category.getDescription());
        }

        final CharSequence[] array = list.toArray(new CharSequence[list.size()]);

        builder.setTitle("Escolha a categoria")
                .setItems(array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        category = categories.get(which);
                        progressDialog = new ProgressDialog(getContext(),
                                R.style.AppCompatAlertDialogStyle);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Salvando...");
                        progressDialog.show();
                        saveMovement();
                    }
                })

                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    public void saveMovement() {
        final Transaction transaction = new Transaction();
        transaction.setId(0L);
        transaction.setYear("2018");
        transaction.setCategoryId(category != null ? category.getId() : 0);
        transaction.setMonth(String.valueOf(tabLayout.getSelectedTabPosition()));
        transaction.setTypeTransaction(TypeTransactionConverter.convert(typeTransaction).toString());
        transaction.setValue(valor);

        Call<Transaction> call = service.saveMovement("Bearer " + token, transaction);

        call.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(@NonNull Call<Transaction> call, @NonNull Response<Transaction> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    listener.setContentTab(Integer.valueOf(transaction.getMonth()));
                    ToastMessageUtil.toastMessage(layoutInflater, "Lançamento salvo com sucesso");
                } else {
                    progressDialog.dismiss();
                    ToastMessageUtil.toastMessage(layoutInflater, "Ops! Algo deu errado...");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Transaction> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                ToastMessageUtil.toastMessage(layoutInflater, "Ops! Algo deu errado...");
            }
        });
    }

    public interface ContentTab {
        void setContentTab(Integer tab);
    }

}