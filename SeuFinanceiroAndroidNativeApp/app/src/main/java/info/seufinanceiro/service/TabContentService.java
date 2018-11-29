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
import info.seufinanceiro.model.Enums.MovementType.TipoDespesa;
import info.seufinanceiro.model.Movement;
import info.seufinanceiro.utils.ResponseData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabContentService extends View {
    HttpClientService service = HttpClientServiceCreator.createService(HttpClientService.class);
    private SharedPreferencesService preferences = new SharedPreferencesService(getContext());
    private View view;

    public TabContentService(Context context, View view) {
        super(context);
        this.view = view;
    }

    public void setContentTab(final Integer month) {
        Call<ResponseData<Movement>> call = service.getMovements(String.format(
                "Bearer %s", preferences.getToken()));

        call.enqueue(new Callback<ResponseData<Movement>>() {
            @Override
            public void onResponse(@NonNull Call<ResponseData<Movement>> call,
                                   @NonNull Response<ResponseData<Movement>> response) {
                if (response.isSuccessful()) {

                    final ResponseData responseData = response.body();

                    if (responseData != null) {
                        final ArrayList<Movement> monthMovements = new ArrayList<>();

                        for (Object movement : responseData.getData()) {
                            if (((Movement) movement).getMes().equals(month.toString())) {
                                monthMovements.add((Movement) movement);
                            }
                        }

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        setInputCardViewContent(monthMovements);
                                        setOutputCardViewContent(monthMovements);
                                        setCashFlowCardViewContent(monthMovements);
                                    }
                                }, 3000);
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

    private void setInputCardViewContent(List<Movement> movements) {
        Map<String, Double> valueByCategories = new HashMap<>();
        Double total = 0.0;

        for (Movement movement : movements) {
            String category = movement.getDescricao();
            Double value = Double.valueOf(movement.getValor());

            if (movement.getTipoDespesa().equals(TipoDespesa.ENTRADA.toString())) {
                if (valueByCategories.containsKey(category)) {
                    Double newValue = valueByCategories.get(category) + value;
                    valueByCategories.put(category, newValue);
                } else {
                    valueByCategories.put(category, value);
                }

                total += value;
            }
        }

        StringBuilder categories  = new StringBuilder("");
        StringBuilder values = new StringBuilder("");

        for(String categoryName : valueByCategories.keySet()) {
            if(categories.toString().equals("")){
                categories.append(String.format("%s", categoryName));
            } else {
                categories.append(String.format("\n%s", categoryName));
            }

            if(values.toString().equals("")){
                values.append(String.format("R$ %.02f", valueByCategories.get(categoryName)));
            } else {
                values.append(String.format("\nR$ %.02f", valueByCategories.get(categoryName)));
            }
        }

        if(values.toString().equals("")){
            categories.append("Não há entradas cadastradas");
        }

        TextView titleInputCard = view.findViewById(R.id.title_input_card);
            titleInputCard.setText(String.format("Entradas: R$ %.02f", total));

        TextView inputsType = view.findViewById(R.id.inputs_type);
        inputsType.setText(categories);

        TextView inputsValue = view.findViewById(R.id.inputs_value);
        inputsValue.setText(values.toString());
    }

    private void setOutputCardViewContent(List<Movement> movements) {
        Map<String, Double> valueByCategories = new HashMap<>();
        Double total = 0.0;

        for (Movement movement : movements) {
            String category = movement.getDescricao();
            Double value = Double.valueOf(movement.getValor());

            if (movement.getTipoDespesa().equals(TipoDespesa.SAIDA.toString())) {
                if (valueByCategories.containsKey(category)) {
                    Double newValue = valueByCategories.get(category) + value;
                    valueByCategories.put(category, newValue);
                } else {
                    valueByCategories.put(category, value);
                }

                total += value;
            }
        }

        StringBuilder categories  = new StringBuilder("");
        StringBuilder values = new StringBuilder("");

        for(String categoryName : valueByCategories.keySet()) {
            if(categories.toString().equals("")){
                categories.append(String.format("%s", categoryName));
            } else {
                categories.append(String.format("\n%s", categoryName));
            }

            if(values.toString().equals("")){
                values.append(String.format("R$ %.02f", valueByCategories.get(categoryName)));
            } else {
                values.append(String.format("\nR$ %.02f", valueByCategories.get(categoryName)));
            }
        }

        if(values.toString().equals("")){
            categories.append("Não há saídas cadastradas");
        }

        TextView titleInputCard = view.findViewById(R.id.title_output_card);
        titleInputCard.setText(String.format("Saídas: R$ %.02f", total));

        TextView inputsType = view.findViewById(R.id.outputs_type);
        inputsType.setText(categories);

        TextView inputsValue = view.findViewById(R.id.outputs_value);
        inputsValue.setText(values.toString());
    }

    private void setCashFlowCardViewContent(ArrayList<Movement> movements) {
        Double totalInput = 0.0;
        Double totalOutput = 0.0;

        for (Movement movement : movements) {
            Double value = Double.valueOf(movement.getValor());

            if (movement.getTipoDespesa().equals(TipoDespesa.ENTRADA.toString())) {
                totalInput+=value;
            } else if (movement.getTipoDespesa().equals(TipoDespesa.SAIDA.toString())) {
                totalOutput+=value;
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
