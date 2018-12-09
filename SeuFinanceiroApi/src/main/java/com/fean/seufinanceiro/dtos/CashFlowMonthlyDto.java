package com.fean.seufinanceiro.dtos;

public class CashFlowMonthlyDto {

    private Integer month;
    private Double cashFlow;

    public CashFlowMonthlyDto() {
    }

    public CashFlowMonthlyDto(Integer month) {
        this.month = month;
    }

    public Integer getMonth() {
        return month;
    }

    public Double getCashFlow() {
        if (this.cashFlow == null)
            this.cashFlow = 0.0;
        return cashFlow;
    }

    public void setCashFlow(Double cashFlow) {
        this.cashFlow = cashFlow;
    }

}
