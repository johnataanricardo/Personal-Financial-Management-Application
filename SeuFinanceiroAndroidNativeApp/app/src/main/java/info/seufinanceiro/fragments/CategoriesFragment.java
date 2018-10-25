package info.seufinanceiro.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import info.seufinanceiro.R;
import info.seufinanceiro.adapter.CategoriaAdapter;
import info.seufinanceiro.main.MainActivity;
import info.seufinanceiro.model.Categoria;
import info.seufinanceiro.model.ListDataCategoria;

public class CategoriesFragment extends Fragment{


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.categories_layout, null);

        ListView listView =  view.findViewById(R.id.list_view_categories);

        ArrayList<Categoria> categorias = ListDataCategoria.getList();

        CategoriaAdapter categoriaAdapter = new CategoriaAdapter(view.getContext(), categorias);

        listView.setAdapter(categoriaAdapter);

        final Button mShowDialog = (Button) view.findViewById(R.id.novo);

        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                View mView = getLayoutInflater().inflate(R.layout.categories_register, null);

                final EditText mNomeCategoria = (EditText) mView.findViewById(R.id.input_name_category);
                Button mSalvar = (Button) mView.findViewById(R.id.btn_save_category);

                mSalvar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Criar uma nova linha noo activity categories_layout.

                        Toast.makeText(inflater.getContext(),
                                      "CHEGOU AQUI!!",
                                      Toast.LENGTH_SHORT).show();
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
        return view;
    }

}
