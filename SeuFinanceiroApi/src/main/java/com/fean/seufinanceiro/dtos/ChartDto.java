package com.fean.seufinanceiro.dtos;

import java.util.ArrayList;
import java.util.List;

public class ChartDto {

    private List<CashFlowMonthlyDto> cashFlowMonthlyDto;

    public ChartDto() {
    }

    public ChartDto(List<CashFlowMonthlyDto> cashFlowMonthlyDto) {
        this.cashFlowMonthlyDto = cashFlowMonthlyDto;
    }

    public List<CashFlowMonthlyDto> getCashFlowMonthlyDto() {
        if (this.cashFlowMonthlyDto == null)
            this.cashFlowMonthlyDto = new ArrayList<>();
        return cashFlowMonthlyDto;
    }

}
