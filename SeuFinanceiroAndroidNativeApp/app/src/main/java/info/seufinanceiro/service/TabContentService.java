package info.seufinanceiro.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.seufinanceiro.R;
import info.seufinanceiro.model.MovementData;
import info.seufinanceiro.model.MovementResponse;
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
        Call<MovementData> call = service.getMovements(String.format(
                "Bearer %s", preferences.getToken()));

        call.enqueue(new Callback<MovementData>() {
            @Override
            public void onResponse(@NonNull Call<MovementData> call,
                                   @NonNull Response<MovementData> response) {
                if (response.isSuccessful()) {

                    final MovementData movementData = response.body();

                    if (movementData != null) {
                        final ArrayList<MovementResponse> monthMovements = new ArrayList<>();

                        for (MovementResponse movement : movementData.getMovementResponses()) {
                            if (movement.getMes().equals(month.toString())) {
                                monthMovements.add(movement);
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

    private void setInputCardViewContent(ArrayList<MovementResponse> movements) {
        TextView titleInputCard = view.findViewById(R.id.title_input_card);
        titleInputCard.setText(String.format("Entradas: R$%s", '0'));

        TextView inputsType = view.findViewById(R.id.inputs_type);
        inputsType.setText("Salário" +
                "\n" + "Rendimentos" +
                "\n" + "Freelancer");

        TextView inputsValue = view.findViewById(R.id.inputs_value);
        inputsValue.setText("R$: 2.000,00" +
                "\n" + "R$: 120,00" +
                "\n" + "R$: 1.000,00");
    }

    private void setOutputCardViewContent(ArrayList<MovementResponse> movements) {
        TextView titleInputCard = view.findViewById(R.id.title_output_card);
        titleInputCard.setText("Saídas R$: 1.290,34");

        TextView outputsType = view.findViewById(R.id.outputs_type);
        outputsType.setText("Inglês" +
                "\n" + "Faculdade" +
                "\n" + "Almoço" +
                "\n" + "Tênis" +
                "\n" + "Calça");

        TextView outputsValue = view.findViewById(R.id.outputs_value);
        outputsValue.setText("R$: 300,00" +
                "\n" + "R$: 650,00" +
                "\n" + "R$: 20,34" +
                "\n" + "R$: 200,00" +
                "\n" + "R$: 120,00");
    }

    private void setCashFlowCardViewContent(ArrayList<MovementResponse> movements) {
        TextView titleInputCard = view.findViewById(R.id.cash_flow);
        titleInputCard.setText("Fluxo de Caixa R$: 1.829,66");
    }

    private void onCallFailed(String message) {
        if (message.equals("")) {
            message = "Ops! Algo deu errado...";
        }

        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
