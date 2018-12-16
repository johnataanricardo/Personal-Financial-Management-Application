package com.fean.seufinanceiro.dtos;

import java.util.ArrayList;
import java.util.List;

public class HomeDto {

    private List<TransactionDto> input;
    private List<TransactionDto> ouput;
    private Double totalInput;
    private Double totalOutput;
    private Double cashFlow;

    public HomeDto() {
    }

    public HomeDto(List<TransactionDto> input,
                   List<TransactionDto> ouput,
                   Double totalInput,
                   Double totalOutput, Double cashFlow) {
        this.input = input;
        this.ouput = ouput;
        this.totalInput = totalInput;
        this.totalOutput = totalOutput;
        this.cashFlow = cashFlow;
    }

    public List<TransactionDto> getInput() {
        if (this.input == null)
            this.input = new ArrayList<>();
        return input;
    }

    public List<TransactionDto> getOuput() {
        if (this.ouput == null)
            this.ouput = new ArrayList<>();
        return ouput;
    }

    public void setInput(List<TransactionDto> input) {
        this.input = input;
    }

    public void setOuput(List<TransactionDto> ouput) {
        this.ouput = ouput;
    }

    public Double getTotalInput() {
        if (this.totalInput == null)
            this.totalInput = 0.0;
        return totalInput;
    }

    public void setTotalInput(Double totalInput) {
        if (this.totalOutput == null)
            this.totalOutput = 0.0;
        this.totalInput = totalInput;
    }

    public Double getTotalOutput() {
        if (this.totalOutput == null)
            this.totalOutput = 0.0;
        return totalOutput;
    }

    public void setTotalOutput(Double totalOutput) {
        if (this.totalInput == null)
            this.totalInput = 0.0;
        this.totalOutput = totalOutput;
    }

    public Double getCashFlow() {
        return cashFlow;
    }

    public void setCashFlow(Double cashFlow) {
        this.cashFlow = cashFlow;
    }

}
