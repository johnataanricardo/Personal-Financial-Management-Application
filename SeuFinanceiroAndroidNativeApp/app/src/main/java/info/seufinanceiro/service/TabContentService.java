package info.seufinanceiro.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.seufinanceiro.R;
import info.seufinanceiro.model.enums.TypeTransaction;
import info.seufinanceiro.model.Transaction;
import info.seufinanceiro.utils.ResponseData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabContentService extends View {

    private HttpClientService service = HttpClientServiceCreator.createService(HttpClientService.class);
    private SharedPreferencesService preferences = new SharedPreferencesService(getContext());
    private View view;

    public TabContentService(Context context, View view) {
        super(context);
        this.view = view;
    }

    public void setContentTab(final Integer month) {
        Call<ResponseData<Transaction>> call = service.getMovements(String.format(
                "Bearer %s", preferences.getToken()));

        call.enqueue(new Callback<ResponseData<Transaction>>() {
            @Override
            public void onResponse(@NonNull Call<ResponseData<Transaction>> call,
                                   @NonNull Response<ResponseData<Transaction>> response) {
                if (response.isSuccessful()) {

                    final ResponseData responseData = response.body();

                    if (responseData != null) {
                        final ArrayList<Transaction> monthTransactions = new ArrayList<>();

                        for (Object movement : responseData.getData()) {
                            if (((Transaction) movement).getMonth().equals(month.toString())) {
                                monthTransactions.add((Transaction) movement);
                            }
                        }

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        setInputCardViewContent(monthTransactions);
                                        setOutputCardViewContent(monthTransactions);
                                        setCashFlowCardViewContent(monthTransactions);
                                    }
                                }, 0);
                    } else {
                        onCallFailed("");
                    }

                } else {
                    onCallFailed("");
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                onCallFailed("");
            }
        });
    }

    private void setInputCardViewContent(List<Transaction> transactions) {
        Map<String, Double> valueByCategories = new HashMap<>();
        Double total = 0.0;

        for (Transaction transaction : transactions) {
            String category = !transaction.getCategoryName().equals("") ? transaction.getCategoryName() : "Sem categoria";
            Double value = Double.valueOf(transaction.getValue());

            if (transaction.getTypeTransaction().equals(TypeTransaction.INPUT.toString())) {
                if (valueByCategories.containsKey(category)) {
                    Double newValue = valueByCategories.get(category) + value;
                    valueByCategories.put(category, newValue);
                } else {
                    valueByCategories.put(category, value);
                }

                total += value;
            }
        }

        StringBuilder categories = new StringBuilder("");
        StringBuilder values = new StringBuilder("");

        for (String categoryName : valueByCategories.keySet()) {
            if (categories.toString().equals("")) {
                categories.append(String.format("%s", categoryName));
            } else {
                categories.append(String.format("\n%s", categoryName));
            }

            if (values.toString().equals("")) {
                values.append(String.format("R$ %.02f", valueByCategories.get(categoryName)));
            } else {
                values.append(String.format("\nR$ %.02f", valueByCategories.get(categoryName)));
            }
        }

        if (values.toString().equals("")) {
            categories.append("Não há entradas cadastradas");
        }

        TextView titleInputCard = view.findViewById(R.id.title_input_card);
        titleInputCard.setText(String.format("Entradas: R$ %.02f", total));

        TextView inputsType = view.findViewById(R.id.inputs_type);
        inputsType.setText(categories);

        TextView inputsValue = view.findViewById(R.id.inputs_value);
        inputsValue.setText(values.toString());
    }

    private void setOutputCardViewContent(List<Transaction> transactions) {
        Map<String, Double> valueByCategories = new HashMap<>();
        Double total = 0.0;

        for (Transaction transaction : transactions) {
            String category = transaction.getCategoryName() != null ? transaction.getCategoryName() : "Sem categoria";
            Double value = Double.valueOf(transaction.getValue());

            if (transaction.getTypeTransaction().equals(TypeTransaction.OUTPUT.toString())) {
                if (valueByCategories.containsKey(category)) {
                    Double newValue = valueByCategories.get(category) + value;
                    valueByCategories.put(category, newValue);
                } else {
                    valueByCategories.put(category, value);
                }

                total += value;
            }
        }

        StringBuilder categories = new StringBuilder("");
        StringBuilder values = new StringBuilder("");

        for (String categoryName : valueByCategories.keySet()) {
            if (categories.toString().equals("")) {
                categories.append(String.format("%s", categoryName));
            } else {
                categories.append(String.format("\n%s", categoryName));
            }

            if (values.toString().equals("")) {
                values.append(String.format("R$ %.02f", valueByCategories.get(categoryName)));
            } else {
                values.append(String.format("\nR$ %.02f", valueByCategories.get(categoryName)));
            }
        }

        if (values.toString().equals("")) {
            categories.append("Não há saídas cadastradas");
        }

        TextView titleInputCard = view.findViewById(R.id.title_output_card);
        titleInputCard.setText(String.format("Saídas: R$ %.02f", total));

        TextView inputsType = view.findViewById(R.id.outputs_type);
        inputsType.setText(categories);

        TextView inputsValue = view.findViewById(R.id.outputs_value);
        inputsValue.setText(values.toString());
    }

    private void setCashFlowCardViewContent(ArrayList<Transaction> transactions) {
        Double totalInput = 0.0;
        Double totalOutput = 0.0;

        for (Transaction transaction : transactions) {
            Double value = Double.valueOf(transaction.getValue());

            if (transaction.getTypeTransaction().equals(TypeTransaction.INPUT.toString())) {
                totalInput += value;
            } else if (transaction.getTypeTransaction().equals(TypeTransaction.OUTPUT.toString())) {
                totalOutput += value;
            }
        }

        TextView titleInputCard = view.findViewById(R.id.cash_flow);
        titleInputCard.setText(String.format("Fluxo de Caixa R$ %.02f", totalInput - totalOutput));
    }

    private void onCallFailed(String message) {
        if (message.equals("")) {
            message = "Ops! Algo deu errado...";
        }

        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

}
