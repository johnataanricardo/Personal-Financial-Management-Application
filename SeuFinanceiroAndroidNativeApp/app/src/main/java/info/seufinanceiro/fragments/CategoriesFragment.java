package info.seufinanceiro.fragments;

import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import info.seufinanceiro.R;
import info.seufinanceiro.adapter.CategoryAdapter;
import info.seufinanceiro.model.Category;
import info.seufinanceiro.service.HttpClientService;
import info.seufinanceiro.service.HttpClientServiceCreator;
import info.seufinanceiro.service.SharedPreferencesService;
import info.seufinanceiro.utils.ResponseData;
import info.seufinanceiro.utils.ToastMessageUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFragment extends Fragment {

    private View view;
    private LayoutInflater layoutInflater;
    private EditText categoryName;
    private ListView listView;
    private String token;
    private HttpClientService service;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.categories_layout, null);
        layoutInflater = inflater;
        listView = view.findViewById(R.id.list_view_categories);

        SharedPreferencesService preferences = new SharedPreferencesService(layoutInflater.getContext());
        token = preferences.getToken();

        service = HttpClientServiceCreator.createService(HttpClientService.class);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Category category = (Category) listView.getItemAtPosition(position);
                final AlertDialog.Builder builder = new AlertDialog.Builder(inflater.getContext());
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteCategory(category);
                                dialog.dismiss();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                builder.setMessage("Tem certeza de que deseja deletar?").setPositiveButton("Sim", dialogClickListener)
                        .setNegativeButton("Não", dialogClickListener).show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Category category = (Category) listView.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final View dialogView = getLayoutInflater().inflate(R.layout.categories_register, null);

                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();
                categoryName = (EditText) dialogView.findViewById(R.id.input_name_category);
                categoryName.setText(category.getDescription());
                Button save = (Button) dialogView.findViewById(R.id.btn_save_category);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveCategory(category);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        populateListView();

        final FloatingActionButton showDialog = view.findViewById(R.id.newCategorie);

        showDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                final View dialogView = getLayoutInflater().inflate(R.layout.categories_register, null);

                categoryName = (EditText) dialogView.findViewById(R.id.input_name_category);
                Button save = (Button) dialogView.findViewById(R.id.btn_save_category);

                mBuilder.setView(dialogView);
                final AlertDialog dialog = mBuilder.create();

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveCategory(null);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return view;
    }

    private void populateListView() {
        Call<ResponseData<Category>> call = service.getAllCategories("Bearer " + token);
        call.enqueue(new Callback<ResponseData<Category>>() {
            @Override
            public void onResponse(Call<ResponseData<Category>> call, Response<ResponseData<Category>> response) {
                if (response.isSuccessful()) {
                    List<Category> categories = response.body().getData();
                    if (categories.size() > 0) {
                        CategoryAdapter categoriaAdapter = new CategoryAdapter(view.getContext(), categories);
                        listView.setAdapter(null);
                        listView.setAdapter(categoriaAdapter);
                    } else {
                        ToastMessageUtil.toastMessage(layoutInflater, "Não há categorias cadastradas");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseData<Category>> call, Throwable t) {
                ToastMessageUtil.toastMessage(layoutInflater, "Ops! Algo deu errado...");
            }
        });
    }

    private void saveCategory(Category category) {
        Call<Category> call = null;
        if (category == null) {
            category = new Category();
            category.setDescription(categoryName.getText().toString());
            call = service.saveCategory("Bearer " + token, category);
        } else {
            category.setDescription(categoryName.getText().toString());
            call = service.updateCategory("Bearer " + token, category);
        }
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    ToastMessageUtil.toastMessage(layoutInflater, "Categoria salva com sucesso!");
                    populateListView();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                ToastMessageUtil.toastMessage(layoutInflater, "Ops! Algo deu errado...");
            }
        });
    }

    private void deleteCategory(Category category) {
        Call<Category> call = service.deleteCategory("Bearer " + token, category.getId());
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    ToastMessageUtil.toastMessage(layoutInflater, "Categoria deletada com sucesso!");
                    populateListView();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                ToastMessageUtil.toastMessage(layoutInflater, "Ops! Algo deu errado...");
            }
        });
    }

}
