package info.seufinanceiro.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import info.seufinanceiro.R;

public class HomeFragment extends Fragment {

    private ContentTab listener;
    private Context mContext;

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
        CharSequence[] array = {"Entrada", "Saída"};
        final EditText input = new EditText(mContext);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        builder.setTitle("Escolha o tipo de transação e insira o valor")

                .setView(input)

                .setSingleChoiceItems(array, 1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })

                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog pickerDialog = onCreateDialogPick();
                        dialog.dismiss();
                        pickerDialog.show();
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

    public Dialog onCreateDialogPick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        CharSequence[] array = {"Alimentação", "Higiene", "Pet", "Roupas", "Salário"};

        builder.setTitle("Escolha a categoria")
                .setItems(array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                })

                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    public interface ContentTab {
        void setContentTab(Integer tab);
    }

}