package info.seufinanceiro.service;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import info.seufinanceiro.R;

public class TabContentService extends View {

    private View view;

    public TabContentService(Context context, View view) {
        super(context);
        this.view = view;
    }

    public void setContentTab(Integer month) {
        setInputCardViewContent(month);
        setOutputCardViewContent(month);
        setCashFlowCardViewContent(month);
    }

    private void setInputCardViewContent(Integer month) {
        TextView titleInputCard = view.findViewById(R.id.title_input_card);
        titleInputCard.setText("Entrada R$: 3.120,00");

        TextView inputsType = view.findViewById(R.id.inputs_type);
        inputsType.setText("Salário" +
                "\n" + "Rendimentos" +
                "\n" + "Freelancer");

        TextView inputsValue = view.findViewById(R.id.inputs_value);
        inputsValue.setText("R$: 2.000,00" +
                "\n" + "R$: 120,00" +
                "\n" + "R$: 1.000,00");
    }

    private void setOutputCardViewContent(Integer month) {
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

    private void setCashFlowCardViewContent(Integer month) {
        TextView titleInputCard = view.findViewById(R.id.cash_flow);
        titleInputCard.setText("Fluxo de Caixa R$: 1.829,66");
    }

}
